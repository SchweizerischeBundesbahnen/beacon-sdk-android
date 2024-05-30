package ch.allianceswisspass.beaconsdk

import android.app.Notification
import android.content.Context
import android.os.Build
import ch.allianceswisspass.beaconsdk.resolver.BeaconResolver
import ch.allianceswisspass.beaconsdk.resolver.BeaconResolverImpl
import ch.allianceswisspass.beaconsdk.resolver.cache.BeaconInfoCache
import ch.allianceswisspass.beaconsdk.resolver.cache.InMemoryBeaconInfoCache
import ch.allianceswisspass.beaconsdk.scanner.BeaconScanner
import ch.allianceswisspass.beaconsdk.scanner.BeaconType
import ch.allianceswisspass.beaconsdk.scanner.FeedbacksSender
import ch.allianceswisspass.beaconsdk.scanner.altbeacon.AltBeaconBeaconScanner
import ch.allianceswisspass.beaconsdk.scanner.altbeacon.AltBeaconBeaconScannerConfig
import ch.allianceswisspass.beaconsdk.usecaseapi.UseCaseApi
import ch.allianceswisspass.beaconsdk.usecaseapi.auth.Authenticator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.altbeacon.beacon.Identifier
import org.altbeacon.beacon.Region
import java.util.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

/**
 * BeaconSDK
 *
 * @property tokenUrl The OpenID Connect token URL.
 * @property clientId The client id of the UseCase API client.
 * @property clientSecret The client secret of the UseCase API client.
 * @property scope The scope used to request a token for the UseCase API.
 * @property useCaseApiBaseUrl The base URL of the UseCase API.
 * @property platformId The platform id (Android).
 * @property platformVersion The user-visible platform version.
 * @property appId The globally unique id of the app.
 * @property appVersion The version of the app in the format <VERSION-NAME>+<VERSION-CODE>.
 * @property installationId The globally unique id of the app installation.
 * @property sessionId The unique id of application session.
 */
class BeaconSDK(
    private val tokenUrl: String = "https://login.microsoftonline.com/2cda5d11-f0ac-46b3-967d-af1b2e1bd01a/oauth2/v2.0/token",
    private val clientId: String,
    private val clientSecret: String,
    private val scope: String = "api://03e54161-9152-40c6-87b5-f0cf1579099c/.default",
    private val useCaseApiBaseUrl: String = "https://beacon.api.sbb.ch:443",
    private val platformId: String = "Android",
    private val platformVersion: String = Build.VERSION.RELEASE,
    private val appId: String,
    private val appVersion: String? = null,
    private val installationId: String,
    private val sessionId: String? = UUID.randomUUID().toString(),
) {

    /**
     * Creates a new beacon scanner.
     *
     * @param context
     * @param beaconTypes The types of beacons that should be scanned.
     * @param foregroundScanPeriod The duration of the scan cycle.
     * @param foregroundBetweenScanPeriod The duration spent not scanning between each scan cycle.
     * @param backgroundScanPeriod The duration of the scan cycle when in background.
     * @param backgroundBetweenScanPeriod The duration spent not scanning between each scan cycle when in background.
     * @param monitoringRegionId The unique id of the monitoring region.
     * @param rangingRegionId The unique id of the ranging region.
     * @param foregroundServiceNotification The notification that is shown when you want to use a foreground service.
     * @param foregroundServiceNotificationId The id of the foreground service notification.
     *
     * @return The new beacon scanner.
     */
    fun createBeaconScanner(
        context: Context,
        beaconTypes: Set<BeaconType> = BeaconType.entries.toSet(),
        foregroundScanPeriod: Duration = 1100.milliseconds,
        foregroundBetweenScanPeriod: Duration = 0.seconds,
        backgroundScanPeriod: Duration = 1100.milliseconds,
        backgroundBetweenScanPeriod: Duration = 15.minutes,
        monitoringRegionId: String = "MONITORING-REGION-${UUID.randomUUID()}",
        rangingRegionId: String = "RANGING-REGION-${UUID.randomUUID()}",
        foregroundServiceNotification: Notification? = null,
        foregroundServiceNotificationId: Int = 5634,
    ): BeaconScanner {
        val authenticator = createAuthenticator()
        val useCaseApi = createUseCaseApi(authenticator = authenticator)
        val feedbacksSender = FeedbacksSender(useCaseApi = useCaseApi)
        return AltBeaconBeaconScanner(
            context = context,
            config = AltBeaconBeaconScannerConfig(
                beaconTypes = beaconTypes,
                foregroundScanPeriod = foregroundScanPeriod.inWholeMilliseconds,
                foregroundBetweenScanPeriod = foregroundBetweenScanPeriod.inWholeMilliseconds,
                backgroundScanPeriod = backgroundScanPeriod.inWholeMilliseconds,
                backgroundBetweenScanPeriod = backgroundBetweenScanPeriod.inWholeMilliseconds,
                monitoringRegion = Region(
                    monitoringRegionId,
                    Identifier.parse("aea3e301-4bbc-4ecf-ad17-2573922a5f4f"),
                    null,
                    null,
                ),
                rangingRegion = Region(
                    rangingRegionId,
                    null,
                    null,
                    null,
                ),
                foregroundServiceNotification = foregroundServiceNotification,
                foregroundServiceNotificationId = foregroundServiceNotificationId,
            ),
            onStart = { flow ->
                CoroutineScope(context = Dispatchers.Default).launch {
                    flow.collect { scanResults ->
                        feedbacksSender.add(scanResults)
                    }
                }
            },
            onStop = {
                feedbacksSender.flush()
            },
        )
    }

    /**
     * Creates a new beacon resolver.
     *
     * @param beaconInfoCache A cache used by the resolver to avoid having to resolve scan results multiple times.
     *
     * @return The new beacon resolver.
     */
    fun createBeaconResolver(beaconInfoCache: BeaconInfoCache = InMemoryBeaconInfoCache()): BeaconResolver {
        val authenticator = createAuthenticator()
        val useCaseApi = createUseCaseApi(authenticator = authenticator)
        return BeaconResolverImpl(
            useCaseApi = useCaseApi,
            beaconInfoCache = beaconInfoCache,
        )
    }

    private fun createAuthenticator(): Authenticator {
        return Authenticator(
            tokenUrl = tokenUrl,
            clientId = clientId,
            clientSecret = clientSecret,
            scope = scope,
        )
    }

    private fun createUseCaseApi(authenticator: Authenticator): UseCaseApi {
        return UseCaseApi(
            authenticator = authenticator,
            baseUrl = useCaseApiBaseUrl,
            platformId = platformId,
            platformVersion = platformVersion,
            appId = appId,
            appVersion = appVersion,
            installationId = installationId,
            sessionId = sessionId,
        )
    }
}

package ch.allianceswisspass.beaconsdk.example.di

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import ch.allianceswisspass.beaconsdk.BeaconSDK
import ch.allianceswisspass.beaconsdk.example.BuildConfig
import ch.allianceswisspass.beaconsdk.example.Installation
import ch.allianceswisspass.beaconsdk.example.MainActivity
import ch.allianceswisspass.beaconsdk.example.R
import ch.allianceswisspass.beaconsdk.resolver.BeaconResolver
import ch.allianceswisspass.beaconsdk.scanner.BeaconScanner
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import timber.log.Timber
import java.util.*
import javax.inject.Singleton
import kotlin.time.Duration.Companion.milliseconds

const val USE_FOREGROUND_SERVICE = true
const val FOREGROUND_SERVICE_NOTIFICATION_CHANNEL_ID = "beacon-sdk-foreground-service"

@Module
@InstallIn(SingletonComponent::class)
object BeaconSDKModule {

    @Singleton
    @Provides
    fun provideBeaconSDK(@ApplicationContext context: Context): BeaconSDK {
        Timber.d(message = "Create BeaconSDK")
        return BeaconSDK(
            clientId = BuildConfig.BEACON_SDK_CLIENT_ID,
            clientSecret = BuildConfig.BEACON_SDK_CLIENT_SECRET,
            scope = BuildConfig.BEACON_SDK_SCOPE,
            appId = BuildConfig.APPLICATION_ID,
            appVersion = "${BuildConfig.VERSION_NAME}+${BuildConfig.VERSION_CODE}",
            installationId = Installation.id(context),
            sessionId = UUID.randomUUID().toString(),
            useCaseApiBaseUrl = BuildConfig.BEACON_SDK_USE_CASE_API_BASE_URL,
        )
    }
}

@Module
@InstallIn(ViewModelComponent::class)
object BeaconScannerModule {

    @Provides
    fun provideBeaconScanner(@ApplicationContext context: Context, beaconSdk: BeaconSDK): BeaconScanner {
        Timber.d(message = "Create beacon scanner")
        if (USE_FOREGROUND_SERVICE) {
            return beaconSdk.createBeaconScanner(
                context,
                backgroundScanPeriod = 1100.milliseconds,
                backgroundBetweenScanPeriod = 4900.milliseconds,
                foregroundServiceNotification = createForegroundServiceNotification(context),
            )
        }
        return beaconSdk.createBeaconScanner(context)
    }

    private fun createForegroundServiceNotification(context: Context): Notification {
        // Create the notification channel
        val channel = NotificationChannelCompat
            .Builder(FOREGROUND_SERVICE_NOTIFICATION_CHANNEL_ID, NotificationManagerCompat.IMPORTANCE_DEFAULT)
            .setName(context.getString(R.string.beacon_scanner_notification_channel_name))
            .setDescription(context.getString(R.string.beacon_scanner_notification_channel_description))
            .build()
        NotificationManagerCompat.from(context).createNotificationChannel(channel)
        // Create the notification
        val intent = Intent(context, MainActivity::class.java)
        intent.setAction(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val contentIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT + PendingIntent.FLAG_IMMUTABLE,
        )
        return NotificationCompat
            .Builder(context, FOREGROUND_SERVICE_NOTIFICATION_CHANNEL_ID)
            .setContentIntent(contentIntent)
            .setContentTitle(context.getString(R.string.beacon_scanner_notification_content_title))
            .setSmallIcon(R.drawable.ic_bluetooth)
            .setProgress(100, 50, true)
            .build()
    }
}

@Module
@InstallIn(ViewModelComponent::class)
object BeaconResolverModule {

    @Provides
    fun provideBeaconResolver(beaconSdk: BeaconSDK): BeaconResolver {
        Timber.d(message = "Create beacon resolver")
        return beaconSdk.createBeaconResolver()
    }
}

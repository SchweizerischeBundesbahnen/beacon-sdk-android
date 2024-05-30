# Beacon SDK for Android

An Android library providing APIs to interact with beacons.



## Preconditions

To use all features of this SDK, a client app must be registered on the [SBB API platform][2].



## Getting started

Create a new [BeaconSDK][900] instance.

```kotlin
val sdk = BeaconSDK(
    clientId = "<client_id>",
    clientSecret = "<client_secret>",
    appId = BuildConfig.APPLICATION_ID,
    installationId = "<installation_id>",
)
```

Here the `<client_id>` and `<client_secret>` should be replaced by the values of the client app that you registered on 
the [SBB API platform][2]. The `<installation_id>` is a globally unique identifier for an app installation. Learn about 
the latest best practices for unique identifiers [here][5] or use the [implementation in the example app][801] as a 
guide.

### Scan beacons

Use the BeaconSDK instance to create a new beacon scanner. All parameters except `context` are optional.

```kotlin
val scanner = sdk.createBeaconScanner(
    context = context,
    beaconTypes = BeaconType.entries.toSet(),
    foregroundScanPeriod = 1100.milliseconds,
    foregroundBetweenScanPeriod = 0.seconds,
    backgroundScanPeriod = 1100.milliseconds,
    backgroundBetweenScanPeriod = 15.minutes,
    monitoringRegionId = "MONITORING-REGION-${UUID.randomUUID()}",
    rangingRegionId = "RANGING-REGION-${UUID.randomUUID()}",
    foregroundServiceNotification = null,
    foregroundServiceNotificationId = 5634,
)
```

Invoke the `scanner.start()` method to start scanning. [Scan results][901] are emitted using the 
`scanner.scanResults` [flow][8]. To stop scanning invoke the `scanner.stop()` method. 

#### Background scanning

Background scanning behaves differently depending on the android version. See the blog post [here][6] for a detailed 
description of the limits.

#### Using a Foreground Service

The BeaconSDK may be configured to use a [foreground service][7] to scan for beacons. A foreground service shows a 
persistent notification while beacon scanning is running so users know scanning is taking place. To use a foreground
service set the `foregroundServiceNotification` parameter to a not null value and optionally set a custom 
`foregroundServiceNotificationId` when creating a beacon scanner.

You should also change the default background scan period and the time between scans. The scan period should be at 
least 1.1 seconds because many beacons transmit at a frequency of 1 Hz. The time between scans depends on your use 
case. Generally speaking, a larger value means a slower detection of beacons and a smaller value increases battery 
consumption. If the value is set to 0, scanning is constant. If scanning is constant you can decrease the scan period 
to get scan results faster.

The example app has [code][802] that shows how to use a foreground service.

### Resolve scan results

If an app requires not only beacons but also associated information, the beacon resolver can be used to obtain this 
data. Use the BeaconSDK instance to create a new beacon resolver. All parameters are optional.

```kotlin
val resolver = sdk.createBeaconResolver(
    beaconInfoCache = InMemoryBeaconInfoCache(),
)
```

Assume that you have a list of scan results, and you want to get associated data such as the vehicles in which the 
scanned beacons are installed. Call `resolve()` to get that information:

```kotlin
val scanResults = ...
val beaconInfos = resolver.resolve(scanResults)
```



## License

This project is licensed under [MIT](LICENSE.md).



[2]: https://developer.sbb.ch/apis/beacon
[3]: https://developer.android.com/develop/connectivity/bluetooth/bt-permissions
[4]: https://developer.android.com/develop/sensors-and-location/location/permissions#request-background-location
[5]: https://developer.android.com/training/articles/user-data-ids
[6]: http://www.davidgyoungtech.com/2017/08/07/beacon-detection-with-android-8
[7]: https://developer.android.com/develop/background-work/services/foreground-services
[8]: https://kotlinlang.org/docs/flow.html

[800]: example
[801]: example/src/main/java/ch/allianceswisspass/beaconsdk/example/Installation.kt
[802]: example/src/main/java/ch/allianceswisspass/beaconsdk/example/di/BeaconSDKModule.kt

[900]: beacon-sdk/src/main/kotlin/ch/allianceswisspass/beaconsdk/BeaconSDK.kt
[901]: beacon-sdk/src/main/kotlin/ch/allianceswisspass/beaconsdk/scanner/ScanResult.kt

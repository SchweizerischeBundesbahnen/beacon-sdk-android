package ch.allianceswisspass.beaconsdk.example

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            val tree = Timber.DebugTree()
            Timber.plant(tree)
        }
    }
}

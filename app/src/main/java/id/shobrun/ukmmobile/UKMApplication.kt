package id.shobrun.ukmmobile

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import id.shobrun.ukmmobile.di.AppComponent
import id.shobrun.ukmmobile.di.DaggerAppComponent
import timber.log.Timber

class UKMApplication : DaggerApplication() {
    private val component: AppComponent by lazy {
        DaggerAppComponent.builder()
            .application(this)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return component
    }

}
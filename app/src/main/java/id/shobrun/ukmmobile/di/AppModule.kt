package id.shobrun.ukmmobile.di

import android.app.Application
import dagger.Module
import dagger.Provides
import id.shobrun.ukmmobile.utils.SharedPref
import id.shobrun.ukmmobile.utils.Tools

@Module
class AppModule {
    @Provides
    fun provideSharedPref(application: Application) = SharedPref(application)

    @Provides
    fun provideTools(application: Application) = Tools(application)
}
package id.shobrun.ukmmobile.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import id.shobrun.ukmmobile.factory.AppViewModelsFactory

@Module
abstract class ViewModelsFactoryModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: AppViewModelsFactory): ViewModelProvider.Factory
}
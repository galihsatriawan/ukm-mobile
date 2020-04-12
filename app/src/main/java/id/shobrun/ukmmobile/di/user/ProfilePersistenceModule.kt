package id.shobrun.ukmmobile.di.user

import dagger.Module
import dagger.Provides
import id.shobrun.ukmmobile.room.AppDatabase


@Module
class ProfilePersistenceModule {
    @Provides
    fun provideProfileDao(appDatabase: AppDatabase) = appDatabase.profileDao()
}
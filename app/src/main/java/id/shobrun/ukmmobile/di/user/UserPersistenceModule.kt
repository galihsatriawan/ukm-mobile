package id.shobrun.ukmmobile.di.user

import dagger.Module
import dagger.Provides
import id.shobrun.ukmmobile.room.AppDatabase

@Module
class UserPersistenceModule {
    @Provides
    fun provideUserDao(appDatabase: AppDatabase) = appDatabase.userDao()
}
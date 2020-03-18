package id.shobrun.ukmmobile.di.user

import dagger.Module
import dagger.Provides
import id.shobrun.ukmmobile.AppExecutors
import id.shobrun.ukmmobile.api.UserApi
import id.shobrun.ukmmobile.repository.UserRepository
import id.shobrun.ukmmobile.room.UserDao

@Module
class UserRepositoryModule {

    @Provides
    fun provideUserRepository(apiService: UserApi, localDB: UserDao, appExecutors: AppExecutors) =
        UserRepository(apiService, localDB, appExecutors)
}
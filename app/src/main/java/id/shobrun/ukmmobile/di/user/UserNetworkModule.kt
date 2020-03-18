package id.shobrun.ukmmobile.di.user

import dagger.Module
import dagger.Provides
import id.shobrun.ukmmobile.api.UserApi
import retrofit2.Retrofit

@Module
class UserNetworkModule {
    @Provides
    fun provideUserApi(retrofit: Retrofit) = retrofit.create(UserApi::class.java)
}
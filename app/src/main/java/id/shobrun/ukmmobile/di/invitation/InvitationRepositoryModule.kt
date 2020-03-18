package id.shobrun.ukmmobile.di.invitation

import dagger.Module
import dagger.Provides
import id.shobrun.ukmmobile.AppExecutors
import id.shobrun.ukmmobile.api.InvitationApi
import id.shobrun.ukmmobile.repository.InvitationRepository
import id.shobrun.ukmmobile.room.InvitationDao

@Module
class InvitationRepositoryModule {
    @Provides
    fun provideInvitationRepository(
        appExecutors: AppExecutors,
        apiService: InvitationApi,
        localDB: InvitationDao
    ) = InvitationRepository(appExecutors, apiService, localDB)
}
package id.shobrun.ukmmobile.di.participant

import dagger.Module
import dagger.Provides
import id.shobrun.ukmmobile.AppExecutors
import id.shobrun.ukmmobile.api.ParticipantApi
import id.shobrun.ukmmobile.repository.ParticipantRepository
import id.shobrun.ukmmobile.room.ParticipantDao

@Module
class ParticipantRepositoryModule {

    @Provides
    fun provideParticipantRepository(
        appExecutors: AppExecutors,
        apiService: ParticipantApi,
        localDB: ParticipantDao
    ) = ParticipantRepository(appExecutors, apiService, localDB)
}
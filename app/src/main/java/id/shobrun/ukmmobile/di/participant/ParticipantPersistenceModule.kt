package id.shobrun.ukmmobile.di.participant

import dagger.Module
import dagger.Provides
import id.shobrun.ukmmobile.room.AppDatabase

@Module
class ParticipantPersistenceModule {
    @Provides
    fun provideParticipantDao(appDatabase: AppDatabase) = appDatabase.participantDao()
}
package id.shobrun.ukmmobile.di.event

import dagger.Module
import dagger.Provides
import id.shobrun.ukmmobile.room.AppDatabase

@Module
class EventPersistenceModule {
    @Provides
    fun provideEventDao(appDatabase: AppDatabase) = appDatabase.eventDao()
}
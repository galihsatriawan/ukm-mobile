package id.shobrun.ukmmobile.di.event

import dagger.Module
import dagger.Provides
import id.shobrun.ukmmobile.AppExecutors
import id.shobrun.ukmmobile.api.EventApi
import id.shobrun.ukmmobile.repository.EventRepository
import id.shobrun.ukmmobile.room.EventDao

@Module
class EventRepositoryModule {
    @Provides
    fun provideEventRepository(
        appExecutors: AppExecutors,
        apiService: EventApi,
        localDB: EventDao
    ) = EventRepository(appExecutors, apiService, localDB)
}
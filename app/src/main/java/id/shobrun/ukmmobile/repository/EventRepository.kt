package id.shobrun.ukmmobile.repository

import androidx.lifecycle.LiveData
import id.shobrun.ukmmobile.AppExecutors
import id.shobrun.ukmmobile.api.ApiResponse
import id.shobrun.ukmmobile.api.EventApi
import id.shobrun.ukmmobile.models.entity.Event
import id.shobrun.ukmmobile.models.network.EventsResponse
import id.shobrun.ukmmobile.room.EventDao
import id.shobrun.ukmmobile.transporter.EventResponseTransporter
import timber.log.Timber
import javax.inject.Inject

class EventRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val apiService: EventApi,
    private val localDB: EventDao
) {

    fun getEventDetail(id: String) = object :
        NetworkBoundRepository<Event, EventsResponse, EventResponseTransporter>(appExecutors) {
        override fun saveFetchData(items: EventsResponse) {

            if (!items.result.isNullOrEmpty()) {
                localDB.insert(items.result[0])
            }

        }

        override fun shouldFetch(data: Event?): Boolean {
            return true
        }

        override fun loadFromDb(): LiveData<Event> {
            return localDB.getDetailEvent(id)
        }

        override fun fetchService(): LiveData<ApiResponse<EventsResponse>> {
            return apiService.getDetailEvent(id)
        }

        override fun onFetchFailed(message: String?) {
            Timber.d("$message")
        }

        override fun transporter(): EventResponseTransporter {
            return EventResponseTransporter()
        }
    }.asLiveData()

    fun getMyEvents(id: Int) = object :
        NetworkBoundRepository<List<Event>, EventsResponse, EventResponseTransporter>(appExecutors) {
        override fun saveFetchData(items: EventsResponse) {

            if (!items.result.isNullOrEmpty()) {
                localDB.inserts(items.result)
            }
        }

        override fun shouldFetch(data: List<Event>?): Boolean {
            return true
        }

        override fun loadFromDb(): LiveData<List<Event>> {
            return localDB.getMyEvents(id)
        }

        override fun fetchService(): LiveData<ApiResponse<EventsResponse>> {
            Timber.d("Get Event")
            return apiService.getMyEvents(id)
        }

        override fun onFetchFailed(message: String?) {
            Timber.d("$message")
        }

        override fun transporter(): EventResponseTransporter {
            return EventResponseTransporter()
        }
    }.asLiveData()

    fun insertEvent(event: Event) = object :
        NetworkBoundRepository<Event, EventsResponse, EventResponseTransporter>(appExecutors) {
        override fun saveFetchData(items: EventsResponse) {
            if (!items.result.isNullOrEmpty()) {
                localDB.insert(items.result[0])
            } else {
                localDB.insert(event)
            }
        }

        override fun shouldFetch(data: Event?): Boolean {
            return true
        }

        override fun loadFromDb(): LiveData<Event> {
            return localDB.getDetailEvent(event.event_id)
        }

        override fun fetchService(): LiveData<ApiResponse<EventsResponse>> {
            Timber.d("Insert Event")
            val data = hashMapOf(
                "event" to event
            )
            return apiService.insertEvent(data)
        }

        override fun transporter(): EventResponseTransporter {
            return EventResponseTransporter()
        }

        override fun onFetchFailed(message: String?) {
            Timber.d("$message")
        }
    }.asLiveData()

    fun updateEvent(event: Event) = object :
        NetworkBoundRepository<Event, EventsResponse, EventResponseTransporter>(appExecutors) {
        override fun saveFetchData(items: EventsResponse) {
            if (!items.result.isNullOrEmpty()) {
                localDB.update(items.result[0])
            } else {
                localDB.update(event)
            }
        }

        override fun shouldFetch(data: Event?): Boolean {
            return true
        }

        override fun loadFromDb(): LiveData<Event> {
            return localDB.getDetailEvent(event.event_id)
        }

        override fun fetchService(): LiveData<ApiResponse<EventsResponse>> {
            Timber.d("Update Event")
            val data = hashMapOf(
                "event" to event
            )
            return apiService.updateEvent(data)
        }

        override fun transporter(): EventResponseTransporter {
            return EventResponseTransporter()
        }

        override fun onFetchFailed(message: String?) {
            Timber.d("$message")
        }
    }.asLiveData()
}
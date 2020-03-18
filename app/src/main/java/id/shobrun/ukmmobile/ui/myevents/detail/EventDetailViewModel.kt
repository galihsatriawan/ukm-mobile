package id.shobrun.ukmmobile.ui.myevents.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.google.android.gms.maps.GoogleMap
import id.shobrun.ukmmobile.models.Resource
import id.shobrun.ukmmobile.models.Status
import id.shobrun.ukmmobile.models.entity.Event
import id.shobrun.ukmmobile.models.entity.EventStatus
import id.shobrun.ukmmobile.models.network.EventsResponse
import id.shobrun.ukmmobile.repository.EventRepository
import id.shobrun.ukmmobile.utils.AbsentLiveData
import id.shobrun.ukmmobile.utils.Helper.getUniqueID
import id.shobrun.ukmmobile.utils.Helper.isValidDate
import id.shobrun.ukmmobile.utils.SharedPref
import id.shobrun.ukmmobile.utils.SharedPref.Companion.PREFS_USER_EMAIL
import id.shobrun.ukmmobile.utils.SharedPref.Companion.PREFS_USER_ID
import id.shobrun.ukmmobile.utils.SharedPref.Companion.PREFS_USER_USERNAME
import timber.log.Timber
import javax.inject.Inject

class EventDetailViewModel @Inject constructor(
    repository: EventRepository,
    val sharedPref: SharedPref
) : ViewModel() {
    private val TAG = "EventDetailViewModel"
    private val eventId = MutableLiveData<String>()
    private val event: LiveData<Resource<Event, EventsResponse>>
    private var isNewEvent: Boolean = false
    var isUpdateLocation = MutableLiveData(false)
    val loading: LiveData<Boolean>
    val loadingUpdate: LiveData<Boolean>
    val isSuccessLoad = MutableLiveData<Boolean>()
    val isSuccess= MutableLiveData<Boolean>()
    val eventIdNew = MutableLiveData<String>()
    private val _map: MutableLiveData<GoogleMap> = MutableLiveData()
    val map : LiveData<GoogleMap> = _map
    /**
     * Text Input
     */
    val eventName = MutableLiveData<String>()
    val eventDescription = MutableLiveData<String>()
    val eventDate = MutableLiveData<String>()
    val eventLocation = MutableLiveData<String>()
    val eventLinkMaps = MutableLiveData<String>()
    val eventCp = MutableLiveData<String>()
    val eventStatus = MutableLiveData<String>()
    val eventLatitude = MutableLiveData<Double>()
    val eventLongitude = MutableLiveData<Double>()

    private val _snackbarText = MutableLiveData<String>()
    val snackbarText: LiveData<String> = _snackbarText
    val eventSend = MutableLiveData<Event>()
    val eventForUpdate = MutableLiveData<Event>()
    var action = false
    val eventAction: LiveData<Resource<Event, EventsResponse>>

    init {
        event = eventId.switchMap {
            eventId.value?.let {
                repository.getEventDetail(it)
            } ?: AbsentLiveData.create()
        }
        loading = event.switchMap {
            val isLoading = it.status == Status.LOADING && !isNewEvent
            if(!isLoading)onEventLoaded(it.data)
            MutableLiveData(isLoading)
        }
        eventAction = eventSend.switchMap {
            eventSend.value?.let {
                if (isNewEvent)
                    repository.insertEvent(it)
                else
                    repository.updateEvent(it)
            } ?: AbsentLiveData.create()
        }
        loadingUpdate = eventAction.switchMap {
            val isLoading = it.status == Status.LOADING

            if (!isLoading){
                if(it.status == Status.ERROR) _snackbarText.value = "Please Check Your Connection"
                else{
                    Timber.d("Masuk Update ${eventAction.hashCode()}- ${eventSend.hashCode()} - ${event.hashCode()}")
                    if(action) _snackbarText.value = it.message ?: it.additionalData?.message
                    isSuccess.value = true
                    isNewEvent = false
                    action = false
                }
            }
            MutableLiveData(isLoading)
        }

    }

    private fun onEventLoaded(event: Event?) {
        Timber.d("$TAG eventLoad")
        eventName.value = event?.event_name
        eventDescription.value = event?.event_description
        eventDate.value = event?.event_date
        eventLinkMaps.value = event?.event_map_location
        eventLocation.value = event?.event_location
        eventCp.value = event?.event_cp

        eventLatitude.value = event?.event_latitude
        eventLongitude.value = event?.event_longitude
        isSuccessLoad.value = true
    }

    fun saveEvent() {
        Timber.d("$TAG Save")
        val currentName = eventName.value
        val currentDesc = eventDescription.value
        val currentDate = eventDate.value
        val currentLink = eventLinkMaps.value
        val currentLocation = eventLocation.value
        val currentCp = eventCp.value
        val currentLatitude = eventLatitude.value
        val currentLongitude = eventLongitude.value
        if (currentName.isNullOrEmpty() || currentDesc.isNullOrEmpty() || currentDate.isNullOrEmpty() || currentLocation.isNullOrEmpty() || currentCp.isNullOrEmpty()) {
            _snackbarText.value = "Please fill completely"
            return
        }
        /**
         * Validate date
         */
        if(isValidDate(currentDate).isNullOrEmpty()){
            _snackbarText.value = "Your date is not valid, please check again"
            return
        }
        if (isNewEvent) {
            val user_id = sharedPref.getValue(PREFS_USER_ID, -1)
            val user_username = sharedPref.getValue(PREFS_USER_USERNAME, "")
            val user_email = sharedPref.getValue(PREFS_USER_EMAIL, "")
            val eventId = getUniqueID("$user_id")
            eventIdNew.value = eventId
            val eventNew = Event(
                eventId,
                user_id,
                user_username,
                user_email,
                currentName,
                currentDesc,
                currentDate,
                currentLocation,
                currentLink,
                currentLatitude,
                currentLongitude,
                currentCp,
                EventStatus.ACTIVE.toString(),
                0,
                0
            )
            insertEvent(eventNew)
        } else {
            val eventTemp= Event(
                event.value?.data!!.event_id,
                event.value?.data!!.user_id,
                event.value?.data!!.user_username,
                event.value?.data!!.user_email,
                currentName,
                currentDesc,
                currentDate,
                currentLocation,
                currentLink,
                currentLatitude,
                currentLongitude,
                currentCp,
                event.value?.data!!.event_status,
                event.value?.data!!.participant_total,
                event.value?.data!!.participant_total
            )
            eventIdNew.value = event.value?.data!!.event_id
            Timber.d("$TAG ${eventTemp.hashCode()} - ${event.value?.data!!.hashCode()}")

            updateEvent(eventTemp)
        }
    }
    private fun insertEvent(e: Event){
        this.eventSend.value = e
        action = true
    }
    private fun updateEvent(e:Event){
        this.eventSend.value = e
        action = true
    }
    fun postEventId(id: String?) {
        Timber.d("$TAG postEvent")
        isNewEvent = id == null
        isUpdateLocation.value = isNewEvent
        if(id!=null)
            this.eventId.value = id
    }

    fun actionUpdateLocation(){
        isUpdateLocation.value = true
    }

    override fun onCleared() {
        super.onCleared()
        Timber.d("$TAG onCleared")
    }
    fun postSnackbarText(message : String){
        this._snackbarText.value = message
    }
    fun postMap(map : GoogleMap){
        this._map.value = map
    }
}
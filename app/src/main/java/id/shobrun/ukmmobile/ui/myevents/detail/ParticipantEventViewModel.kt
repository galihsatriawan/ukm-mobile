package id.shobrun.ukmmobile.ui.myevents.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import id.shobrun.ukmmobile.models.Resource
import id.shobrun.ukmmobile.models.Status
import id.shobrun.ukmmobile.models.entity.Invitation
import id.shobrun.ukmmobile.models.network.InvitationsResponse
import id.shobrun.ukmmobile.repository.InvitationRepository
import id.shobrun.ukmmobile.utils.AbsentLiveData
import id.shobrun.ukmmobile.utils.SharedPref
import id.shobrun.ukmmobile.utils.SharedPref.Companion.PREFS_USER_ID
import javax.inject.Inject

class ParticipantEventViewModel @Inject constructor(
    repository: InvitationRepository,
    sharedPref: SharedPref
) : ViewModel() {
    private var isNewEvent = false
    private var eventId: MutableLiveData<String> = MutableLiveData()
    val participantsLiveData: LiveData<Resource<List<Invitation>, InvitationsResponse>>
    val loading: LiveData<Boolean>

    init {
        participantsLiveData = eventId.switchMap {
            eventId.value?.let {
                repository.getParticipantsEvent(sharedPref.getValue(PREFS_USER_ID, -1), it)
            } ?: AbsentLiveData.create()
        }
        loading = participantsLiveData.switchMap {
            var isLoading = it.status == Status.LOADING
            MutableLiveData(isLoading)
        }
    }

    fun postEventId(id: String?) {
        isNewEvent = id == null
        eventId.postValue(id ?: "-1")
    }
}

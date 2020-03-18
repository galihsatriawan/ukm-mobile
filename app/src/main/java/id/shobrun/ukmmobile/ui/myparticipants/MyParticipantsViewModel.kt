package id.shobrun.ukmmobile.ui.myparticipants

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import id.shobrun.ukmmobile.models.Resource
import id.shobrun.ukmmobile.models.Status
import id.shobrun.ukmmobile.models.entity.Participant
import id.shobrun.ukmmobile.models.network.ParticipantsResponse
import id.shobrun.ukmmobile.repository.ParticipantRepository
import id.shobrun.ukmmobile.utils.AbsentLiveData
import id.shobrun.ukmmobile.utils.SharedPref
import id.shobrun.ukmmobile.utils.SharedPref.Companion.PREFS_USER_ID
import javax.inject.Inject

class MyParticipantsViewModel @Inject constructor(
    repository: ParticipantRepository,
    sharedPref: SharedPref
) : ViewModel() {

    private var userId: MutableLiveData<Int> = MutableLiveData()
    val participantsLiveData: LiveData<Resource<List<Participant>, ParticipantsResponse>>
    val loading: LiveData<Boolean>

    init {

        participantsLiveData = userId.switchMap {
            userId.value?.let {
                repository.getMyParticipants(it)
            } ?: AbsentLiveData.create()
        }
        loading = participantsLiveData.switchMap {
            var isLoading = it.status == Status.LOADING
            MutableLiveData(isLoading)
        }
        postUserId(sharedPref.getValue(PREFS_USER_ID, -1))
    }

    fun postUserId(id: Int) {
        userId.postValue(id)
    }
}
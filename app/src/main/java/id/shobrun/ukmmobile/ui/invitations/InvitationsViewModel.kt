package id.shobrun.ukmmobile.ui.invitations

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
import id.shobrun.ukmmobile.utils.SharedPref.Companion.PREFS_USER_EMAIL
import timber.log.Timber
import javax.inject.Inject

class InvitationsViewModel @Inject constructor(
    repository: InvitationRepository,
    sharedPref: SharedPref
) : ViewModel() {
    private val participantEmail: MutableLiveData<String> = MutableLiveData()
    val invitationsLiveData: LiveData<Resource<List<Invitation>, InvitationsResponse>>
    val loading: LiveData<Boolean>

    init {

        invitationsLiveData = participantEmail.switchMap {
            participantEmail.value?.let {
                repository.getMyInvitation(it)
            } ?: AbsentLiveData.create()
        }
        loading = invitationsLiveData.switchMap {
            var isLoading = it.status == Status.LOADING
            MutableLiveData(isLoading)
        }
        Timber.d(sharedPref.getValue(PREFS_USER_EMAIL, ""))
        postParticipantEmail(sharedPref.getValue(PREFS_USER_EMAIL, ""))
    }

    fun postParticipantEmail(email: String) {
        this.participantEmail.value = email
    }
}
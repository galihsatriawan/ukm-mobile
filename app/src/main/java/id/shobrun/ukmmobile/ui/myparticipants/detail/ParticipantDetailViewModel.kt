package id.shobrun.ukmmobile.ui.myparticipants.detail

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
import id.shobrun.ukmmobile.utils.Helper.getUniqueID
import id.shobrun.ukmmobile.utils.Helper.isValidEmail
import id.shobrun.ukmmobile.utils.SharedPref
import timber.log.Timber
import javax.inject.Inject


class ParticipantDetailViewModel @Inject constructor(
    private val repository: ParticipantRepository,
    private val sharedPref: SharedPref
) : ViewModel() {
    private var isNewParticipant = false
    private val participantId: MutableLiveData<String> = MutableLiveData()
    val participant: LiveData<Resource<Participant, ParticipantsResponse>>
    val participantName = MutableLiveData<String>()
    val participantTelp = MutableLiveData<String>()
    val participantEmail = MutableLiveData<String>()
    val participantAddress = MutableLiveData<String>()
    private val participantMutable = MutableLiveData<Participant>()
    private val participantForUpdate = MutableLiveData<Participant>()
    private val participantAction: LiveData<Resource<Participant, ParticipantsResponse>>
    val loading: LiveData<Boolean>
    val loadingUpdate: LiveData<Boolean>
    private val _snackbarText = MutableLiveData<String>()
    val snackbarText: LiveData<String> = _snackbarText

    init {
        participant = participantId.switchMap {
            participantId.value?.let {
                repository.getParticipantDetail(it)
            } ?: AbsentLiveData.create()
        }
        loading = participant.switchMap {
            val state = it.status == Status.LOADING && !isNewParticipant
            val mutable: MutableLiveData<Boolean> = MutableLiveData()
            onParticipantLoaded(it.data)
            participantForUpdate.postValue(it.data)
            mutable.postValue(state)
            mutable
        }
        participantAction = participantMutable.switchMap {
            participantMutable.value?.let {
                if (isNewParticipant) repository.insertObj(it)
                else repository.updateObj(it)
            } ?: AbsentLiveData.create()
        }
        loadingUpdate = participantAction.switchMap {
            val isLoading = it.status == Status.LOADING
            if (!isLoading){
                if(it.status == Status.ERROR) _snackbarText.value = "Please Check Your Connection"
                else{
                    isNewParticipant = false
                    participantForUpdate.postValue(it.data)
                    _snackbarText.value = it.message ?: it.additionalData?.message
                }
            }
            MutableLiveData(isLoading)
        }

    }

    private fun onParticipantLoaded(participant: Participant?) {
        participantName.value = participant?.participant_name
        participantAddress.value = participant?.participant_address
        participantEmail.value = participant?.participant_email
        participantTelp.value = participant?.participant_telp
    }

    fun postParticipantId(id: String?) {
        isNewParticipant = id == null
        this.participantId.value = id ?: "-1"
    }

    fun saveParticipant() {
        val currentName = participantName.value
        val currentAddress = participantAddress.value
        val currentEmail = participantEmail.value
        val currentTelp = participantTelp.value
        if (currentName.isNullOrEmpty() || currentAddress.isNullOrEmpty() || currentEmail.isNullOrEmpty() || currentTelp.isNullOrEmpty()) {
            /**
             * Message
             */
            Timber.d("$currentName - $currentAddress - $currentEmail - $currentTelp null edittext")
            _snackbarText.value = "Please fill completely"
            return
        }
        if(isValidEmail(currentEmail).isNullOrEmpty()) {
            _snackbarText.value = "The email is not valid"
            return
        }
        if (isNewParticipant) {
            val user_id = sharedPref.getValue(SharedPref.PREFS_USER_ID, -1)
            val user_username = sharedPref.getValue(SharedPref.PREFS_USER_USERNAME, "")
            val user_email = sharedPref.getValue(SharedPref.PREFS_USER_EMAIL, "")
            val participant = Participant(
                getUniqueID("$user_id"),
                currentName,
                currentEmail,
                user_id,
                user_username,
                user_email,
                currentTelp,
                currentAddress
            )
            insertParticipant(participant)
        } else {
            val participant = participantForUpdate.value!!
            participant.participant_name = currentName
            participant.participant_address = currentAddress
            participant.participant_email = currentEmail
            participant.participant_telp = currentTelp
            updateParticipant(participant)
        }
    }

    private fun insertParticipant(participant: Participant) {
        participantMutable.value = participant
    }

    private fun updateParticipant(participant: Participant) {
        participantMutable.value = participant
    }
}
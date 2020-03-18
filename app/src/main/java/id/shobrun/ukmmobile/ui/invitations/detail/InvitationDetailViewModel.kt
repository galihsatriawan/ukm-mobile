package id.shobrun.ukmmobile.ui.invitations.detail

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import id.shobrun.ukmmobile.models.Resource
import id.shobrun.ukmmobile.models.Status
import id.shobrun.ukmmobile.models.entity.Event
import id.shobrun.ukmmobile.models.entity.Invitation
import id.shobrun.ukmmobile.models.network.EventsResponse
import id.shobrun.ukmmobile.models.network.InvitationsResponse
import id.shobrun.ukmmobile.repository.EventRepository
import id.shobrun.ukmmobile.repository.InvitationRepository
import id.shobrun.ukmmobile.utils.AbsentLiveData
import id.shobrun.ukmmobile.utils.Helper.directionMaps
import javax.inject.Inject


class InvitationDetailViewModel @Inject constructor(
    invitationRepository: InvitationRepository,
    eventRepository: EventRepository
) : ViewModel() {
    private val invitationId = MutableLiveData<Int>()
    private val eventId = MutableLiveData<String>()
    val event: LiveData<Resource<Event, EventsResponse>>
    val invitation: LiveData<Resource<Invitation, InvitationsResponse>>
    val loadingEvent: LiveData<Boolean>
    val loadingInvitation: LiveData<Boolean>
    val actionQrCode = MutableLiveData<Boolean>()

    init {
        event = eventId.switchMap {
            eventId.value?.let {
                eventRepository.getEventDetail(it)
            } ?: AbsentLiveData.create()
        }
        loadingEvent = event.switchMap {
            var isLoading = it.status == Status.LOADING
            MutableLiveData(isLoading)
        }
        invitation = invitationId.switchMap {
            invitationId.value?.let {
                invitationRepository.getInvitationDetail(it)
            } ?: AbsentLiveData.create()
        }
        loadingInvitation = invitation.switchMap {
            var isLoading = it.status == Status.LOADING
            MutableLiveData(isLoading)
        }
    }


    fun postInvitationId(id: Int?) {
        this.invitationId.value = id ?: -1
    }

    fun postEventId(id: String?) {
        this.eventId.value = id ?: "-1"
    }

    fun directMaps(v: View) {
        event.value?.let {
            val map = directionMaps(it.data!!.event_latitude!!, it.data!!.event_longitude!!)
            v.context.startActivity(map)
        }
    }

    fun showQRCode() {
        if (actionQrCode.value == null) actionQrCode.value = true
        else actionQrCode.value = !actionQrCode.value!!

    }
}
package id.shobrun.ukmmobile.ui.myevents.detail

import androidx.lifecycle.ViewModel
import id.shobrun.ukmmobile.repository.EventRepository
import id.shobrun.ukmmobile.repository.InvitationRepository
import javax.inject.Inject

class EventDetailMainViewModel @Inject constructor(private val eventRepo:EventRepository, private  val invitationRepo: InvitationRepository): ViewModel(){

}
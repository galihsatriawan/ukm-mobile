package id.shobrun.ukmmobile.models.network

import id.shobrun.ukmmobile.models.NetworkResponseModel
import id.shobrun.ukmmobile.models.entity.Participant

class ParticipantsResponse(
    var result: List<Participant>,
    var status: String,
    var message: String
) : NetworkResponseModel
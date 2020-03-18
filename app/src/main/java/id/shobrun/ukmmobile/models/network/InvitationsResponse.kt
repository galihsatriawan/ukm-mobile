package id.shobrun.ukmmobile.models.network

import id.shobrun.ukmmobile.models.NetworkResponseModel
import id.shobrun.ukmmobile.models.entity.Invitation

class InvitationsResponse(
    var result: List<Invitation>,
    var status: String,
    var message: String
) : NetworkResponseModel
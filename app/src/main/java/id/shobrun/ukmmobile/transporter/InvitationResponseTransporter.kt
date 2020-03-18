package id.shobrun.ukmmobile.transporter

import id.shobrun.ukmmobile.models.network.InvitationsResponse

class InvitationResponseTransporter : NetworkResponseTransporter<InvitationsResponse> {
    override fun additionalData(response: InvitationsResponse): InvitationsResponse {
        response.result = ArrayList()
        return response
    }

}
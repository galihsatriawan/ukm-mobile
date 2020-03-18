package id.shobrun.ukmmobile.transporter

import id.shobrun.ukmmobile.models.network.InvitationsResponse

class InvitationParticipantResponseTransporter : NetworkResponseTransporter<InvitationsResponse> {
    override fun additionalData(response: InvitationsResponse): InvitationsResponse {
        return response
    }
}
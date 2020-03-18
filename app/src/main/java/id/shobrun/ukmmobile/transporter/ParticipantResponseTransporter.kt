package id.shobrun.ukmmobile.transporter

import id.shobrun.ukmmobile.models.network.ParticipantsResponse

class ParticipantResponseTransporter : NetworkResponseTransporter<ParticipantsResponse> {
    /**
     * Remove Data that has added to database
     */
    override fun additionalData(response: ParticipantsResponse): ParticipantsResponse {
        response.result = ArrayList()
        return response
    }

}
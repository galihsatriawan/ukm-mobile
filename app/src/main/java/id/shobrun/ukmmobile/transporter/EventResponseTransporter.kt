package id.shobrun.ukmmobile.transporter

import id.shobrun.ukmmobile.models.network.EventsResponse

class EventResponseTransporter : NetworkResponseTransporter<EventsResponse> {
    override fun additionalData(response: EventsResponse): EventsResponse {
        response.result = ArrayList()
        return response
    }

}
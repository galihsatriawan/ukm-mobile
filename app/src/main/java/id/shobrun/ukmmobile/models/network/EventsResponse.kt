package id.shobrun.ukmmobile.models.network

import id.shobrun.ukmmobile.models.NetworkResponseModel
import id.shobrun.ukmmobile.models.entity.Event

class EventsResponse(
    var result: List<Event>,
    var status: String,
    var message: String
) : NetworkResponseModel
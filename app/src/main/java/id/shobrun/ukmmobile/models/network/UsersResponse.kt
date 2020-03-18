package id.shobrun.ukmmobile.models.network

import id.shobrun.ukmmobile.models.NetworkResponseModel
import id.shobrun.ukmmobile.models.entity.User

class UsersResponse(
    var result: List<User>,
    var status: String,
    var message: String
) : NetworkResponseModel
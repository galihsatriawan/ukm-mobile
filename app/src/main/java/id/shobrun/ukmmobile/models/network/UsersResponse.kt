package id.shobrun.ukmmobile.models.network

import id.shobrun.ukmmobile.models.NetworkResponseModel
import id.shobrun.ukmmobile.models.entity.Profile
import id.shobrun.ukmmobile.models.entity.Role
import id.shobrun.ukmmobile.models.entity.User

class UsersResponse(
    var result_profile: Profile,
    var result_role: Role,
    var statuscode: Int,
    var status:String,
    var message: String
) : NetworkResponseModel
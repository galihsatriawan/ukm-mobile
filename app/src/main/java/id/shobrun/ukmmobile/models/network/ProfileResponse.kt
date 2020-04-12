package id.shobrun.ukmmobile.models.network

import id.shobrun.ukmmobile.models.NetworkResponseModel
import id.shobrun.ukmmobile.models.entity.Profile
import id.shobrun.ukmmobile.models.entity.Role

class ProfileResponse (
    var result_profile: Profile,
    var result_role: Role,
    var statuscode: Int,
    var status:String,
    var message: String
) : NetworkResponseModel
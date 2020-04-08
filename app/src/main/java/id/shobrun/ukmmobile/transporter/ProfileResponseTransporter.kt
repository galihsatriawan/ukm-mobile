package id.shobrun.ukmmobile.transporter

import id.shobrun.ukmmobile.models.network.ProfileResponse

class ProfileResponseTransporter : NetworkResponseTransporter<ProfileResponse>{
    override fun additionalData(response: ProfileResponse): ProfileResponse {
        return response
    }

}
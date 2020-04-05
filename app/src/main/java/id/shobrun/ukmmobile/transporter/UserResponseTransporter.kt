package id.shobrun.ukmmobile.transporter

import id.shobrun.ukmmobile.models.network.UsersResponse

class UserResponseTransporter : NetworkResponseTransporter<UsersResponse> {
    override fun additionalData(response: UsersResponse): UsersResponse {
        return response
    }
}
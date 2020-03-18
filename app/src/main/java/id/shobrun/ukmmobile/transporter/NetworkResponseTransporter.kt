package id.shobrun.ukmmobile.transporter

import id.shobrun.ukmmobile.models.NetworkResponseModel

interface NetworkResponseTransporter<FROM : NetworkResponseModel> {
    fun additionalData(response: FROM): FROM
}
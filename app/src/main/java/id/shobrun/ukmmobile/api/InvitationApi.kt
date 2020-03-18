package id.shobrun.ukmmobile.api

import androidx.lifecycle.LiveData
import id.shobrun.ukmmobile.models.network.InvitationsResponse
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface InvitationApi {
    @POST("invitation/myinvitation")
    @FormUrlEncoded
    fun getMyInvitation(@Field("id") email: String): LiveData<ApiResponse<InvitationsResponse>>

    @POST("invitation/status")
    @FormUrlEncoded
    fun getInvitationDetail(@Field("id") id: Int): LiveData<ApiResponse<InvitationsResponse>>

    @POST("invitation/participants")
    @FormUrlEncoded
    fun getInvitationParticipants(@Field("id") id: String): LiveData<ApiResponse<InvitationsResponse>>

    @POST("invitation/allparticipants")
    @FormUrlEncoded
    fun getInvitationAllParticipants(@Field("id") id: Int, @Field("eventId") eventId: String): LiveData<ApiResponse<InvitationsResponse>>

    @POST("invitation/update")
    fun updateInvitation(@Body data: HashMap<String, Any?>): LiveData<ApiResponse<InvitationsResponse>>

    @POST("invitation/update_participant_event")
    fun updateParticipantEvent(@Body data: HashMap<String, Any?>): LiveData<ApiResponse<InvitationsResponse>>
}
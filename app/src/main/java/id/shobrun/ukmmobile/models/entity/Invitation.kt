package id.shobrun.ukmmobile.models.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import id.shobrun.ukmmobile.room.AppDatabase.Companion.TABLE_INVITATION
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = TABLE_INVITATION)
data class Invitation(
    @PrimaryKey
    @SerializedName("ID") var invitation_id: Int?,
    @SerializedName("PARTICIPANT_ID") var participant_id: String?,
    @SerializedName("PARTICIPANT_EMAIL") var participant_email: String?,
    @SerializedName("PARTICIPANT_NAME") var participant_name: String?,
    @SerializedName("EVENT_ID") var event_id: String?,
    @SerializedName("ARRIVED_TIME") var arrived_time: String?,
    @SerializedName("STATUS") var status: String?,
    @SerializedName("EVENT_NAME") var event_name: String?,
    @SerializedName("EVENT_DATE") var event_date: String?,
    @SerializedName("INVITER_ID") var inviter_id: Int?,
    @SerializedName("INVITER") var inviter: String?,
    @SerializedName("IS_INVITED") var is_invited: Boolean?
) : Parcelable
package id.shobrun.ukmmobile.models.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import id.shobrun.ukmmobile.room.AppDatabase.Companion.TABLE_PARTICIPANT
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = TABLE_PARTICIPANT)
data class Participant(
    @PrimaryKey
    @SerializedName("PARTICIPANT_ID") var participant_id: String,
    @SerializedName("PARTICIPANT_NAME") var participant_name: String,
    @SerializedName("PARTICIPANT_EMAIL") var participant_email: String,
    @SerializedName("USER_ID") var user_id: Int?,
    @SerializedName("USER_USERNAME") var user_username: String?,
    @SerializedName("USER_EMAIL") var user_email: String?,
    @SerializedName("PARTICIPANT_TELP") var participant_telp: String?,
    @SerializedName("PARTICIPANT_ADDRESS") var participant_address: String?
) : Parcelable
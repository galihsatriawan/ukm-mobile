package id.shobrun.ukmmobile.models.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import id.shobrun.ukmmobile.room.AppDatabase.Companion.TABLE_EVENT
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = TABLE_EVENT)
data class Event(
    @PrimaryKey
    @SerializedName("EVENT_ID") var event_id: String,
    @SerializedName("USER_ID") var user_id: Int,
    @SerializedName("USER_USERNAME") var user_username: String,
    @SerializedName("USER_EMAIL") var user_email: String,
    @SerializedName("EVENT_NAME") var event_name: String,
    @SerializedName("EVENT_DESCRIPTION") var event_description: String,
    @SerializedName("EVENT_DATE") var event_date: String,
    @SerializedName("EVENT_LOCATION") var event_location: String?,
    @SerializedName("EVENT_MAP_LOCATION") var event_map_location: String?,
    @SerializedName("EVENT_LATITUDE") var event_latitude: Double?,
    @SerializedName("EVENT_LONGITUDE") var event_longitude: Double?,
    @SerializedName("EVENT_CP") var event_cp: String?,
    @SerializedName("EVENT_STATUS") var event_status: String?,
    @SerializedName("EVENT_PARTICIPANTS_TOTAL") var participant_total: Int?,
    @SerializedName("EVENT_PARTICIPANTS_ATTEND") var participant_attend: Int?
) : Parcelable
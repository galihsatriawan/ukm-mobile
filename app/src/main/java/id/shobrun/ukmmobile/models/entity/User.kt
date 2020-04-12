package id.shobrun.ukmmobile.models.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import id.shobrun.ukmmobile.room.AppDatabase.Companion.TABLE_USER
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = TABLE_USER)
data class User(
    @PrimaryKey
    var id: Int?,
    var username: String?,
    var password: String?,
    var email: String?,
    @SerializedName("profile_id")var profileId:Int?,
    @SerializedName("role_id")var roleId: Int?,
    @SerializedName("role_value")var roleValue: String?,
    @SerializedName("created_at") var createdAt : String?,
    @SerializedName("update_at") var updateAt : String?

) : Parcelable
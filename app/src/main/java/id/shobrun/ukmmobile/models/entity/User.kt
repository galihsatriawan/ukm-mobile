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
    @SerializedName("id") var user_id: Int?,
    @SerializedName("username") var user_username: String,
    @SerializedName("password") var user_password: String,
    @SerializedName("email") var user_email: String,
    @SerializedName("profile_id") var user_profile_id:Int,
    @SerializedName("role_id") var user_role_id: Int?,
    @SerializedName("role_value") var user_role_value: String,
    @SerializedName("createdAt") var createdAt : String?,
    @SerializedName("updateAt") var updateAt : String?

) : Parcelable
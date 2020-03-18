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
    @SerializedName("USER_ID") var user_id: Int?,
    @SerializedName("USER_USERNAME") var user_username: String,
    @SerializedName("USER_EMAIL") var user_email: String,
    @SerializedName("USER_NAME") var user_name: String,
    @SerializedName("USER_TELP") var user_telp: String?,
    @SerializedName("USER_ADDRESS") var user_address: String?,
    @SerializedName("USER_PASSWORD") var user_password: String,
    @SerializedName("IS_ACTIVE") var is_active: Boolean
) : Parcelable
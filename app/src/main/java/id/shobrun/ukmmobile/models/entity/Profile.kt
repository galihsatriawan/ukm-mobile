package id.shobrun.ukmmobile.models.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import id.shobrun.ukmmobile.room.AppDatabase.Companion.TABLE_PROFILE
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = TABLE_PROFILE)
data class Profile (
    @PrimaryKey
    val id : Int?,
    var firstName : String?,
    var lastName : String?,
    @SerializedName("year_generation")var yearGeneration : String?,
    var phone : String?,
    var status : Boolean = true,
    var jk: String?,
    @SerializedName("img_profile") var imgProfile : String?,
    @SerializedName("created_at") var createdAt : String?,
    @SerializedName("update_at") var updateAt : String?,
    @SerializedName("role_id")var roleId: Int?,
    var email: String?
    ) : Parcelable
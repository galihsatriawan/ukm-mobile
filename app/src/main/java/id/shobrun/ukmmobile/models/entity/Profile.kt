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
    @SerializedName("id") val profile_id : Int?,
    var firstName : String?,
    var lastName : String?,
    @SerializedName("yearGeneration") var yearGeneration : String?,
    var phone : String?,
    var status : Boolean = true,
    @SerializedName("imgProfile") var imgProfile : String?,
    @SerializedName("createdAt") var createdAt : String?,
    @SerializedName("updateAt") var updateAt : String?
    ) : Parcelable
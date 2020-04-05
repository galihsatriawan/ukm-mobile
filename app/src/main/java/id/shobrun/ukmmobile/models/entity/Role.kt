package id.shobrun.ukmmobile.models.entity

import android.os.Parcelable
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Role(
    @PrimaryKey
    var id : Int?,
    var value : String?
) : Parcelable

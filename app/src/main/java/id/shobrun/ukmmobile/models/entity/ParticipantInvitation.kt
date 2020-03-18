package id.shobrun.ukmmobile.models.entity

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import id.shobrun.ukmmobile.room.AppDatabase.Companion.TABLE_PARTICIPANT_INVITATION
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = TABLE_PARTICIPANT_INVITATION)
data class ParticipantInvitation(
    @Embedded
    val participant: Participant,
    @Embedded
    val invitation: Invitation
) : Parcelable
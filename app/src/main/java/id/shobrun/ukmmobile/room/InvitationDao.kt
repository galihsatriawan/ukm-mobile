package id.shobrun.ukmmobile.room

import androidx.lifecycle.LiveData
import androidx.room.*
import id.shobrun.ukmmobile.models.entity.Invitation
import id.shobrun.ukmmobile.room.AppDatabase.Companion.ID_EVENT
import id.shobrun.ukmmobile.room.AppDatabase.Companion.ID_INVITATION
import id.shobrun.ukmmobile.room.AppDatabase.Companion.ID_PARTICIPANT
import id.shobrun.ukmmobile.room.AppDatabase.Companion.TABLE_INVITATION
import id.shobrun.ukmmobile.room.AppDatabase.Companion.TABLE_PARTICIPANT

@Dao
interface InvitationDao {
    /**
     * CRUD Room
     */
    @Query("SELECT * FROM $TABLE_INVITATION WHERE $ID_INVITATION = :id")
    fun getDetailInvitation(id: Int): LiveData<Invitation>

    @Query("SELECT * FROM $TABLE_INVITATION WHERE PARTICIPANT_EMAIL = :email AND IS_INVITED=:isInvited")
    fun getMyInvitations(email: String, isInvited: Boolean): LiveData<List<Invitation>>

    @Query("SELECT $TABLE_INVITATION.* FROM $TABLE_INVITATION LEFT JOIN $TABLE_PARTICIPANT ON $TABLE_INVITATION.$ID_PARTICIPANT = $TABLE_PARTICIPANT.$ID_PARTICIPANT WHERE ($ID_EVENT = :idEvent) AND INVITER_ID=:userId")
    fun getInvitatationParticipants(userId: Int, idEvent: String): LiveData<List<Invitation>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(invitation: Invitation)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserts(invitations: List<Invitation>)

    @Update
    fun update(invitation: Invitation): Int

    @Query("DELETE FROM $TABLE_INVITATION WHERE $ID_INVITATION= :id")
    fun delete(id: Int)

    @Query("DELETE FROM $TABLE_INVITATION WHERE $ID_EVENT= :eventId AND INVITER_ID=:userId")
    fun deleteByEventId(userId: Int, eventId: String)

    @Query("DELETE FROM $TABLE_INVITATION")
    fun deletes()
}
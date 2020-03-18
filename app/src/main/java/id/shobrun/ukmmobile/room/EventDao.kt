package id.shobrun.ukmmobile.room

import androidx.lifecycle.LiveData
import androidx.room.*
import id.shobrun.ukmmobile.models.entity.Event
import id.shobrun.ukmmobile.room.AppDatabase.Companion.ID_EVENT
import id.shobrun.ukmmobile.room.AppDatabase.Companion.TABLE_EVENT

@Dao
interface EventDao {
    /**
     * CRUD Room
     */
    @Query("SELECT * FROM $TABLE_EVENT WHERE $ID_EVENT = :id")
    fun getDetailEvent(id: String): LiveData<Event>

    @Query("SELECT * FROM $TABLE_EVENT WHERE USER_ID = :id")
    fun getMyEvents(id: Int): LiveData<List<Event>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(event: Event)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserts(events: List<Event>)

    @Update
    fun update(event: Event): Int

    @Query("DELETE FROM $TABLE_EVENT WHERE $ID_EVENT = :id")
    fun delete(id: String)

    @Query("DELETE FROM $TABLE_EVENT")
    fun deletes()

}
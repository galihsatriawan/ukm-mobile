package id.shobrun.ukmmobile.room

import androidx.lifecycle.LiveData
import androidx.room.*
import id.shobrun.ukmmobile.models.entity.Profile
import id.shobrun.ukmmobile.room.AppDatabase.Companion.TABLE_PROFILE

@Dao
interface ProfileDao {
    @Query("SELECT * FROM $TABLE_PROFILE WHERE EMAIL = :email")
    fun getProfileDetail(email:String) : LiveData<Profile>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(profile: Profile)

    @Update
    fun update(profile: Profile): Int

    @Delete
    fun delete(profile: Profile)

    @Query("DELETE FROM $TABLE_PROFILE WHERE EMAIL = :email")
    fun deleteByEmail(email: String)
}
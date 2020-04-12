package id.shobrun.ukmmobile.room

import androidx.lifecycle.LiveData
import androidx.room.*
import id.shobrun.ukmmobile.models.entity.User
import id.shobrun.ukmmobile.room.AppDatabase.Companion.ID_USER
import id.shobrun.ukmmobile.room.AppDatabase.Companion.TABLE_USER

@Dao
interface UserDao {
    /**
     * CRUD Room
     */
    @Query("SELECT * FROM $TABLE_USER WHERE $ID_USER = :id")
    fun getDetailUser(id: Int): LiveData<User>

    @Query("SELECT * FROM $TABLE_USER WHERE USERNAME = :username AND PASSWORD = :password")
    fun getDetailUserByUsername(username: String, password: String): LiveData<List<User>>

    @Query("SELECT * FROM $TABLE_USER WHERE EMAIL = :email ")
    fun getDetailUserByEmail(email: String): LiveData<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Update
    fun update(user: User): Int

    @Query("DELETE FROM $TABLE_USER WHERE ${ID_USER}= :id")
    fun delete(id: Int)

    @Query("DELETE FROM $TABLE_USER")
    fun delete()

    @Query("DELETE FROM $TABLE_USER WHERE USERNAME = :username")
    fun deleteByEmail(username: String)

}
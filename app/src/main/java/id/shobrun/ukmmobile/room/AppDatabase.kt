package id.shobrun.ukmmobile.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import id.shobrun.ukmmobile.models.entity.*
import id.shobrun.ukmmobile.utils.DateConverter

@Database(
    entities = [Event::class, Invitation::class, Participant::class, User::class, Profile::class],
    version = 15,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDao
    abstract fun invitationDao(): InvitationDao
    abstract fun participantDao(): ParticipantDao
    abstract fun userDao(): UserDao
    abstract fun profileDao(): ProfileDao

    companion object {
        private const val
                DB_UKM = "UKM-db"

        const val TABLE_INVITATION = "INVITATION_table"
        const val ID_INVITATION = "invitation_id"

        const val TABLE_EVENT = "event_table"
        const val ID_EVENT = "event_id"

        const val TABLE_PARTICIPANT = "participant_table"
        const val ID_PARTICIPANT = "participant_id"

        const val TABLE_USER = "user_table"
        const val ID_USER = "id"

        const val TABLE_PROFILE = "profile_table"
        const val ID_PROFILE = "id"

        const val TABLE_PARTICIPANT_INVITATION = "participant_invitation_table"
        // For Singleton instantiation
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance
                ?: synchronized(this) {
                    instance
                        ?: buildDatabase(
                            context
                        ).also { instance = it }
                }
        }

        // Create and pre-populate the database.
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context, AppDatabase::class.java,
                DB_UKM
            ).allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
package id.shobrun.ukmmobile.utils

import androidx.room.TypeConverter
import java.util.*


/**
 * Type converters to allow Room to reference complex data types.
 */
class DateConverter {
    @TypeConverter
    fun calendarToDateStamp(calendar: Calendar): Long = calendar.timeInMillis

    @TypeConverter
    fun dateStampToCalendar(value: Long): Calendar =
        Calendar.getInstance().apply { timeInMillis = value }
}
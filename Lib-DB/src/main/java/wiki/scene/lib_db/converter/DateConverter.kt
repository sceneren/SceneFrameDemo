package wiki.scene.lib_db.converter

import androidx.room.TypeConverter
import java.util.*

class DateConverter {

    @TypeConverter
    fun revertDate(value: Long): Date {
        return Date(value)
    }

    @TypeConverter
    fun converterDate(value: Date?): Long {
        var newValue = value
        if (newValue == null) {
            newValue = Date()
        }
        return newValue.time
    }
}
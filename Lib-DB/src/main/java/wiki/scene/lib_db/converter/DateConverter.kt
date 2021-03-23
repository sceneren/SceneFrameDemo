package wiki.scene.lib_db.converter

import android.util.Log
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
        Log.i("DateConverter", "converterDate=" + newValue.time + "")
        return newValue.time
    }
}
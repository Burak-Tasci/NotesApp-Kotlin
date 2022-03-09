package com.tsci.notesapp.data

import androidx.room.TypeConverter
import java.util.*


class Converters {

    @TypeConverter
    fun toDate(value:Long):Date{
        //If your result always returns 1970, then comment this line
        //val date =  Date(value)

        //If your result always returns 1970, then uncomment this line
        val date = Date(value*1000L)

        return date
    }

    @TypeConverter
    fun fromDate(date:Date):Long{
        return date.time
    }

}
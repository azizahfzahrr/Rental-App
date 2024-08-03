package com.example.core.data.room.localDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.core.data.room.dao.BookingDao
import com.example.core.data.room.entity.BookingEntity

@Database(entities = [BookingEntity::class], version = 1, exportSchema = false)
abstract class BookingDatabase : RoomDatabase() {
    abstract fun bookingDao(): BookingDao

    companion object {
        @Volatile
        private var INSTANCE: BookingDatabase? = null

        fun getDatabase(context: Context): BookingDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BookingDatabase::class.java,
                    "booking_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

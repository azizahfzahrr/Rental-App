package com.example.core.data.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.core.data.room.entity.BookingEntity

@Dao
interface BookingDao {
    @Insert
    suspend fun insertBooking(booking: BookingEntity)

    @Query("SELECT * FROM bookings")
    fun getAllBookings(): LiveData<List<BookingEntity>>

    @Query("DELETE FROM bookings")
    suspend fun clearAllBookings()

    @Query("DELETE FROM bookings WHERE id = :id")
    suspend fun deleteBookingByCodeBook(id: Long)

    @Update
    suspend fun updateBooking(booking: BookingEntity)

    @Query("SELECT COUNT(*) FROM bookings")
    suspend fun getBookingCount(): Int

    @Query("DELETE FROM bookings WHERE id = :id")
    suspend fun deleteBookingById(id: Long)

    @Query("SELECT * FROM bookings WHERE id = :id LIMIT 1")
    suspend fun getBookingById(id: Long): BookingEntity?
}

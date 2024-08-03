package com.example.core.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.core.data.network.Result
import com.example.core.data.reqres.CarData
import com.example.core.data.reqres.Booking
import com.example.core.data.reqres.Metadata
import com.example.core.data.reqres.Record
import com.example.core.data.room.dao.BookingDao
import com.example.core.data.room.entity.BookingEntity
import com.example.core.di.ApiContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val apiContract: ApiContract,
    private val bookingDao: BookingDao
) {
    fun getCarData(): LiveData<Result<CarData>> = liveData(Dispatchers.IO) {
        emit(Result.Loading)
        try {
            val localDataCount = withContext(Dispatchers.IO) { bookingDao.getBookingCount() }
            if (localDataCount > 0) {
                // If local data is present, fetch from local database
                val localData = bookingDao.getAllBookings().value ?: emptyList()
                emit(Result.Success(CarData(
                    record = Record(bookings = localData.map { it.toBooking() })
                )))
            } else {
                // If local data is not present, fetch from remote source
                val response = apiContract.getCarData()
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    emit(Result.Success(responseBody))
                    withContext(Dispatchers.IO) {
                        deleteAllBookings()
                        parseAndInsertBookings(responseBody)
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = try {
                        JSONObject(errorBody).getString("message")
                    } catch (e: JSONException) {
                        "Unknown Error Occurred"
                    }
                    emit(Result.Error(errorMessage))
                }
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "An error occurred"))
        }
    }

    private suspend fun parseAndInsertBookings(carData: CarData) {
        carData.record.bookings.let { bookingsArray ->
            for (booking in bookingsArray) {
                val bookingEntity = BookingEntity(
                    codeBook = booking.codeBook,
                    vehicleName = booking.vehicleName,
                    vehicleImage = booking.vehicleImage,
                    dateStart = booking.dateStart,
                    dateEnd = booking.dateEnd
                )
                bookingDao.insertBooking(bookingEntity)
            }
        }
    }

    suspend fun insertBooking(booking: BookingEntity) {
        bookingDao.insertBooking(booking)
    }

    fun getAllBookings(): LiveData<List<BookingEntity>> = bookingDao.getAllBookings()

    private suspend fun deleteAllBookings() {
        withContext(Dispatchers.IO) {
            bookingDao.clearAllBookings()
        }
    }

    suspend fun deleteBookingById(id: Long) {
        bookingDao.deleteBookingById(id)
    }

    suspend fun updateBookingById(id: Long, updatedBooking: BookingEntity) {
        val bookingToUpdate = bookingDao.getBookingById(id)
        bookingToUpdate?.let {
            it.copy(
                codeBook = updatedBooking.codeBook,
                vehicleName = updatedBooking.vehicleName,
                vehicleImage = updatedBooking.vehicleImage,
                dateStart = updatedBooking.dateStart,
                dateEnd = updatedBooking.dateEnd
            ).let { updated ->
                bookingDao.updateBooking(updated)
            }
        }
    }

    suspend fun createBooking(newBooking: BookingEntity): Result<BookingEntity> {
        return try {
            withContext(Dispatchers.IO) {
                bookingDao.insertBooking(newBooking)
            }
            Result.Success(newBooking)
        } catch (e: Exception) {
            Result.Error(e.message ?: "An error occurred while creating the booking")
        }
    }
}

// Extension function to convert BookingEntity to Booking
fun BookingEntity.toBooking(): Booking {
    return Booking(
        codeBook = this.codeBook,
        vehicleName = this.vehicleName,
        vehicleImage = this.vehicleImage,
        dateStart = this.dateStart,
        dateEnd = this.dateEnd
    )
}
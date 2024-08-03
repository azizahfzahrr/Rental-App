package com.example.cakratech.features

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.repositories.DataRepository
import com.example.core.data.room.entity.BookingEntity
import com.example.core.data.network.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {
    fun getCarData() = dataRepository.getCarData()
    fun getLocalData() = dataRepository.getAllBookings()
    fun deleteBookingByIndex(id: Long) {
        viewModelScope.launch {
            dataRepository.deleteBookingById(id)
        }
    }
    fun insertBooking(booking: BookingEntity) {
        viewModelScope.launch {
            dataRepository.insertBooking(booking)
        }
    }

    fun updateBookingById(id: Long, updatedBooking: BookingEntity) {
        viewModelScope.launch {
            dataRepository.updateBookingById(id, updatedBooking)
        }
    }

    fun createBooking(newBooking: BookingEntity) {
        viewModelScope.launch {
            dataRepository.createBooking(newBooking)
        }
    }
}
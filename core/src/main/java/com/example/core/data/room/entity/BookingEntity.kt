package com.example.core.data.room.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "bookings")
@Parcelize
data class BookingEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0, // Auto-generated unique ID
    val codeBook: String,
    val vehicleName: String,
    val vehicleImage: String,
    val dateStart: String,
    val dateEnd: String
): Parcelable

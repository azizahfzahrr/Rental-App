package com.example.core.data.reqres


import com.google.gson.annotations.SerializedName

data class Record(
    @SerializedName("bookings")
    val bookings: List<Booking>
)
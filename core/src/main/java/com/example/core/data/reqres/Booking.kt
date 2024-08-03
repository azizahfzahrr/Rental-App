package com.example.core.data.reqres


import com.google.gson.annotations.SerializedName

data class Booking(
    @SerializedName("code_book")
    val codeBook: String,
    @SerializedName("date_end")
    val dateEnd: String,
    @SerializedName("date_start")
    val dateStart: String,
    @SerializedName("vehicle_image")
    val vehicleImage: String,
    @SerializedName("vehicle_name")
    val vehicleName: String
)
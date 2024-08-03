package com.example.core.data.reqres


import com.google.gson.annotations.SerializedName

data class CarData(
    @SerializedName("record")
    val record: Record
)
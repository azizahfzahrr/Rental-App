package com.example.core.data.reqres


import com.google.gson.annotations.SerializedName

data class Metadata(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("private")
    val `private`: Boolean
)
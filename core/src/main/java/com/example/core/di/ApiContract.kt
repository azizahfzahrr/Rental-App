package com.example.core.di

import com.example.core.data.reqres.CarData
import retrofit2.Response
import retrofit2.http.GET

interface ApiContract {
    @GET("b/668b8663ad19ca34f884757e")
    suspend fun getCarData(): Response<CarData>
}
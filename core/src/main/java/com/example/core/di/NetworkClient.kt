package com.example.core.di

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.example.core.data.repositories.DataRepository
import com.example.core.data.room.dao.BookingDao
import com.example.core.data.room.localDatabase.BookingDatabase
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkClient {
    companion object{
        private const val BASE_URL ="https://api.jsonbin.io/v3/"
        private const val token = ""
    }
    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        sharedPreferences: SharedPreferences,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val requestBuilder = chain.request()
                    .newBuilder()
                    .header("Authorization", "Bearer ${getAuthToken(sharedPreferences)}")
                    .build()
                val response = chain.proceed(requestBuilder)
                response
            }
            .build()
    }

    @Singleton
    @Provides
    fun getAuthToken(sharedPreferences: SharedPreferences): String {
        return sharedPreferences.getString("token", "") ?: ""
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient) : Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClient)
            .build()

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    @Singleton
    @Provides
    fun provideApiContract(retrofit: Retrofit): ApiContract =
        retrofit.create(ApiContract::class.java)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): BookingDatabase {
        return BookingDatabase.getDatabase(context)
    }

    @Provides
    fun provideBookingDao(database: BookingDatabase): BookingDao {
        return database.bookingDao()
    }
}
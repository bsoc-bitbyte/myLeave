package com.example.myleave.di

import com.example.myleave.api.myLeaveAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    @Provides
    @Singleton
    fun provideRetrofitInstance(): myLeaveAPI =
        Retrofit.Builder()
            .baseUrl("https://myleave-server.onrender.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(myLeaveAPI::class.java)
}
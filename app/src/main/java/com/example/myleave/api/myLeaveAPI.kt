package com.example.myleave.api

import com.example.myleave.models.User
import com.example.myleave.models.UserBody
import com.example.myleave.models.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface myLeaveAPI {
    @POST("/signup")
    suspend fun signUp(@Body body: UserBody): Response<UserResponse>

    @POST("/login")
    suspend fun login(@Body body: UserBody): Response<User>
}
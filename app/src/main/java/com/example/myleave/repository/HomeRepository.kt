package com.example.myleave.repository

import com.example.myleave.api.myLeaveAPI
import com.example.myleave.models.User
import com.example.myleave.models.UserBody
import javax.inject.Inject

class HomeRepository
@Inject constructor(private val myLeaveAPI: myLeaveAPI){

    suspend fun signUp(userBody: UserBody)=myLeaveAPI.signUp(userBody)

    suspend fun login(userBody: UserBody)=myLeaveAPI.login(userBody)

}
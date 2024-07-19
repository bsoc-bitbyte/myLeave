package com.example.myleave.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myleave.di.Resource
import com.example.myleave.models.User
import com.example.myleave.models.UserBody
import com.example.myleave.models.UserResponse
import com.example.myleave.repository.HomeRepository
import com.example.myleave.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject constructor(private val homeRepository: HomeRepository): ViewModel() {

    private val errorMessage = MutableLiveData<String>()
    private val signUp = MutableLiveData<Resource<UserResponse>>()
    val signUpResponse: LiveData<Resource<UserResponse>>
        get() = signUp

    private val login = MutableLiveData<Resource<User>>()
    val loginResponse: LiveData<Resource<User>>
        get() = login

    fun signUp(userBody: UserBody) {
        viewModelScope.launch {
            try {
                val response = homeRepository.signUp(userBody)
                when (response.code()) {

                    201 -> {
                        val uid = response.body()?.uid
//                        Log.d("Sign Up",uid!!)
                        if (response.isSuccessful) {
                            signUp.value = Resource.success(response.body()!!)
                        } else {
                            signUp.value = Resource.empty()
                        }
                    }
                    400 -> {
                        val errorJson = JSONObject(response.errorBody()?.string())
                        val error = errorJson.getString("error")
                        Log.d("Sign Up",error)
                        signUp.value=Resource.error(message = error)
                        errorMessage.postValue(error)
                    }
                    else -> {
                    }
                }
            } catch (e: Exception) {
                if (e is UnknownHostException) {
                    signUp.value = Resource.offlineError()
                }
                Log.d("Server Error", e.message.toString())
            }
        }
    }

    fun login(userBody: UserBody) {
        viewModelScope.launch {
            try {
                // When user called login function setting resource as loading
                login.value = Resource.loading(null)

                val response = homeRepository.login(userBody)
                when (response.code()) {
                    201 -> {
                        val userData = User(response.body()?.name!!,response.body()?.email!!,response.body()?.uid!!,response.body()?.isAdmin!!)
//                        Log.d("Login",userData.toString())
                        if (response.isSuccessful) {
                            login.value = Resource.success(userData)
                        } else {
                            login.value = Resource.empty()
                        }
                    }
                    400 -> {
                        val errorJson = JSONObject(response.errorBody()?.string())
                        val error = errorJson.getString("error")
                        Log.d("Login",error)
                        login.value=Resource.error(message = error)
                        errorMessage.postValue(error)
                    }
                    else -> {
                    }
                }
            } catch (e: Exception) {
                if (e is UnknownHostException) {
                    login.value = Resource.offlineError()
                }
                Log.d("Server Error", e.message.toString())
            }
        }
    }
}
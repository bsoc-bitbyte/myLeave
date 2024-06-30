package com.example.myleave.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.myleave.databinding.ActivityLoginBinding
import com.example.myleave.di.Resource
import com.example.myleave.models.User
import com.example.myleave.models.UserBody
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel= ViewModelProvider(this)[HomeViewModel::class.java]

        binding.btnLogin.setOnClickListener(){
            if (binding.etLoginEmail.text.isNotEmpty() && binding.etLoginPassword.text.isNotEmpty()){
                viewModel.login(UserBody("",binding.etLoginEmail.text.toString(),binding.etLoginPassword.text.toString()))
                setObservers()
            }
        }
    }

    private fun setObservers() {
        viewModel.loginResponse.observe(this){
            when(it.status){
                Resource.Status.LOADING->{
                    //
                }
                Resource.Status.SUCCESS->{
                    //
                }
                Resource.Status.ERROR-> {
                    //
                }
                Resource.Status.EMPTY->{
                    //
                }
                else->{
                    //
                }
            }
        }
    }
}
package com.example.myleave.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.myleave.databinding.ActivitySignUpBinding
import com.example.myleave.di.Resource
import com.example.myleave.models.UserBody
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel=ViewModelProvider(this)[HomeViewModel::class.java]

        binding.btnSignUp.setOnClickListener(){
            if (binding.etName.text.isNotEmpty() && binding.etEmail.text.isNotEmpty() && binding.etPassword.text.isNotEmpty()){
                viewModel.signUp(UserBody(binding.etName.text.toString(),binding.etEmail.text.toString(),binding.etPassword.text.toString()))
                setObservers()
            }
        }
    }

    private fun setObservers() {
        viewModel.signUpResponse.observe(this){
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
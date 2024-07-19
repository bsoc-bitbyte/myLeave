package com.example.myleave.ui.home

import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.myleave.R
import com.example.myleave.databinding.ActivityLoginBinding
import com.example.myleave.di.Resource
import com.example.myleave.models.User
import com.example.myleave.models.UserBody
import com.example.myleave.ui.admin.AdminDashboardActivity
import com.example.myleave.ui.user.UserDashboardActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progressBarLogin.indeterminateDrawable.setColorFilter(
            ContextCompat.getColor(this, R.color.white),
            PorterDuff.Mode.SRC_IN
        )


        viewModel= ViewModelProvider(this)[HomeViewModel::class.java]
        setObservers()

        binding.btnLogin.setOnClickListener() {
            // Check if email is empty
            // If empty show toast
            if(binding.etLoginPassword.text.isEmpty() && binding.etLoginEmail.text.isEmpty()){
                Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(binding.etLoginEmail.text.isEmpty()){
                Toast.makeText(this, "Email cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener

            }

            // Check if password is empty or not
            // If empty show toast
            if(binding.etLoginPassword.text.isEmpty()){
                Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }



            viewModel.login(
                UserBody(
                    "",
                    binding.etLoginEmail.text.toString(),
                    binding.etLoginPassword.text.toString()
                )
            )
        }

        binding.tvSignUp.setOnClickListener(){
            val intent= Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        
    }

    private fun setObservers() {
        viewModel.loginResponse.observe(this){
            Log.d("LogTag", it.toString())
            when(it.status){
                Resource.Status.LOADING->{
                    binding.btnLogin.text=""
                    binding.progressBarLogin.visibility = View.VISIBLE
                }
                Resource.Status.SUCCESS->{
                    binding.progressBarLogin.visibility = View.GONE
                    if(it.data?.isAdmin == true){
                        // if the user is admin show admin ui
                        val intent= Intent(this, AdminDashboardActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish()
                    }else{
                        // Navigate to user ui
                        val intent= Intent(this, UserDashboardActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish()
                    }
                }
                Resource.Status.ERROR-> {
                    binding.progressBarLogin.visibility = View.GONE
                    binding.btnLogin.text="Login"
                    Toast.makeText(this, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
                }
                Resource.Status.EMPTY->{
                    binding.progressBarLogin.visibility = View.GONE
                    binding.btnLogin.text="Login"
                    Toast.makeText(this, "Error Logging In", Toast.LENGTH_SHORT).show()
                }
                else->{
                }
            }
        }
    }
}
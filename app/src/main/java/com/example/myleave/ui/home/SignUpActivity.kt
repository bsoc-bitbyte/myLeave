package com.example.myleave.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.myleave.databinding.ActivitySignUpBinding
import com.example.myleave.di.Resource
import com.example.myleave.models.UserBody
import dagger.hilt.android.AndroidEntryPoint
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.example.myleave.ui.user.UserDashboardActivity
import kotlinx.coroutines.delay

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        binding.btnSignUp.setOnClickListener() {
            if (binding.etName.text.isEmpty() || binding.etPassword.text.isEmpty() || binding.etEmail.text.isEmpty()) {
                Toast.makeText(this, "Field cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!validEmail(binding.etEmail.text.toString())) {
                return@setOnClickListener
            }

            if (binding.etName.text.isNotEmpty() && binding.etEmail.text.isNotEmpty() && binding.etPassword.text.isNotEmpty()) {
                viewModel.signUp(
                    UserBody(
                        binding.etName.text.toString(),
                        binding.etEmail.text.toString(),
                        binding.etPassword.text.toString()
                    )
                )
                loading()
                setObservers()
            }
        }

        binding.tvLogin.setOnClickListener() {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loading(){
        binding.btnSignUp.text = ""
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun validEmail(email: String): Boolean {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show()
            return false
        }

        val domainRegex = Regex("^[a-zA-Z0-9._%+-]+@iiitdmj\\.ac\\.in$")
        val valid = domainRegex.matches(email)
        if(!valid){
            Toast.makeText(this, "Use only College Email", Toast.LENGTH_SHORT).show()
        }

        return valid
    }

    private fun setObservers() {
        viewModel.signUpResponse.observe(this) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    loading()
                }

                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = android.view.View.GONE
                    val intent = Intent(this, UserDashboardActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }

                Resource.Status.ERROR -> {
                    binding.progressBar.visibility = android.view.View.GONE
                    binding.btnSignUp.text = "Sign Up"
                    Toast.makeText(this, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
                }

                Resource.Status.EMPTY -> {
                    binding.progressBar.visibility = android.view.View.GONE
                    binding.btnSignUp.text = "Sign Up"
                    Toast.makeText(this, "Error Signing Up", Toast.LENGTH_SHORT).show()
                }

                else -> {
                }
            }
        }
    }
}
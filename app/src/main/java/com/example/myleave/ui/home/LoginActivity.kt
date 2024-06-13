package com.example.myleave.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myleave.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var bindings: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindings=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(bindings.root)
    }
}
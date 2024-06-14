package com.example.myleave.ui.admin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myleave.databinding.ActivityAdmDashboardBinding

class AdminDashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdmDashboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityAdmDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
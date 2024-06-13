package com.example.myleave.ui.admin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myleave.databinding.ActivityLeaveHistoryBinding

class LeaveHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLeaveHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLeaveHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
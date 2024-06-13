package com.example.myleave.ui.admin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myleave.databinding.ActivityLeaveEntryBinding

class LeaveEntryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLeaveEntryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLeaveEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
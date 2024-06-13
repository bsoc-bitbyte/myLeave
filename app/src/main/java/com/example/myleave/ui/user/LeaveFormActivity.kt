package com.example.myleave.ui.user

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.myleave.R
import com.example.myleave.databinding.ActivityLeaveFormBinding

class LeaveFormActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLeaveFormBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLeaveFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val leaveTypeAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.leave_types,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        binding.spLeaveType.adapter = leaveTypeAdapter

        val departmentAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.department_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        binding.spDepartment.adapter = departmentAdapter
    }
}

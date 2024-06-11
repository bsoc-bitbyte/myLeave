package com.example.myleave.ui.user

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.myleave.R

class LeaveFormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leave_form)

        val spinnerLeaveType: Spinner = findViewById(R.id.spinnerLeaveType)
        val leaveTypeAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.leave_types,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        spinnerLeaveType.adapter = leaveTypeAdapter

        val spinnerDepartment: Spinner = findViewById(R.id.spinnerDepartment)
        val departmentAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.department_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        spinnerDepartment.adapter = departmentAdapter
    }
}

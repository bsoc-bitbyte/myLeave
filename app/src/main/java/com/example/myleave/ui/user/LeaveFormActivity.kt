package com.example.myleave.ui.user

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.myleave.R
import com.example.myleave.databinding.ActivityLeaveFormBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class LeaveFormActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLeaveFormBinding
    private lateinit var startDate: Date
    private lateinit var endDate: Date

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLeaveFormBinding.inflate(layoutInflater)
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

        val calendar = Calendar.getInstance()
        startDate = calendar.time
        endDate = calendar.time

        val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(startDate)
        binding.tvStartDate.text = formattedDate
        binding.tvEndDate.text = formattedDate

        binding.tvStartDate.setOnClickListener {
            openDatePicker(0)
        }
        binding.tvEndDate.setOnClickListener {
            openDatePicker(1)
        }

    }

    private fun openDatePicker(check: Int) {
        val calendar = Calendar.getInstance()
        val currentDate = if (check == 0) startDate else endDate

        calendar.time = currentDate

        val datePickerDialog = DatePickerDialog(this,
            { _, year, monthOfYear, dayOfMonth ->
                val selectedCalendar = Calendar.getInstance()
                selectedCalendar.set(year, monthOfYear, dayOfMonth)

                val selectedDate = selectedCalendar.time
                val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(selectedDate)

                if (check == 0) {
                    startDate = selectedDate
                    binding.tvStartDate.text = formattedDate

                    // Reset end date if it is before the new start date
                    if (endDate.before(startDate)) {
                        endDate = startDate
                        binding.tvEndDate.text = formattedDate
                    }
                } else {
                    endDate = selectedDate
                    binding.tvEndDate.text = formattedDate
                }
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH))

        // Set the minimum date
        if (check == 0) {
            datePickerDialog.datePicker.minDate = System.currentTimeMillis()
        } else {
            datePickerDialog.datePicker.minDate = startDate.time
        }

        datePickerDialog.show()
    }
}

package com.example.myleave.ui.user

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myleave.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class LeaveFormActivity : AppCompatActivity() {
    private lateinit var startDateEditView: TextView
    private lateinit var endDateEditView: TextView
    private lateinit var startDate: Date
    private lateinit var endDate: Date

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leave_form)

        val spinnerLeaveType: Spinner = findViewById(R.id.sp_leave_type)
        val leaveTypeAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.leave_types,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        spinnerLeaveType.adapter = leaveTypeAdapter

        val spinnerDepartment: Spinner = findViewById(R.id.sp_department)
        val departmentAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.department_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        spinnerDepartment.adapter = departmentAdapter

        startDateEditView = findViewById(R.id.et_start_date)
        endDateEditView = findViewById(R.id.et_end_date)

        val calendar = Calendar.getInstance()
        startDate = calendar.time
        endDate = calendar.time

        // Set initial date display (optional)
        val formattedDate = SimpleDateFormat("dd/MM/yyyy").format(startDate)

        startDateEditView.setOnClickListener {
            openDatePicker(0)
        }
        endDateEditView.setOnClickListener {
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
                val formattedDate = SimpleDateFormat("dd/MM/yyyy").format(selectedDate)

                if (check == 0) {
                    startDate = selectedDate
                    startDateEditView.text = formattedDate

                    // Reset end date if it is before the new start date
                    if (endDate.before(startDate)) {
                        endDate = startDate
                        endDateEditView.text = formattedDate
                    }
                } else {
                    endDate = selectedDate
                    endDateEditView.text = formattedDate
                }
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH))

        // Set the minimum date to today for start date picker
        if (check == 0) {
            datePickerDialog.datePicker.minDate = System.currentTimeMillis()
        } else {
            // Set the minimum date for end date picker to the start date
            datePickerDialog.datePicker.minDate = startDate.time
        }

        datePickerDialog.show()
    }
}

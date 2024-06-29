package com.example.myleave.ui.user

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.provider.Settings
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
    private val PICK_FILE_REQUEST_CODE = 1
    private val REQUEST_PERMISSION_CODE = 2
    private val requiredPermission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)


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

        binding.ibChooseFile.setOnClickListener() {

            if (isPermissionGranted()) {
                openFilePicker()
            } else {
                requestPermission()
            }


        }

    }

    private fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            requiredPermission,
            REQUEST_PERMISSION_CODE
        )

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_CODE
            && grantResults.isNotEmpty()
            && grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            openFilePicker()
        } else {
            showSettingDialog()
        }
    }

    private fun showSettingDialog() {
        AlertDialog.Builder(this)
            .setTitle("Permission Required")
            .setMessage("This app needs access to your storage to upload files.")
            .setPositiveButton("Go to Settings") { dialog, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", packageName, null)
                }
                startActivity(intent)
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "*/*"
            putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("application/pdf", "image/*"))
            addCategory(Intent.CATEGORY_OPENABLE)

        }
        startActivityForResult(intent, PICK_FILE_REQUEST_CODE)

    }

    @Deprecated("This method has been deprecated in favor of using the Activity Result API")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                handleFileUri(uri)
            }
        }
    }

    private fun handleFileUri(uri: Uri) {
        val fileName = getFileName(uri)
        binding.etAttachment.setText(fileName)
    }

    private fun getFileName(uri: Uri): String? {
        var result: String? = null
        if (uri.scheme == "content") {
            val cursor = contentResolver.query(uri, null, null, null, null)
            cursor?.use {
                if (it.moveToFirst()) {
                    result = it.getString(it.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME))
                }
            }
        }
        if (result == null) {
            result = uri.path
            val cut = result?.lastIndexOf('/')
            if (cut != -1) {
                result = result?.substring(cut!! + 1)
            }
        }
        if (result == "" || result == null) {
            result = "Unknown file"
        }
        return result
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

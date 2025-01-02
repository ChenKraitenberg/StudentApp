package com.example.studentsapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.studentsapp.R
import com.example.studentsapp.models.Student

class NewStudentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_student)

        // Initialize toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Enable back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)

        val nameInput = findViewById<EditText>(R.id.student_name)
        val idInput = findViewById<EditText>(R.id.student_id)
        val phoneInput = findViewById<EditText>(R.id.student_phone)
        val addressInput = findViewById<EditText>(R.id.student_address)
        val checkedInput = findViewById<CheckBox>(R.id.student_checked)

        val saveButton = findViewById<Button>(R.id.save_button)
        val cancelButton = findViewById<Button>(R.id.cancel_button)

        // SaveButton
        saveButton.setOnClickListener {
            val name = nameInput.text.toString()
            val id = idInput.text.toString()
            val phone = phoneInput.text.toString()
            val address = addressInput.text.toString()
            val isChecked = checkedInput.isChecked

            if (name.isNotEmpty() && id.isNotEmpty()) {
                val newStudent = Student(name, id, phone, address, isChecked)
                // Add student to the list (pass this data back to the main activity)
                val resultIntent = Intent()
                resultIntent.putExtra("newStudent", newStudent)
                setResult(RESULT_OK, resultIntent)
                finish()
            } else {
                Toast.makeText(this, "Name and ID are required!", Toast.LENGTH_SHORT).show()
            }
        }

        // CancelButton
        cancelButton.setOnClickListener {
            finish()
        }

//        // BackButton
//        backButton.setOnClickListener {
//            finish()
//        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed() // פעולה שמחזירה לפעילות הקודמת
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}

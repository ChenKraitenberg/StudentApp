package com.example.studentsapp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.studentsapp.R
import com.example.studentsapp.models.Student

class EditStudentActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var addressEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var deleteButton: Button

    private var student: Student? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_student)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // הפעלת כפתור החזרה
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back) // אייקון מותאם אישית

        // חיבור רכיבי ה-XML
        nameEditText = findViewById(R.id.nameEditText)
        phoneEditText = findViewById(R.id.phoneEditText)
        addressEditText = findViewById(R.id.addressEditText)
        saveButton = findViewById(R.id.saveButton)
        deleteButton = findViewById(R.id.deleteButton)

        // קבלת נתוני הסטודנט
        student = intent.getParcelableExtra("STUDENT")
        student?.let {
            nameEditText.setText(it.name)
            phoneEditText.setText(it.phone)
            addressEditText.setText(it.address)
        }

        // שמירת עדכון
        saveButton.setOnClickListener {
            student?.let {
                it.name = nameEditText.text.toString()
                it.phone = phoneEditText.text.toString()
                it.address = addressEditText.text.toString()

                val resultIntent = Intent()
                resultIntent.putExtra("UPDATED_STUDENT", it)
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }

        // מחיקת סטודנט עם הודעת אישור
        deleteButton.setOnClickListener {
            student?.let {
                android.app.AlertDialog.Builder(this)
                    .setTitle("Delete Student")
                    .setMessage("Are you sure you want to delete this student?")
                    .setPositiveButton("Yes") { _, _ ->
                        val resultIntent = Intent()
                        resultIntent.putExtra("DELETE_STUDENT_ID", it.id)
                        setResult(RESULT_FIRST_USER, resultIntent)
                        Log.d("EditStudentActivity", "Delete result sent for ID: ${it.id} with RESULT_FIRST_USER")
                        finish()
                    }
                    .setNegativeButton("No", null)
                    .show()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

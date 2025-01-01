package com.example.studentsapp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.studentsapp.R
import com.example.studentsapp.models.Student

class EditStudentActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var idEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var addressEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var deleteButton: Button

    private var student: Student? = null
    private var originalId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_student)

        // חיבור רכיבי ה-XML
        nameEditText = findViewById(R.id.nameEditText)
        idEditText = findViewById(R.id.idEditText)
        phoneEditText = findViewById(R.id.phoneEditText)
        addressEditText = findViewById(R.id.addressEditText)
        saveButton = findViewById(R.id.saveButton)
        deleteButton = findViewById(R.id.deleteButton)

        // קבלת נתוני הסטודנט
        student = intent.getParcelableExtra("STUDENT")
        student?.let {
            originalId = it.id // we cant change the id
            nameEditText.setText(it.name)
            idEditText.setText(it.id)
            phoneEditText.setText(it.phone)
            addressEditText.setText(it.address)
        }

        // שמירת עדכון
        saveButton.setOnClickListener {
            student?.let {
                it.name = nameEditText.text.toString()
                it.id = idEditText.text.toString()
                it.phone = phoneEditText.text.toString()
                it.address = addressEditText.text.toString()

                val resultIntent = Intent()
                resultIntent.putExtra("UPDATED_STUDENT", it)
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }


        // מחיקת סטודנט
        deleteButton.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("DELETE_STUDENT_ID", student?.id)
            setResult(RESULT_FIRST_USER, resultIntent)
            Log.d("EditStudentActivity", "Delete result sent for ID: ${student?.id} with RESULT_FIRST_USER")
            finish()
        }



    }
}

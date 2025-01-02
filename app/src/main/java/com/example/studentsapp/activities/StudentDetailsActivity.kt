package com.example.studentsapp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.studentsapp.R
import com.example.studentsapp.models.Student

class StudentDetailsActivity : AppCompatActivity() {

    private lateinit var studentImageView: ImageView
    private lateinit var nameTextView: TextView
    private lateinit var idTextView: TextView
    private lateinit var phoneTextView: TextView
    private lateinit var addressTextView: TextView
    private lateinit var editButton: Button
    private lateinit var backButton: Button

    private var student: Student? = null

    private val EDIT_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_details)

        // Initialize toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Enable back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)

        // חיבור רכיבי ה-XML
        studentImageView = findViewById(R.id.studentImageView)
        nameTextView = findViewById(R.id.nameTextView)
        idTextView = findViewById(R.id.idTextView)
        phoneTextView = findViewById(R.id.phoneTextView)
        addressTextView = findViewById(R.id.addressTextView)
        editButton = findViewById(R.id.editButton)
        //backButton = findViewById(R.id.backButton)

        // קבלת הנתונים
        student = intent.getParcelableExtra("STUDENT")
        student?.let {
            studentImageView.setImageResource(R.drawable.ic_student)
            nameTextView.text = it.name
            idTextView.text = it.id
            phoneTextView.text = it.phone
            addressTextView.text = it.address
        }

        // מעבר למסך עריכה
        editButton.setOnClickListener {
            val intent = Intent(this, EditStudentActivity::class.java)
            intent.putExtra("STUDENT", student)
            startActivityForResult(intent, EDIT_REQUEST_CODE)
        }

    }

override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)

    if (requestCode == EDIT_REQUEST_CODE) {
        when (resultCode) {
            RESULT_OK -> {
                val updatedStudent = data?.getParcelableExtra<Student>("UPDATED_STUDENT")
                updatedStudent?.let {
                    student = it
                    nameTextView.text = it.name
                    idTextView.text = it.id
                    phoneTextView.text = it.phone
                    addressTextView.text = it.address

                    Log.d("StudentDetailsActivity", "Returned with updated student: ${it.name}")

                    // עדכון התוצאה ל-RESULT_OK
                    val resultIntent = Intent()
                    resultIntent.putExtra("UPDATED_STUDENT", student)
                    setResult(RESULT_OK, resultIntent)
                }
            }
            RESULT_FIRST_USER -> {
                val deletedStudentId = data?.getStringExtra("DELETE_STUDENT_ID")
                Log.d("StudentDetailsActivity", "Student deleted with ID: $deletedStudentId")

                // עדכון התוצאה ל-RESULT_FIRST_USER
                val resultIntent = Intent()
                resultIntent.putExtra("DELETE_STUDENT_ID", deletedStudentId)
                setResult(RESULT_FIRST_USER, resultIntent)

                finish() // סגירת המסך
            }
        }
    }
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

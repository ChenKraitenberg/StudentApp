package com.example.studentsapp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studentsapp.R
import com.example.studentsapp.adapters.StudentsAdapter
import com.example.studentsapp.models.Student

class StudentsListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var studentsAdapter: StudentsAdapter
    private val studentsList = mutableListOf<Student>()

    private val DETAILS_REQUEST_CODE = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_students_list)

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Load dummy data
        loadStudents()

        // Set adapter
        studentsAdapter = StudentsAdapter(studentsList) { student ->
            val intent = Intent(this, StudentDetailsActivity::class.java)
            intent.putExtra("STUDENT", student)
            startActivityForResult(intent, DETAILS_REQUEST_CODE)
        }

        recyclerView.adapter = studentsAdapter
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        Log.d("StudentsListActivity", "onActivityResult called with requestCode=$requestCode, resultCode=$resultCode")
//
//        if (requestCode == DETAILS_REQUEST_CODE) {
//            when (resultCode) {
//                RESULT_OK -> {
//                    val updatedStudent = data?.getParcelableExtra<Student>("UPDATED_STUDENT")
//                    updatedStudent?.let {
//                        val index = studentsList.indexOfFirst { student -> student.id == it.id }
//                        if (index != -1) {
//                            studentsList[index] = it
//                            studentsAdapter.notifyItemChanged(index)
//                            Log.d("StudentsListActivity", "Student updated: ${it.name}")
//                        }
//                    }
//                }
//                RESULT_FIRST_USER -> { // מחיקה
//                    val deletedStudentId = data?.getStringExtra("DELETE_STUDENT_ID")
//                    Log.d("StudentsListActivity", "Received delete request for ID: $deletedStudentId")
//                    val index = studentsList.indexOfFirst { student -> student.id == deletedStudentId }
//                    Log.d("StudentsListActivity", "Index to remove: $index")
//
//                    if (index != -1) {
//                        Log.d("StudentsListActivity", "List before deletion: ${studentsList.map { it.id }}")
//                        studentsList.removeAt(index)
//                        studentsAdapter.notifyDataSetChanged()
//                        Log.d("StudentsListActivity", "List after deletion: ${studentsList.map { it.id }}")
//                        Log.d("StudentsListActivity", "Student deleted with ID: $deletedStudentId")
//                    } else {
//                        Log.d("StudentsListActivity", "Student with ID $deletedStudentId not found")
//                    }
//                }
//            }
//        }
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.d("StudentsListActivity", "onActivityResult called with requestCode=$requestCode, resultCode=$resultCode")

        if (requestCode == DETAILS_REQUEST_CODE) {
            when (resultCode) {
                RESULT_OK -> {
                    val updatedStudent = data?.getParcelableExtra<Student>("UPDATED_STUDENT")
                    updatedStudent?.let {
                        val index = studentsList.indexOfFirst { student -> student.id == it.id }
                        if (index != -1) {
                            studentsList[index] = it
                            studentsAdapter.notifyItemChanged(index)
                            Log.d("StudentsListActivity", "Student updated: ${it.name}")
                        }
                    }
                }
                RESULT_FIRST_USER -> { // טיפול במחיקה
                    val deletedStudentId = data?.getStringExtra("DELETE_STUDENT_ID")
                    Log.d("StudentsListActivity", "Received delete request for ID: $deletedStudentId")

                    val index = studentsList.indexOfFirst { student -> student.id == deletedStudentId }
                    if (index != -1) {
                        Log.d("StudentsListActivity", "List before deletion: ${studentsList.map { it.id }}")
                        studentsList.removeAt(index)
                        studentsAdapter.notifyItemRemoved(index)
                        Log.d("StudentsListActivity", "List after deletion: ${studentsList.map { it.id }}")
                    } else {
                        Log.d("StudentsListActivity", "Student with ID $deletedStudentId not found")
                    }
                }
            }
        }
    }







    private fun loadStudents() {
        // Add some dummy students
        studentsList.add(Student("John Doe", "123456789", "050-1234567", "Tel Aviv"))
        studentsList.add(Student("Jane Smith", "987654321", "054-7654321", "Jerusalem"))
    }
}

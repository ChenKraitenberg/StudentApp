package com.example.studentsapp.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studentsapp.R
import com.example.studentsapp.adapters.StudentsAdapter
import com.example.studentsapp.models.Student
import com.google.android.material.floatingactionbutton.FloatingActionButton

class StudentsListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var studentsAdapter: StudentsAdapter
    private val studentsList = mutableListOf<Student>()

    private val DETAILS_REQUEST_CODE = 2
    private val REQUEST_CODE_NEW_STUDENT = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_students_list)

        // Initialize Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        title = "Students List"

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Add divider between items
        val dividerItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(dividerItemDecoration)

        // Set up FloatingActionButton
        val addStudentButton = findViewById<FloatingActionButton>(R.id.add_student_button)
        addStudentButton.setOnClickListener {
            val intent = Intent(this, NewStudentActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_NEW_STUDENT)
        }

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.d("StudentsListActivity", "onActivityResult called with requestCode=$requestCode, resultCode=$resultCode")

        when (requestCode) {
            REQUEST_CODE_NEW_STUDENT -> {
                if (resultCode == RESULT_OK) {
                    val newStudent = data?.getParcelableExtra<Student>("newStudent")
                    newStudent?.let {
                        studentsList.add(it)
                        studentsAdapter.notifyItemInserted(studentsList.size - 1)
                        Log.d("StudentsListActivity", "New student added: ${it.name}")
                    }
                }
            }

            DETAILS_REQUEST_CODE -> {
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
                    RESULT_FIRST_USER -> {
                        val deletedStudentId = data?.getStringExtra("DELETE_STUDENT_ID")
                        deletedStudentId?.let { id ->
                            val index = studentsList.indexOfFirst { student -> student.id == id }
                            if (index != -1) {
                                studentsList.removeAt(index)
                                studentsAdapter.notifyItemRemoved(index)
                                Log.d("StudentsListActivity", "Student deleted with ID: $id")
                            }
                        }
                    }
                }
            }
        }
    }

    private fun loadStudents() {
        studentsList.add(Student("John Doe", "207654982", "050-1234567", "Tel Aviv"))
        studentsList.add(Student("David Smith", "206897414", "054-7654321", "Jerusalem"))
    }
}

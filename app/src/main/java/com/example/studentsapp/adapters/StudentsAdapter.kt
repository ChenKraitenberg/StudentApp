package com.example.studentsapp.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studentsapp.R
import com.example.studentsapp.models.Student

class StudentsAdapter(
    private val students: MutableList<Student>, // הרשימה ניתנת לשינוי
    private val onItemClick: (Student) -> Unit
) : RecyclerView.Adapter<StudentsAdapter.StudentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.student_item, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]
        holder.bind(student)
        holder.itemView.setOnClickListener { onItemClick(student) }
    }

    override fun getItemCount() = students.size


    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val studentImageView: ImageView = itemView.findViewById(R.id.studentImageView)
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val idTextView: TextView = itemView.findViewById(R.id.idTextView)
        private val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)

        fun bind(student: Student) {
            nameTextView.text = student.name
            idTextView.text = student.id
            studentImageView.setImageResource(R.drawable.ic_contact) // הגדרת תמונה גנרית

        }
    }
}

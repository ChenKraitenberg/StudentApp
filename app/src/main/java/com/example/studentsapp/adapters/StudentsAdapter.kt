package com.example.studentsapp.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studentsapp.R
import com.example.studentsapp.models.Student

//class StudentsAdapter(
//    private val students: MutableList<Student>, // השתמשי ב-MutableList
//    private val onItemClick: (Student) -> Unit
//) : RecyclerView.Adapter<StudentsAdapter.StudentViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.student_item, parent, false)
//        return StudentViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
//        val student = students[position]
//        holder.bind(student)
//        holder.itemView.setOnClickListener { onItemClick(student) }
//    }
//
//    override fun getItemCount() = students.size
//
//    fun removeStudentAt(index: Int) { // פונקציה חדשה למחיקה
//        students.removeAt(index)
//        Log.d("StudentsAdapter", "List in adapter after deletion: ${students.map { it.name }}")
//        notifyItemRemoved(index)
//    }
//
//    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
//        private val idTextView: TextView = itemView.findViewById(R.id.idTextView)
//        private val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)
//
//        fun bind(student: Student) {
//            nameTextView.text = student.name
//            idTextView.text = student.id
//            checkBox.isChecked = student.isChecked
//            Log.d("StudentsAdapter", "Binding student: ${student.name} with ID: ${student.id}")
//            checkBox.setOnCheckedChangeListener { _, isChecked ->
//                student.isChecked = isChecked
//            }
//        }
//    }
//}
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

    fun removeStudentAt(index: Int) { // פונקציה למחיקת סטודנט מה-Adapter
        students.removeAt(index)
        notifyItemRemoved(index)
        Log.d("StudentsAdapter", "List in adapter after deletion: ${students.map { it.id }}")
    }

    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val idTextView: TextView = itemView.findViewById(R.id.idTextView)

        fun bind(student: Student) {
            nameTextView.text = student.name
            idTextView.text = student.id
        }
    }
}

package pl.juliankominiak.asystentnauczyciela.viewmodel

import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import pl.juliankominiak.asystentnauczyciela.R
import pl.juliankominiak.asystentnauczyciela.model.Student
import pl.juliankominiak.asystentnauczyciela.view.StudentsFragmentDirections
import pl.juliankominiak.asystentnauczyciela.view.SubjectsFragmentDirections

class StudentsListAdapter(private val view: View,
                          private val viewModel: StudentsListViewModel,
                          private val context: Context?)
    : RecyclerView.Adapter<StudentsListAdapter.StudentsListHolder>() {

    inner class StudentsListHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewStudentName: TextView = view.findViewById(R.id.student_name)
        val textViewStudentSurname: TextView = view.findViewById(R.id.student_surname)
        val buttonViewStudentOption: TextView = view.findViewById(R.id.student_options)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentsListHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_student, parent, false)
        return StudentsListHolder(view)
    }

    override fun onBindViewHolder(holder: StudentsListHolder, position: Int) {
        holder.textViewStudentName.text = viewModel.students.value?.get(position)?.name
        holder.textViewStudentSurname.text = viewModel.students.value?.get(position)?.surname

        holder.buttonViewStudentOption.setOnClickListener {
            val popup = PopupMenu(context, holder.buttonViewStudentOption)
            popup.inflate(R.menu.student_options_menu)
            popup.setOnMenuItemClickListener { item: MenuItem? ->
                when (item!!.itemId) {
                    R.id.student_delete -> {
                        viewModel.delete(holder.textViewStudentName.text.toString(),
                            holder.textViewStudentSurname.text.toString())
                    }
                    R.id.student_marks -> {
                        val action = StudentsFragmentDirections
                            .actionStudentsFragmentToMarksFragment(viewModel.students.value?.get(position)?.id!!,
                                        viewModel.students.value?.get(position)?.name as String + " " +
                                        viewModel.students.value?.get(position)?.surname as String,
                                        viewModel.students.value?.get(position)?.parentSubjectName as String)
                        view.findNavController().navigate(action)
                    }
                }
                true
            }
            popup.show()
        }
    }

    override fun getItemCount(): Int {
        return viewModel.students.value?.size ?: 0
    }
}
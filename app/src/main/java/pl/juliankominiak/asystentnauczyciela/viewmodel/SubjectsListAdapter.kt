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
import pl.juliankominiak.asystentnauczyciela.model.Subject
import pl.juliankominiak.asystentnauczyciela.view.ClassesFragmentDirections
import pl.juliankominiak.asystentnauczyciela.view.SubjectsFragmentDirections


class SubjectsListAdapter(private val view: View,
                          private val viewModel: SubjectsListViewModel,
                          private val subjects: LiveData<List<Subject>>,
                          private val context: Context?)
    : RecyclerView.Adapter<SubjectsListAdapter.SubjectsListHolder>() {

    inner class SubjectsListHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewSubjectName: TextView = view.findViewById(R.id.subject_name)
        val buttonViewSubjectOption: TextView = view.findViewById(R.id.subject_options)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectsListHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_subject, parent, false)
        return SubjectsListHolder(view)
    }

    override fun onBindViewHolder(holder: SubjectsListHolder, position: Int) {
        holder.textViewSubjectName.text = subjects.value?.get(position)?.subjectName
        holder.textViewSubjectName.setOnClickListener {
            val action = SubjectsFragmentDirections
                .actionSubjectsFragmentToStudentsFragment(holder.textViewSubjectName.text.toString())
            view.findNavController().navigate(action)
        }

        holder.buttonViewSubjectOption.setOnClickListener {
            val popup = PopupMenu(context, holder.buttonViewSubjectOption)
            popup.inflate(R.menu.subject_options_menu)
            popup.setOnMenuItemClickListener { item: MenuItem? ->
                when (item!!.itemId) {
                    R.id.subject_delete -> {
                        viewModel.delete(holder.textViewSubjectName.text.toString())
                    }
                    R.id.subject_classes -> {
                        val action = SubjectsFragmentDirections
                            .actionSubjectsFragmentToClassesFragment(holder.textViewSubjectName.text.toString())
                        view.findNavController().navigate(action)
                    }
                }
                true
            }
            popup.show()
        }
    }

    override fun getItemCount(): Int {
        return subjects.value?.size ?: 0
    }
}
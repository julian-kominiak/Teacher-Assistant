package pl.juliankominiak.asystentnauczyciela.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pl.juliankominiak.asystentnauczyciela.MainActivity
import pl.juliankominiak.asystentnauczyciela.R
import pl.juliankominiak.asystentnauczyciela.viewmodel.*

class StudentsFragment : Fragment() {

    lateinit var viewModel: StudentsListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        (activity as MainActivity).supportActionBar?.title = "Uczniowie przedmiotu " +
                (arguments?.get("subjectName") ?: String)
        return inflater.inflate(R.layout.fragment_students, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = StudentsListViewModel((requireNotNull(this.activity).application), arguments)
        val studentsListAdapter = StudentsListAdapter(view, viewModel, this.context)

        viewModel.students.observe(viewLifecycleOwner) {
            studentsListAdapter.notifyDataSetChanged()
        }

        val layoutManager = LinearLayoutManager(view.context)
        view.findViewById<RecyclerView>(R.id.students_recyclerView).let {
            it.adapter = studentsListAdapter
            it.layoutManager = layoutManager
        }

        view.findViewById<Button>(R.id.button_students_to_subjects).apply {
            setOnClickListener {
                view.findNavController().navigate(R.id.action_studentsFragment_to_subjectsFragment)
            }
        }

        view.findViewById<Button>(R.id.button_add_student).apply {
            setOnClickListener {
                val dialog = StudentAddDialogFragment(viewModel, arguments)
                dialog.show(parentFragmentManager, "add_student")

            }
        }
    }
}


package pl.juliankominiak.asystentnauczyciela.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pl.juliankominiak.asystentnauczyciela.MainActivity
import pl.juliankominiak.asystentnauczyciela.R
import pl.juliankominiak.asystentnauczyciela.model.Mark
import pl.juliankominiak.asystentnauczyciela.viewmodel.ClassesListViewModel
import pl.juliankominiak.asystentnauczyciela.viewmodel.MarksListAdapter
import pl.juliankominiak.asystentnauczyciela.viewmodel.MarksListViewModel
import pl.juliankominiak.asystentnauczyciela.viewmodel.StudentsListViewModel


class MarksFragment : Fragment() {

    lateinit var viewModel: MarksListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        (activity as MainActivity).supportActionBar?.title = "Oceny " + arguments?.get("studentName") as String +
                " z " + arguments?.get("subjectName")
        return inflater.inflate(R.layout.fragment_marks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = MarksListViewModel((requireNotNull(this.activity).application), arguments)
        val marksListAdapter = MarksListAdapter(viewModel, this.context)

        viewModel.marks.observe(viewLifecycleOwner) {
            marksListAdapter.notifyDataSetChanged()
            view.findViewById<TextView>(R.id.average_mark).text = buildString {
                append("Średnia: ")
                append(viewModel.getAverage())
            }
        }

        val layoutManager = LinearLayoutManager(view.context)
        view.findViewById<RecyclerView>(R.id.marks_recyclerView).let {
            it.adapter = marksListAdapter
            it.layoutManager = layoutManager
        }

        view.findViewById<Button>(R.id.button_marks_to_students).apply {
            setOnClickListener {
                val action = MarksFragmentDirections
                    .actionMarksFragmentToStudentsFragment(arguments?.get("subjectName") as String)
                view.findNavController().navigate(action)
            }
        }

        view.findViewById<Button>(R.id.button_add_mark).apply {
            setOnClickListener {
                val dialog = MarkAddDialogFormat(viewModel, arguments)
                dialog.show(parentFragmentManager, "add_mark")
            }
        }
    }
}
package pl.juliankominiak.asystentnauczyciela.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pl.juliankominiak.asystentnauczyciela.MainActivity
import pl.juliankominiak.asystentnauczyciela.R
import pl.juliankominiak.asystentnauczyciela.viewmodel.ClassesListAdapter
import pl.juliankominiak.asystentnauczyciela.viewmodel.ClassesListViewModel
import pl.juliankominiak.asystentnauczyciela.model.Class
import java.time.DayOfWeek

class ClassesFragment : Fragment() {

    lateinit var viewModel: ClassesListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        (activity as MainActivity).supportActionBar?.title = "Zajęcia dla " + (arguments?.get("subjectName") ?: String)
        return inflater.inflate(R.layout.fragment_classes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ClassesListViewModel((requireNotNull(this.activity).application), arguments)
        val classesListAdapter = ClassesListAdapter(viewModel, this)

        viewModel.classes.observe(viewLifecycleOwner) {
            classesListAdapter.notifyDataSetChanged()
        }

        val layoutManager = LinearLayoutManager(view.context)
        view.findViewById<RecyclerView>(R.id.classes_recyclerView).let {
            it.adapter = classesListAdapter
            it.layoutManager = layoutManager
        }

        view.findViewById<Button>(R.id.button_classes_to_subjects).apply {
            setOnClickListener {
                view.findNavController().navigate(R.id.action_classesFragment_to_subjectsFragment)
            }
        }

        view.findViewById<Button>(R.id.button_add_class).apply {
            setOnClickListener {
                val dialog = ClassAddDayDialogFragment(viewModel, arguments)
                dialog.show(parentFragmentManager, "add_class_day")
            }
        }
    }
}
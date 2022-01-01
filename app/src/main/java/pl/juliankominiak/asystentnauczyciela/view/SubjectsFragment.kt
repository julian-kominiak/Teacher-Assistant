package pl.juliankominiak.asystentnauczyciela.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pl.juliankominiak.asystentnauczyciela.MainActivity
import pl.juliankominiak.asystentnauczyciela.R
import pl.juliankominiak.asystentnauczyciela.viewmodel.SubjectsListAdapter
import pl.juliankominiak.asystentnauczyciela.viewmodel.SubjectsListViewModel

class SubjectsFragment : Fragment() {

    lateinit var viewModel: SubjectsListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        (activity as MainActivity).supportActionBar?.title = "Przedmioty"
        return inflater.inflate(R.layout.fragment_subjects, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = SubjectsListViewModel((requireNotNull(this.activity).application))
        val subjectsListAdapter = SubjectsListAdapter(view, viewModel, viewModel.subjects, this.context)

        viewModel.subjects.observe(viewLifecycleOwner) {
            subjectsListAdapter.notifyDataSetChanged()
        }

        val layoutManager = LinearLayoutManager(view.context)
        view.findViewById<RecyclerView>(R.id.subjects_recyclerView).let {
            it.adapter = subjectsListAdapter
            it.layoutManager = layoutManager
        }

        view.findViewById<Button>(R.id.button_subjects_to_home).apply {
            setOnClickListener {
                view.findNavController().navigate(R.id.action_subjectsFragment_to_homeFragment)
            }
        }

        view.findViewById<Button>(R.id.button_add_subject).apply {
            setOnClickListener {
               val dialog = SubjectAddDialogFragment(viewModel)
               dialog.show(parentFragmentManager, "add_subject")
            }
        }

    }
}
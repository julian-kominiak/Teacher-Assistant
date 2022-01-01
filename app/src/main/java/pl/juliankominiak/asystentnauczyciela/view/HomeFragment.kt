package pl.juliankominiak.asystentnauczyciela.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import pl.juliankominiak.asystentnauczyciela.MainActivity
import pl.juliankominiak.asystentnauczyciela.R

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).supportActionBar?.title = "Asystent Nauczyciela"
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_home_to_subjects).apply {
            setOnClickListener {
                view.findNavController().navigate(R.id.action_homeFragment_to_subjectsFragment)
            }
        }

    }
}
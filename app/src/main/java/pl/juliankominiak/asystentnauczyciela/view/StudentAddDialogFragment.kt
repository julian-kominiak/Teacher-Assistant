package pl.juliankominiak.asystentnauczyciela.view

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import pl.juliankominiak.asystentnauczyciela.R
import pl.juliankominiak.asystentnauczyciela.model.Student
import pl.juliankominiak.asystentnauczyciela.viewmodel.StudentsListAdapter
import pl.juliankominiak.asystentnauczyciela.viewmodel.StudentsListViewModel

class StudentAddDialogFragment(private val viewModel: StudentsListViewModel,
                               private val subjectNameArgument: Bundle?) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = requireActivity().layoutInflater
        val dialogView: View = inflater.inflate(R.layout.dialog_add_student, null)
        val nameInput: EditText = dialogView.findViewById(R.id.name_input)
        val surnameInput: EditText = dialogView.findViewById(R.id.surname_input)
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setView(dialogView)
                .setPositiveButton("Dodaj") { _, _ ->
                    if (nameInput.text.isNotEmpty() and surnameInput.text.isNotEmpty()) {
                        viewModel.add(
                            Student(0, subjectNameArgument?.get("subjectName") as String,
                            nameInput.text.toString(), surnameInput.text.toString())
                        )
                    }
                }
                .setNegativeButton("Anuluj") { _, _ ->
                    dialog?.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
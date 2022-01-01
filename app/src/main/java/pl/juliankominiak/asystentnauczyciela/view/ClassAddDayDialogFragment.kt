package pl.juliankominiak.asystentnauczyciela.view

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TimePicker
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import pl.juliankominiak.asystentnauczyciela.R
import pl.juliankominiak.asystentnauczyciela.viewmodel.ClassesListViewModel
import pl.juliankominiak.asystentnauczyciela.model.Class

class ClassAddDayDialogFragment(private val viewModel: ClassesListViewModel,
                                private val subjectNameArgument: Bundle?) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = requireActivity().layoutInflater
        val dialogView: View = inflater.inflate(R.layout.dialog_add_class_day, null)
        val spinner: Spinner = dialogView.findViewById(R.id.day_spinner)
        ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.days,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        val startTimePicker: TimePicker = dialogView.findViewById(R.id.start_time_picker)
        val endTimePicker: TimePicker = dialogView.findViewById(R.id.end_time_picker)
        startTimePicker.setIs24HourView(true)
        endTimePicker.setIs24HourView(true)

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setView(dialogView)
                .setPositiveButton("Dodaj") { _, _ ->
                    viewModel.add(Class(0, subjectNameArgument?.get("subjectName") as String,
                        spinner.selectedItem as String,
                        startTimePicker.hour.toString() + ":" + startTimePicker.minute.toString(),
                        endTimePicker.hour.toString() + ":" + endTimePicker.minute.toString()))
                }
                .setNegativeButton("Anuluj") { _, _ ->
                    dialog?.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
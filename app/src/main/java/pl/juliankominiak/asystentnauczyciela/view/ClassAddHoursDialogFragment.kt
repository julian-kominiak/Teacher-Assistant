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

class ClassAddHoursDialogFragment(private val viewModel: ClassesListViewModel,
                                  private val classId: Long) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = requireActivity().layoutInflater
        val dialogView: View = inflater.inflate(R.layout.dialog_add_class_hours, null)
        val startTimePicker: TimePicker = dialogView.findViewById(R.id.start_time_picker)
        val endTimePicker: TimePicker = dialogView.findViewById(R.id.end_time_picker)
        startTimePicker.setIs24HourView(true)
        endTimePicker.setIs24HourView(true)
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setView(dialogView)
                .setPositiveButton("Dodaj") { _, _ ->
                    val myClass: Class = viewModel.get(classId)
                    myClass.startTime = startTimePicker.hour.toString() + ":" + startTimePicker.minute.toString()
                    myClass.endTime = endTimePicker.hour.toString() + ":" + endTimePicker.minute.toString()
                    viewModel.delete(classId)
                    viewModel.add(myClass)
                }
                .setNegativeButton("Anuluj") { _, _ ->
                    dialog?.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
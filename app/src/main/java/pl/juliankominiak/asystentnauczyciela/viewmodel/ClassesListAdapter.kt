package pl.juliankominiak.asystentnauczyciela.viewmodel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pl.juliankominiak.asystentnauczyciela.R
import pl.juliankominiak.asystentnauczyciela.view.ClassAddHoursDialogFragment
import pl.juliankominiak.asystentnauczyciela.view.ClassesFragment

class ClassesListAdapter(private val viewModel: ClassesListViewModel,
                         private val parentFragment: ClassesFragment)
    : RecyclerView.Adapter<ClassesListAdapter.ClassesListHolder>() {

    inner class ClassesListHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewClassDayOfWeek: TextView = view.findViewById(R.id.class_day_of_week)
        val textViewClassTimeframe: TextView = view.findViewById(R.id.class_timeframe)
        val buttonViewClassOption: TextView = view.findViewById(R.id.class_options)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassesListHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_class, parent, false)
        return ClassesListHolder(view)
    }

    override fun onBindViewHolder(holder: ClassesListHolder, position: Int) {
        holder.textViewClassDayOfWeek.text = viewModel.classes.value?.get(position)?.dayOfWeek.toString()
        holder.textViewClassTimeframe.text = buildString {
            append(viewModel.classes.value?.get(position)?.startTime)
            append(" - ")
            append(viewModel.classes.value?.get(position)?.endTime)
        }

        holder.buttonViewClassOption.setOnClickListener {
            val popup = PopupMenu(parentFragment.context, holder.buttonViewClassOption)
            popup.inflate(R.menu.class_options_menu)
            popup.setOnMenuItemClickListener { item: MenuItem? ->
                when (item!!.itemId) {
                    R.id.class_delete -> {
                        viewModel.delete(viewModel.classes.value?.get(position)?.id!!)
                    }
                    R.id.class_set_hours -> {
                        val dialog = ClassAddHoursDialogFragment(viewModel,
                            viewModel.classes.value?.get(position)?.id!!)
                        dialog.show(parentFragment.parentFragmentManager, "add_class_hours")
                    }
                }
                true
            }
            popup.show()
        }
    }

    override fun getItemCount(): Int {
        return viewModel.classes.value?.size ?: 0
    }
}
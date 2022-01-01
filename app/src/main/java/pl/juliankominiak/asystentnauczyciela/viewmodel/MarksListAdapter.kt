package pl.juliankominiak.asystentnauczyciela.viewmodel

import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import pl.juliankominiak.asystentnauczyciela.R

class MarksListAdapter(private val viewModel: MarksListViewModel,
                       private val context: Context?)
    : RecyclerView.Adapter<MarksListAdapter.MarksListHolder>() {

    inner class MarksListHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewMarkValue: TextView = view.findViewById(R.id.mark_value)
        val buttonViewMarkOption: TextView = view.findViewById(R.id.mark_options)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarksListHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_mark, parent, false)
        return MarksListHolder(view)
    }

    override fun onBindViewHolder(holder: MarksListHolder, position: Int) {
        holder.textViewMarkValue.text = viewModel.marks.value?.get(position)?.value.toString()

        holder.buttonViewMarkOption.setOnClickListener {
            val popup = PopupMenu(context, holder.buttonViewMarkOption)
            popup.inflate(R.menu.mark_options_menu)
            popup.setOnMenuItemClickListener { item: MenuItem? ->
                when (item!!.itemId) {
                    R.id.mark_delete -> {
                        viewModel.delete(viewModel.marks.value?.get(position)?.id!!)
                    }
                }
                true
            }
            popup.show()
        }
    }

    override fun getItemCount(): Int {
        return viewModel.marks.value?.size ?: 0
    }
}
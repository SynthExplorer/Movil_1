package com.example.room

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.room.databinding.ItemTareaBinding
import com.example.room.model.Tarea

class TareaAdapter(
    private val onTareaCompletada: (Tarea) -> Unit,
    private val onTareaEliminada: (Int) -> Unit
) : ListAdapter<Tarea, TareaAdapter.TareaViewHolder>(TareaDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TareaViewHolder {
        val binding = ItemTareaBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TareaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TareaViewHolder, position: Int) {
        val tarea = getItem(position)
        holder.bind(tarea)
    }

    inner class TareaViewHolder(private val binding: ItemTareaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(tarea: Tarea) {
            binding.textViewDescripcion.text = tarea.descripcion
            binding.checkBoxCompletada.isChecked = tarea.completada

            binding.checkBoxCompletada.isEnabled = !tarea.completada

            binding.checkBoxCompletada.setOnClickListener {
                if (!tarea.completada) {
                    onTareaCompletada(tarea)
                }
            }

            binding.buttonEliminar.setOnClickListener {
                onTareaEliminada(tarea.id)
            }
        }
    }

    class TareaDiffCallback : DiffUtil.ItemCallback<Tarea>() {
        override fun areItemsTheSame(oldItem: Tarea, newItem: Tarea): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Tarea, newItem: Tarea): Boolean {
            return oldItem == newItem
        }
    }
}
package com.example.room


import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.room.controller.TareaController
import com.example.room.databinding.ActivityMainBinding
import com.example.room.model.Tarea
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var tareaController: TareaController
    private lateinit var tareaAdapter: TareaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar el controlador
        tareaController = TareaController(this)

        // Configurar el RecyclerView y el adaptador
        setupRecyclerView()

        // Configurar el botón de agregar
        binding.buttonAgregar.setOnClickListener {
            val descripcion = binding.editTextTarea.text.toString()
            if (descripcion.isNotEmpty()) {
                lifecycleScope.launch {
                    tareaController.agregarTarea(descripcion)
                    binding.editTextTarea.text.clear()
                }
            } else {
                Toast.makeText(this, "La descripción no puede estar vacía", Toast.LENGTH_SHORT).show()
            }
        }

        // Observar cambios en la lista de tareas
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                tareaController.obtenerTareas().collectLatest { tareas ->
                    tareaAdapter.submitList(tareas)
                }
            }
        }
    }

    private fun setupRecyclerView() {
        tareaAdapter = TareaAdapter(
            onTareaCompletada = { tarea ->
                lifecycleScope.launch {
                    tareaController.completarTarea(tarea)
                }
            },
            onTareaEliminada = { id ->
                lifecycleScope.launch {
                    tareaController.eliminarTarea(id)
                }
            }
        )

        binding.recyclerViewTareas.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = tareaAdapter
        }
    }
}
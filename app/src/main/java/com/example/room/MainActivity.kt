package com.example.room

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.room.controller.TareaController
import com.example.room.databinding.ActivityMainBinding
<<<<<<< HEAD
import com.example.room.view.HomeFragment
=======
>>>>>>> 549d6bb6885fd90188fd0e99d5c1132b1b4d02b3
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

        tareaController = TareaController(this)
<<<<<<< HEAD
=======

>>>>>>> 549d6bb6885fd90188fd0e99d5c1132b1b4d02b3
        setupRecyclerView()

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

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                tareaController.obtenerTareas().collectLatest { tareas ->
                    tareaAdapter.submitList(tareas)
                }
            }
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, HomeFragment())
            .commit()
    }

    private fun setupRecyclerView() {
        tareaAdapter = TareaAdapter(
            onTareaCompletada = { tarea ->
                lifecycleScope.launch {
                    tareaController.completarTarea(tarea)

                    Toast.makeText(
                        this@MainActivity,
                        "Tarea completada: ${tarea.descripcion}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            onTareaEliminada = { id ->
                lifecycleScope.launch {
                    tareaController.eliminarTarea(id)

                    Toast.makeText(
                        this@MainActivity,
                        "Tarea eliminada",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )

        binding.recyclerViewTareas.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = tareaAdapter
        }
    }
}

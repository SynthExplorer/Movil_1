
package com.example.room.controller

import android.content.Context
import com.example.room.data.AppDatabase
import com.example.room.model.Tarea
import kotlinx.coroutines.flow.Flow

class TareaController(context: Context) {
    private val tareaDao = AppDatabase.getDatabase(context).tareaDao()

    fun obtenerTareas(): Flow<List<Tarea>> = tareaDao.obtenerTodas()

    suspend fun agregarTarea(descripcion: String) {
        val nuevaTarea = Tarea(descripcion = descripcion)
        tareaDao.insertar(nuevaTarea)
    }

    suspend fun completarTarea(tarea: Tarea) {
        tarea.completada = true
        tareaDao.actualizar(tarea)
    }

    suspend fun eliminarTarea(id: Int) {
        tareaDao.eliminarPorId(id)
    }
}
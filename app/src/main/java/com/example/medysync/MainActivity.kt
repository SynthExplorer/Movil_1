package com.example.medysync

import UserPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.content.Intent
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, FabFragment())
            .commit()

        val tvSaludo = findViewById<TextView>(R.id.tvSaludo)
        val userPreferences = UserPreferences(this)

        lifecycleScope.launch {
            userPreferences.nombreUsuario.collect { nombre ->
                userPreferences.apellidoUsuario.collect { apellido ->
                    userPreferences.idUsuario.collect { id ->
                        tvSaludo.text = "Bienvenido $nombre $apellido con cédula $id"
                    }
                }
            }
        }


    }
}
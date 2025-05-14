package com.example.room.view

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.room.data.UserPreferences
import com.example.room.databinding.ActivityRegisterBinding
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPreferences = UserPreferences(this)

        binding.btnGuardar.setOnClickListener {
            val name = binding.etNombre.text.toString()
            val lastname = binding.etApellido.text.toString()
            val cc = binding.etCc.text.toString()
            val email = binding.etEmail.text.toString()

                if (cc.isNotEmpty() && cc.all { it.isDigit() }) {

                if (email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                    lifecycleScope.launch {
                        userPreferences.saveUser(name, lastname, cc, email)
                        startActivity(Intent(this@RegisterActivity, ProfileActivity::class.java))
                        finish()
                    }
                } else {
                    Toast.makeText(this, "Correo electrónico inválido", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Cédula debe contener solo números", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

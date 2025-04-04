package com.example.medysync

import UserPreferences
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay
import kotlinx.coroutines.withContext

import kotlinx.coroutines.delay


class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
        userPreferences = UserPreferences(this)

        val etEmail = findViewById<EditText>(R.id.etRegisterEmail)
        val etPassword = findViewById<EditText>(R.id.etRegisterPassword)
        val etConfirmPassword = findViewById<EditText>(R.id.etConfirmPassword)

        val etNombre = findViewById<EditText>(R.id.etRegisterNombre)
        val etApellido = findViewById<EditText>(R.id.etRegisterApellido)
        val etId = findViewById<EditText>(R.id.etRegisterId)

        val btnRegister = findViewById<Button>(R.id.btnRegisterUser)
        val btnGoLogin = findViewById<Button>(R.id.btnGoLogin)

        btnRegister.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val confirmPassword = etConfirmPassword.text.toString().trim()

            val nombre = etNombre.text.toString().trim()
            val apellido = etApellido.text.toString().trim()
            val id = etId.text.toString().trim()

            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || id.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            } else if (password != confirmPassword) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            } else {
                registerUser(email, password, nombre, apellido, id)

            }
        }

        btnGoLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }



    }

    private fun registerUser(email: String, password: String, nombre: String, apellido: String, id: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){ task ->
                if (task.isSuccessful) {

                    lifecycleScope.launch {
                       val userPreferences = UserPreferences(this@RegisterActivity)
                        userPreferences.saveUserData(nombre, apellido, id)

                        delay(500)

                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@RegisterActivity, "Registro exitoso ¡Bienvenido! 🎉", Toast.LENGTH_SHORT).show()

                            startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
                            finish()
                        }
                    }
                } else {
                        Toast.makeText(this, "Error en el registro: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }

    }
}
package com.example.room.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.room.R
import com.example.room.data.UserPreferences
import com.example.room.databinding.ActivityProfileBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity() {


    private lateinit var binding: ActivityProfileBinding
    private lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, HomeFragment())
            .commit()


        userPreferences = UserPreferences(this)

        lifecycleScope.launch {
            userPreferences.userData.collectLatest { data ->
                binding.tvNombre.text = "Nombre: ${data["name"]}"
                binding.tvApellido.text = "Apellido: ${data["lastname"]}"
                binding.tvCc.text = "Cédula: ${data["cc"]}"
                binding.tvEmail.text = "Email: ${data["email"]}"
            }
        }
    }
}

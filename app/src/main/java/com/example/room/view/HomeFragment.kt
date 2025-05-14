package com.example.room.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.room.MainActivity
import com.example.room.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var isMenuOpen = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.btnMainActivity.visibility = View.GONE
        binding.btnPerfil.visibility = View.GONE

        binding.btnPrincipal.setOnClickListener {
            toggleMenu()
        }

        binding.btnMainActivity.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(intent)
        }

        binding.btnPerfil.setOnClickListener {
            val intent = Intent(requireContext(), ProfileActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startActivity(intent)
        }

        return binding.root
    }
    private fun toggleMenu() {
        if (isMenuOpen) {
            // Cerrar botones con animación
            binding.btnMainActivity.animate().alpha(0f).setDuration(300).withEndAction {
                binding.btnMainActivity.visibility = View.GONE
            }
            binding.btnPerfil.animate().alpha(0f).setDuration(300).withEndAction {
                binding.btnPerfil.visibility = View.GONE
            }
        } else {
            // Abrir botones con animación
            binding.btnMainActivity.visibility = View.VISIBLE
            binding.btnPerfil.visibility = View.VISIBLE
            binding.btnMainActivity.animate().alpha(1f).setDuration(300)
            binding.btnPerfil.animate().alpha(1f).setDuration(300)
        }
        isMenuOpen = !isMenuOpen
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

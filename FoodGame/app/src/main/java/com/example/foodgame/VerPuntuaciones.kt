package com.example.foodgame

import adaptador.PuntuacionAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodgame.databinding.ActivityVerPuntuacionesBinding
import auxiliar.Conexion

class VerPuntuaciones : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: ActivityVerPuntuacionesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityVerPuntuacionesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        val puntuaciones = Conexion.obtenerPuntuaciones(this)

        // Configurar el RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = PuntuacionAdapter(puntuaciones)
        if (puntuaciones != null) {
            recyclerView.adapter = PuntuacionAdapter(puntuaciones)
        }

        binding.btVolver2.setOnClickListener {
            finish()
        }
    }
}
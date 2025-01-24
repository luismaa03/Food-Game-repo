package com.example.foodgame

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.foodgame.databinding.ActivityResultadoCuestionarioBinding
import modelo.Puntuacion
import auxiliar.Conexion
import android.content.Intent

class ResultadoCuestionario : AppCompatActivity() {

    private lateinit var binding: ActivityResultadoCuestionarioBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultadoCuestionarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val puntuacion = intent.getIntExtra("puntuacion", 0)
        val totalPreguntas = intent.getIntExtra("totalPreguntas", 0)

        val tiempoTranscurrido = intent.getLongExtra("tiempoTranscurrido", 0)

        val preguntasFalladas = totalPreguntas - puntuacion

        binding.tvPuntuacion.text = "Puntuación: $puntuacion"
        binding.tvPreguntasAcertadas.text = "Preguntas acertadas: $puntuacion"
        binding.tvPreguntasFalladas.text = "Preguntas falladas: $preguntasFalladas"

        // Calcular y mostrar la puntuación general
        val puntuacionGeneral = if (tiempoTranscurrido > 0) {
            (puntuacion * 1000000 / tiempoTranscurrido).toInt() // Ejemplo: puntuación por cada 10 segundos
        } else {
            0 // Evitar división por cero
        }
        binding.tvPuntuacionGeneral.text = "Puntuación general: $puntuacionGeneral"
        Conexion.addPuntuacion(this, Puntuacion(puntos = puntuacionGeneral))

        binding.ibInicio.setOnClickListener {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }
    }
}
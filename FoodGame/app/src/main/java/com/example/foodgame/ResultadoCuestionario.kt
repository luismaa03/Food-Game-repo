package com.example.foodgame

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.foodgame.databinding.ActivityResultadoCuestionarioBinding
import modelo.Puntuacion
import auxiliar.Conexion

class ResultadoCuestionario : AppCompatActivity() {

    private lateinit var binding: ActivityResultadoCuestionarioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultadoCuestionarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recibir datos del Intent
        val correctas = intent.getIntExtra("correctas", 0)
        val incorrectas = intent.getIntExtra("incorrectas", 0)
        val tiempo = intent.getLongExtra("tiempo", 0)
        val puntuacionIngredientes = intent.getIntExtra("puntuacionIngredientes", 0)
        val totalPreguntas = intent.getIntExtra("totalPreguntas", 0)

        // Calcular puntuación total
        val puntuacionTotal = correctas + puntuacionIngredientes

        // Calcular puntuación general
        val puntuacionGeneral = if (tiempo > 0) {
            (puntuacionTotal * 1000000 / tiempo).toInt()
        } else {
            0
        }

        // Mostrar resultados
        binding.tvPuntuacion.text = "Puntuación General: $puntuacionGeneral"
        binding.tvPreguntasAcertadas.text = "Preguntas acertadas: $correctas"
        binding.tvPreguntasFalladas.text = "Preguntas falladas: $incorrectas"

        // Guardar puntuación en Firebase
        Conexion.addPuntuacion(this, Puntuacion(puntos = puntuacionGeneral))

        // Configurar el botón de inicio
        binding.ibInicio.setOnClickListener {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }
    }
}
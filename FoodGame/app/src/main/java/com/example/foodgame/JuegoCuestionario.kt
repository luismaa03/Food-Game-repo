package com.example.foodgame

import adaptador.PreguntaPagerAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.foodgame.databinding.ActivityJuegoCuestionarioBinding
import modelo.Plato
import modelo.Pregunta

class JuegoCuestionario : AppCompatActivity(), PreguntaFragment.RespuestaSeleccionadaListener {

    private lateinit var binding: ActivityJuegoCuestionarioBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var btEnviar: Button
    private lateinit var preguntas: List<Pregunta>
    private var correctas = 0
    private val respuestas = mutableMapOf<Int, String>()
    private lateinit var selectedPlato: Plato

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJuegoCuestionarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        selectedPlato = intent.getParcelableExtra("selectedPlato")!!
        preguntas = selectedPlato.preguntas
        Toast.makeText(this, "Número de preguntas: ${preguntas.size}", Toast.LENGTH_SHORT).show()

        // Inicializar ViewPager2 y botón Enviar
        viewPager = binding.viewPager
        btEnviar = binding.btEnviar

        // El botón siempre es visible, pero empieza deshabilitado
        btEnviar.isEnabled = false
        btEnviar.visibility = View.VISIBLE

        // Configurar ViewPager2 y su adaptador
        viewPager.adapter = PreguntaPagerAdapter(this, preguntas, this)
        viewPager.offscreenPageLimit = preguntas.size // Evita que se destruyan los fragmentos

        // Configurar el botón enviar
        btEnviar.setOnClickListener {
            onAllQuestionsAnswered()
            val intent = Intent(this, JuegoIngredientes::class.java)
            intent.putExtra("plato", selectedPlato) // Pasa el objeto Plato
            startActivity(intent)
        }
    }

    override fun onRespuestaSeleccionada(respuesta: String, position: Int) {
        Log.d("JuegoCuestionario", "Respuesta seleccionada en posición: $position - $respuesta")

        // Guardar o eliminar la respuesta
        if (respuesta.isNotEmpty()) {
            respuestas[position] = respuesta
        } else {
            respuestas.remove(position)
        }

        Log.d("JuegoCuestionario", "Total de respuestas registradas: ${respuestas.size} de ${preguntas.size}")

        // Verificar si todas las preguntas han sido respondidas
        actualizarBotonEnviar()
    }

    private fun actualizarBotonEnviar() {
        runOnUiThread {
            val todasRespondidas = respuestas.size == preguntas.size
            btEnviar.isEnabled = todasRespondidas
            Log.d("JuegoCuestionario", "Botón enviar habilitado: ${btEnviar.isEnabled}")
        }
    }

    private fun verificarRespuestas() {
        if (respuestas.size != preguntas.size) {
            Toast.makeText(this, "Debes responder a todas las preguntas", Toast.LENGTH_SHORT).show()
            return
        }

        correctas = 0 // Reiniciar el contador de respuestas correctas
        for ((position, respuestaSeleccionada) in respuestas) {
            val pregunta = preguntas[position]
            if (respuestaSeleccionada == pregunta.respuestaCorrecta) {
                incrementarPuntuacion()
            }
        }
        Toast.makeText(this, "Has acertado $correctas de ${preguntas.size}", Toast.LENGTH_SHORT).show()
    }

    private fun incrementarPuntuacion() {
        correctas++
    }

    private fun onAllQuestionsAnswered() {
        if (respuestas.size == preguntas.size) {
            verificarRespuestas()
        }
    }
}
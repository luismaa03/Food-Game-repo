package com.example.foodgame

import adaptador.PreguntaPagerAdapter
import android.content.Intent
import android.os.Bundle
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
    private var respuestasDadas = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJuegoCuestionarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val selectedPlato = intent.getParcelableExtra<Plato>("selectedPlato")
        preguntas = selectedPlato?.preguntas ?: emptyList()

        // Inicializar viewPager y btEnviar
        viewPager = binding.viewPager
        btEnviar = binding.btEnviar
        //btEnviar.visibility = View.GONE // Ocultar el botón inicialmente

        viewPager.adapter = PreguntaPagerAdapter(this, preguntas)

        // Establecer el listener en cada fragmento
        for (i in 0 until preguntas.size) {
            val fragment = supportFragmentManager.findFragmentByTag("f$i") as? PreguntaFragment
            fragment?.setRespuestaSeleccionadaListener(this)
        }

        btEnviar.setOnClickListener {
            onAllQuestionsAnswered()
            val intent = Intent(this, JuegoIngredientes::class.java)
            intent.putExtra("selectedPlato", selectedPlato)
            startActivity(intent)
        }
    }

    override fun onRespuestaSeleccionada(respuesta: String, position: Int) {
        if (!respuestas.containsKey(position)) {
            respuestasDadas++
        }
        respuestas[position] = respuesta

        // Comprobar si todas las preguntas están respondidas
        btEnviar.visibility = if (respuestasDadas == preguntas.size) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private fun verificarRespuestas() {
        if (respuestasDadas != preguntas.size) {
            Toast.makeText(this, "Debes responder a todas las preguntas", Toast.LENGTH_SHORT).show()
            return
        }

        for ((position, respuestaSeleccionada) in respuestas) {
            val pregunta = preguntas[position]
            if (respuestaSeleccionada == pregunta.respuestaCorrecta) {
                incrementarPuntuacion()
            }
        }
        Toast.makeText(this, "Has acertado $correctas de ${preguntas.size}", Toast.LENGTH_SHORT).show()
        finish()
    }

    fun incrementarPuntuacion() {
        correctas++
    }

    fun onAllQuestionsAnswered() {
        if (respuestasDadas == preguntas.size) {
            verificarRespuestas()
        }
    }
}
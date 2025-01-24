package com.example.foodgame

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.foodgame.databinding.ActivityJuegoCuestionarioBinding
import modelo.Pregunta
import modelo.TipoPregunta

class JuegoCuestionario : AppCompatActivity() {

    private lateinit var binding: ActivityJuegoCuestionarioBinding
    private var preguntas: List<Pregunta> = listOf(
        Pregunta("¿Cuál de estos ingredientes es una buena fuente de fibra?", listOf("Pan", "Aguacate", "Tomate"), 0, TipoPregunta.OPCION_MULTIPLE),
        Pregunta("¿Qué ingrediente tiene más proteínas?", listOf("Huevo", "Leche", "Pan"), 0, TipoPregunta.OPCION_MULTIPLE),
        Pregunta("¿Cuál de las siguientes afirmaciones sobre el tomate es correcta?", listOf("El tomate es una verdura rica en proteínas", "El tomate es una fruta que contiene licopeno, un antioxidante beneficioso", "El tomate debe consumirse siempre sin cocer, ya que pierde sus nutrientes al cocinarse"), 1, TipoPregunta.OPCION_MULTIPLE),
    )
    private var preguntaActual = 0
    private var tiempoInicio: Long = 0
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: PreguntaPagerAdapter
    private var avanzar = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJuegoCuestionarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tiempoInicio = System.currentTimeMillis() // Guardar el tiempo de inicio

        viewPager = binding.viewPager
        adapter = PreguntaPagerAdapter(this, preguntas)
        viewPager.adapter = adapter

        binding.btEnviar.setOnClickListener {
            avanzar = true
            verificarRespuesta()
        }
    }

    private fun verificarRespuesta() {
        // Verificar si se han respondido todas las preguntas
        var todasRespondidas = true
        for (i in 0 until preguntas.size) {
            val fragmentTag = "f$i" // Etiqueta para identificar el fragmento
            val fragment = supportFragmentManager.findFragmentByTag(fragmentTag) as? PreguntaFragment
            if (fragment != null) {
                val respuesta = fragment.obtenerRespuestaSeleccionada()
                if (respuesta == -1) {
                    todasRespondidas = false
                    break
                }
            }
        }

        if (!todasRespondidas) {
            Toast.makeText(this, "Responde a todas las preguntas antes de enviar", Toast.LENGTH_SHORT).show()
            return
        }

        // Si todas las preguntas están respondidas, proceder a la siguiente lógica
        if (todasRespondidas && avanzar) {
            if (preguntaActual < preguntas.size - 1) {
                // Si no es la última pregunta, avanzar a la siguiente
                viewPager.currentItem = preguntaActual + 1
                preguntaActual++
                avanzar = false
            } else {
                // Si es la última pregunta, mostrar el resultado final
                val puntuacion = calcularPuntuacion()
                val tiempoTranscurrido = System.currentTimeMillis() - tiempoInicio

                // Crear el Intent y iniciar la nueva actividad
                val intent = Intent(this, ResultadoCuestionario::class.java)
                intent.putExtra("puntuacion", puntuacion)
                intent.putExtra("totalPreguntas", preguntas.size)
                intent.putExtra("tiempoTranscurrido", tiempoTranscurrido)
                startActivity(intent)
                finish() // Cierra la actividad actual
            }
        }
    }

    private fun calcularPuntuacion(): Int {
        var puntuacion = 0
        for (i in preguntas.indices) {
            val fragmentTag = "f$i" // Etiqueta para identificar el fragmento
            val fragment = supportFragmentManager.findFragmentByTag(fragmentTag) as? PreguntaFragment
            if (fragment != null) {
                if (fragment.obtenerRespuestaSeleccionada() == preguntas[i].respuestaCorrecta) {
                    puntuacion++
                }
            }
        }
        return puntuacion
    }

    inner class PreguntaPagerAdapter(fragmentActivity: FragmentActivity, private val preguntas: List<Pregunta>) :
        FragmentStateAdapter(fragmentActivity) {

        override fun getItemCount(): Int = preguntas.size

        override fun createFragment(position: Int): Fragment {
            val fragment = PreguntaFragment.newInstance(preguntas[position])
            fragment.retainInstance = true
            return fragment
        }
    }
}
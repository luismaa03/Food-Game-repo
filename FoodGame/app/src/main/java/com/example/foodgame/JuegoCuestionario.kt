package com.example.foodgame

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.foodgame.databinding.ActivityJuegoCuestionarioBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import modelo.Pregunta
import modelo.TipoPregunta

class JuegoCuestionario : AppCompatActivity() {

    private lateinit var binding: ActivityJuegoCuestionarioBinding
    private var preguntas: MutableList<Pregunta> = mutableListOf()
    private var preguntaActual = 0
    private var tiempoInicio: Long = 0
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: PreguntaPagerAdapter
    private var avanzar = false
    private val db = Firebase.firestore
    private val preguntasCollection = db.collection("preguntas")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJuegoCuestionarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tiempoInicio = System.currentTimeMillis() // Guardar el tiempo de inicio

        viewPager = binding.viewPager
        adapter = PreguntaPagerAdapter(this, preguntas)
        viewPager.adapter = adapter

        // Obtener el tipo de plato de la actividad anterior
        val tipoPlato = intent.getStringExtra("tipoPlato") ?: "desayunos" // Valor por defecto si no se recibe nada
        val nombrePlato = intent.getStringExtra("nombrePlato") ?: ""

        obtenerPreguntas(tipoPlato, nombrePlato)
    }

    private fun obtenerPreguntas(tipoPlato: String, nombrePlato: String) {
        preguntasCollection
            .whereEqualTo("tipoPlato", tipoPlato)
            .whereEqualTo("nombrePlato", nombrePlato)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val pregunta = document.getString("pregunta") ?: ""
                    val respuestas = document.get("respuestas") as? List<String> ?: emptyList()
                    val respuestaCorrecta = document.getString("respuestaCorrecta") ?: ""
                    val tipo = document.getString("tipo") ?: TipoPregunta.OPCION_MULTIPLE.name
                    val tipoPregunta = TipoPregunta.valueOf(tipo)

                    preguntas.add(Pregunta(pregunta, respuestas, respuestaCorrecta, tipoPregunta))
                }
                if (preguntas.isEmpty()) {
                    Toast.makeText(this, "No hay preguntas para este plato", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    adapter.notifyDataSetChanged()
                }
            }
            .addOnFailureListener { exception ->
                Log.w("JuegoCuestionario", "Error getting documents: ", exception)
                Toast.makeText(this, "Error al obtener las preguntas", Toast.LENGTH_SHORT).show()
                finish()
            }
    }
}

class PreguntaPagerAdapter(fragmentActivity: FragmentActivity, private val preguntas: List<Pregunta>) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = preguntas.size

    override fun createFragment(position: Int): Fragment {
        return PreguntaFragment.newInstance(preguntas[position])
    }
}
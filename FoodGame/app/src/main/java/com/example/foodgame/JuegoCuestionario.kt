package com.example.foodgame

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.foodgame.databinding.ActivityJuegoCuestionarioBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import modelo.Plato
import modelo.Pregunta

class JuegoCuestionario : AppCompatActivity() {

    private lateinit var binding: ActivityJuegoCuestionarioBinding
    private var preguntas: MutableList<Pregunta> = mutableListOf()
    private var preguntaActual = 0
    private var tiempoInicio: Long = 0
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: PreguntaPagerAdapter // Importa PreguntaPagerAdapter
    private var avanzar = false
    private val db = Firebase.firestore
    private val preguntasCollection = db.collection("preguntas")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJuegoCuestionarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tiempoInicio = System.currentTimeMillis() // Guardar el tiempo de inicio

        viewPager = binding.viewPager
        adapter = PreguntaPagerAdapter(this, preguntas) // Usa PreguntaPagerAdapter
        viewPager.adapter = adapter

        // Obtener el plato seleccionado
        val selectedPlato = intent.getParcelableExtra<Plato>("selectedPlato")

        if (selectedPlato != null) {
            // Usar las preguntas del plato
            preguntas.addAll(selectedPlato.preguntas)

            if (preguntas.isEmpty()) {
                Toast.makeText(this, "No hay preguntas para este plato", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                adapter.notifyDataSetChanged()
            }
        } else {
            Toast.makeText(this, "No se ha seleccionado ningún plato", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
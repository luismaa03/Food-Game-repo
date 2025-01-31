package com.example.foodgame

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.foodgame.databinding.ActivityJuegoCuestionarioBinding
import modelo.Plato
import modelo.Pregunta

class JuegoCuestionario : AppCompatActivity() {

    private lateinit var binding: ActivityJuegoCuestionarioBinding
    private var preguntas: MutableList<Pregunta> = mutableListOf()
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: PreguntaPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJuegoCuestionarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewPager = binding.viewPager

        // Obtener el plato seleccionado
        val selectedPlato = intent.getParcelableExtra<Plato>("selectedPlato")

        if (selectedPlato != null) {
            // Usar las preguntas del plato
            preguntas.addAll(selectedPlato.preguntas)

            if (preguntas.isEmpty()) {
                Toast.makeText(this, "No hay preguntas para este plato", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                adapter = PreguntaPagerAdapter(this, preguntas)
                viewPager.adapter = adapter
            }
        } else {
            Toast.makeText(this, "No se ha seleccionado ningún plato", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
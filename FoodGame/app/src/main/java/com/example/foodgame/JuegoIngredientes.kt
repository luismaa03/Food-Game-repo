package com.example.foodgame

import adaptador.IngredientesAdapter
import android.content.Intent
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodgame.databinding.ActivityJuegoIngredientesBinding

class JuegoIngredientes : AppCompatActivity() {

    private lateinit var binding: ActivityJuegoIngredientesBinding

    // Listas para los ingredientes
    private lateinit var ingredientesReceta: List<String> // Ingredientes correctos de la receta
    private lateinit var ingredientesMostrados: MutableList<String> // Lista de ingredientes mezclados
    private val ingredientesSeleccionados = mutableListOf<String>() // Ingredientes seleccionados por el usuario

    // Detector de gestos para manejar eventos de pulsaciones largas
    private lateinit var gestureDetector: GestureDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJuegoIngredientesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recupera los ingredientes de la receta desde el Intent
        ingredientesReceta = intent.getStringExtra("ingredientes")?.split(",")?.map { it.trim() } ?: emptyList()

        // Agrega ingredientes incorrectos y mezcla todos
        ingredientesMostrados = ingredientesReceta.toMutableList()
        ingredientesMostrados.addAll(listOf("Sal", "Pimienta", "Aceite de girasol", "Tomate", "Pan", "Aguacate", "Aceite de oliva", "Cebolla", "Lechuga"))
        ingredientesMostrados.shuffle()

        // Configuración del RecyclerView
        val adapter = IngredientesAdapter(ingredientesMostrados, this::onIngredienteClick)
        binding.rvIngredientes.adapter = adapter
        binding.rvIngredientes.layoutManager = GridLayoutManager(this, 3) // Configura un diseño de cuadrícula con 3 columnas

        // Botón para verificar la selección del usuario
        binding.btVerificar.setOnClickListener {
            verificarIngredientes()
        }

        // Botón para navegar a la siguiente actividad
        binding.ibFlecha.setOnClickListener {
            val intent = Intent(this, JuegoCuestionario::class.java)
            startActivity(intent)
        }

        // Configuración del detector de gestos para pulsaciones largas
        gestureDetector = GestureDetector(this, object : GestureDetector.SimpleOnGestureListener() {
            override fun onLongPress(e: MotionEvent) {
                val child = binding.rvIngredientes.findChildViewUnder(e.x, e.y)
                if (child != null) {
                    val position = binding.rvIngredientes.getChildAdapterPosition(child)
                    val ingrediente = ingredientesMostrados[position]
                    mostrarInformacionNutricional(ingrediente) // Muestra la información nutricional del ingrediente
                }
            }
        })

        // Agrega el detector de gestos al RecyclerView
        binding.rvIngredientes.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                gestureDetector.onTouchEvent(e)
                return false
            }

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
        })
    }

    // Maneja los clics en los ingredientes
    private fun onIngredienteClick(ingrediente: String, position: Int) {
        if (ingredientesSeleccionados.contains(ingrediente)) {
            ingredientesSeleccionados.remove(ingrediente)
        } else {
            ingredientesSeleccionados.add(ingrediente)
        }
    }

    // Verifica si los ingredientes seleccionados son correctos
    private fun verificarIngredientes() {
        binding.ibFlecha.visibility = View.GONE

        val ingredientesCorrectos = listOf("Pan", "Aguacate")

        // Obtiene la lista de ingredientes seleccionados desde el adaptador
        val ingredientesSeleccionadosAdapter = (binding.rvIngredientes.adapter as IngredientesAdapter).getSelectedItems()

        if (ingredientesSeleccionadosAdapter.containsAll(ingredientesCorrectos) &&
            ingredientesSeleccionadosAdapter.size == ingredientesCorrectos.size
        ) {
            // Si todos los ingredientes correctos fueron seleccionados
            Toast.makeText(
                this,
                "¡Correcto! Has seleccionado todos los ingredientes",
                Toast.LENGTH_SHORT
            ).show()
            binding.ibFlecha.visibility = View.VISIBLE
            binding.btVerificar.visibility = View.GONE
        } else {
            Toast.makeText(this, "Incorrecto. Inténtalo de nuevo.", Toast.LENGTH_SHORT).show()
        }
    }

    // Muestra la información nutricional de un ingrediente
    private fun mostrarInformacionNutricional(ingrediente: String) {
        val informacion = obtenerInformacionNutricional(ingrediente)
        AlertDialog.Builder(this)
            .setTitle(ingrediente)
            .setMessage(informacion)
            .setPositiveButton("Aceptar", null)
            .show()
    }

    // Retorna la información nutricional de los ingredientes conocidos
    private fun obtenerInformacionNutricional(ingrediente: String): String {
        return when (ingrediente) {
            "Pan" -> "Calorías: 265 kcal por 100 g\nCarbohidratos: 49 g\nProteínas: 9 g\nGrasas: 3.2 g\nFibra: 2.7 g\nFuente de carbohidratos complejos y energía."
            "Aguacate" -> "Calorías: 160 kcal por 100 g\nGrasas: 15 g (principalmente monoinsaturadas)\nCarbohidratos: 8.5 g\nProteínas: 2 g\nFibra: 6.7 g\nRico en grasas saludables, vitamina E y potasio."
            "Sal" -> "Calorías: 0 kcal\nSodio: 38758 mg por 100 g\nEsencial para el equilibrio de electrolitos; consumir con moderación."
            "Pimienta" -> "Calorías: 255 kcal por 100 g\nCarbohidratos: 64 g\nProteínas: 10 g\nGrasas: 3.3 g\nAporta un toque picante y antioxidantes."
            "Aceite de girasol" -> "Calorías: 884 kcal por 100 g\nGrasas: 100 g (principalmente poliinsaturadas)\nRico en vitamina E y grasas saludables."
            "Tomate" -> "Calorías: 18 kcal por 100 g\nCarbohidratos: 3.9 g\nProteínas: 0.9 g\nGrasas: 0.2 g\nFibra: 1.2 g\nFuente de vitamina C y antioxidantes como el licopeno."
            "Cebolla" -> "Calorías: 40 kcal por 100 g\nCarbohidratos: 9.3 g\nProteínas: 1.1 g\nGrasas: 0.1 g\nFibra: 1.7 g\nRica en antioxidantes y compuestos que fortalecen el sistema inmunológico."
            "Lechuga" -> "Calorías: 15 kcal por 100 g\nCarbohidratos: 2.9 g\nProteínas: 1.4 g\nGrasas: 0.2 g\nFibra: 1.3 g\nBaja en calorías y buena fuente de vitaminas A y K."
            else -> "Información nutricional no disponible."
        }
    }
}

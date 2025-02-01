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
import modelo.Ingrediente
import modelo.Plato
import modelo.PlatoData

class JuegoIngredientes : AppCompatActivity() {

    private lateinit var binding: ActivityJuegoIngredientesBinding
    private lateinit var ingredientesReceta: List<Ingrediente>
    private lateinit var ingredientesMostrados: MutableList<String>
    private val ingredientesSeleccionados = mutableListOf<String>()
    private lateinit var gestureDetector: GestureDetector
    private lateinit var btVerificar: View
    private lateinit var selectedPlato: Plato

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJuegoIngredientesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        btVerificar = binding.btVerificar

        // Recibir el plato seleccionado del intent
        selectedPlato = intent.getParcelableExtra("selectedPlato")!!

        // Obtener los ingredientes correctos para el plato seleccionado
        ingredientesReceta = obtenerIngredientesCorrectos(selectedPlato)

        // Configuración del RecyclerView de ingredientes
        ingredientesMostrados = obtenerNombresIngredientesMezclados(ingredientesReceta)
        val adapter = IngredientesAdapter(ingredientesMostrados, this::onIngredienteClick)
        binding.rvIngredientes.adapter = adapter
        binding.rvIngredientes.layoutManager = GridLayoutManager(this, 3)

        // Configuración del botón verificar
        btVerificar.setOnClickListener {
            verificarIngredientes()
        }

        // Configuración del botón de flecha
        binding.ibFlecha.setOnClickListener {
            val intent = Intent(this, ResultadoCuestionario::class.java)
            startActivity(intent)
        }

        // Configuración del detector de gestos
        gestureDetector = GestureDetector(this, object : GestureDetector.SimpleOnGestureListener() {
            override fun onLongPress(e: MotionEvent) {
                val child = binding.rvIngredientes.findChildViewUnder(e.x, e.y)
                if (child != null) {
                    val position = binding.rvIngredientes.getChildAdapterPosition(child)
                    val ingrediente = ingredientesMostrados[position]
                    mostrarInformacionNutricional(ingrediente)
                }
            }
        })

        binding.rvIngredientes.addOnItemTouchListener(object :
            RecyclerView.OnItemTouchListener {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                gestureDetector.onTouchEvent(e)
                return false
            }

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
        })
    }

    // Función para obtener los ingredientes correctos para un plato específico
    private fun obtenerIngredientesCorrectos(plato: Plato): List<Ingrediente> {
        // Usar la lista de ingredientes del plato directamente desde PlatoData
        return PlatoData.getPlatoIngredientes(plato.nombre)
    }

    private fun obtenerNombresIngredientesMezclados(ingredientesCorrectos: List<Ingrediente>): MutableList<String> {
        val nombresIngredientes = ingredientesCorrectos.map { it.nombre }.toMutableList()
        // Añadir ingredientes incorrectos relacionados con los platos
        val ingredientesIncorrectos = listOf(
            "Jamón", "Queso", "Atún", "Lentejas", "Brócoli", "Pimiento", "Yogur", "Granola", "Mantequilla", "Cereales", "Nueces", "Miel", "Chocolate", "Café", "Zumo", "Refresco"
        )
        val ingredientesIncorrectosFiltrados = ingredientesIncorrectos.filter { !nombresIngredientes.contains(it) }
        nombresIngredientes.addAll(ingredientesIncorrectosFiltrados.shuffled().take(5))
        nombresIngredientes.shuffle()
        return nombresIngredientes
    }

    private fun onIngredienteClick(ingrediente: String, position: Int) {
        if (ingredientesSeleccionados.contains(ingrediente)) {
            ingredientesSeleccionados.remove(ingrediente)
        } else {
            ingredientesSeleccionados.add(ingrediente)
        }
    }

    private fun verificarIngredientes() {
        val ingredientesCorrectos = ingredientesReceta.map { it.nombre }
        val ingredientesSeleccionadosAdapter = (binding.rvIngredientes.adapter as IngredientesAdapter).getSelectedItems()

        if (ingredientesSeleccionadosAdapter.containsAll(ingredientesCorrectos) &&
            ingredientesSeleccionadosAdapter.size == ingredientesCorrectos.size
        ) {
            Toast.makeText(
                this,
                "¡Correcto! Has seleccionado todos los ingredientes",
                Toast.LENGTH_SHORT
            ).show()
            binding.ibFlecha.visibility = View.VISIBLE
            btVerificar.visibility = View.GONE
        } else {
            Toast.makeText(this, "Incorrecto. Inténtalo de nuevo.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun mostrarInformacionNutricional(ingrediente: String) {
        val ingredienteEncontrado = ingredientesReceta.find { it.nombre == ingrediente }
        val informacion = ingredienteEncontrado?.informacionNutricional ?: obtenerInformacionNutricional(ingrediente)
        AlertDialog.Builder(this)
            .setTitle(ingrediente)
            .setMessage(informacion)
            .setPositiveButton("Aceptar", null)
            .show()
    }

    private fun obtenerInformacionNutricional(ingrediente: String): String {
        return when (ingrediente) {
            "Jamón" -> "Calorías: 145 kcal por 100g\nProteínas: 22g\nGrasas: 6g\nFuente de proteínas y vitaminas del grupo B."
            "Queso" -> "Calorías: 350-400 kcal por 100g\nProteínas: 25g\nGrasas: 30g\nFuente de calcio y proteínas."
            "Atún" -> "Calorías: 130 kcal por 100g\nProteínas: 29g\nGrasas: 2g\nFuente de proteínas y omega-3."
            "Lentejas" -> "Calorías: 116 kcal por 100g\nProteínas: 9g\nCarbohidratos: 20g\nFibra: 8g\nFuente de proteínas vegetales y fibra."
            "Brócoli" -> "Calorías: 34 kcal por 100g\nProteínas: 2.8g\nCarbohidratos: 6.6g\nFibra: 2.6g\nFuente de vitaminas C y K."
            "Pimiento" -> "Calorías: 31 kcal por 100g\nProteínas: 1g\nCarbohidratos: 6g\nFibra: 2.1g\nFuente de vitaminas A y C."
            "Yogur" -> "Calorías: 60 kcal por 100g\nProteínas: 3.5g\nGrasas: 3.3g\nCarbohidratos: 4.7g\nFuente de calcio y probióticos."
            "Granola" -> "Calorías: 471 kcal por 100g\nCarbohidratos: 60g\nProteínas: 10g\nGrasas: 20g\nFibra: 8g\nFuente de fibra y energía."
            "Mantequilla" -> "Calorías: 717 kcal por 100g\nGrasas: 81g\nFuente de grasas saturadas y vitaminas A y D."
            "Cereales" -> "Calorías: 350-400 kcal por 100g\nCarbohidratos: 70-80g\nProteínas: 6-10g\nFibra: 5-10g\nFuente de carbohidratos y fibra."
            "Nueces" -> "Calorías: 654 kcal por 100g\nGrasas: 65g\nProteínas: 15g\nFibra: 7g\nFuente de grasas saludables y proteínas."
            "Miel" -> "Calorías: 304 kcal por 100g\nCarbohidratos: 82g\nFuente de azúcares naturales y antioxidantes."
            "Chocolate" -> "Calorías: 500-600 kcal por 100g\nGrasas: 30-40g\nCarbohidratos: 50-60g\nFuente de antioxidantes y energía."
            "Café" -> "Calorías: 2 kcal por 100ml\nCafeína: 40mg por 100ml\nFuente de cafeína y antioxidantes."
            "Zumo" -> "Calorías: 40-50 kcal por 100ml\nCarbohidratos: 10-12g\nFuente de vitaminas y azúcares naturales."
            "Refresco" -> "Calorías: 40-50 kcal por 100ml\nCarbohidratos: 10-12g\nFuente de azúcares añadidos."
            "Huevos" -> "Calorías: 143 kcal por 100 g\nProteínas: 13 g\nGrasas: 9.5 g\nVitaminas: A, D, E, B12\nMinerales: Hierro, zinc"
            "Leche" -> "Calorías: 42 kcal por 100 ml\nProteínas: 3.4 g\nGrasas: 1 g\nCarbohidratos: 5 g\nVitaminas: A, D, B12\nMinerales: Calcio"
            "Sal" -> "Minerales: Sodio, yodo (en sal yodada)"
            "Pimienta" -> "Antioxidantes: Piperina"
            "Pan" -> "Calorías: 265 kcal por 100 g\nCarbohidratos: 49 g\nProteínas: 9 g\nGrasas: 3.2 g\nFibra: 2.7 g\nFuente de carbohidratos complejos y energía."
            "Aguacate" -> "Calorías: 160 kcal por 100 g\nGrasas: 15 g (principalmente monoinsaturadas)\nCarbohidratos: 8.5 g\nProteínas: 2 g\nFibra: 6.7 g\nRico en grasas saludables, vitamina E y potasio."
            "Tomate" -> "Calorías: 18 kcal por 100 g\nCarbohidratos: 3.9 g\nProteínas: 0.9 g\nGrasas: 0.2 g\nFibra: 1.2 g\nFuente de vitamina C y antioxidantes como el licopeno."
            "Aceite de oliva" -> "Calorías: 884 kcal por 100 g\nGrasas: 100 g (principalmente monoinsaturadas)\nAntioxidantes: Polifenoles"
            "Espinacas" -> "Calorías: 23 kcal por 100 g\nCarbohidratos: 3.6 g\nProteínas: 2.9 g\nFibra: 2.2 g\nVitaminas: A, C, K\nMinerales: Hierro, calcio"
            "Plátano" -> "Calorías: 89 kcal por 100 g\nCarbohidratos: 23 g\nProteínas: 1.1 g\nFibra: 2.6 g\nVitaminas: B6, C\nMinerales: Potasio"
            "Leche de almendras" -> "Calorías: 13 kcal por 100 ml\nGrasas: 1.1 g\nCarbohidratos: 0.2 g\nProteínas: 0.5 g\nVitaminas: E, D\nMinerales: Calcio"
            "Semillas de chía" -> "Calorías: 486 kcal por 100 g\nGrasas: 31 g\nCarbohidratos: 42 g\nProteínas: 17 g\nFibra: 34 g\nMinerales: Calcio, magnesio\nÁcidos grasos: Omega-3"
            "Arándanos" -> "Calorías: 57 kcal por 100 g\nCarbohidratos: 14.5 g\nProteínas: 0.7 g\nFibra: 2.4 g\nVitaminas: C, K\nAntioxidantes"
            "Pollo" -> "Calorías: 165 kcal por 100 g\nProteínas: 31 g\nGrasas: 3.6 g\nFuente de proteínas magras."
            "Patatas" -> "Calorías: 77 kcal por 100 g\nCarbohidratos: 17 g\nProteínas: 2 g\nFibra: 2.2 g\nVitaminas: C, B6\nMinerales: Potasio"
            "Cebolla" -> "Calorías: 40 kcal por 100 g\nCarbohidratos: 9.3 g\nProteínas: 1.1 g\nGrasas: 0.1 g\nFibra: 1.7 g\nRica en antioxidantes y compuestos que fortalecen el sistema inmunológico."
            "Ajo" -> "Calorías: 149 kcal por 100 g\nCarbohidratos: 33 g\nProteínas: 6.4 g\nFibra: 2.1 g\nAntioxidantes: Allicina"
            "Tofu" -> "Calorías: 76 kcal por 100 g\nProteínas: 8 g\nGrasas: 4.8 g\nCarbohidratos: 1.9 g\nMinerales: Calcio, hierro"
            "Zanahoria" -> "Calorías: 41 kcal por 100 g\nCarbohidratos: 9.6 g\nProteínas: 0.9 g\nFibra: 2.8 g\nVitaminas: A, K\nAntioxidantes: Betacaroteno"
            "Pasta" -> "Calorías: 131 kcal por 100 g (cocida)\nCarbohidratos: 25 g\nProteínas: 5 g\nGrasas: 1 g"
            "Lechuga" -> "Calorías: 15 kcal por 100 g\nCarbohidratos: 2.9 g\nProteínas: 1.4 g\nGrasas: 0.2 g\nFibra: 1.3 g\nBaja en calorías y buena fuente de vitaminas A y K."
            else -> "Información nutricional no disponible."
        }
    }
}
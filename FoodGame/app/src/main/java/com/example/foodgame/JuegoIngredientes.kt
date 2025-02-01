package com.example.foodgame

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodgame.databinding.ActivityJuegoIngredientesBinding
import modelo.Ingrediente
import modelo.Plato
import modelo.PlatoData
import adaptador.IngredientesAdapter

class JuegoIngredientes : AppCompatActivity() {

    private lateinit var binding: ActivityJuegoIngredientesBinding
    private lateinit var ingredientesAdapter: IngredientesAdapter
    private lateinit var plato: Plato
    private var ingredientesCorrectos: List<Ingrediente> = emptyList()
    private var ingredientesMezclados: List<Ingrediente> = emptyList()
    private var ingredientesSeleccionados: MutableList<Ingrediente> = mutableListOf()
    private var correctas: Int = 0
    private var incorrectas: Int = 0
    private var tiempo: Long = 0
    private var totalPreguntas: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJuegoIngredientesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        plato = intent.getParcelableExtra("plato")!!
        correctas = intent.getIntExtra("correctas", 0)
        incorrectas = intent.getIntExtra("incorrectas", 0)
        tiempo = intent.getLongExtra("tiempo", 0)
        totalPreguntas = intent.getIntExtra("totalPreguntas", 0)

        ingredientesCorrectos = obtenerIngredientesCorrectos(plato)
        ingredientesMezclados = obtenerIngredientesMezclados(plato)

        ingredientesAdapter = IngredientesAdapter(ingredientesMezclados, ingredientesSeleccionados,
            onIngredienteClick = { ingrediente ->
                onIngredienteClick(ingrediente)
            },
            onIngredienteLongClick = { ingrediente ->
                mostrarInformacionNutricional(ingrediente)
            }
        )

        binding.rvIngredientes.apply {
            layoutManager = GridLayoutManager(this@JuegoIngredientes, 2)
            adapter = ingredientesAdapter
        }

        binding.btVerificar.setOnClickListener {
            verificarIngredientes()
        }
    }

    private fun obtenerIngredientesCorrectos(plato: Plato): List<Ingrediente> {
        return PlatoData.getPlatoIngredientes(plato.nombre)
    }

    private fun obtenerIngredientesMezclados(plato: Plato): List<Ingrediente> {
        val ingredientesCorrectos = obtenerIngredientesCorrectos(plato)
        val ingredientesIncorrectos = obtenerIngredientesIncorrectos()
        return (ingredientesCorrectos + ingredientesIncorrectos).shuffled()
    }

    private fun obtenerIngredientesIncorrectos(): List<Ingrediente> {
        val ingredientesIncorrectos = listOf(
            Ingrediente("jamon", "Jamón", "50g", "Calorías: 145 kcal por 100 g\nProteínas: 22 g\nGrasas: 6 g\nVitaminas: B1, B3, B6"),
            Ingrediente("queso", "Queso", "50g", "Calorías: 402 kcal por 100 g\nProteínas: 25 g\nGrasas: 33 g\nVitaminas: A, D\nMinerales: Calcio"),
            Ingrediente("atun", "Atún", "80g", "Calorías: 130 kcal por 100 g\nProteínas: 29 g\nGrasas: 2 g\nÁcidos grasos: Omega-3"),
            Ingrediente("lentejas", "Lentejas", "150g", "Calorías: 116 kcal por 100 g (cocidas)\nCarbohidratos: 20 g\nProteínas: 9 g\nFibra: 8 g\nMinerales: Hierro"),
            Ingrediente("brocoli", "Brócoli", "100g", "Calorías: 34 kcal por 100 g\nCarbohidratos: 6.6 g\nProteínas: 2.8 g\nFibra: 2.6 g\nVitaminas: C, K"),
            Ingrediente("pimiento", "Pimiento", "1/2", "Calorías: 31 kcal por 100 g\nCarbohidratos: 6 g\nProteínas: 1 g\nFibra: 2.1 g\nVitaminas: C, A"),
            Ingrediente("yogur", "Yogur", "150g", "Calorías: 61 kcal por 100 g\nCarbohidratos: 4.7 g\nProteínas: 3.5 g\nGrasas: 3.3 g\nVitaminas: B12\nMinerales: Calcio\nProbióticos"),
            Ingrediente("granola", "Granola", "2 cucharadas", "Calorías: 471 kcal por 100 g\nCarbohidratos: 60 g\nProteínas: 11 g\nGrasas: 20 g\nFibra: 7 g\nFuente de energía y fibra."),
            Ingrediente("mantequilla", "Mantequilla", "1 cucharada", "Grasas, vitaminas A y D"),
            Ingrediente("cereales", "Cereales", "30g", "Carbohidratos, fibra"),
            Ingrediente("nueces", "Nueces", "30g", "Grasas saludables, proteínas"),
            Ingrediente("miel", "Miel", "1 cucharada", "Azúcares, antioxidantes"),
            Ingrediente("chocolate", "Chocolate", "30g", "Antioxidantes, azúcares"),
            Ingrediente("cafe", "Café", "1 taza", "Antioxidantes, cafeína"),
            Ingrediente("zumo", "Zumo", "200ml", "Vitaminas, azúcares"),
            Ingrediente("refresco", "Refresco", "330ml", "Azúcares, sodio"),
            Ingrediente("pollo", "Pollo", "1 pechuga", "Calorías: 165 kcal por 100 g (pechuga)\nProteínas: 31 g\nGrasas: 3.6 g\nVitaminas:B3, B6\nMinerales: Fósforo"),
            Ingrediente("patatas", "Patatas", "2", "Calorías: 77 kcal por 100 g\nCarbohidratos: 17 g\nProteínas: 2 g\nFibra: 2.2 g\nVitaminas: C, B6\nMinerales: Potasio"),
            Ingrediente("cebolla", "Cebolla", "1/2", "Calorías: 40 kcal por 100 g\nCarbohidratos: 9 g\nProteínas: 1.1 g\nFibra: 1.7 g\nVitaminas: C, B6\nAntioxidantes: Quercetina"),
            Ingrediente("ajo", "Ajo", "2 dientes", "Calorías: 149 kcal por 100 g\nCarbohidratos: 33 g\nProteínas: 6.4 g\nFibra: 2.1 g\nAntioxidantes: Allicina"),
            Ingrediente("tofu", "Tofu", "200g", "Calorías: 76 kcal por 100 g\nProteínas: 8 g\nGrasas: 4.8 g\nCarbohidratos: 1.9 g\nMinerales: Calcio, hierro"),
            Ingrediente("zanahoria", "Zanahoria", "1", "Calorías: 41 kcal por 100 g\nCarbohidratos: 9.6 g\nProteínas: 0.9 g\nFibra: 2.8 g\nVitaminas: A\nAntioxidantes: Betacaroteno"),
            Ingrediente("pasta", "Pasta", "100g", "Calorías: 131 kcal por 100 g (cocida)\nCarbohidratos: 25 g\nProteínas: 5 g\nGrasas: 1 g\nFuente de energía."),
            Ingrediente("lechuga", "Lechuga", "50g", "Calorías: 15 kcal por 100 g\nCarbohidratos: 2.9 g\nProteínas: 1.4 g\nFibra: 1.3 g\nVitaminas: A, K")
        )
        return ingredientesIncorrectos.shuffled().take(4)
    }

    private fun onIngredienteClick(ingrediente: Ingrediente) {
        if (ingredientesSeleccionados.contains(ingrediente)) {
            ingredientesSeleccionados.remove(ingrediente)
        } else {
            ingredientesSeleccionados.add(ingrediente)
        }
        ingredientesAdapter.notifyDataSetChanged()
    }

    private fun mostrarInformacionNutricional(ingrediente: Ingrediente) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(ingrediente.nombre)
        builder.setMessage(ingrediente.informacionNutricional)
        builder.setPositiveButton("Cerrar") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun verificarIngredientes() {
        val ingredientesCorrectosNombres = ingredientesCorrectos.map { it.nombre }
        val ingredientesSeleccionadosNombres = ingredientesSeleccionados.map { it.nombre }
        val sonCorrectos = ingredientesSeleccionadosNombres.sorted() == ingredientesCorrectosNombres.sorted()

        val puntuacionIngredientes = if (sonCorrectos) {
            10
        } else {
            0
        }

        if (sonCorrectos) {
            Toast.makeText(this, "¡Correcto!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Incorrecto", Toast.LENGTH_SHORT).show()
        }

        val intent = Intent(this, ResultadoCuestionario::class.java)
        intent.putExtra("correctas", correctas)
        intent.putExtra("incorrectas", incorrectas)
        intent.putExtra("tiempo", tiempo)
        intent.putExtra("puntuacionIngredientes", puntuacionIngredientes)
        intent.putExtra("totalPreguntas", totalPreguntas)
        startActivity(intent)
    }
}
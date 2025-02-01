package com.example.foodgame

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodgame.databinding.ActivityJuegoIngredientesBinding
import modelo.Ingrediente
import modelo.Plato
import modelo.PlatoData
import adaptador.IngredientesAdapter
import androidx.recyclerview.widget.GridLayoutManager

class JuegoIngredientes : AppCompatActivity() {

    private lateinit var binding: ActivityJuegoIngredientesBinding
    private lateinit var ingredientesAdapter: IngredientesAdapter
    private lateinit var plato: Plato
    private var ingredientesCorrectos: List<Ingrediente> = emptyList()
    private var ingredientesMezclados: List<String> = emptyList()
    private var ingredientesSeleccionados: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJuegoIngredientesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        plato = intent.getParcelableExtra("plato")!!

        ingredientesCorrectos = obtenerIngredientesCorrectos(plato)
        ingredientesMezclados = obtenerNombresIngredientesMezclados(plato)

        ingredientesAdapter = IngredientesAdapter(ingredientesMezclados, ingredientesSeleccionados) { ingrediente ->
            onIngredienteClick(ingrediente)
        }

        binding.rvIngredientes.apply {
            // Usar GridLayoutManager con 2 columnas
            layoutManager = GridLayoutManager(this@JuegoIngredientes, 2)
            adapter = ingredientesAdapter
        }

        binding.btVerificar.setOnClickListener {
            verificarIngredientes()
        }
    }


    private fun obtenerIngredientesCorrectos(plato: Plato): List<Ingrediente> {
        val ingredientes = PlatoData.getPlatoIngredientes(plato.nombre).toMutableList()

        // Fetch image URLs for each ingredient
        ingredientes.forEach { ingrediente ->
            FirebaseUtils.obtenerMetadatosImagenDesdeBaseDeDatos(ingrediente.nombre) { imageMetadata ->
                ingrediente.imageUrl = imageMetadata?.urlImagen
                // Notify the adapter that the data has changed
                runOnUiThread {
                    ingredientesAdapter.notifyDataSetChanged()
                }
            }
        }
        return ingredientes
    }

    private fun obtenerNombresIngredientesMezclados(plato: Plato): List<String> {
        val ingredientesCorrectos = obtenerIngredientesCorrectos(plato).map { it.nombre }
        val ingredientesIncorrectos = obtenerIngredientesIncorrectos()
        val ingredientesMezclados = (ingredientesCorrectos + ingredientesIncorrectos).shuffled()
        return ingredientesMezclados
    }

    private fun obtenerIngredientesIncorrectos(): List<String> {
        val ingredientesIncorrectos = listOf(
            "Jamón", "Queso", "Atún", "Lentejas", "Brócoli", "Pimiento", "Yogur", "Granola",
            "Mantequilla", "Cereales", "Nueces", "Miel", "Chocolate", "Café", "Zumo", "Refresco",
            "Pollo", "Patatas", "Cebolla", "Ajo", "Tofu", "Zanahoria", "Pasta", "Lechuga"
        )
        return ingredientesIncorrectos.shuffled().take(4)
    }

    private fun onIngredienteClick(ingrediente: String) {
        if (ingredientesSeleccionados.contains(ingrediente)) {
            ingredientesSeleccionados.remove(ingrediente)
        } else {
            ingredientesSeleccionados.add(ingrediente)
        }
        ingredientesAdapter.notifyDataSetChanged()
    }

    fun mostrarInformacionNutricional(ingrediente: String) {
        val informacion = obtenerInformacionNutricional(ingrediente)
        Toast.makeText(this, "$ingrediente: $informacion", Toast.LENGTH_LONG).show()
    }

    private fun obtenerInformacionNutricional(ingrediente: String): String {
        return when (ingrediente) {
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
            "Arándanos" -> "Calorías: 57 kcal por 100 g\nCarbohidratos: 14 g\nProteínas: 0.7 g\nFibra: 2.4 g\nVitaminas: C, K\nAntioxidantes: Antocianinas"
            "Fresas" -> "Calorías: 32 kcal por 100 g\nCarbohidratos: 7.7 g\nProteínas: 0.7 g\nFibra: 2 g\nVitaminas: C\nAntioxidantes: Antocianinas"
            "Yogur natural" -> "Calorías: 61 kcal por 100 g\nCarbohidratos: 4.7 g\nProteínas: 3.5 g\nGrasas: 3.3 g\nVitaminas: B12\nMinerales: Calcio\nProbióticos"
            "Granola" -> "Calorías: 471 kcal por 100 g\nCarbohidratos: 60 g\nProteínas: 11 g\nGrasas: 20 g\nFibra: 7 g\nFuente de energía y fibra."
            "Pasta" -> "Calorías: 131 kcal por 100 g (cocida)\nCarbohidratos: 25 g\nProteínas: 5 g\nGrasas: 1 g\nFuente de energía."
            "Atún" -> "Calorías: 130 kcal por 100 g\nProteínas: 29 g\nGrasas: 2 g\nÁcidos grasos: Omega-3"
            "Lechuga" -> "Calorías: 15 kcal por 100 g\nCarbohidratos: 2.9 g\nProteínas: 1.4 g\nFibra: 1.3 g\nVitaminas: A, K"
            "Pollo" -> "Calorías: 165 kcal por 100 g (pechuga)\nProteínas: 31 g\nGrasas: 3.6 g\nVitaminas: B3, B6\nMinerales: Fósforo"
            "Patatas" -> "Calorías: 77 kcal por 100 g\nCarbohidratos: 17 g\nProteínas: 2 g\nFibra: 2.2 g\nVitaminas: C, B6\nMinerales: Potasio"
            "Cebolla" -> "Calorías: 40 kcal por 100 g\nCarbohidratos: 9 g\nProteínas: 1.1 g\nFibra: 1.7 g\nVitaminas: C, B6\nAntioxidantes: Quercetina"
            "Ajo" -> "Calorías: 149 kcal por 100 g\nCarbohidratos: 33 g\nProteínas: 6.4 g\nFibra: 2.1 g\nAntioxidantes: Allicina"
            "Tofu" -> "Calorías: 76 kcal por 100 g\nProteínas: 8 g\nGrasas: 4.8 g\nCarbohidratos: 1.9 g\nMinerales: Calcio, hierro"
            "Zanahoria" -> "Calorías: 41 kcal por 100 g\nCarbohidratos: 9.6 g\nProteínas: 0.9 g\nFibra: 2.8 g\nVitaminas: A\nAntioxidantes: Betacaroteno"
            "Brócoli" -> "Calorías: 34 kcal por 100 g\nCarbohidratos: 6.6 g\nProteínas: 2.8 g\nFibra: 2.6 g\nVitaminas: C, K"
            "Pimiento" -> "Calorías: 31 kcal por 100 g\nCarbohidratos: 6 g\nProteínas: 1 g\nFibra: 2.1 g\nVitaminas: C, A"
            "Salsa de soja" -> "Calorías: 53 kcal por 100 ml\nCarbohidratos: 6.6 g\nProteínas: 5.5 g\nMinerales: Sodio"
            "Lentejas" -> "Calorías: 116 kcal por 100 g (cocidas)\nCarbohidratos: 20 g\nProteínas: 9 g\nFibra: 8 g\nMinerales: Hierro"
            "Jamón" -> "Calorías: 145 kcal por 100 g\nProteínas: 22 g\nGrasas: 6 g\nVitaminas: B1, B3, B6"
            "Queso" -> "Calorías: 402 kcal por 100 g\nProteínas: 25 g\nGrasas: 33 g\nVitaminas: A, D\nMinerales: Calcio"
            "Mantequilla de maní" -> "Calorías: 588 kcal por 100 g\nProteínas: 25 g\nGrasas: 50 g\nCarbohidratos: 20 g\nFibra: 6 g\nMinerales: Magnesio, fósforo"
            "Hummus" -> "Calorías: 166 kcal por 100 g\nProteínas: 7.9 g\nGrasas: 9.6 g\nCarbohidratos: 14.3 g\nFibra: 6 g\nMinerales: Hierro, magnesio"
            else -> "Información no disponible"
        }
    }

    private fun verificarIngredientes() {
        val ingredientesCorrectosNombres = ingredientesCorrectos.map { it.nombre }
        val sonCorrectos = ingredientesSeleccionados.sorted() == ingredientesCorrectosNombres.sorted()

        if (sonCorrectos) {
            Toast.makeText(this, "¡Correcto!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Incorrecto", Toast.LENGTH_SHORT).show()
        }
    }
}
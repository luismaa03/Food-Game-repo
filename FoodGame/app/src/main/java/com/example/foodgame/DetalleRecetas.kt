package com.example.foodgame

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.foodgame.databinding.ActivityDetalleRecetasBinding
import modelo.PlatoData

class DetalleRecetas : AppCompatActivity() {
    private lateinit var binding: ActivityDetalleRecetasBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDetalleRecetasBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Obtiene datos del Intent
        val selectedItems = intent.getIntegerArrayListExtra("selectedItems") ?: emptyList<Int>()
        val categoria = intent.getStringExtra("categoria") ?: "desayunos"

        // Obtiene recetas según la categoría seleccionada
        val platos = when (categoria) {
            "desayunos" -> PlatoData.getPlatosDesayunos()
            "almuerzos" -> PlatoData.getPlatosAlmuerzos()
            "meriendas" -> PlatoData.getPlatosMeriendas()
            else -> PlatoData.getPlatosDesayunos()
        }

        // Filtra los platos seleccionados usando los índices recibidos
        val selectedPlatos = selectedItems.map { platos[it] }

        // Genera el detalle de las recetas seleccionadas
        val platoDetails = selectedPlatos.joinToString("\n") { plato ->
            """
            Nombre: ${plato.nombre}
            Calorías: ${plato.calorias}
            Ingredientes: ${plato.ingredientes}
            Pasos de preparación:
            ${plato.pasosPreparacion}
            """.trimIndent()
        }

        // Muestra los detalles en el EditText del binding
        binding.mlRecetas.setText(platoDetails)

        binding.btVolver.setOnClickListener {
            finish()
        }
    }
}

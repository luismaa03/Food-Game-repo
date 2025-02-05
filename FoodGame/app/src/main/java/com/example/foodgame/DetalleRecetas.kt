package com.example.foodgame

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.foodgame.databinding.ActivityDetalleRecetasBinding
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import modelo.Plato
import java.io.File

class DetalleRecetas : AppCompatActivity() {
    private lateinit var binding: ActivityDetalleRecetasBinding
    // Referencia a Firebase Storage
    private val storage = Firebase.storage
    private val storageRef = storage.reference

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
        val selectedPlato = intent.getParcelableExtra<Plato>("selectedPlato")
        val categoria = intent.getStringExtra("categoria") ?: "desayunos"

        // Muestra los detalles del plato
        if (selectedPlato != null) {
            val ingredientesText = selectedPlato.ingredientes.joinToString("\n") { ingrediente -> // Especificamos el tipo "ingrediente"
                "- ${ingrediente.nombre}: ${ingrediente.cantidad}"
            }
            val platoDetails = """
            Nombre: ${selectedPlato.nombre}
            Calorías: ${selectedPlato.calorias}
            Ingredientes:
            $ingredientesText
            Pasos de preparación: ${selectedPlato.preparacion}
            """.trimIndent()
            binding.mlRecetas.text = platoDetails

            // Cargar la imagen desde Firebase Storage
            val imageName = selectedPlato.imageName
            val imageRef = storageRef.child("$categoria/$imageName.jpg")
            descargarImagen(imageRef)
        }

        binding.btVolver.setOnClickListener {
            finish()
        }
    }

    private fun descargarImagen(imageRef: com.google.firebase.storage.StorageReference) {
        val localfile = File.createTempFile("tempImage", "jpg")

        imageRef.getFile(localfile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            // Actualizar la imagen
            binding.ivPlatoDetalle.setImageBitmap(bitmap)
        }.addOnFailureListener {
            // Manejar el error
        }
    }
}
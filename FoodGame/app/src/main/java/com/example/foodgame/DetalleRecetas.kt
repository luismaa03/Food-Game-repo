package com.example.foodgame

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.foodgame.databinding.ActivityDetalleRecetasBinding
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import modelo.Plato
import modelo.Pregunta
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
            val platoDetails = """
            Nombre: ${selectedPlato.nombre}
            Calorías: ${selectedPlato.calorias}
            Ingredientes: ${selectedPlato.ingredientes}
            Pasos de preparación: ${selectedPlato.descripcion}
            """.trimIndent()
            binding.mlRecetas.setText(platoDetails)

            // Cargar la imagen desde Firebase Storage
            val imageName = selectedPlato.imageName
            val imageRef = storageRef.child("$categoria/$imageName.jpg")
            descargarImagen(imageRef)

            // Muestra las preguntas
            mostrarPreguntas(selectedPlato.preguntas)
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

    private fun mostrarPreguntas(preguntas: List<Pregunta>) {
        val layoutPreguntas = binding.layoutPreguntas // LinearLayout donde se mostrarán las preguntas

        // Limpiar el layout antes de añadir nuevas preguntas
        layoutPreguntas.removeAllViews()

        for (pregunta in preguntas) {
            // Crear un TextView para la pregunta
            val textViewPregunta = android.widget.TextView(this)
            textViewPregunta.text = pregunta.pregunta
            textViewPregunta.textSize = 18f
            layoutPreguntas.addView(textViewPregunta)

            // Crear un RadioGroup para las opciones
            val radioGroup = RadioGroup(this)
            radioGroup.orientation = LinearLayout.VERTICAL

            for (opcion in pregunta.respuestas) {
                val radioButton = RadioButton(this)
                radioButton.text = opcion
                radioGroup.addView(radioButton)
            }

            layoutPreguntas.addView(radioGroup)
        }
    }
}
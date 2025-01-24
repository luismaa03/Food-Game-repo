package modelo

import android.graphics.Bitmap

data class Plato(
    val nombre: String,
    val calorias: Double,
    val ingredientes: String,
    val imageName: String = nombre.lowercase(),
    val pasosPreparacion: String,
    var bitmap: Bitmap? = null // Nueva propiedad para almacenar el Bitmap
)

package modelo

import android.util.Log
import android.widget.ImageView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

object FirebaseUtils {

    fun cargarImagen(idIngrediente: String, imageView: ImageView) {
        val storage = Firebase.storage
        val storageRef = storage.reference
        val nombreImagen = "$idIngrediente.png"
        val imageRef = storageRef.child("ingredientes/$nombreImagen")

        imageRef.downloadUrl.addOnSuccessListener { uri ->
            val urlDescarga = uri.toString()
            Log.d("FirebaseUtils", "Intentando cargar imagen con URL: $urlDescarga")
            Picasso.get().load(urlDescarga).into(imageView, object : Callback {
                override fun onSuccess() {
                    Log.d("FirebaseUtils", "Imagen cargada correctamente: $urlDescarga")
                }

                override fun onError(e: Exception?) {
                    Log.e("FirebaseUtils", "Error al cargar la imagen: $urlDescarga", e)
                }
            })
        }.addOnFailureListener { exception ->
            Log.e("FirebaseUtils", "Error al obtener la URL de descarga para $idIngrediente: ${exception.message}")
        }
    }
}
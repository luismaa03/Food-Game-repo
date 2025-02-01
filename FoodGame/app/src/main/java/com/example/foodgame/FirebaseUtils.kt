package com.example.foodgame

import android.net.Uri
import android.widget.ImageView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso
import java.util.UUID

object FirebaseUtils {

    data class MetadatosImagen(
        val nombreImagen: String = "",
        val urlImagen: String = ""
    )

    fun guardarMetadatosImagenEnBaseDeDatos(imageUri: Uri, nombreIngrediente: String, callback: (Boolean) -> Unit) {
        val storage = Firebase.storage
        val storageRef = storage.reference
        val database = Firebase.database
        val myRef = database.getReference("ingredientes")

        val nombreImagen = "${nombreIngrediente}_${UUID.randomUUID()}.jpg"
        val imageRef = storageRef.child("ingredientes/$nombreImagen")

        val uploadTask = imageRef.putFile(imageUri)

        uploadTask.addOnSuccessListener { taskSnapshot ->
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                val urlDescarga = uri.toString()
                val metadatosImagen = MetadatosImagen(nombreImagen, urlDescarga)
                myRef.child(nombreIngrediente).child(nombreImagen.substringAfter("_").substringBefore(".")).setValue(metadatosImagen)
                    .addOnSuccessListener {
                        println("Metadatos de la imagen guardados correctamente")
                        callback(true)
                    }
                    .addOnFailureListener { exception ->
                        println("Error al guardar los metadatos de la imagen: ${exception.message}")
                        callback(false)
                    }
            }
        }.addOnFailureListener { exception ->
            println("Error al subir la imagen: ${exception.message}")
            callback(false)
        }
    }

    fun obtenerMetadatosImagenDesdeBaseDeDatos(nombreIngrediente: String, callback: (MetadatosImagen?) -> Unit) {
        val database = Firebase.database
        val myRef = database.getReference("ingredientes")

        myRef.child(nombreIngrediente).limitToFirst(1).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val firstChild = dataSnapshot.children.first()
                    val metadatosImagen = firstChild.getValue(MetadatosImagen::class.java)
                    callback(metadatosImagen)
                } else {
                    callback(null)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                println("Error al obtener los metadatos de la imagen: ${databaseError.message}")
                callback(null)
            }
        })
    }

    fun cargarImagen(urlImagen: String, imageView: ImageView) {
        Picasso.get().load(urlImagen).into(imageView)
    }
}
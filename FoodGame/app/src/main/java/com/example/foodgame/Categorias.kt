package com.example.foodgame

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.foodgame.databinding.ActivityCategoriasBinding
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import java.io.File

class Categorias : AppCompatActivity() {

    // View Binding para facilitar el acceso a las vistas
    lateinit var binding: ActivityCategoriasBinding

    var storage = Firebase.storage
    // Crea una referencia con la instancia singleton FirebaseStorage y con una llamada al método reference.
    var storageRef = storage.reference

    val desayunoRef = storageRef.child("categorias/desayuno.png")
    val almuerzoRef = storageRef.child("categorias/almuerzo.png")
    val meriendaRef = storageRef.child("categorias/merienda.png")

    val localfileDesayuno  = File.createTempFile("tempImageDesayuno","png")
    val localfileAlmuerzo  = File.createTempFile("tempImageAlmuerzo","png")
    val localfileMerienda  = File.createTempFile("tempImageMerienda","png")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCategoriasBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btDesayunos.setOnClickListener {
            val intent = Intent(this, Desayunos::class.java)
            startActivity(intent)
        }

        binding.btAlmuerzos.setOnClickListener {
            val intent = Intent(this, Almuerzos::class.java)
            startActivity(intent)
        }

        binding.btMeriendas.setOnClickListener {
            val intent = Intent(this, Meriendas::class.java)
            startActivity(intent)
        }

        binding.btVolver.setOnClickListener {
            finish()
        }

        desayunoRef.getFile(localfileDesayuno).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localfileDesayuno.absolutePath)
            binding.ivDesayuno.setImageBitmap(bitmap)
        }.addOnFailureListener { exception ->
            Toast.makeText(this,"Algo ha fallado en la descarga", Toast.LENGTH_SHORT).show()
        }

        almuerzoRef.getFile(localfileAlmuerzo).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localfileAlmuerzo.absolutePath)
            binding.ivAlmuerzo.setImageBitmap(bitmap)
        }.addOnFailureListener { exception ->
            Toast.makeText(this,"Algo ha fallado en la descarga", Toast.LENGTH_SHORT).show()
        }

        meriendaRef.getFile(localfileMerienda).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localfileMerienda.absolutePath)
            binding.ivMerienda.setImageBitmap(bitmap)
        }.addOnFailureListener { exception ->
            Toast.makeText(this,"Algo ha fallado en la descarga", Toast.LENGTH_SHORT).show()
        }

    }
}

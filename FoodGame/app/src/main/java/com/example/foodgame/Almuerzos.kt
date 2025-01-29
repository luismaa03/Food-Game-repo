package com.example.foodgame

import adaptador.PlatoAdapter
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodgame.databinding.ActivityAlmuerzosBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.StorageException
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.google.firebase.storage.storage
import modelo.Plato
import modelo.PlatoData
import java.io.File

class Almuerzos : AppCompatActivity() {

    // Inicialización tardía de variables para el binding y autenticación Firebase
    lateinit var binding: ActivityAlmuerzosBinding
    private lateinit var firebaseauth: FirebaseAuth
    private lateinit var adapter: PlatoAdapter
    private val platos = mutableListOf<Plato>()
    private val categoria = "almuerzos" // Definimos la categoría

    // Referencia a Firebase Storage
    private val storage = Firebase.storage
    private val storageRef = storage.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAlmuerzosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Deshabilita el botón "Aceptar" inicialmente
        binding.btAceptarAlm.isEnabled = false

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Obtiene una instancia de FirebaseAuth
        firebaseauth = FirebaseAuth.getInstance()

        // Configuración del RecyclerView
        val rvAlmuerzos = findViewById<RecyclerView>(R.id.rvAlmuerzos)
        platos.addAll(PlatoData.getPlatosAlmuerzos())
        adapter = PlatoAdapter(platos, categoria) // Pasamos la categoría al adaptador
        rvAlmuerzos.adapter = adapter
        rvAlmuerzos.layoutManager = LinearLayoutManager(this)

        // Descargar imágenes de Firebase Storage y actualizar el adaptador
        descargarImagenes()

        // Configura un listener para la selección de elementos en el adaptador
        adapter.setOnItemSelectedListener { position ->
            binding.btAceptarAlm.isEnabled = position != -1
        }

        binding.btVolverAlm.setOnClickListener {
            finish()
        }

        binding.btAceptarAlm.setOnClickListener {
            if (adapter.selectedPosition != -1) {
                val selectedPlato = platos[adapter.selectedPosition]
                val intent = Intent(this, JuegoCuestionario::class.java)
                intent.putExtra("tipoPlato", categoria)
                intent.putExtra("nombrePlato", selectedPlato.nombre)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Selecciona un plato", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun descargarImagenes() {
        for (plato in platos) {
            val imageName = plato.imageName // Nombre de la imagen en Firebase Storage
            val imageRef = storageRef.child("almuerzos/$imageName.jpg") // Ruta en Firebase Storage
            descargarImagen(imageRef, plato)
        }
    }

    private fun descargarImagen(imageRef: StorageReference, plato: Plato) {
        val localfile = File.createTempFile("tempImage", "jpg")

        imageRef.getFile(localfile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            // Actualizar el plato con el bitmap descargado
            plato.bitmap = bitmap
            // Notificar al adaptador que los datos han cambiado
            adapter.notifyDataSetChanged()
        }.addOnFailureListener { exception ->
            when (exception) {
                is StorageException -> {
                    when (exception.errorCode) {
                        StorageException.ERROR_OBJECT_NOT_FOUND -> {
                            Log.e("Almuerzos", "Error al descargar imagen: Imagen no encontrada")
                            Toast.makeText(this, "Imagen no encontrada", Toast.LENGTH_SHORT).show()
                        }
                        StorageException.ERROR_NOT_AUTHENTICATED -> {
                            Log.e("Almuerzos", "Error al descargar imagen: No autenticado")
                            Toast.makeText(this, "No autenticado", Toast.LENGTH_SHORT).show()
                        }
                        StorageException.ERROR_NOT_AUTHORIZED -> {
                            Log.e("Almuerzos", "Error al descargar imagen: No autorizado")
                            Toast.makeText(this, "No autorizado", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            Log.e("Almuerzos", "Error al descargar imagen: ${exception.message}")
                            Toast.makeText(this, "Error al descargar imagen", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                else -> {
                    Log.e("Almuerzos", "Error al descargar imagen: ${exception.message}")
                    Toast.makeText(this, "Error al descargar imagen", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
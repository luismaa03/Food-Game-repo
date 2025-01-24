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
import com.example.foodgame.databinding.ActivityMeriendasBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.StorageException
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.google.firebase.storage.storage
import modelo.Plato
import modelo.PlatoData
import java.io.File

class Meriendas : AppCompatActivity() {

    // Declaración de variables globales.
    lateinit var binding: ActivityMeriendasBinding
    private lateinit var firebaseauth: FirebaseAuth
    private lateinit var adapter: PlatoAdapter
    private val platos = mutableListOf<Plato>()
    private val categoria = "meriendas" // Definimos la categoría

    // Referencia a Firebase Storage
    private val storage = Firebase.storage
    private val storageRef = storage.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMeriendasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Desactiva el botón de aceptar al inicio.
        binding.btAceptarMer.isEnabled = false

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializa FirebaseAuth para la autenticación (si se requiere en el futuro).
        firebaseauth = FirebaseAuth.getInstance()

        // Encuentra el RecyclerView definido en el layout.
        val rvMeriendas = findViewById<RecyclerView>(R.id.rvMeriendas)

        // Obtiene la lista de platos disponibles para las meriendas.
        platos.addAll(PlatoData.getPlatosMeriendas())

        // Crea un adaptador con la lista de platos y lo asigna al RecyclerView.
        adapter = PlatoAdapter(platos, categoria) // Pasamos la categoría al adaptador
        rvMeriendas.adapter = adapter

        // Configura el diseño del RecyclerView para que muestre los elementos en una lista vertical.
        rvMeriendas.layoutManager = LinearLayoutManager(this)

        // Descargar imágenes de Firebase Storage y actualizar el adaptador
        descargarImagenes()

        // Configura un listener para manejar la selección de elementos del adaptador.
        adapter.setOnItemSelectedListener { position ->
            binding.btAceptarMer.isEnabled = position != -1
        }

        // Configura el botón "Volver" para cerrar esta actividad y regresar a la anterior.
        binding.btVolverMer.setOnClickListener {
            finish()
        }

        // Configura el botón "Aceptar" para abrir la actividad JuegoIngredientes.
        binding.btAceptarMer.setOnClickListener {
            val intent = Intent(this, JuegoIngredientes::class.java)
            startActivity(intent)
        }
    }

    private fun descargarImagenes() {
        for (plato in platos) {
            val imageName = plato.imageName // Nombre de la imagen en Firebase Storage
            val imageRef = storageRef.child("meriendas/$imageName.jpg") // Ruta en Firebase Storage
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
                            Log.e("Meriendas", "Error al descargar imagen: Imagen no encontrada")
                            Toast.makeText(this, "Imagen no encontrada", Toast.LENGTH_SHORT).show()
                        }
                        StorageException.ERROR_NOT_AUTHENTICATED -> {
                            Log.e("Meriendas", "Error al descargar imagen: No autenticado")
                            Toast.makeText(this, "No autenticado", Toast.LENGTH_SHORT).show()
                        }
                        StorageException.ERROR_NOT_AUTHORIZED -> {
                            Log.e("Meriendas", "Error al descargar imagen: No autorizado")
                            Toast.makeText(this, "No autorizado", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            Log.e("Meriendas", "Error al descargar imagen: ${exception.message}")
                            Toast.makeText(this, "Error al descargar imagen", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                else -> {
                    Log.e("Meriendas", "Error al descargar imagen: ${exception.message}")
                    Toast.makeText(this, "Error al descargar imagen", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
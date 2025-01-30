package com.example.foodgame

import adaptador.PlatoAdapter
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.storage.StorageException
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import modelo.Plato
import java.io.File

abstract class PlatosActivity : AppCompatActivity() {

    protected lateinit var adapter: PlatoAdapter
    protected val platos = mutableListOf<Plato>()
    protected abstract val categoria: String
    protected lateinit var btAceptar: Button
    protected lateinit var rvPlatos: RecyclerView

    // Referencia a Firebase Storage
    private val storage = Firebase.storage
    protected val storageRef = storage.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(getLayoutResourceId())

        btAceptar = findViewById(getButtonAceptarId())
        rvPlatos = findViewById(getRecyclerViewId())

        btAceptar.isEnabled = false

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configuración del RecyclerView
        adapter = PlatoAdapter(platos, categoria)
        rvPlatos.adapter = adapter
        rvPlatos.layoutManager = LinearLayoutManager(this)

        // Descargar imágenes de Firebase Storage y actualizar el adaptador
        descargarImagenes()

        // Listener para manejar la selección de elementos en el RecyclerView
        adapter.setOnItemSelectedListener { position ->
            btAceptar.isEnabled = position != -1
        }

        findViewById<Button>(getButtonVolverId()).setOnClickListener {
            finish()
        }

        btAceptar.setOnClickListener {
            if (adapter.selectedItemPosition != -1) {
                val selectedPlato = platos[adapter.selectedItemPosition]
                val intent = Intent(this, JuegoCuestionario::class.java)
                intent.putExtra("selectedPlato", selectedPlato) // Pasa el objeto Plato
                startActivity(intent)
            } else {
                Toast.makeText(this, "Selecciona un plato", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun descargarImagenes() {
        for (plato in platos) {
            val imageName = plato.imageName // Nombre de la imagen en Firebase Storage
            val imageRef = storageRef.child("$categoria/$imageName.jpg") // Ruta en Firebase Storage
            descargarImagen(imageRef, plato)
        }
    }

    private fun descargarImagen(imageRef: StorageReference, plato: Plato) {
        val localfile = File.createTempFile("tempImage", "jpg")

        imageRef.getFile(localfile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            // Notificar al adaptador que los datos han cambiado
            adapter.notifyDataSetChanged()
        }.addOnFailureListener { exception ->
            when (exception) {
                is StorageException -> {
                    when (exception.errorCode) {
                        StorageException.ERROR_OBJECT_NOT_FOUND -> {
                            Log.e(categoria, "Error al descargar imagen: Imagen no encontrada")
                            Toast.makeText(this, "Imagen no encontrada", Toast.LENGTH_SHORT).show()
                        }
                        StorageException.ERROR_NOT_AUTHENTICATED -> {
                            Log.e(categoria, "Error al descargar imagen: No autenticado")
                            Toast.makeText(this, "No autenticado", Toast.LENGTH_SHORT).show()
                        }
                        StorageException.ERROR_NOT_AUTHORIZED -> {
                            Log.e(categoria, "Error al descargar imagen: No autorizado")
                            Toast.makeText(this, "No autorizado", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            Log.e(categoria, "Error al descargar imagen: ${exception.message}")
                            Toast.makeText(this, "Error al descargar imagen", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                else -> {
                    Log.e(categoria, "Error al descargar imagen: ${exception.message}")
                    Toast.makeText(this, "Error al descargar imagen", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    protected abstract fun getLayoutResourceId(): Int
    protected abstract fun getButtonAceptarId(): Int
    protected abstract fun getButtonVolverId(): Int
    protected abstract fun getRecyclerViewId(): Int
}
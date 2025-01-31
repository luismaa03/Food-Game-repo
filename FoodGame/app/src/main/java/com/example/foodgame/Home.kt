package com.example.foodgame

import android.Manifest
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.foodgame.databinding.ActivityHomeBinding
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.io.output.ByteArrayOutputStream
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import modelo.Puntuacion
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.Serializable

class Home : AppCompatActivity() {
    lateinit var binding: ActivityHomeBinding
    private lateinit var firebaseauth: FirebaseAuth
    private lateinit var bitmap: Bitmap
    private var uriImagen: Uri? = null
    private val TAG = "LMEG"

    private val storageReference = FirebaseStorage.getInstance().reference
    private val databaseReference = com.google.firebase.database.FirebaseDatabase.getInstance().reference
    private val IMAGE_FILE_NAME = "profile_image.jpg" // Nombre del archivo de la imagen en el almacenamiento interno

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Listener del botón "Historial de Puntos"
        binding.btHistorialPuntos.setOnClickListener {
            val puntuaciones = obtenerPuntuacionesAlmacenadas()

            // Crear un Intent y pasar la lista de puntuaciones como dato serializable
            val intent = Intent(this, VerPuntuaciones::class.java)
            intent.putExtra("puntuaciones", puntuaciones as Serializable)
            startActivity(intent)
        }
        // Listener del botón "Acerca de"
        binding.btAcercaDe.setOnClickListener {
            mostrarDialogoAcercaDe()
        }

        firebaseauth = FirebaseAuth.getInstance()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Listener del botón "Cerrar Sesión"
        binding.btCerrarSesion.setOnClickListener {
            // Muestra el usuario actual en el Log para depuración
            Log.e(TAG, firebaseauth.currentUser.toString())

            // Cierra sesión con FirebaseAuth
            firebaseauth.signOut()
            // También cierra sesión con Google Identity
            val signInClient = Identity.getSignInClient(this)
            signInClient.signOut()
            Log.e(TAG, "Cerrada sesión completamente")

            // Finaliza la actividad actual y redirige al Login
            finish()
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        // Listener del botón "Comenzar" que redirige a la actividad Categorías
        binding.btComenzar.setOnClickListener {
            val intent = Intent(this, Categorias::class.java)
            startActivity(intent)
        }

        // Recupera información de SharedPreferences
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val email = sharedPreferences.getString("email", null)
        val provider = sharedPreferences.getString("provider", null)
        val nombre = sharedPreferences.getString("nombre", null)

        binding.ibIcono.setOnClickListener {
            val popupMenu = PopupMenu(this, binding.ibIcono)
            popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.openCamera -> {
                        // Solicitar el permiso de la cámara antes de abrirla
                        requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                        true
                    }
                    R.id.openGallery -> {
                        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                        true
                    }
                    else -> false
                }
            }

            popupMenu.show()
        }

        // Listener del ícono que muestra un AlertDialog con información del usuario conectado
        binding.ibIcono.setOnLongClickListener {
            runOnUiThread {
                val alertDialog = AlertDialog.Builder(this).create()
                alertDialog.setTitle("Información de conexión")
                alertDialog.setMessage(
                    "Email: $email\n" +
                            "Proveedor: $provider\n" +
                            "Nombre: $nombre"
                )
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK") { dialog, _ ->
                    dialog.dismiss()
                }
                alertDialog.show()
            }
            true // Devolver true para indicar que el evento ha sido manejado
        }
        cargarImagenDePerfil()

    }

    // Registro para abrir la cámara
    private val openCamera = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK && result.data != null) {
            val data = result.data
            bitmap = data?.extras?.get("data") as Bitmap

            // Cargar la imagen con Glide y aplicar la transformación circular
            Glide.with(this)
                .load(bitmap)
                .circleCrop() // O .transform(RoundedCorners(radius)) para bordes redondeados
                .into(binding.ibIcono)

            subirImagenAStorage(bitmap)
        }
    }

    // Registro para abrir la galería
    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            uriImagen = uri

            // Cargar la imagen con Glide y aplicar la transformación circular
            Glide.with(this)
                .load(uriImagen)
                .circleCrop() // O .transform(RoundedCorners(radius)) para bordes redondeados
                .into(binding.ibIcono)
            // Subir la imagen a Firebase Storage
            subirImagenAStorage(uriImagen!!)

        } else {
            Log.d(TAG, "No media selected")
        }
    }

    // Permiso para la cámara
    private val requestCameraPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                openCamera.launch(intent)
            } else {
                Toast.makeText(this, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show()
            }
        }

    private fun subirImagenAStorage(image: Any) {
        val imageRef = storageReference.child("user_images/${firebaseauth.currentUser?.uid}.jpg")

        val uploadTask = if (image is Uri) {
            imageRef.putFile(image)
        } else {
            val baos = ByteArrayOutputStream()
            (image as Bitmap).compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()
            imageRef.putBytes(data)
        }
        uploadTask.addOnSuccessListener { taskSnapshot ->
            // Obtener la URL de descarga de la imagen
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                val imageUrl = uri.toString()

                // Guardar la URL de la imagen en Firebase Realtime Database
                val userRef = databaseReference.child("users/${firebaseauth.currentUser?.uid}")
                userRef.child("profileImageUrl").setValue(imageUrl)

                // Guardar la imagen en el almacenamiento interno
                if (image is Bitmap) {
                    guardarImagenEnAlmacenamientoInterno(image)
                } else if (image is Uri) {
                    try {
                        val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, image)
                        guardarImagenEnAlmacenamientoInterno(bitmap)
                    }
                    catch (e: IOException) {
                        Log.e(TAG, "Error al obtener el bitmap de la Uri: ${e.message}")
                    }
                }
            }
        }.addOnFailureListener { exception ->
            // Manejar el error de subida
            Log.e(TAG, "Error al subir la imagen: ${exception.message}")
        }
    }

    // Función para cargar la imagen de perfil al iniciar sesión
    private fun cargarImagenDePerfil() {
        // Intentar cargar la imagen desde el almacenamiento interno
        val bitmap = cargarImagenDesdeAlmacenamientoInterno()
        if (bitmap != null) {
            // Si la imagen está en el almacenamiento interno, cargarla
            Glide.with(this)
                .load(bitmap)
                .circleCrop()
                .into(binding.ibIcono)
        } else {
            // Si no está en el almacenamiento interno, cargarla desde Firebase
            val userRef = databaseReference.child("users/${firebaseauth.currentUser?.uid}")
            userRef.child("profileImageUrl").addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val imageUrl = snapshot.getValue(String::class.java)
                    if (imageUrl != null) {
                        Glide.with(this@Home)
                            .load(imageUrl)
                            .circleCrop()
                            .into(binding.ibIcono)

                        // Descargar la imagen de Firebase y guardarla en el almacenamiento interno
                        Glide.with(this@Home)
                            .asBitmap()
                            .load(imageUrl)
                            .into(object : com.bumptech.glide.request.target.CustomTarget<Bitmap>() {
                                override fun onResourceReady(resource: Bitmap, transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?) {
                                    guardarImagenEnAlmacenamientoInterno(resource)
                                }

                                override fun onLoadCleared(placeholder: android.graphics.drawable.Drawable?) {
                                    // No es necesario hacer nada aquí
                                }
                            })
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Manejar el error de lectura
                    Log.e(TAG, "Error al cargar la imagen de perfil: ${error.message}")
                }
            })
        }
    }

    // Función para guardar la imagen en el almacenamiento interno
    private fun guardarImagenEnAlmacenamientoInterno(bitmap: Bitmap) {
        val file = File(filesDir, IMAGE_FILE_NAME)
        try {
            val stream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
        } catch (e: IOException) {
            Log.e(TAG, "Error al guardar la imagen en el almacenamiento interno: ${e.message}")
        }
    }

    // Función para cargar la imagen desde el almacenamiento interno
    private fun cargarImagenDesdeAlmacenamientoInterno(): Bitmap? {
        val file = File(filesDir, IMAGE_FILE_NAME)
        return if (file.exists()) {
            BitmapFactory.decodeFile(file.absolutePath)
        } else {
            null
        }
    }

    // Función para obtener las puntuaciones almacenadas
    private fun obtenerPuntuacionesAlmacenadas(): List<Puntuacion> {
        // Implementa la lógica para recuperar puntuaciones de SharedPreferences
        val sharedPreferences = getSharedPreferences("puntuaciones", Context.MODE_PRIVATE)
        val puntuaciones = mutableListOf<Puntuacion>()
        // Recupera todas las entradas de SharedPreferences
        val allEntries = sharedPreferences.all
        for ((_, value) in allEntries) {
            if (value is Int) {
                puntuaciones.add(Puntuacion(puntos = value))
            }
        }
        return puntuaciones
    }
    private fun mostrarDialogoAcercaDe() {
        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle("Acerca de FoodGame")
        alertDialog.setMessage(
            "FoodGame es una aplicación desarrollada para poner a prueba tus conocimientos sobre recetas y gastronomía.\n\n" +
                    "Desarrollado por: Luis Manuel Exposito y Roberto Honrado\n" +
                    "Versión: 1.0\n"
        )
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK") { dialog, _ ->
            dialog.dismiss()
        }
        alertDialog.show()
    }
}
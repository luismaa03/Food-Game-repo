package com.example.foodgame

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.foodgame.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.GoogleAuthProvider

class Login : AppCompatActivity() {
    private lateinit var firebaseauth : FirebaseAuth
    private lateinit var binding: ActivityLoginBinding

    private lateinit var googleSignInClient: GoogleSignInClient

    private val TAG = "LMEG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseauth = FirebaseAuth.getInstance()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // comentario prueba

        //------------------------------ Autenticación con email y password ------------------------------------
        binding.btRegistrar.setOnClickListener {
            val email = binding.edEmail.text.toString()
            val password = binding.edContrasena.editText?.text?.toString() ?: ""
            if (email.isNotEmpty() && password.isNotEmpty()) {
                firebaseauth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        irHome(
                            it.result?.user?.email ?: "",
                            Proveedor.BASIC
                        )

                    } else {
                        showAlert("Error registrando al usuario. Comprueba el formato del email o que la contraseña sea segura")
                    }
                }.addOnFailureListener {
                    Toast.makeText(this, "La conexión ha fallado", Toast.LENGTH_SHORT).show()
                }
            } else {
                showAlert("Rellene los campos")
            }
        }

        binding.btLogin.setOnClickListener {
            val email = binding.edEmail.text.toString()
            val password = binding.edContrasena.editText?.text?.toString() ?: ""
            if (email.isNotEmpty() && password.isNotEmpty()) {
                firebaseauth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        irHome(
                            it.result?.user?.email ?: "",
                            Proveedor.BASIC
                        )
                    } else {
                        showAlert()
                    }
                }.addOnFailureListener {
                    Toast.makeText(this, "Conexión no establecida", Toast.LENGTH_SHORT).show()
                }
            } else {
                showAlert("Rellene los campos")
            }
        }

        if (firebaseauth.currentUser != null) {
            // El usuario ya ha iniciado sesión, redirigir a Home
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
            finish() // Finalizar la actividad Login para que no se pueda volver atrás
        }

        //------------------Variables para realizar el Login con Google -------------------
        firebaseauth.signOut()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.your_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.ibGoogle.setOnClickListener {
            loginEnGoogle()
        }
    }

    private fun showAlert(msg:String = "Se ha producido un error autenticando al usuario"){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage(msg)
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
    private fun irHome(email: String, provider: Proveedor, nombre: String = "Usuario") {
        Log.e(TAG, "Valores: $email, $provider, $nombre")

        // Guardar la información de conexión en Shared Preferences
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("email", email)
        editor.putString("provider", provider.name)
        editor.putString("nombre", nombre)
        editor.apply()

        val homeIntent = Intent(this, Home::class.java).apply {
            putExtra("email", email)
            putExtra("provider", provider.name)
            putExtra("nombre", nombre)
        }
        startActivity(homeIntent)
    }
    private fun loginEnGoogle(){
        val signInClient = googleSignInClient.signInIntent
        launcherVentanaGoogle.launch(signInClient)
    }

    private val launcherVentanaGoogle =  registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == Activity.RESULT_OK){
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            manejarResultados(task)
        }
    }

    private fun manejarResultados(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful){
            val account : GoogleSignInAccount? = task.result
            if (account != null){
                actualizarUI(account)
            }
        }
        else {
            Toast.makeText(this,task.exception.toString(), Toast.LENGTH_SHORT).show()
        }
    }


    private fun actualizarUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseauth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                // Guardar la información de conexión en Shared Preferences
                val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("email", account.email)
                editor.putString("provider", Proveedor.GOOGLE.name)
                editor.putString("nombre", account.displayName)
                editor.apply()

                irHome(account.email.toString(), Proveedor.GOOGLE, account.displayName.toString())
            } else {
                Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
}
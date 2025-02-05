package com.example.foodgame

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.foodgame.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import modelo.Proveedor
import java.util.concurrent.Executor

class Login : AppCompatActivity() {
    private lateinit var firebaseauth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private val TAG = "LMEG"
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

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

        // Comprobar si la biometría está disponible
        val biometricManager = BiometricManager.from(this)
        when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                // Biometría disponible
                if (firebaseauth.currentUser != null) {
                    // El usuario ya ha iniciado sesión, pedir la huella
                    authenticateUser()
                } else {
                    // El usuario no ha iniciado sesión, mostrar la pantalla de login
                    showLoginScreen()
                }
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                // No hay hardware biométrico
                Toast.makeText(this, "No hay hardware biométrico disponible", Toast.LENGTH_SHORT).show()
                showLoginScreen()
            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                // Hardware biométrico no disponible
                Toast.makeText(this, "El hardware biométrico no está disponible", Toast.LENGTH_SHORT).show()
                showLoginScreen()
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                // No hay biometría registrada
                Toast.makeText(this, "No hay biometría registrada", Toast.LENGTH_SHORT).show()
                // Redirigir a la configuración de seguridad
                val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                    putExtra(
                        Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                        BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL
                    )
                }
                startActivityForResult(enrollIntent, 100)
            }
            BiometricManager.BIOMETRIC_ERROR_SECURITY_UPDATE_REQUIRED -> {
                Toast.makeText(this, "Se requiere una actualización de seguridad", Toast.LENGTH_SHORT).show()
                showLoginScreen()
            }
            BiometricManager.BIOMETRIC_ERROR_UNSUPPORTED -> {
                Toast.makeText(this, "Dispositivo no soportado", Toast.LENGTH_SHORT).show()
                showLoginScreen()
            }
            BiometricManager.BIOMETRIC_STATUS_UNKNOWN -> {
                Toast.makeText(this, "Error desconocido", Toast.LENGTH_SHORT).show()
                showLoginScreen()
            }
        }
    }

    private fun authenticateUser() {
        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(applicationContext, "Error de autenticación: $errString", Toast.LENGTH_SHORT).show()
                    finish()
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(applicationContext, "Autenticación exitosa", Toast.LENGTH_SHORT).show()
                    // Aquí puedes continuar con el flujo normal de la app
                    // Por ejemplo, iniciar la siguiente actividad
                    val intent = Intent(this@Login, Home::class.java)
                    startActivity(intent)
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(applicationContext, "Autenticación fallida", Toast.LENGTH_SHORT).show()
                    finish()
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Autenticación Biométrica")
            .setSubtitle("Inicia sesión usando tu huella digital")
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
            .build()

        biometricPrompt.authenticate(promptInfo)
    }

    private fun showLoginScreen() {
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

        //------------------Variables para realizar el Login con Google -------------------
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.your_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.ibGoogle.setOnClickListener {
            loginEnGoogle()
        }
    }

    private fun showAlert(msg: String = "Se ha producido un error autenticando al usuario") {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage(msg)
        builder.setPositiveButton("Aceptar", null)
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

    private fun loginEnGoogle() {
        val signInClient = googleSignInClient.signInIntent
        launcherVentanaGoogle.launch(signInClient)
    }

    private val launcherVentanaGoogle =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                manejarResultados(task)
            }
        }

    private fun manejarResultados(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful) {
            val account: GoogleSignInAccount? = task.result
            if (account != null) {
                actualizarUI(account)
            }
        } else {
            Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT).show()
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
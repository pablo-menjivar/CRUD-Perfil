package pablo.ayala.crudooo

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import java.util.UUID

class registrarse : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registrarse)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //1- Mando a llamar a todos los elementos de la vista
        val imgAtrasregistrarse = findViewById<ImageView>(R.id.imgBack)
        val txtCorreoRegistro = findViewById<EditText>(R.id.txtEmail1)
        val txtPasswordRegistro = findViewById<EditText>(R.id.txtContr)
        val txtConfirmarPassword = findViewById<EditText>(R.id.txtConfContr)
        val imgVerPassword = findViewById<ImageView>(R.id.imgVer1)
        val imgVerConfirmacionPassword = findViewById<ImageView>(R.id.imgVer2)
        val btnCrearCuenta = findViewById<Button>(R.id.btnCreate)
        val btnRegresarLogin = findViewById<Button>(R.id.btnLogear)

        //2- Programar los botones
        //TODO: Boton para crear la cuenta//
        //Al darle clic al boton se hace un insert a la base con los valores que escribe el usuario
        btnCrearCuenta.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                //Creo un objeto de la clase conexion
                val objConexion = ClaseConexion().cadenaConexion()

                //Creo una variable que contenga un PrepareStatement
                val crearUsuario =
                    objConexion?.prepareStatement("INSERT INTO tbUsuarios(UUID_usuario, correoElectronico, clave) VALUES (?, ?, ?)")!!
                crearUsuario.setString(1, UUID.randomUUID().toString())
                crearUsuario.setString(2, txtCorreoRegistro.text.toString())
                crearUsuario.setString(3, txtPasswordRegistro.text.toString())
                crearUsuario.executeUpdate()
                withContext(Dispatchers.Main) {
                    //Abro otra corrutina o "Hilo" para mostrar un mensaje y limpiar los campos
                    //Lo hago en el Hilo Main por que el hilo IO no permite mostrar nada en pantalla
                    Toast.makeText(this@registrarse, "Usuario creado", Toast.LENGTH_SHORT).show()
                    txtCorreoRegistro.setText("")
                    txtPasswordRegistro.setText("")
                    txtConfirmarPassword.setText("")
                }
            }

        }
        ///////////////////////////TODO: otras cosas ////////////////////////
        //Programo los botones para mostrar u ocultar la contraseña
        imgVerPassword.setOnClickListener {
            if (txtPasswordRegistro.inputType == InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD) {
                txtPasswordRegistro.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                txtPasswordRegistro.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
        }
        imgVerConfirmacionPassword.setOnClickListener {
            if (txtConfirmarPassword.inputType == InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD) {
                txtConfirmarPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                txtConfirmarPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
        }
        //Al darle clic a la flechita de arriba - Regresar al Login
        imgAtrasregistrarse.setOnClickListener {
            val pantallaLogin = Intent(this, login::class.java)
            startActivity(pantallaLogin)
        }
        //Al darle clic a al boton que ya tengo una cuenta - Regresar al Login
        btnRegresarLogin.setOnClickListener {
            val pantallaLogin = Intent(this, login::class.java)
            startActivity(pantallaLogin)
        }
    }
}
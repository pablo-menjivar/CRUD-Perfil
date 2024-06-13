package pablo.ayala.crudooo

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import modelo.ClaseConexion
import java.util.Date
import java.util.UUID

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //1- Mandar a llamar a todos los elementos de la vista activity-main
        val txtNum = findViewById<EditText>(R.id.txtNumTicket)
        val txtDec = findViewById<EditText>(R.id.txtDescripcion)
        val txtTitle = findViewById<EditText>(R.id.txtTitulo)
        val txtAut = findViewById<EditText>(R.id.txtAutor)
        val txtMail = findViewById<EditText>(R.id.txtEmail)
        val dateCreated = findViewById<EditText>(R.id.dateCreacion)
        val txtStatus = findViewById<EditText>(R.id.txtEstado)
        val dateFinished = findViewById<EditText>(R.id.dateFinalizacion)
        val btnSave = findViewById<Button>(R.id.btnGuardar)

        //2- Programar el botón de agregar
        btnSave.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {

                //1- Crear un objeto de la clase conexion
                val objConexion = ClaseConexion().cadenaConexion()

                //2- Crear una variable que contenga un PrepareStatement
                val addTicket = objConexion?.prepareStatement("insert into tbTickets (uuid, numero_ticket, titulo_ticket, descripcion_ticket, autor_ticket, email_autor, creacion_ticket, estado_ticket, finalizacion_ticket) values (?, ?, ?, ?, ?, ?, ?, ?, ?")!!
                addTicket.setString(1, UUID.randomUUID().toString())
                addTicket.setInt(2, txtNum.text.toString().toInt())
                addTicket.setString(3, txtTitle.text.toString())
                addTicket.setString(4, txtDec.text.toString())
                addTicket.setString(5, txtAut.text.toString())
                addTicket.setString(6, txtMail.text.toString())
                addTicket.setString(7, dateCreated.text.toString())
                addTicket.setString(8, txtStatus.text.toString())
                addTicket.setString(9, dateFinished.text.toString())
                addTicket.executeUpdate()
            }
        }
    }
}
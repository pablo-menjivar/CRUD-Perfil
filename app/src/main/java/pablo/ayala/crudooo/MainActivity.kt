package pablo.ayala.crudooo

import RecyclerViewHelpers.Adaptador
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import modelo.dataClassTickets
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
        val rcvTickets = findViewById<RecyclerView>(R.id.rcvTickets)

        //Primer paso para mostrar datos, asignarle un layout al RecyclerView
        rcvTickets.layoutManager = LinearLayoutManager(this)

        /////////////////// TODO: Mostrar datos ////////////////////
        //Funcion para mostrar los datos
        fun obtenerDatos(): List<dataClassTickets>{
            //1- Creo un objeto de la clase conexion
            val objConexion = ClaseConexion().cadenaConexion()

            //2- Creo un Statement
            val statement = objConexion?.createStatement()
            val resulSet = statement?.executeQuery("select * from tbTickets")!!

            val tickets = mutableListOf<dataClassTickets>()

            //Recorro todos los registros de la base de datos
            while (resulSet.next()){
                val uuid = resulSet.getString("uuid")
                val numero = resulSet.getInt("numero_ticket")
                val titulo = resulSet.getString("titulo_ticket")
                val descripcion = resulSet.getString("descripcion_ticket")
                val autor = resulSet.getString("autor_ticket")
                val email = resulSet.getString("email_autor")
                val creacion = resulSet.getString("creacion_ticket")
                val estado = resulSet.getString("estado_ticket")
                val finalizacion = resulSet.getString("finalizacion_ticket")

                val ticket = dataClassTickets(uuid, numero, titulo, descripcion, autor, email, creacion, estado, finalizacion)
                tickets.add(ticket)
            }
            return tickets
        }
        //Ultimo paso
        //Asignar el adaptador al RecyclerView
        CoroutineScope(Dispatchers.IO).launch {
            val ticketsDB = obtenerDatos()
            withContext(Dispatchers.Main){
                val adapter = Adaptador(ticketsDB)
                rcvTickets.adapter = adapter
            }
        }
        //2- Programar el botón de agregar
        btnSave.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {

                //1- Crear un objeto de la clase conexion
                val objConexion = ClaseConexion().cadenaConexion()

                //2- Crear una variable que contenga un PrepareStatement
                val addTicket = objConexion?.prepareStatement("insert into tbTickets(uuid, numero_ticket, titulo_ticket, descripcion_ticket, autor_ticket, email_autor, creacion_ticket, estado_ticket, finalizacion_ticket) values (?, ?, ?, ?, ?, ?, ?, ?, ?)")!!
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

                //Refresco la lista
                val nuevosTickets = obtenerDatos()
                withContext(Dispatchers.Main){
                    (rcvTickets.adapter as? Adaptador)?.actualizarLista(nuevosTickets)
                }
            }
        }
    }
}
package pablo.ayala.crudooo

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class detalle_ticket : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalle_ticket)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //Recibir los valores
        val UUIDRecibido = intent.getStringExtra("TicketUUID")
        val numeroRecibido = intent.getIntExtra("numero", 0)
        val tituloRecibido = intent.getStringExtra("titulo")
        val descripcionRecibida = intent.getStringExtra("descripcion")
        val autorRecibido = intent.getStringExtra("autor")
        val emailRecibido = intent.getStringExtra("email")
        val creacionFechaRecibida = intent.getStringExtra("creacionFecha")
        val estadoRecibido = intent.getStringExtra("estado")
        val finalizacionFechaRecibida = intent.getStringExtra("finalizacionFecha")

        //Mando a llamar a todos los elementos de la pantalla
        val txtUUIDDetalle = findViewById<TextView>(R.id.txtUUIDDetalle)
        val txtNumeroDetalle = findViewById<TextView>(R.id.txtNumeroDetalle)
        val txtTituloDetalle = findViewById<TextView>(R.id.txtTituloDetalle)
        val txtDescDetalle = findViewById<TextView>(R.id.txtDescDetalle)
        val txtAutorDetalle = findViewById<TextView>(R.id.txtAutorDetalle)
        val txtEmailDetalle = findViewById<TextView>(R.id.txtEmailDetalle)
        val txtCreacionFechaDetalle = findViewById<TextView>(R.id.txtCreacionFechaDetalle)
        val txtEstadoDetalle = findViewById<TextView>(R.id.txtEstadoDetalle)
        val txtFinalizacionFechaDetalle = findViewById<TextView>(R.id.txtFinalizacionFechaDetalle)

        //Asigarle los datos recibidos a mis TextView
        txtUUIDDetalle.text = UUIDRecibido
        txtNumeroDetalle.text = numeroRecibido.toString()
        txtTituloDetalle.text = tituloRecibido
        txtDescDetalle.text = descripcionRecibida
        txtAutorDetalle.text = autorRecibido
        txtEmailDetalle.text = emailRecibido
        txtCreacionFechaDetalle.text = creacionFechaRecibida
        txtEstadoDetalle.text = estadoRecibido
        txtFinalizacionFechaDetalle.text = finalizacionFechaRecibida
    }
}
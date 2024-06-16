package RecyclerViewHelpers

import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import modelo.dataClassTickets
import pablo.ayala.crudooo.R
import pablo.ayala.crudooo.detalle_ticket

class Adaptador(private var Datos: List<dataClassTickets>) : RecyclerView.Adapter<ViewHolder>() {
    fun actualizarLista(nuevaLista: List<dataClassTickets>) {
        Datos = nuevaLista
        notifyDataSetChanged() //Notificar al adaptor sobre los cambios
    }

    fun actualicePantalla(uuid: String, nuevoNombre: String){
        val index = Datos.indexOfFirst { it.uuid == uuid }
        Datos[index].titulo_ticket = nuevoNombre
        notifyDataSetChanged()
    }

    /////////////////// TODO: Eliminar datos
    fun eliminarDatos(titulo_ticket: String, posicion: Int){
        //Actualizo la lista de datos y notifico al adaptador
        val listaDatos = Datos.toMutableList()
        listaDatos.removeAt(posicion)

        GlobalScope.launch(Dispatchers.IO){
            //1- Creamos un objeto de la clase conexion
            val objConexion = ClaseConexion().cadenaConexion()

            //2- Crear una variable que contenga un PrepareStatement
            val deleteTicket = objConexion?.prepareStatement("delete from tbTickets where titulo_ticket = ?")!!
            deleteTicket.setString(1, titulo_ticket)
            deleteTicket.executeUpdate()

            val commit = objConexion.prepareStatement("commit")!!
            commit.executeUpdate()
        }
        Datos = listaDatos.toList()
        // Notificar al adaptador sobre los cambios
        notifyItemRemoved(posicion)
        notifyDataSetChanged()
    }

    //////////////////////TODO: Editar datos
    fun actualizarDato(nuevoNombre: String, uuid: String) {
        GlobalScope.launch(Dispatchers.IO) {

            //1- Creo un objeto de la clase de conexion
            val objConexion = ClaseConexion().cadenaConexion()

            //2- creo una variable que contenga un PrepareStatement
            val updateTicket = objConexion?.prepareStatement("update tbTickets set titulo_ticket = ? where uuid = ?")!!
            updateTicket.setString(1, nuevoNombre)
            updateTicket.setString(2, uuid)
            updateTicket.executeUpdate()

            withContext(Dispatchers.Main) {
                actualicePantalla(uuid, nuevoNombre)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista =
            LayoutInflater.from(parent.context).inflate(R.layout.activity_item_card, parent, false)
        return ViewHolder(vista)
    }

    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ticket = Datos[position]
        holder.textView.text = ticket.titulo_ticket

        //todo: clic al icono de eliminar
        holder.imgBorrar.setOnClickListener {

            //Creamos un Alert Dialog
            val context = holder.itemView.context

            val builder = AlertDialog.Builder(context)
            builder.setTitle("Eliminar")
            builder.setMessage("¿Desea eliminar el ticket?")

            //Botones
            builder.setPositiveButton("Si") { dialog, which ->
                eliminarDatos(ticket.titulo_ticket, position)
            }
            builder.setNegativeButton("No"){dialog, which ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()

        }

        //Todo: icono de editar
        holder.imgEditar.setOnClickListener{
            //Creamos un Alert Dialog
            val context = holder.itemView.context

            val builder = AlertDialog.Builder(context)
            builder.setTitle("Actualizar")
            builder.setMessage("¿Desea actualizar el ticket?")

            //Agregarle un cuadro de texto para
            //que el usuario escriba el nuevo nombre
            val cuadroTexto = EditText(context)
            cuadroTexto.setHint(ticket.titulo_ticket)
            builder.setView(cuadroTexto)

            //Botones
            builder.setPositiveButton("Actualizar") { dialog, which ->
                actualizarDato(cuadroTexto.text.toString(), ticket.uuid)
            }

            builder.setNegativeButton("Cancelar"){dialog, which ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()
        }

        //Todo: Clic a la card completa
        //Vamos a ir a otra pantalla donde me mostrará todos los datos
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context

            //Cambiar de pantalla a la pantalla de detalle
            val pantallaDetalle = Intent(context, detalle_ticket::class.java)
            //enviar a la otra pantalla todos mis valores
            pantallaDetalle.putExtra("TicketUUID", ticket.uuid)
            pantallaDetalle.putExtra("numero", ticket.numero_ticket)
            pantallaDetalle.putExtra("titulo", ticket.titulo_ticket)
            pantallaDetalle.putExtra("descripcion", ticket.descripcion_ticket)
            pantallaDetalle.putExtra("autor", ticket.autor_ticket)
            pantallaDetalle.putExtra("email", ticket.email_autor)
            pantallaDetalle.putExtra("creacionFecha", ticket.creacion_ticket)
            pantallaDetalle.putExtra("estado", ticket.estado_ticket)
            pantallaDetalle.putExtra("finalizacionFecha", ticket.finalizacion_ticket)

            context.startActivity(pantallaDetalle)
        }
    }

}
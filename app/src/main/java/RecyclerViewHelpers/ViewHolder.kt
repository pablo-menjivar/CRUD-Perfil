package RecyclerViewHelpers

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pablo.ayala.crudooo.R

class ViewHolder(view: View): RecyclerView.ViewHolder(view){
    //En el ViewHolder mando a llamar todos los elementos de la Card
    val textView: TextView = view.findViewById(R.id.txtTicketsCard)
    val imgEditar: ImageView = view.findViewById(R.id.imgEditar)
    val imgBorrar: ImageView = view.findViewById(R.id.imgBorrar)
}
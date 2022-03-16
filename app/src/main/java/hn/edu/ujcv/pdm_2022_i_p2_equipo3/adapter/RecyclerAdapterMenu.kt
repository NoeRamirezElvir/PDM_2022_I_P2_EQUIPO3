package hn.edu.ujcv.pdm_2022_i_p2_equipo3.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import hn.edu.ujcv.pdm_2022_i_p2_equipo3.R
import hn.edu.ujcv.pdm_2022_i_p2_equipo3.VerMenusActivity
import hn.edu.ujcv.pdm_2022_i_p2_equipo3.clases.Menu

class RecyclerAdapterMenu(pLista: ArrayList<Menu>? = null,activity: VerMenusActivity): RecyclerView.Adapter<RecyclerAdapterMenu.ViewHolder>() {
    var listaMenu:ArrayList<Menu> = pLista!!
    private var positiong:Int = 0

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var itemImage: ImageView = itemView.findViewById(R.id.itemImageM)
        var itemNombre: TextView = itemView.findViewById(R.id.itemNombreM)
        var itemCodigo: TextView = itemView.findViewById(R.id.itemCodigoM)
        var itemDescripcion: TextView = itemView.findViewById(R.id.itemDescripcion)
        var itemPrecio:TextView = itemView.findViewById(R.id.itemPrecioM)
        init {
            itemView.setOnClickListener{ pv:View ->
                val position:Int = adapterPosition
                positiong = position
                Snackbar.make(pv,"Desea eliminar el registro en la posicion ${positiong+1}",
                    Snackbar.LENGTH_LONG).setAction("Eliminar",eliminarOnClickListener).show()
            }
        }
    }
    private val eliminarOnClickListener:View.OnClickListener = View.OnClickListener { view ->
        listaMenu.removeAt(positiong)
        activity.actualizarRecyclerView()
        Snackbar.make(view, "Registro Eliminado", Snackbar.LENGTH_LONG)
            .setAction("Eliminado", null).show()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapterMenu.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout_menu,parent,false)
        return ViewHolder(v)
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerAdapterMenu.ViewHolder, position: Int) {
        val menu=listaMenu[position]
        holder.itemImage.setImageResource(menu.imag!!)
        holder.itemCodigo.text = "Código: ${menu.codigo}"
        holder.itemNombre.text = "Nombre: ${menu.nombre}"
        holder.itemPrecio.text = "Lps. ${menu.precio.toString()}"
        holder.itemDescripcion.text = menu.descripcion
    }
    override fun getItemCount(): Int {
        return listaMenu.size
    }


}
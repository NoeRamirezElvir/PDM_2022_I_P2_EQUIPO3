package hn.edu.ujcv.pdm_2022_i_p2_equipo3.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import hn.edu.ujcv.pdm_2022_i_p2_equipo3.R
import hn.edu.ujcv.pdm_2022_i_p2_equipo3.VerClientesActivity
import hn.edu.ujcv.pdm_2022_i_p2_equipo3.clases.Cliente

class RecyclerAdapterCliente(pLista: ArrayList<Cliente>? = null,activity: VerClientesActivity) : RecyclerView.Adapter<RecyclerAdapterCliente.ViewHolder>(){
    var listaCliente:ArrayList<Cliente> = pLista!!
    private var positiong:Int = 0
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var itemImage:ImageView = itemView.findViewById(R.id.itemImage)
        var itemNombre:TextView = itemView.findViewById(R.id.itemNombre)
        var itemCorreo:TextView = itemView.findViewById(R.id.itemCorreo)
        var itemId:TextView = itemView.findViewById(R.id.itemId)

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
        listaCliente.removeAt(positiong)
        activity.actualizarRecyclerView()
        Snackbar.make(view, "Registro Eliminado", Snackbar.LENGTH_LONG)
            .setAction("Eliminado", null).show()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapterCliente.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout_cliente,parent,false)
        return ViewHolder(v)
    }
    override fun onBindViewHolder(holder: RecyclerAdapterCliente.ViewHolder, position: Int) {
        val cliente = listaCliente[position]
        holder.itemId.text = cliente.id
        holder.itemNombre.text = cliente.nombre
        holder.itemCorreo.text = cliente.correo
        holder.itemImage.setImageResource(cliente.imag!!)
    }
    override fun getItemCount(): Int {
        return listaCliente.size
    }
}
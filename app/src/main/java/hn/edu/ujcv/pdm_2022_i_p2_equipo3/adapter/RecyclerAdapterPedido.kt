package hn.edu.ujcv.pdm_2022_i_p2_equipo3.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import hn.edu.ujcv.pdm_2022_i_p2_equipo3.R
import hn.edu.ujcv.pdm_2022_i_p2_equipo3.VerPedidosActivity
import hn.edu.ujcv.pdm_2022_i_p2_equipo3.clases.Pedidos

class RecyclerAdapterPedido(pListaPedido: ArrayList<Pedidos>? = null,
                            activity: VerPedidosActivity):
    RecyclerView.Adapter<RecyclerAdapterPedido.ViewHolder>() {
    var listaPedidos:ArrayList<Pedidos> = pListaPedido!!
    private var positiong:Int = 0

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var itemImagenPedido: ImageView = itemView.findViewById(R.id.itemImagenPedido)
        var itemCliente: TextView = itemView.findViewById(R.id.itemCliente)
        var itemEmpleado: TextView = itemView.findViewById(R.id.itemEmpleado)
        var itemPago: TextView = itemView.findViewById(R.id.itemPago)

        init {
            itemView.setOnClickListener { pv: View ->
                val position: Int = adapterPosition
                positiong = position
                Snackbar.make(pv, "Desea elminar el registro en la posicion ${positiong+1}?",
                Snackbar.LENGTH_LONG).setAction("Eliminar",eliminarOnClickListener).show()
            }
        }
    }
    private val eliminarOnClickListener:View.OnClickListener = View.OnClickListener { view ->
        listaPedidos.removeAt(positiong)
        activity.actualizarRecyclerView()
        Snackbar.make(view, "Registro Eliminado", Snackbar.LENGTH_LONG)
            .setAction("Eliminado", null).show()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v=LayoutInflater.from(parent.context)
            .inflate(R.layout.card_layout_pedidos, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pedido=listaPedidos[position]
        holder.itemCliente.text = pedido.cliente!!.nombre
        holder.itemEmpleado.text = "Atendido por: " +pedido.empleado!!.nombre
        holder.itemImagenPedido.setImageResource(pedido.imagen!!)
        holder.itemPago.text = "Pago: L"+pedido.total.toString()
    }

    override fun getItemCount(): Int {
        return listaPedidos.size
    }

}
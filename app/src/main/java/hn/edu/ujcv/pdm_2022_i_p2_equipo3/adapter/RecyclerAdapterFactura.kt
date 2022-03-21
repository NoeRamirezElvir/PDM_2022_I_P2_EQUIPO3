package hn.edu.ujcv.pdm_2022_i_p2_equipo3.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import hn.edu.ujcv.pdm_2022_i_p2_equipo3.R
import hn.edu.ujcv.pdm_2022_i_p2_equipo3.VerFacturaActivity
import hn.edu.ujcv.pdm_2022_i_p2_equipo3.clases.Factura

class RecyclerAdapterFactura(listaFact:ArrayList<Factura>? =null , activity: VerFacturaActivity):
    RecyclerView.Adapter<RecyclerAdapterFactura.ViewHolder>(){

    var listaFacturas:ArrayList<Factura> = listaFact!!
    private var positionf:Int = 0

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var itemImagenFact: ImageView = itemView.findViewById(R.id.itemImageFactura)
        var itemNoPedido: TextView = itemView.findViewById(R.id.itemNoPedido)
        var itemTipoPago: TextView = itemView.findViewById(R.id.itemTipoPago)
        var itemEmpleado: TextView = itemView.findViewById(R.id.itemEmpleadoFact)

        init {
            itemView.setOnClickListener { fv: View ->
                val position: Int = adapterPosition
                positionf = position
                Snackbar.make(fv, "Desea elminar el registro en la posicion ${positionf+1}?",
                    Snackbar.LENGTH_LONG).setAction("Eliminar",eliminarOnClickListener).show()
            }
        }
    }
    private val eliminarOnClickListener:View.OnClickListener = View.OnClickListener { view ->
        listaFacturas.removeAt(positionf)
        activity.actualizarRecyclerView()
        Snackbar.make(view, "Registro Eliminado", Snackbar.LENGTH_LONG)
            .setAction("Eliminado", null).show()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v= LayoutInflater.from(parent.context)
            .inflate(R.layout.card_layout_facturas, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val factura=listaFacturas[position]
        holder.itemNoPedido.text = factura.pedido!!.noPedido.toString()
        holder.itemTipoPago.text = "Tipo de Pago: "+factura.tipoPago
        holder.itemEmpleado.text = "Atendido por: " +factura.empleadoFact
        holder.itemImagenFact.setImageResource(factura.imagenF!!)
    }

    override fun getItemCount(): Int {
        return listaFacturas.size
    }
}
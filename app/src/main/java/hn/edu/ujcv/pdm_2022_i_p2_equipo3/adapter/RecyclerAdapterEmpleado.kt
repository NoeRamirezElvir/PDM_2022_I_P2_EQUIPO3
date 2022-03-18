package hn.edu.ujcv.pdm_2022_i_p2_equipo3.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import androidx.recyclerview.widget.RecyclerView
import hn.edu.ujcv.pdm_2022_i_p2_equipo3.R
import hn.edu.ujcv.pdm_2022_i_p2_equipo3.VerEmpleadosActivity
import hn.edu.ujcv.pdm_2022_i_p2_equipo3.clases.Empleado

class RecyclerAdapterEmpleado(pListaEmpleado: ArrayList<Empleado>? = null,
                              activity: VerEmpleadosActivity):
    RecyclerView.Adapter<RecyclerAdapterEmpleado.ViewHolder>() {
    var listaEmpleado:ArrayList<Empleado> = pListaEmpleado!!
    private var positiong:Int = 0

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var itemImage: ImageView = itemView.findViewById(R.id.itemImagenEmpleado)
        var itemCodigo: TextView = itemView.findViewById(R.id.itemCodigoEmpleado)
        var itemNombre: TextView = itemView.findViewById(R.id.itemNombreEmpleado)
        var itemPuesto: TextView = itemView.findViewById(R.id.itemPuestoEmpleado)

        init {
            itemView.setOnClickListener { pv: View ->
                val position: Int = adapterPosition
                positiong = position
                Snackbar.make(pv, "¿Desea eliminar el registro en la posicion ${positiong + 1} ?",
                    Snackbar.LENGTH_LONG).setAction("Eliminar", eliminarOnClickListener).show()
            }

        }
    }
    private val eliminarOnClickListener:View.OnClickListener = View.OnClickListener { view ->
        listaEmpleado.removeAt(positiong)
        activity.actualizarRecyclerView()
        Snackbar.make(view, "Registro Eliminado", Snackbar.LENGTH_LONG)
            .setAction("Eliminado", null).show()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v=LayoutInflater.from(parent.context)
            .inflate(R.layout.card_layout_empleado, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val empleado=listaEmpleado[position]
        holder.itemCodigo.text = empleado.codigo
        holder.itemNombre.text = empleado.nombre
        holder.itemPuesto.text = empleado.puesto
        holder.itemImage.setImageResource(empleado.imagen_!!)
    }

    override fun getItemCount(): Int {
        return listaEmpleado.size
    }

}
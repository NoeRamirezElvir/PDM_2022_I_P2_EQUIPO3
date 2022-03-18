package hn.edu.ujcv.pdm_2022_i_p2_equipo3.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import hn.edu.ujcv.pdm_2022_i_p2_equipo3.R
import hn.edu.ujcv.pdm_2022_i_p2_equipo3.RegistrarPedidoActivity

class ListViewAdapter(var mCxtx: Context, var resources:Int, var items:List<ModelListView>)
    :ArrayAdapter<ModelListView>(mCxtx,resources,items){

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCxtx)
        val view:View = layoutInflater.inflate(resources,null)

        val precio:TextView = view.findViewById(R.id.txvPrecio)
        val menu: TextView = view.findViewById(R.id.txvMenu)
        val cantidad: TextView = view.findViewById(R.id.txvCantidad)
        val sumar: ImageView = view.findViewById(R.id.ivAdd)
        val restar: ImageView = view.findViewById(R.id.ivLess)


        var mItem:ModelListView = items[position]
        precio.text = "L"+mItem.subtotal.toString()
        menu.text = mItem.codigo+"  "+mItem.nombre
        cantidad.text = mItem.cantidad.toString()

        val _precio = mItem.precio
        sumar.setOnClickListener {
            if (cantidad.text.toString().toInt() < 99) {
                var nueva_cantidad = cantidad.text.toString().toInt() + 1
                var sub = _precio*nueva_cantidad
                cantidad.text = nueva_cantidad.toString()
                precio.text = "L"+sub
                mItem.cantidad=nueva_cantidad
                mItem.subtotal=sub
            }
        }

        restar.setOnClickListener {
            if (cantidad.text.toString().toInt()> 1) {
                var nueva_cantidad = cantidad.text.toString().toInt() - 1
                var sub = _precio*nueva_cantidad
                cantidad.text = nueva_cantidad.toString()
                precio.text = "L"+sub
                mItem.cantidad=nueva_cantidad
                mItem.subtotal=sub
            }
        }

        return view
    }

    private fun calcular() {

    }
    }
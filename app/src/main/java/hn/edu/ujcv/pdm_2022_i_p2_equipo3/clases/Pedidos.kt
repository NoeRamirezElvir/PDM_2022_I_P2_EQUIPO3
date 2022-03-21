package hn.edu.ujcv.pdm_2022_i_p2_equipo3.clases

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Pedidos(var noPedido: Int?,var cliente:Cliente?, var empleado:Empleado?,
              var listaPedidosMenu: ArrayList<Menu>?, var total:Double?, var imagen:Int?):Parcelable {
}
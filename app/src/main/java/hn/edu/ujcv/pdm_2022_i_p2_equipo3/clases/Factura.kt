package hn.edu.ujcv.pdm_2022_i_p2_equipo3.clases

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Factura(var noFact:Int?,var pedido:Pedidos?,var tipoPago:String?,var efvo:Double?,
              var empleadoFact:Empleado?,var imagenF :Int?) :Parcelable{
}
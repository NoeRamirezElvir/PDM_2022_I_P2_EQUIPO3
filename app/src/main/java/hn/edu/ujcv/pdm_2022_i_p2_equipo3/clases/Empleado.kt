package hn.edu.ujcv.pdm_2022_i_p2_equipo3.clases

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Empleado(val codigo:String?, val nombre:String?, val puesto:String?, val imagen_:Int?):Parcelable {
}
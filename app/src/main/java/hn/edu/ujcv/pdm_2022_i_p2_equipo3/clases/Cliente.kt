package hn.edu.ujcv.pdm_2022_i_p2_equipo3.clases

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Cliente(val id:String?,val nombre:String?,val correo:String?,val imag:Int?):Parcelable {
}
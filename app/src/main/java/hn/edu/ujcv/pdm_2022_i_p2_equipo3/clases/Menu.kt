package hn.edu.ujcv.pdm_2022_i_p2_equipo3.clases

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Menu(val codigo:String?,val nombre:String?,val precio:Double?,val descripcion:String?,val imag:Int?):Parcelable {
}
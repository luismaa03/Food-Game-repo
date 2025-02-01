package modelo

import android.os.Parcel
import android.os.Parcelable

data class Ingrediente(
    val nombre: String,
    val cantidad: String,
    val informacionNutricional: String,
    var imageUrl: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() // Read imageUrl
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombre)
        parcel.writeString(cantidad)
        parcel.writeString(informacionNutricional)
        parcel.writeString(imageUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Ingrediente> {
        override fun createFromParcel(parcel: Parcel): Ingrediente {
            return Ingrediente(parcel)
        }

        override fun newArray(size: Int): Array<Ingrediente?> {
            return arrayOfNulls(size)
        }
    }
}
package modelo

import android.os.Parcel
import android.os.Parcelable

data class Plato(
    val nombre: String,
    val calorias: Double,
    val ingredientes: String,
    val imageName: String,
    val descripcion: String,
    var preguntas: List<Pregunta> = emptyList()
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readDouble(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.createTypedArrayList(Pregunta) ?: emptyList()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombre)
        parcel.writeDouble(calorias)
        parcel.writeString(ingredientes)
        parcel.writeString(imageName)
        parcel.writeString(descripcion)
        parcel.writeTypedList(preguntas)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Plato> {
        override fun createFromParcel(parcel: Parcel): Plato {
            return Plato(parcel)
        }

        override fun newArray(size: Int): Array<Plato?> {
            return arrayOfNulls(size)
        }
    }
}
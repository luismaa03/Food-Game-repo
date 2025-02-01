package modelo

import android.os.Parcel
import android.os.Parcelable
import com.example.foodgame.R


data class Plato(
    val nombre: String,
    val calorias: Double,
    val ingredientes: List<Ingrediente>,
    val imageName: String,
    val preparacion: String,
    val preguntas: List<Pregunta> = emptyList(),
    val imagen: Int = R.drawable.placeholder
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readDouble(),
        parcel.createTypedArrayList(Ingrediente) ?: emptyList(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.createTypedArrayList(Pregunta) ?: emptyList(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombre)
        parcel.writeDouble(calorias)
        parcel.writeTypedList(ingredientes)
        parcel.writeString(imageName)
        parcel.writeString(preparacion)
        parcel.writeTypedList(preguntas)
        parcel.writeInt(imagen)
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
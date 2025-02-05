package modelo

import android.os.Parcel
import android.os.Parcelable

enum class TipoPregunta {
    OPCION_MULTIPLE,
    RESPUESTA_LIBRE
}

data class Pregunta(
    val texto: String,
    val opciones: List<String>,
    val respuestaCorrecta: String,
    val tipo: TipoPregunta
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.createStringArrayList() ?: emptyList(),
        parcel.readString() ?: "",
        TipoPregunta.valueOf(parcel.readString() ?: "OPCION_MULTIPLE")
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(texto)
        parcel.writeStringList(opciones)
        parcel.writeString(respuestaCorrecta)
        parcel.writeString(tipo.name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Pregunta> {
        override fun createFromParcel(parcel: Parcel): Pregunta {
            return Pregunta(parcel)
        }

        override fun newArray(size: Int): Array<Pregunta?> {
            return arrayOfNulls(size)
        }
    }
}
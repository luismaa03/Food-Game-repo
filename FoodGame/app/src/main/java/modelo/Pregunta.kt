package modelo

import android.os.Parcel
import android.os.Parcelable

enum class TipoPregunta {
    OPCION_MULTIPLE,
    VERDADERO_FALSO
}

data class Pregunta(
    val pregunta: String = "",
    val respuestas: List<String> = emptyList(),
    val respuestaCorrecta: String = "",
    val tipo: TipoPregunta = TipoPregunta.OPCION_MULTIPLE
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.createStringArrayList() ?: emptyList(),
        parcel.readString() ?: "",
        TipoPregunta.valueOf(parcel.readString() ?: TipoPregunta.OPCION_MULTIPLE.name)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(pregunta)
        parcel.writeStringList(respuestas)
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


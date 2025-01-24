package modelo

import java.io.Serializable

enum class TipoPregunta {
    OPCION_MULTIPLE,
    VERDADERO_FALSO
}

data class Pregunta(
    val texto: String,
    val opciones: List<String>,
    val respuestaCorrecta: Int,
    val tipo: TipoPregunta
) : Serializable
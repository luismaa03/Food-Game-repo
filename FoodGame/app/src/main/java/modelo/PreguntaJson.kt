package modelo

data class PreguntaJson(
    val pregunta: String = "",
    val respuestas: List<String> = emptyList(),
    val respuestaCorrecta: String = ""
)


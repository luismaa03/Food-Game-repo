package com.example.foodgame

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import modelo.Pregunta
import modelo.PreguntaJson
import modelo.TipoPregunta

class JsonUtils {
    companion object {
        fun convertirPreguntasAJson(preguntas: List<Pregunta>): String {
            val preguntasJson = preguntas.map { pregunta ->
                PreguntaJson(
                    pregunta = pregunta.pregunta,
                    respuestas = pregunta.respuestas,
                    respuestaCorrecta = pregunta.respuestaCorrecta
                )
            }
            val gson = Gson()
            return gson.toJson(preguntasJson)
        }

        fun convertirJsonAPreguntas(json: String): List<Pregunta> {
            val gson = Gson()
            val type = object : TypeToken<List<PreguntaJson>>() {}.type
            val preguntasJson: List<PreguntaJson> = gson.fromJson(json, type)
            return preguntasJson.map { preguntaJson ->
                Pregunta(
                    pregunta = preguntaJson.pregunta,
                    respuestas = preguntaJson.respuestas,
                    respuestaCorrecta = preguntaJson.respuestaCorrecta,
                    tipo = TipoPregunta.OPCION_MULTIPLE
                )
            }
        }
    }
}
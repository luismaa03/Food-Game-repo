package com.example.foodgame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import modelo.Pregunta
import modelo.TipoPregunta

class PreguntaFragment : Fragment() {

    private lateinit var pregunta: Pregunta
    private lateinit var tvPregunta: TextView
    private lateinit var rgOpciones: RadioGroup
    private lateinit var rbOpcion1: RadioButton
    private lateinit var rbOpcion2: RadioButton
    private lateinit var rbOpcion3: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            pregunta = it.getParcelable(ARG_PREGUNTA)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pregunta, container, false)

        tvPregunta = view.findViewById(R.id.tvPregunta)
        rgOpciones = view.findViewById(R.id.rgOpciones)
        rbOpcion1 = view.findViewById(R.id.rbOpcion1)
        rbOpcion2 = view.findViewById(R.id.rbOpcion2)
        rbOpcion3 = view.findViewById(R.id.rbOpcion3)

        tvPregunta.text = pregunta.pregunta

        if (pregunta.tipo == TipoPregunta.OPCION_MULTIPLE) {
            rbOpcion1.text = pregunta.respuestas[0]
            rbOpcion2.text = pregunta.respuestas[1]
            // Asegúrate de que haya al menos 3 respuestas antes de intentar acceder a la tercera
            if (pregunta.respuestas.size > 2) {
                rbOpcion3.text = pregunta.respuestas[2]
                rbOpcion3.visibility = View.VISIBLE
            } else {
                rbOpcion3.visibility = View.GONE
            }
        } else {
            // ... (lógica para otros tipos de preguntas) ...
        }

        return view
    }

    fun obtenerRespuestaSeleccionada(): Int {
        return when (rgOpciones.checkedRadioButtonId) {
            rbOpcion1.id -> 0
            rbOpcion2.id -> 1
            rbOpcion3.id -> 2
            else -> -1
        }
    }

    companion object {
        private const val ARG_PREGUNTA = "pregunta"

        fun newInstance(pregunta: Pregunta): PreguntaFragment {
            val fragment = PreguntaFragment()
            val args = Bundle()
            args.putParcelable(ARG_PREGUNTA, pregunta)
            fragment.arguments = args
            return fragment
        }
    }
}
package com.example.foodgame

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import com.example.foodgame.databinding.FragmentPreguntaBinding
import modelo.Pregunta
import modelo.TipoPregunta

class PreguntaFragment : Fragment() {

    private var _binding: FragmentPreguntaBinding? = null
    private val binding get() = _binding!!
    private lateinit var pregunta: Pregunta
    private var position: Int = 0
    private var listener: RespuestaSeleccionadaListener? = null
    private var respuestaSeleccionada: String = ""
    private var isAnswered: Boolean = false

    interface RespuestaSeleccionadaListener {
        fun onRespuestaSeleccionada(respuesta: String, position: Int)
    }

    companion object {
        private const val ARG_PREGUNTA = "pregunta"
        private const val ARG_POSITION = "position"

        fun newInstance(pregunta: Pregunta, position: Int): PreguntaFragment {
            val fragment = PreguntaFragment()
            val args = Bundle()
            args.putParcelable(ARG_PREGUNTA, pregunta)
            args.putInt(ARG_POSITION, position)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            pregunta = it.getParcelable(ARG_PREGUNTA)!!
            position = it.getInt(ARG_POSITION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPreguntaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            tvPregunta.text = pregunta.texto // Cambiado a pregunta.texto
            rgOpciones.removeAllViews() // Limpiar RadioGroup

            when (pregunta.tipo) {
                TipoPregunta.OPCION_MULTIPLE -> {
                    pregunta.opciones.forEach { respuesta -> // Cambiado a pregunta.opciones
                        val radioButton = RadioButton(context)
                        radioButton.text = respuesta
                        rgOpciones.addView(radioButton)
                    }
                    // Llamar a aumentarTamanoLetraRadioButtons() aquí, después de añadir los RadioButton
                    aumentarTamanoLetraRadioButtons(rgOpciones)
                }

                TipoPregunta.RESPUESTA_LIBRE -> TODO()
            }
            rgOpciones.setOnCheckedChangeListener { _, checkedId ->
                if (checkedId == -1) {
                    if (isAnswered) {
                        listener?.onRespuestaSeleccionada("", position)
                        isAnswered = false
                    }
                } else {
                    val radioButton = binding.rgOpciones.findViewById<RadioButton>(checkedId)
                    respuestaSeleccionada = radioButton?.text.toString()
                    listener?.onRespuestaSeleccionada(respuestaSeleccionada, position)
                    isAnswered = true
                }
            }
        }
    }

    fun setRespuestaSeleccionadaListener(listener: RespuestaSeleccionadaListener) {
        this.listener = listener
    }

    private fun aumentarTamanoLetraRadioButtons(radioGroup: RadioGroup) {
        for (i in 0 until radioGroup.childCount) {
            val radioButton = radioGroup.getChildAt(i) as RadioButton
            radioButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24f) // Tamaño de letra en sp (puedes cambiarlo)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
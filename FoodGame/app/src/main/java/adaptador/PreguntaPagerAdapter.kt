package adaptador

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import modelo.Pregunta

class PreguntaPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val preguntas: List<Pregunta>,
    private val listener: PreguntaFragment.RespuestaSeleccionadaListener
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = preguntas.size

    override fun createFragment(position: Int): Fragment {
        val fragment = PreguntaFragment.newInstance(preguntas[position], position)
        fragment.setRespuestaSeleccionadaListener(listener)
        return fragment
    }
}
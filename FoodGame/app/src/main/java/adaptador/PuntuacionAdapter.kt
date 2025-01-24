package adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodgame.R
import modelo.Puntuacion

// Adaptador para gestionar una lista de objetos de tipo Puntuacion en un RecyclerView
class PuntuacionAdapter(private val puntuaciones: List<Puntuacion>) :
    RecyclerView.Adapter<PuntuacionAdapter.ViewHolder>() {

    // Crea un nuevo ViewHolder inflando el diseño del elemento
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_puntuacion, parent, false)
        return ViewHolder(view)
    }

    // Vincula los datos de una puntuación al ViewHolder en una posición específica
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val puntuacion = puntuaciones[position]
        holder.tvPuntos.text = "Puntuación: ${puntuacion.puntos}"
    }

    // Devuelve el número total de elementos en la lista
    override fun getItemCount(): Int {
        return puntuaciones.size
    }

    // ViewHolder interno para representar un elemento del RecyclerView
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvPuntos: TextView = itemView.findViewById(R.id.tvPuntos)
    }
}

package adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodgame.FirebaseUtils
import com.example.foodgame.JuegoIngredientes
import com.example.foodgame.R

class IngredientesAdapter(
    private val ingredientes: List<String>,
    private val ingredientesSeleccionados: MutableList<String>,
    private val onIngredienteClick: (String) -> Unit
) : RecyclerView.Adapter<IngredientesAdapter.IngredienteViewHolder>() {

    class IngredienteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivIngrediente: ImageView = view.findViewById(R.id.ivIngrediente)
        val tvNombreIngrediente: TextView = view.findViewById(R.id.tvNombreIngrediente)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredienteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ingrediente, parent, false)
        return IngredienteViewHolder(view)
    }

    override fun onBindViewHolder(holder: IngredienteViewHolder, position: Int) {
        val ingrediente = ingredientes[position]
        holder.tvNombreIngrediente.text = ingrediente

        FirebaseUtils.obtenerMetadatosImagenDesdeBaseDeDatos(ingrediente) { metadatos ->
            if (metadatos != null) {
                FirebaseUtils.cargarImagen(metadatos.urlImagen, holder.ivIngrediente)
            }
        }

        // Marcar/Desmarcar ingrediente
        if (ingredientesSeleccionados.contains(ingrediente)) {
            holder.itemView.alpha = 0.5f
        } else {
            holder.itemView.alpha = 1.0f
        }

        holder.itemView.setOnClickListener {
            onIngredienteClick(ingrediente)
        }

        holder.itemView.setOnLongClickListener {
            (holder.itemView.context as? JuegoIngredientes)?.mostrarInformacionNutricional(ingrediente)
            true
        }
    }

    override fun getItemCount(): Int = ingredientes.size
}
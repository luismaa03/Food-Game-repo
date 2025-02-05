package adaptador

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import modelo.FirebaseUtils
import com.example.foodgame.R
import modelo.Ingrediente

class IngredientesAdapter(
    private val ingredientes: List<Ingrediente>,
    private val ingredientesSeleccionados: MutableList<Ingrediente>,
    private val onIngredienteClick: (Ingrediente) -> Unit,
    private val onIngredienteLongClick: (Ingrediente) -> Unit // Añadimos el long click listener
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
        holder.tvNombreIngrediente.text = ingrediente.nombre

        Log.d("IngredientesAdapter", "Cargando imagen para ${ingrediente.nombre}")
        FirebaseUtils.cargarImagen(ingrediente.id, holder.ivIngrediente)

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
            onIngredienteLongClick(ingrediente)
            true
        }
    }

    override fun getItemCount(): Int = ingredientes.size
}
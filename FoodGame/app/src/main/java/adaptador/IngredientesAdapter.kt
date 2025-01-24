package adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.foodgame.R

class IngredientesAdapter(
    private val ingredientes: List<String>, // Lista de ingredientes que se mostrará en el RecyclerView
    private val onIngredienteClick: (String, Int) -> Unit // Callback para manejar clics en los ingredientes
) : RecyclerView.Adapter<IngredientesAdapter.IngredienteViewHolder>() {

    private val selectedItems = mutableSetOf<Int>() // Conjunto para rastrear los índices de los ingredientes seleccionados

    // Crea un nuevo ViewHolder para cada elemento del RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredienteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ingrediente, parent, false)
        return IngredienteViewHolder(view)
    }

    // Vincula los datos de un ingrediente a un ViewHolder en una posición específica
    override fun onBindViewHolder(holder: IngredienteViewHolder, position: Int) {
        val ingrediente = ingredientes[position]
        val imagenId = obtenerImagenIngrediente(ingrediente)
        holder.ivIngrediente.setImageResource(imagenId)

        // Cambia el fondo según si el elemento está seleccionado o no
        if (selectedItems.contains(position)) {
            holder.itemView.setBackgroundColor(
                ContextCompat.getColor(holder.itemView.context, R.color.ingrediente_seleccionado)
            )
        } else {
            holder.itemView.background = null
        }

        // Configura un click listener para manejar clics en el elemento
        holder.itemView.setOnClickListener {
            onIngredienteClick(ingrediente, position)

            // Actualiza el estado de selección para el elemento actual
            if (selectedItems.contains(position)) {
                selectedItems.remove(position)
            } else {
                selectedItems.add(position)
            }
            notifyItemChanged(position)
        }
    }

    // Devuelve el número total de ingredientes en la lista
    override fun getItemCount(): Int {
        return ingredientes.size
    }

    // Clase interna que representa la vista de un ingrediente
    inner class IngredienteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivIngrediente: ImageView = itemView.findViewById(R.id.ivIngrediente)
    }

    // Obtiene el recurso de imagen correspondiente a un ingrediente específico
    private fun obtenerImagenIngrediente(ingrediente: String): Int {
        return when (ingrediente) {
            "Pan" -> R.drawable.pan
            "Aguacate" -> R.drawable.aguacate
            "Tomate" -> R.drawable.tomate
            "Aceite de oliva" -> R.drawable.aceiteoliva
            "Sal" -> R.drawable.sal
            "Pimienta" -> R.drawable.pimienta
            "Aceite de girasol" -> R.drawable.aceitegirasol
            "Cebolla" -> R.drawable.cebolla
            "Lechuga" -> R.drawable.lechuga
            else -> R.drawable.placeholder // Imagen predeterminada si no hay una específica para el ingrediente
        }
    }

    // Devuelve una lista de los ingredientes seleccionados
    fun getSelectedItems(): List<String> {
        return selectedItems.map { ingredientes[it] } // Convierte los índices seleccionados en nombres de ingredientes
    }
}

package adaptador

import android.app.AlertDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.foodgame.DetalleRecetas
import com.example.foodgame.JuegoCuestionario
import com.example.foodgame.R
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import modelo.Plato
import java.io.File

class PlatoAdapter(
    private val platos: MutableList<Plato>, // Lista mutable de platos
    private val categoria: String // Nueva propiedad para la categoría
) : RecyclerView.Adapter<PlatoAdapter.PlatoViewHolder>() {

    private val selectedItems = mutableSetOf<Int>() // Conjunto para rastrear los elementos seleccionados
    var selectedItemPosition: Int = -1 // Posición del elemento seleccionado actualmente
    private var onItemSelectedListener: ((Int) -> Unit)? = null // Callback para notificar selecciones
    // Referencia a Firebase Storage
    private val storage = Firebase.storage
    private val storageRef = storage.reference

    // ViewHolder para representar un elemento del RecyclerView
    inner class PlatoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreTextView: TextView = itemView.findViewById(R.id.platoNombre)
        val caloriasTextView: TextView = itemView.findViewById(R.id.platoCalorias)
        val ingredientesTextView: TextView = itemView.findViewById(R.id.platoIngredientes)
        val platoImageView: ImageView = itemView.findViewById(R.id.ivPlato)
    }

    // Crea un nuevo ViewHolder inflando el diseño de cada elemento
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlatoViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recetas, parent, false)
        return PlatoViewHolder(itemView)
    }

    // Vincula los datos del plato al ViewHolder en una posición específica
    override fun onBindViewHolder(holder: PlatoViewHolder, position: Int) {
        val plato = platos[position] // Obtiene el plato actual
        holder.nombreTextView.text = plato.nombre
        holder.caloriasTextView.text = "Calorías: ${plato.calorias} cal"
        holder.ingredientesTextView.text = "Ingredientes: ${plato.ingredientes}"
        // Cargar la imagen desde Firebase Storage
        val imageName = plato.imageName
        val imageRef = storageRef.child("$categoria/$imageName.jpg")
        descargarImagen(imageRef, holder.platoImageView)

        // Maneja clics en el elemento
        holder.itemView.setOnClickListener {
            if (selectedItems.contains(position)) {
                selectedItems.remove(position)
                selectedItemPosition = -1
            } else {
                selectedItems.clear()
                selectedItems.add(position)
                selectedItemPosition = position
            }

            notifyDataSetChanged() // Notifica al adaptador que la lista ha cambiado
            onItemSelectedListener?.invoke(selectedItemPosition) // Notifica a la actividad el cambio
        }

        // Maneja clics largos para mostrar un diálogo de detalles
        holder.itemView.setOnLongClickListener {
            AlertDialog.Builder(holder.itemView.context)
                .setTitle("Detalle")
                .setMessage("¿Quieres ver los detalles del plato seleccionado o jugar?")
                .setPositiveButton("Detalle") { dialog, _ ->
                    val selectedPlato = platos[position] // Obtiene el plato seleccionado
                    val intent = Intent(holder.itemView.context, DetalleRecetas::class.java)
                    intent.putExtra("selectedPlato", selectedPlato) // Pasa el objeto Plato
                    intent.putExtra("categoria", categoria) // Pasa la categoría al Intent
                    startActivity(holder.itemView.context, intent, null)
                    dialog.dismiss()
                }
                .setNegativeButton("Jugar") { dialog, _ ->
                    val selectedPlato = platos[position] // Obtiene el plato seleccionado
                    val intent = Intent(holder.itemView.context, JuegoCuestionario::class.java)
                    intent.putExtra("selectedPlato", selectedPlato) // Pasa el objeto Plato
                    startActivity(holder.itemView.context, intent, null)
                    dialog.dismiss()
                }
                .show()

            true
        }

        // Cambia el color de fondo según si el elemento está seleccionado o no
        if (selectedItems.contains(position)) {
            holder.itemView.setBackgroundColor(
                ContextCompat.getColor(holder.itemView.context, R.color.item_seleccionado)
            )
        } else {
            holder.itemView.setBackgroundColor(
                ContextCompat.getColor(holder.itemView.context, R.color.item_no_seleccionado)
            )
        }
    }
    private fun descargarImagen(imageRef: com.google.firebase.storage.StorageReference, imageView: ImageView) {
        val localfile = File.createTempFile("tempImage", "jpg")

        imageRef.getFile(localfile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            // Actualizar la imagen
            imageView.setImageBitmap(bitmap)
        }.addOnFailureListener {
            // Manejar el error
        }
    }

    // Devuelve el número total de platos en la lista
    override fun getItemCount(): Int {
        return platos.size
    }

    // Obtiene los índices de los elementos seleccionados
    private fun getSelectedItems(): List<Int> {
        return selectedItems.toList()
    }

    // Configura el listener para manejar cambios en la selección
    fun setOnItemSelectedListener(listener: (Int) -> Unit) {
        onItemSelectedListener = listener
    }
}
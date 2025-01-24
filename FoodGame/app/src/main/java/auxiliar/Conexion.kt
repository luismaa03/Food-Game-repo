package auxiliar

import conexion.AdminSQLiteConexion
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import modelo.Puntuacion

object Conexion {
    private var DATABASE_NAME = "puntuaciones.db2"
    private var DATABASE_VERSION = 1

    fun cambiarBD(nombreBD: String) {
        this.DATABASE_NAME = nombreBD
    }

    fun addPuntuacion(contexto: AppCompatActivity, p: Puntuacion): Long {
        val admin = AdminSQLiteConexion(contexto, this.DATABASE_NAME, null, DATABASE_VERSION)
        val bd = admin.writableDatabase
        val registro = ContentValues()
        registro.put("puntos", p.puntos)
        val codigo = bd.insert("puntuaciones", null, registro)
        bd.close()
        return codigo
    }

    fun obtenerPuntuaciones(contexto: AppCompatActivity): ArrayList<Puntuacion> {
        val puntuaciones = ArrayList<Puntuacion>()
        val admin = AdminSQLiteConexion(contexto, this.DATABASE_NAME, null, DATABASE_VERSION)
        val bd = admin.readableDatabase
        val fila = bd.rawQuery("SELECT id, puntos FROM puntuaciones", null)

        while (fila.moveToNext()) {
            val p = Puntuacion(
                id = fila.getLong(0),
                puntos = fila.getInt(1)
            )
            puntuaciones.add(p)
        }
        bd.close()
        return puntuaciones
    }
}
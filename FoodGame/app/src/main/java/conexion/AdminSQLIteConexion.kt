package conexion

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class AdminSQLiteConexion(context: Context, name: String, factory: SQLiteDatabase.CursorFactory?, version: Int) :
    SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(db: SQLiteDatabase) {
        Log.e("Roberto", "Paso por OnCreate del AdminSQLLite")
        db.execSQL("CREATE TABLE puntuaciones (id INTEGER PRIMARY KEY AUTOINCREMENT, puntos INTEGER)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Log.e("Roberto", "Paso por OnUpgrade del AdminSQLLite")
    }
}
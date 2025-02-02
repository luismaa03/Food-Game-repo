package com.example.foodgame

import android.os.Bundle
import modelo.PlatoData

class Almuerzos : PlatosActivity() {

    override val categoria: String = "almuerzos"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        platos.addAll(PlatoData.getPlatosAlmuerzos())
    }

    override fun getLayoutResourceId(): Int = R.layout.activity_almuerzos
    override fun getButtonAceptarId(): Int = R.id.btAceptarAlm
    override fun getButtonVolverId(): Int = R.id.btVolverAlm
    override fun getRecyclerViewId(): Int = R.id.rvAlmuerzos
}
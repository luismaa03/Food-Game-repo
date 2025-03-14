package com.example.foodgame

import android.os.Bundle
import modelo.PlatoData


class Meriendas : PlatosActivity() {

    override val categoria: String = "meriendas"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        platos.addAll(PlatoData.getPlatosMeriendas())
    }

    override fun getLayoutResourceId(): Int = R.layout.activity_meriendas
    override fun getButtonAceptarId(): Int = R.id.btAceptarMer
    override fun getButtonVolverId(): Int = R.id.btVolverMer
    override fun getRecyclerViewId(): Int = R.id.rvMeriendas
}
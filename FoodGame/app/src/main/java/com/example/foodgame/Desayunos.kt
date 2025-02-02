package com.example.foodgame

import android.os.Bundle
import modelo.PlatoData

class Desayunos : PlatosActivity() {

    override val categoria: String = "desayunos"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        platos.addAll(PlatoData.getPlatosDesayunos())
    }

    override fun getLayoutResourceId(): Int = R.layout.activity_desayunos
    override fun getButtonAceptarId(): Int = R.id.btAceptarDes
    override fun getButtonVolverId(): Int = R.id.btVolverDes
    override fun getRecyclerViewId(): Int = R.id.rvDesayunos
}
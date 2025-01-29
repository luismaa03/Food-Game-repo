package com.example.foodgame

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import modelo.Pregunta

class PreguntaPagerAdapter(fragmentActivity: FragmentActivity, private val preguntas: List<Pregunta>) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = preguntas.size

    override fun createFragment(position: Int): Fragment {
        return PreguntaFragment.newInstance(preguntas[position])
    }
}
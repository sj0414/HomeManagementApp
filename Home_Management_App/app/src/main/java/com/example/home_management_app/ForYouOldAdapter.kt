package com.example.home_management_app

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ForYouOldAdapter(fragment: ForYouOldFragment) : FragmentStateAdapter(fragment){
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> ForYouOldFragment1()
            else -> ForYouOldFragment2()
        }
    }
}
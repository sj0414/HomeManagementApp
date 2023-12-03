package com.example.home_management_app.for_you_adult

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ForYouTabVPAdapter(fragment: ForYouAdultFragment) : FragmentStateAdapter(fragment){
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> ForYouAdult1Fragment()
            else -> ForYouAdult2Fragment()
        }
    }

}
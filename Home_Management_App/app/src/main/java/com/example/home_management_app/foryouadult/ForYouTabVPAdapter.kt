package com.example.home_management_app.foryouadult

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.home_management_app.foryouadult.ForYouAdult1Fragment
import com.example.home_management_app.foryouadult.ForYouAdult2Fragment
import com.example.home_management_app.foryouadult.ForYouAdultFragment

class ForYouTabVPAdapter(fragment: ForYouAdultFragment) : FragmentStateAdapter(fragment){
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> ForYouAdult1Fragment()
            else -> ForYouAdult2Fragment()
        }
    }

}
package com.example.home_management_app

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ForYouTabVPAdapter(fragment: ForYouFragment) : FragmentStateAdapter(fragment){
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> ForYouSchoolFragment()
            else -> ForYouLifeFragment()
        }
    }

}
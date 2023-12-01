package com.example.home_management_app.ForYouOld

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.home_management_app.ForYouOld.ForYouOldAdapter
import com.example.home_management_app.databinding.FragmentForYouOldBinding
import com.google.android.material.tabs.TabLayoutMediator

class ForYouOldFragment : Fragment() {
    lateinit var binding : FragmentForYouOldBinding
    private val tabList = arrayListOf("건강 뉴스","치매 퀴즈")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForYouOldBinding.inflate(layoutInflater)

        initView()
        return binding.root
    }

    private fun initView() {

        binding.vpForYouInfo.adapter = ForYouOldAdapter(this)

        TabLayoutMediator(binding.tlForYouInfo, binding.vpForYouInfo){ tab, position ->
            tab.text = tabList[position]
        }.attach()

    }

}
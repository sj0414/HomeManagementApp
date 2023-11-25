package com.example.home_management_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.home_management_app.databinding.FragmentForYouBinding
import com.google.android.material.tabs.TabLayoutMediator

class ForYouFragment : Fragment() {
    lateinit var binding : FragmentForYouBinding
    private val tabList = arrayListOf("자녀 학사 정보","생활 정보")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForYouBinding.inflate(layoutInflater)

        initView()
        return binding.root
    }

    private fun initView() {
        binding.vpForYouInfo.adapter = ForYouTabVPAdapter(this)

        TabLayoutMediator(binding.tlForYouInfo, binding.vpForYouInfo){ tab, position ->
            tab.text = tabList[position]
        }.attach()
    }


}
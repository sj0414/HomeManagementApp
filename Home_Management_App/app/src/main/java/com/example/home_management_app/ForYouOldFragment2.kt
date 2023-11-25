package com.example.home_management_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.home_management_app.databinding.FragmentForYouOld1Binding
import com.example.home_management_app.databinding.FragmentForYouOld2Binding

class ForYouOldFragment2 : Fragment() {
    lateinit var binding : FragmentForYouOld2Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForYouOld2Binding.inflate(inflater, container, false)

        return binding.root
    }
}
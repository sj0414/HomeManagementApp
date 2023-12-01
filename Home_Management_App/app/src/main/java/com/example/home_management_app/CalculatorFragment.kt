package com.example.home_management_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.home_management_app.databinding.FragmentCalculatorBinding


class CalculatorFragment : Fragment() {

    lateinit var binding : FragmentCalculatorBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCalculatorBinding.inflate(layoutInflater)
        return binding.root
    }
}
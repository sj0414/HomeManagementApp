package com.example.home_management_app.for_you_teen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.home_management_app.R
import com.example.home_management_app.databinding.FragmentForYouAdultBinding
import com.example.home_management_app.databinding.FragmentForYouTeenBinding

class ForYouTeenFragment : Fragment() {

    lateinit var binding : FragmentForYouTeenBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForYouTeenBinding.inflate(layoutInflater)

        return binding.root
    }
}
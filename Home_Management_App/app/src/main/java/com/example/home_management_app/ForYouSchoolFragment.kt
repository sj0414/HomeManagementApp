package com.example.home_management_app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.home_management_app.databinding.FragmentForYouSchoolBinding

class ForYouSchoolFragment : Fragment() {
    lateinit var binding : FragmentForYouSchoolBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForYouSchoolBinding.inflate(layoutInflater)

        return binding.root
    }

}

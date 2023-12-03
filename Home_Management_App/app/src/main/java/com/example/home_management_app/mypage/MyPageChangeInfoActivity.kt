package com.example.home_management_app.mypage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.home_management_app.R
import com.example.home_management_app.databinding.ActivityMainBinding
import com.example.home_management_app.databinding.ActivityMyPageChangeInfoBinding

class MyPageChangeInfoActivity : AppCompatActivity() {
    lateinit var binding : ActivityMyPageChangeInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMyPageChangeInfoBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}
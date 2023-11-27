package com.example.home_management_app.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.home_management_app.databinding.ActivitySelectThemeBinding

//나중에 사용자가 테마를 고를 수 있게 할 수 있는 activity
class SelectThemeActivity : AppCompatActivity(){
    lateinit var binding : ActivitySelectThemeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySelectThemeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)


        setContentView(binding.root)
    }
}
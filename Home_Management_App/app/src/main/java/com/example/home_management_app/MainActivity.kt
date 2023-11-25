package com.example.home_management_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.home_management_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.navigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_calendar -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, CalendarFragment())
                        .commit()
                    return@setOnItemSelectedListener true
                }

                R.id.menu_calculator -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, CalculatorFragment()).commit()
                    return@setOnItemSelectedListener true
                }

                R.id.menu_role_management -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, RoleManagementFragment())
                        .commit()
                    return@setOnItemSelectedListener true
                }

                R.id.menu_for_you -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, ForYouOldFragment())
                        .commit()
                    return@setOnItemSelectedListener true
                }

                R.id.menu_my_page -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, MyPageFragment()).commit()
                    return@setOnItemSelectedListener true
                }

                else -> {
                    return@setOnItemSelectedListener true
                }
            }
        }

        binding.navigation.selectedItemId = R.id.menu_calendar
    }


}
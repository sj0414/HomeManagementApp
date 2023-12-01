package com.example.home_management_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.home_management_app.foryouadult.ForYouAdultFragment
import com.example.home_management_app.Calculator.CalculatorFragment
import com.example.home_management_app.Calculator.CalendarFragment
import com.example.home_management_app.ForYouOld.ForYouOldFragment
import com.example.home_management_app.RoleManagement.RoleManagementFragment
import com.example.home_management_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    private var inputRole : Int = 0
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
                    val fragment = when (inputRole) {
//                        0 -> ForYouTeenFragment()
//                        1 -> ForYouAdultFragment()
//                        2 -> ForYouOldFragment()
                        else -> ForYouAdultFragment()
                    }
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, fragment)
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
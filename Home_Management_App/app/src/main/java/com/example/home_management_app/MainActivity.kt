package com.example.home_management_app

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.home_management_app.Calculator.CalculatorFragment
import com.example.home_management_app.Calculator.CalculatorThingAdapter
import com.example.home_management_app.Calculator.CalculatorThingData
import com.example.home_management_app.Calculator.CalendarFragment
import com.example.home_management_app.ForYouOld.ForYouOldFragment
import com.example.home_management_app.RoleManagement.RoleManagementFragment
import com.example.home_management_app.databinding.ActivityMainBinding
import com.example.home_management_app.databinding.FragmentCalculatorBinding
import com.example.home_management_app.databinding.FragmentCalculatorDialog1Binding
import com.github.mikephil.charting.utils.Utils.init

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
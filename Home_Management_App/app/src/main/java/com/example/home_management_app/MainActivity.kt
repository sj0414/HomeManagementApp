package com.example.home_management_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.home_management_app.for_you_teen.ForYouTeenFragment
import com.example.home_management_app.Calendar.role.CalendarFragment
import com.example.home_management_app.for_you_adult.ForYouAdultFragment
import com.example.home_management_app.calculatorFragment.CalculatorFragment
import com.example.home_management_app.RoleManagement.RoleManagementFragment
import com.example.home_management_app.data.UserData
//import com.example.home_management_app.RoleManagement.RoleManagementFragment
import com.example.home_management_app.databinding.ActivityMainBinding
import com.example.home_management_app.for_you_old.ForYouOldFragment
import com.example.home_management_app.login.LoginActivity
import com.example.home_management_app.mypage.MyPageFragment


class MainActivity : AppCompatActivity(), ResetListener{

    lateinit var binding : ActivityMainBinding
    private var inputRole : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        inputRole = intent.getIntExtra("IntRole", 0)
        setThemeBasedOnxUserInput(inputRole)
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)


        setContentView(binding.root)

        val userData = intent.getSerializableExtra("UserData") as UserData
        val groupCode = intent.getStringExtra("GroupCode")
        Log.d("userdata",userData.toString())


        binding.navigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_calendar -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, CalendarFragment())
                        .commit()
                    return@setOnItemSelectedListener true
                }

                R.id.menu_calculator -> {
                    val calculatorFragment = CalculatorFragment().apply {
                        val bundle = Bundle()
                        bundle.putSerializable("UserData", userData)
                        arguments = bundle
                    }
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, calculatorFragment)
                        .commit()
                    return@setOnItemSelectedListener true
                }

                R.id.menu_role_management -> {
                    val roleManagement = RoleManagementFragment().apply {
                        val bundle = Bundle()
                        bundle.putSerializable("UserData", userData)
                        bundle.putString("GroupCode", groupCode)
                        bundle.putInt("IntRole", inputRole)
                        arguments = bundle
                    }
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, roleManagement)
                        .commit()
                    return@setOnItemSelectedListener true
                }

                R.id.menu_for_you -> {
                    val fragment = when (inputRole) {
                        0 -> ForYouTeenFragment()
                        1 -> ForYouAdultFragment()
                        2 -> ForYouOldFragment()
                        else -> ForYouAdultFragment()
                    }

                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, fragment)
                        .commit()
                    return@setOnItemSelectedListener true
                }

                R.id.menu_my_page -> {
                    val myPageFragment = MyPageFragment().apply {
                        val bundle = Bundle()
                        bundle.putSerializable("UserData", userData)
                        bundle.putString("GroupCode", groupCode)
                        arguments = bundle
                    }
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, myPageFragment)
                        .commit()
                    return@setOnItemSelectedListener true
                }

                else -> {
                    return@setOnItemSelectedListener true
                }
            }
        }

        binding.navigation.selectedItemId = R.id.menu_calendar

    }

    private fun setThemeBasedOnxUserInput(inputRole: Int) {
        when (inputRole) {
            0 -> setTheme(R.style.Theme_App_Teen)
            1 -> setTheme(R.style.Theme_App_Adult)
            2 -> setTheme(R.style.Theme_App_Old)
            // 기타 숫자에 따른 테마 설정
        }
    }


    override fun onReset() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }


}
interface ResetListener {
    fun onReset()
}


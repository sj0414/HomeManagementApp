package com.example.home_management_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.os.bundleOf
import com.example.ForYouTeenFragment
import com.example.home_management_app.for_you_adult.ForYouAdultFragment
import com.example.home_management_app.calculatorFragment.CalculatorFragment
import com.example.home_management_app.calculatorFragment.CalendarFragment
import android.app.AlertDialog
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.home_management_app.RoleManagement.RoleManagementFragment
import com.example.home_management_app.data.UserData
//import com.example.home_management_app.RoleManagement.RoleManagementFragment
import com.example.home_management_app.databinding.ActivityMainBinding
import com.example.home_management_app.for_you_old.ForYouOldFragment
import com.example.home_management_app.login.LoginActivity
import com.example.home_management_app.mypage.MyPageFragment
import com.example.home_management_app.databinding.FragmentCalculatorBinding
import com.example.home_management_app.databinding.FragmentCalculatorDialog1Binding
import com.github.mikephil.charting.utils.Utils.init
class MainActivity : AppCompatActivity(), ResetListener{

    lateinit var binding : ActivityMainBinding
    private var inputRole : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        inputRole = intent.getIntExtra("IntRole", 0)
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
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, CalculatorFragment())
                        .commit()
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

    override fun onReset() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()  // 현재 MainActivity 종료
    }


}
interface ResetListener {
    fun onReset()
}


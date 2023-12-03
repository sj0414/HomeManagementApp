package com.example.home_management_app.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.home_management_app.MainActivity
import com.example.home_management_app.data.UserData
import com.example.home_management_app.databinding.ActivityGroupGeneratingBinding
import com.google.firebase.database.DatabaseReference

class GroupGeneratingActivity : AppCompatActivity() {
    lateinit var binding : ActivityGroupGeneratingBinding
    lateinit var rdb: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityGroupGeneratingBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val userData = intent.getSerializableExtra("UserData") as UserData
        val intRole = intent.getIntExtra("IntRole",0)
        userData.let{

        }
        val groupCode = intent.getStringExtra("GroupCode")

        binding.tvFamilyCode.text = groupCode

        binding.btnConfirm.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("UserData",userData)
            intent.putExtra("GroupCode",groupCode)
            intent.putExtra("IntRole", intRole)

            startActivity(intent)
            finish()
        }
    }
}
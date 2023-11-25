package com.example.home_management_app.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.home_management_app.databinding.ActivityGroupSelectBinding

class GroupSelectActivity : AppCompatActivity() {
    lateinit var binding : ActivityGroupSelectBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityGroupSelectBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val userData = intent.getSerializableExtra("UserData")

        binding.btnYes.setOnClickListener {
            val intent = Intent(this, GroupPresentActivity::class.java)
            intent.putExtra("UserData",userData)
            startActivity(intent)
        }

        binding.btnNo.setOnClickListener {
            val intent = Intent(this, GroupAbsentActivity::class.java)
            intent.putExtra("UserData",userData)
            startActivity(intent)
        }
    }
}
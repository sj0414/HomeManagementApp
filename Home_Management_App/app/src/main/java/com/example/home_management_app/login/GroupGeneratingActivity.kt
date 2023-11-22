package com.example.home_management_app.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.home_management_app.MainActivity
import com.example.home_management_app.R
import com.example.home_management_app.databinding.ActivityGroupGeneratingBinding
import com.google.firebase.database.DatabaseReference

class GroupGeneratingActivity : AppCompatActivity() {
    lateinit var binding : ActivityGroupGeneratingBinding
    lateinit var rdb: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityGroupGeneratingBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnConfirm.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            //intent.putExtra()

            startActivity(intent)
        }
    }
}
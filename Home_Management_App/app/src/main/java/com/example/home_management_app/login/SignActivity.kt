package com.example.home_management_app.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.home_management_app.data.UserData
import com.example.home_management_app.databinding.ActivitySignBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySignBinding
    private lateinit var rdb : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySignBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)

        binding.btnNext.setOnClickListener {
            val intent = Intent(this, GroupSelectActivity::class.java)
            intent.putExtra("UserData",saveUser())
            Log.d("UserData1",saveUser().toString())
            startActivity(intent)
        }

        setContentView(binding.root)
    }

    private fun saveUser() : UserData{
        rdb = Firebase.database.getReference("ID")
        val userDB = rdb.child("ID")

        val newUserData = UserData(
            binding.etUserName.text.toString(),
            binding.etUserId.text.toString(),
            binding.etUserGoogle.text.toString(),
            binding.etUserPassword.text.toString(),
            ""
        )

        rdb.child(binding.etUserId.text.toString()).setValue(newUserData)

        return newUserData
    }
}
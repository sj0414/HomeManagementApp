package com.example.home_management_app.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import com.example.home_management_app.MainActivity
import com.example.home_management_app.R
import com.example.home_management_app.databinding.ActivityLoginBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    lateinit var binding : ActivityLoginBinding
    lateinit var rdb : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)

        rdb = Firebase.database.getReference("ID")

        binding.btnLogin.setOnClickListener {
            val inputUserId = binding.etLoginId.text.toString()
            val inputUserPassword = binding.etLoginPassword.text.toString()
            authenticateUser(inputUserId, inputUserPassword)
        }

        binding.btnSignup.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        setContentView(binding.root)
    }

    private fun authenticateUser(inputUserId: String, inputUserPassword: String) {
        rdb.child(inputUserId).child("password").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val passwordInDb = snapshot.getValue(String::class.java)

                if (inputUserPassword == passwordInDb) {
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    Toast.makeText(this@LoginActivity, "$inputUserId 님, 환영합니다.", Toast.LENGTH_SHORT).show()
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@LoginActivity, "회원정보가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("LoginActivity", "데이터베이스 처리 오류", databaseError.toException())
            }
        })
    }
}
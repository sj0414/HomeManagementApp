package com.example.home_management_app.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.home_management_app.MainActivity
import com.example.home_management_app.data.UserData
import com.example.home_management_app.databinding.ActivityLoginBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    lateinit var binding : ActivityLoginBinding
    lateinit var rdb : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        Log.d("login 생성","null")

        rdb = Firebase.database.getReference("ID")
        val groupCode = "VOC686"

        binding.btnLogin.setOnClickListener {
            val inputUserId = binding.etLoginId.text.toString()
            val inputUserPassword = binding.etLoginPassword.text.toString()
            authenticateUser(inputUserId, inputUserPassword, groupCode)
        }

        binding.btnSignup.setOnClickListener {
            val intent = Intent(this, SignActivity::class.java)
            startActivity(intent)
        }

        setContentView(binding.root)
    }

    private fun authenticateUser(inputUserId: String, inputUserPassword: String, groupCode : String) {
        rdb.child(inputUserId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val passwordInDb = snapshot.child("password").getValue(String::class.java)

                if (inputUserPassword == passwordInDb) {
                    val roleInDb = snapshot.child("role").getValue(String::class.java)
                    Log.d("inputRole",roleInDb.toString())
                    val userIdInDb = snapshot.child("id").getValue(String::class.java)
                    val nameInDb = snapshot.child("name").getValue(String::class.java)
                    val googleInDb = snapshot.child("google").getValue(String::class.java)
                    val passwdInDb = snapshot.child("password").getValue(String::class.java)

                    val userData = UserData(
                        nameInDb!!,
                        userIdInDb!!,
                        googleInDb!!,
                        passwordInDb,
                        roleInDb!!
                    )

                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.putExtra("IntRole", intRole(roleInDb))
                    intent.putExtra("UserData", userData)
                    intent.putExtra("GroupCode",groupCode)

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

    private fun intRole(roleInDb: String?): Int {
        when (roleInDb) {
            //시작기 main theme 바꾸기
            "엄마", "아빠" -> {
                Toast.makeText(this, "테마 : Adult", Toast.LENGTH_SHORT).show()
                return 1
            }

            "아들", "딸" -> {
                Toast.makeText(this, "테마 : Teen", Toast.LENGTH_SHORT).show()
                return 0
            }

            "할머니", "할아버지" -> {
                Toast.makeText(this, "테마 : Old", Toast.LENGTH_SHORT).show()
                return 2
            }
        }
        return 0
    }


}
package com.example.home_management_app.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.home_management_app.MainActivity
import com.example.home_management_app.data.UserData
import com.example.home_management_app.databinding.ActivityGroupRoleBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class GroupRoleActivity : AppCompatActivity() {
    lateinit var binding : ActivityGroupRoleBinding
    private lateinit var rdb : DatabaseReference
    private lateinit var iddb : DatabaseReference
    private var intRole : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityGroupRoleBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)

        rdb = Firebase.database.getReference("Group")
        iddb = Firebase.database.getReference("ID")

        val userData = intent.getSerializableExtra("UserData") as UserData
        val groupCode = intent.getStringExtra("GroupCode")

        binding.btnConfirm.setOnClickListener {
            val groupRole = binding.viewInputCode.text.toString()
            Toast.makeText(this, "$groupCode 에 입장하셨습니다!", Toast.LENGTH_SHORT).show()
            Log.d("userData1",userData.toString())
            saveGroupMember(userData, groupCode!!, groupRole)

            when(groupRole) {
                //시작기 main theme 바꾸기
                "엄마", "아빠" -> {
                    Toast.makeText(this, "테마 : Adult", Toast.LENGTH_SHORT).show()
                    intRole = 1
                }

                "아들", "딸" -> {
                    Toast.makeText(this, "테마 : Teen", Toast.LENGTH_SHORT).show()
                    intRole = 0
                }

                "할머니", "할아버지" -> {
                    Toast.makeText(this, "테마 : Old", Toast.LENGTH_SHORT).show()
                    intRole = 2
                }
            }

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("UserData", userData)
            Log.d("userData2",userData.toString())
            intent.putExtra("GroupCode", groupCode)
            intent.putExtra("IntRole", intRole)
            Log.d("inputRole",intRole.toString())
            startActivity(intent)

        }

        setContentView(binding.root)
    }

    private fun saveGroupMember(userData: UserData, groupCode: String, groupRole : String) {
        val codeDb = rdb.child(groupCode)
        userData.let {
            val userId = userData.id
            val userName = userData.name
            val idRole = iddb.child(userId)
            codeDb.child("groupMember").child(userId).child("id").setValue(userId)
            codeDb.child("groupMember").child(userId).child("name").setValue(userName)
            codeDb.child("groupMember").child(userId).child("role").setValue(groupRole)
            idRole.child("role").setValue(groupRole)
        }
    }
}
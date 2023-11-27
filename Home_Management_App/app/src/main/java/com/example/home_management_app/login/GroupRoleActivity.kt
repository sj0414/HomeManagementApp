package com.example.home_management_app.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.home_management_app.MainActivity
import com.example.home_management_app.R
import com.example.home_management_app.data.UserData
import com.example.home_management_app.databinding.ActivityGroupRoleBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class GroupRoleActivity : AppCompatActivity() {
    lateinit var binding : ActivityGroupRoleBinding
    private lateinit var rdb : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityGroupRoleBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)

        rdb = Firebase.database.getReference("Group")

        val userData = intent.getSerializableExtra("UserData") as UserData
        val groupCode = intent.getStringExtra("GroupCode")

        binding.btnConfirm.setOnClickListener {
            val groupRole = binding.viewInputCode.text.toString()
            Toast.makeText(this, "$groupCode 에 입장하셨습니다!", Toast.LENGTH_SHORT).show()
            saveGroupMember(userData, groupCode!!, groupRole)

            when(groupRole){
                //시작기 main theme 바꾸기
                "엄마","아빠" -> Toast.makeText(this, "테마 : Adult", Toast.LENGTH_SHORT).show()
                "아들","딸" -> Toast.makeText(this, "테마 : Teen", Toast.LENGTH_SHORT).show()
                "할머니","할아머지" -> Toast.makeText(this, "테마 : Old", Toast.LENGTH_SHORT).show()
            }

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("UserData", userData)
            intent.putExtra("GroupCode", groupCode)
            startActivity(intent)

        }

        setContentView(binding.root)
    }

    private fun saveGroupMember(userData: UserData, groupCode: String, groupRole : String) {
        val codeDb = rdb.child(groupCode)
        userData.let {
            val userId = userData.id
            val userName = userData.name
            codeDb.child("groupMember").child(userId).child("id").setValue(userId)
            codeDb.child("groupMember").child(userId).child("name").setValue(userName)
            codeDb.child("groupMember").child(userId).child("role").setValue(groupRole)

        }
    }
}
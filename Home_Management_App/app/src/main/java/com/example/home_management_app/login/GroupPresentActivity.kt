package com.example.home_management_app.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.home_management_app.MainActivity
import com.example.home_management_app.data.UserData
import com.example.home_management_app.databinding.ActivityGroupPresentBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class GroupPresentActivity : AppCompatActivity() {
    lateinit var binding : ActivityGroupPresentBinding
    private lateinit var rdb : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityGroupPresentBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)

        val userData = intent.getSerializableExtra("UserData") as UserData

        rdb = Firebase.database.getReference("Group")

        binding.btnConfirm.setOnClickListener {
            val inputCode = binding.etInputCode.text.toString()
            authenticateCode(userData, inputCode)
        }

        setContentView(binding.root)
    }

    private fun authenticateCode(userData: UserData, inputCode: String) {
        rdb.child(inputCode).child("groupManager").child("GroupCode").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val codeInDb = snapshot.getValue(String::class.java)

                if (inputCode == codeInDb) {
                    val intent = Intent(this@GroupPresentActivity, GroupRoleActivity::class.java)
                    intent.putExtra("UserData", userData)
                    intent.putExtra("GroupCode", inputCode)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@GroupPresentActivity, "일치하는 그룹 정보가 없습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("GroupPresentActivity", "데이터베이스 처리 오류", databaseError.toException())
            }
        })
    }

}
package com.example.home_management_app.login

import android.content.Intent
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.home_management_app.data.GroupData
import com.example.home_management_app.data.UserData
import com.example.home_management_app.databinding.ActivityGroupAbscentBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.io.Serializable
import kotlin.random.Random

class GroupAbsentActivity : AppCompatActivity() {
    lateinit var binding : ActivityGroupAbscentBinding
    private lateinit var rdb : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityGroupAbscentBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val userData = intent.getSerializableExtra("UserData") as UserData
        val groupCode = getRandomCode()

        binding.btnConfirm.setOnClickListener {
            val groupRole = binding.etInputRole.text.toString()

            saveGroupMember(userData, groupCode, groupRole)
            saveGroupManager(userData, groupCode)

            val intent = Intent(this, GroupGeneratingActivity::class.java)
            intent.putExtra("UserData", userData)
            intent.putExtra("GroupCode", groupCode)
            startActivity(intent)
        }
    }


    private fun saveGroupManager(userData: UserData, groupCode: String) {
        rdb = Firebase.database.getReference("Group").child(groupCode)
        rdb.child("groupManager").child("Calculator").setValue("")
        rdb.child("groupManager").child("ForYou").setValue("")
        rdb.child("groupManager").child("RoleManagement").setValue("")
        rdb.child("groupManager").child("GroupCode").setValue(groupCode)
    }

    private fun saveGroupMember(userData: UserData, groupCode: String, groupRole: String) {
        rdb = Firebase.database.getReference("Group").child(groupCode)
        userData.let{
            val userId = userData.id
            val userName = userData.name
            rdb.child("groupMember").child(userId).child("id").setValue(userId)
            rdb.child("groupMember").child(userId).child("name").setValue(userName)
            rdb.child("groupMember").child(userId).child("role").setValue(groupRole)
        }
    }


    private fun getRandomCode(): String {
        val alphabet = ('A'..'Z')
        val number = ('0'..'9')
        val random = Random(System.currentTimeMillis())
        val randomAlphabet = (1..3).map { alphabet.random(random) }
        val randomNumber = (1..3).map { number.random(random) }

        return (randomAlphabet+randomNumber).joinToString("")

    }
}
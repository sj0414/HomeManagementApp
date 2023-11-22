package com.example.home_management_app.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.home_management_app.data.GroupData
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

        val userData = intent.getSerializableExtra("UserData")

        binding.btnConfirm.setOnClickListener {
            val intent = Intent(this, GroupGeneratingActivity::class.java)
            //intent.putExtra()
            saveGroup(userData)
            startActivity(intent)
        }
    }


    private fun saveGroup(userData: Serializable?){
        rdb = Firebase.database.getReference("Group")
        val groupDB = rdb.child("Group")

        val newGroupCode = getRandomCode()
        var newGroupData = GroupData(
            "GroupManager",
            "GroupMember"
        )


        rdb.child(newGroupCode).setValue(newGroupData)
        rdb.child(newGroupCode).child("GroupMember").setValue(userData)
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
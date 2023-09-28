package com.example.home_management_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.Sampler.Value
import com.example.home_management_app.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    lateinit var FDB : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)

        //데이터베이스 초기화
        FDB = FirebaseDatabase.getInstance().reference

        binding.btnAdd.setOnClickListener {
            initAdd()
        }

        binding.btnDelete.setOnClickListener {
            initDelete()
        }

        setContentView(binding.root)
    }

    private fun initAdd(){
        FDB.child("New").setValue("First")
        FDB.child("Old").setValue("First2")
    }

    private fun initDelete(){

    }


}
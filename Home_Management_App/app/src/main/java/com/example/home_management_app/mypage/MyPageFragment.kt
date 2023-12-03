package com.example.home_management_app.mypage

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.home_management_app.ResetListener
import com.example.home_management_app.data.UserData
import com.example.home_management_app.databinding.DialogShowResetBinding
import com.example.home_management_app.databinding.FragmentMyPageBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MyPageFragment : Fragment() {
    lateinit var binding : FragmentMyPageBinding
    private var resetDialog: Dialog? = null
    lateinit var iddb : DatabaseReference
    private var resetListener: ResetListener? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        iddb = Firebase.database.getReference("ID")
        binding = FragmentMyPageBinding.inflate(layoutInflater)

        Log.d("argument",arguments.toString())
        arguments?.getString("GroupCode").let { data ->
            Log.d("data",data.toString())
            binding.tvMyPageGroupCode.text = "가족코드 : ${data.toString()}"
        }

        arguments?.getSerializable("UserData")?.let { data ->
            val userData = data as UserData
            binding.tvMyPageId.text = userData.id
            binding.tvMyPageRole.text = "역할 : " + userData.role
        }

        binding.tvResetInfo.setOnClickListener {
            arguments?.getSerializable("UserData")?.let { data ->
                val userData = data as UserData
                showResetDialog(userData.id)
            }
        }

        binding.tvChangeInfo.setOnClickListener {
            arguments?.getSerializable("UserData")?.let { data ->
                val intent = Intent(requireContext(), MyPageChangeInfoActivity::class.java)
                intent.putExtra("UserData", data)
                startActivity(intent)
            }
        }


        return binding.root
    }

    private fun showResetDialog(userId : String) {
        val dlgBinding = DialogShowResetBinding.inflate(layoutInflater)
        val dialogBuilder = AlertDialog.Builder(requireContext())
        resetDialog = dialogBuilder.setView(dlgBinding.root).show()

        resetDialog?.window?.setLayout(900, ViewGroup.LayoutParams.WRAP_CONTENT)
        resetDialog?.window?.setGravity(Gravity.CENTER)
        resetDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dlgBinding.registerBtn.setOnClickListener {
            val confirmationDialogBuilder = AlertDialog.Builder(requireContext())
            confirmationDialogBuilder.setTitle("탈퇴 확인")
            confirmationDialogBuilder.setMessage("정말로 초기화 하시겠습니까?")
            confirmationDialogBuilder.setPositiveButton("확인") { _, _ ->
                performFiring(userId)
            }
            confirmationDialogBuilder.setNegativeButton("취소", null)

            val confirmationDialog = confirmationDialogBuilder.create()
            confirmationDialog.show()


        }

        dlgBinding.cancelBtn.setOnClickListener {
            resetDialog?.dismiss()
        }
    }

    private fun performFiring(userId : String) {
        iddb.child(userId).removeValue()
        resetListener?.onReset()
        resetDialog?.dismiss()
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ResetListener) {
            resetListener = context
        }
    }

}
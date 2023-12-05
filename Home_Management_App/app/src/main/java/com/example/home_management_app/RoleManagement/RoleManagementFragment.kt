package com.example.home_management_app.RoleManagement

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.home_management_app.R
import com.example.home_management_app.databinding.*
import com.example.home_management_app.databinding.FragmentRoleManagementBinding

class RoleManagementFragment : Fragment() {
    lateinit var binding : FragmentRoleManagementBinding
    lateinit var binding2 : FragmentRoleManagementRoleBinding
    // database 연결 필요
    val roleData: ArrayList<RoleManagementData> = ArrayList()
    private var dialog : Dialog? = null

    lateinit var role: String
    lateinit var task: String

    lateinit var adapter: RoleManagementAdapter
    lateinit var itemAdapter: RoleManagementAdapter2
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRoleManagementBinding.inflate(inflater, container, false)
        binding2 = FragmentRoleManagementRoleBinding.inflate(inflater, container, false)
        init()

        binding.roleRecyclerView.adapter = adapter
        binding2.taskRecyclerView.adapter = itemAdapter
        return binding.root
    }


    fun init() {
        // database 연결 필요
        val roleArray = resources.getStringArray(R.array.role_arrays)
        roleArray.forEach { roleName ->
            roleData.add(RoleManagementData(roleName, ArrayList()))
        }

        adapter = RoleManagementAdapter(roleData)
        itemAdapter = RoleManagementAdapter2(ArrayList())

        val selectedRoleData1 = roleData.find { it.name == "엄마" }
        val selectedRoleData2 = roleData.find { it.name == "아빠" }
        val selectedRoleData3 = roleData.find { it.name == "아들" }
        val selectedRoleData4 = roleData.find { it.name == "딸" }
        val selectedRoleData5 = roleData.find { it.name == "할머니" }
        val selectedRoleData6 = roleData.find { it.name == "할아버지" }
        selectedRoleData1?.tasks?.add(TaskData("설거지"))
        selectedRoleData1?.tasks?.add(TaskData("쓰레기통 비우기"))
        selectedRoleData2?.tasks?.add(TaskData("화장실 청소"))
        selectedRoleData3?.tasks?.add(TaskData("분리수거"))
        selectedRoleData4?.tasks?.add(TaskData("청소기 청소"))
        selectedRoleData5?.tasks?.add(TaskData("물걸레 청소"))
        selectedRoleData6?.tasks?.add(TaskData("휴지 교체"))

        binding.addRole.setOnClickListener {
            addRoleDialog()
        }
        binding.deleteRole.setOnClickListener {
            deleteRoleDialog()
        }

        binding.roleRecyclerView.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL, false)
        //adapter = RoleManagementAdapter(roleData)
        val roleManagementAdapter = RoleManagementAdapter(roleData)
        binding.roleRecyclerView.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL, false)
        binding.roleRecyclerView.adapter = adapter
        binding.roleRecyclerView.adapter = roleManagementAdapter
    }

    inner class CustomOnItemSelectedListener1 : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            role = parent?.getItemAtPosition(position).toString()
            //Log.d("RoleManagementFragment", "Selected Role: $role")
            update()
        }
    }
    inner class CustomOnItemSelectedListener2 : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            task = parent?.getItemAtPosition(position).toString()
        }
    }

    fun update() {
        val dialogBinding = FragmentRoleManagementDialog2Binding.inflate(
            LayoutInflater.from(requireContext())
        )

        val selectedRoleData = roleData.find { it.name == role }
        //Log.d("RoleManagementFragment", "Selected Role: $selectedRoleData")
        val tasksAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            selectedRoleData?.tasks?.map { it.description } ?: listOf()
        )
        //Log.d("RoleManagementFragment", "Selected Role: ${tasksAdapter.getItem(0)}")
        tasksAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dialogBinding.spinner2.adapter = tasksAdapter
        tasksAdapter.notifyDataSetChanged()
        dialogBinding.spinner2.onItemSelectedListener = CustomOnItemSelectedListener2()
    }

    private fun addRoleDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val dialogBinding = FragmentRoleManagementDialogBinding.inflate(layoutInflater)
        dialogBinding.spinner.onItemSelectedListener = CustomOnItemSelectedListener1()
        val description = dialogBinding.roleText

        dialog = builder.setView(dialogBinding.root).show()

        dialog?.window?.setLayout(900, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setGravity(Gravity.CENTER)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // 다이얼로그의 "추가" 버튼 클릭 시
        dialogBinding.roleDialogFin.setOnClickListener {
            val newThing = description.text.toString()

            val selectedRoleData = roleData.find { it.name == role }
            // database 연결 필요
            selectedRoleData?.tasks?.add(TaskData(newThing))

            adapter.notifyDataSetChanged()
            itemAdapter.setData(selectedRoleData?.tasks ?: emptyList())
            dialog?.dismiss()

        }

        dialogBinding.cancelBtn.setOnClickListener {
            dialog?.dismiss()
        }
    }

    private fun deleteRoleDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val dialogBinding = FragmentRoleManagementDialog2Binding.inflate(
            LayoutInflater.from(requireContext())
        )

        role = dialogBinding.spinner1.selectedItem?.toString() ?: "엄마"
        dialogBinding.spinner1.onItemSelectedListener = CustomOnItemSelectedListener1()

        //update()
        val selectedRoleData = roleData.find { it.name == role }
        val tasksAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            selectedRoleData?.tasks?.map { it.description } ?: listOf()
        )

        tasksAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dialogBinding.spinner2.adapter = tasksAdapter
        tasksAdapter.notifyDataSetChanged()
        dialogBinding.spinner2.onItemSelectedListener = CustomOnItemSelectedListener2()

        builder.setView(dialogBinding.root)

        val dialog = builder.create()

        // 다이얼로그의 "완료" 버튼 클릭 시
        dialogBinding.roleDialogFin2.setOnClickListener {
            val selectedRoleData = roleData.find { it.name == role }
            selectedRoleData?.tasks?.removeIf { it.description == task }

            adapter.notifyDataSetChanged()
            itemAdapter.setData(selectedRoleData?.tasks ?: emptyList())
            dialog.dismiss()
        }

        dialog.show()
    }
}
package com.example.home_management_app.RoleManagement

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.home_management_app.R
import com.example.home_management_app.databinding.*

class RoleManagementFragment : Fragment() {
    lateinit var binding : FragmentRoleManagementBinding
    lateinit var binding2 : FragmentRoleManagementRoleBinding
    // database 연결 필요
    val roleData: ArrayList<RoleManagementData> = ArrayList()

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
        selectedRoleData1?.tasks?.add(TaskData("설거지"))
        selectedRoleData1?.tasks?.add(TaskData("쓰레기통 비우기"))
        selectedRoleData2?.tasks?.add(TaskData("화장실 청소"))
        selectedRoleData3?.tasks?.add(TaskData("분리수거"))

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
        val dialogBinding = FragmentRoleManagementDialogBinding.inflate(
            LayoutInflater.from(requireContext())
        )

        dialogBinding.spinner.onItemSelectedListener = CustomOnItemSelectedListener1()
        val description = dialogBinding.roleText

        builder.setView(dialogBinding.root)

        val dialog = builder.create()

        // 다이얼로그의 "추가" 버튼 클릭 시
        dialogBinding.roleDialogFin.setOnClickListener {
            val newThing = description.text.toString()

            val selectedRoleData = roleData.find { it.name == role }
            // database 연결 필요
            selectedRoleData?.tasks?.add(TaskData(newThing))

            adapter.notifyDataSetChanged()
            itemAdapter.setData(selectedRoleData?.tasks ?: emptyList())
            dialog.dismiss()
        }

        dialog.show()
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
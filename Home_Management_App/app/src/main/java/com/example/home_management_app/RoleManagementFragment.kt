package com.example.home_management_app

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.home_management_app.databinding.*

class RoleManagementFragment : Fragment() {
    lateinit var binding : FragmentRoleManagementBinding
    // database 연결 필요
    val roleData: ArrayList<RoleManagementData> = ArrayList()

    lateinit var role: String
    lateinit var task: String

    lateinit var adapter: RoleManagementAdapter
    lateinit var adapter2: RoleManagementAdapter2
    lateinit var itemAdapter: RoleManagementAdapter2
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRoleManagementBinding.inflate(inflater, container, false)
        init()
        binding.roleRecyclerView.adapter = adapter
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
        //binding.roleRecyclerView.
        //binding.taskRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        //binding.taskRecyclerView.adapter = itemAdapter

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
        }
    }
    inner class CustomOnItemSelectedListener2 : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            task = parent?.getItemAtPosition(position).toString()
        }
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

        dialogBinding.spinner1.onItemSelectedListener = CustomOnItemSelectedListener1()
        val selectedRoleData = roleData.find { it.name == role }
        val tasksAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            selectedRoleData?.tasks?.map { it.description } ?: listOf()
        )
        tasksAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dialogBinding.spinner2.adapter = tasksAdapter
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
package com.example.home_management_app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.home_management_app.databinding.FragmentRoleManagementRoleBinding
import java.util.ArrayList

class RoleManagementAdapter(val roleList: ArrayList<RoleManagementData>) : RecyclerView.Adapter<RoleManagementAdapter.ViewHolder>(){
    interface OnItemClickListener {
        //
    }

    var itemClickListener:OnItemClickListener?=null

    inner class ViewHolder(val binding: FragmentRoleManagementRoleBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            //
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = FragmentRoleManagementRoleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = roleList[position]

        holder.binding.role.text = currentItem.name

        // 중첩된 RecyclerView 설정
        val itemRecyclerView: RecyclerView = holder.binding.taskRecyclerView
        val layoutManager = LinearLayoutManager(holder.binding.root.context)
        itemRecyclerView.layoutManager = layoutManager

        val itemAdapter = RoleManagementAdapter2(currentItem.tasks)
        itemRecyclerView.adapter = itemAdapter
    }

    override fun getItemCount(): Int {
        return roleList.size
    }
}


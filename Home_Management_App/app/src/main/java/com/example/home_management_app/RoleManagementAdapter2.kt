package com.example.home_management_app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.home_management_app.databinding.FragmentRoleManagementItemBinding

class RoleManagementAdapter2(val taskList: MutableList<TaskData>) : RecyclerView.Adapter<RoleManagementAdapter2.ViewHolder>() {
    interface OnItemClickListener {
        //
    }

    var itemClickListener:OnItemClickListener?=null

    inner class ViewHolder(val binding: FragmentRoleManagementItemBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            //
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = FragmentRoleManagementItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = taskList[position]
        holder.binding.task.text = currentItem.description
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    fun setData(newList: List<TaskData>) {
        taskList.clear()
        taskList.addAll(newList)
        notifyDataSetChanged()
    }
}

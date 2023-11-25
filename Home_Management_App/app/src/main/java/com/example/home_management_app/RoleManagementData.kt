package com.example.home_management_app

data class RoleManagementData(val name: String, val tasks: ArrayList<TaskData>)
data class TaskData(val description: String)
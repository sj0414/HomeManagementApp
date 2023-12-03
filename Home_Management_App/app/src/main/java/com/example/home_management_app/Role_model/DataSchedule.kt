package com.example.home_management_app.Role_model

import java.io.Serializable

data class ScheduleData(
    var role: String? = null,
    var specialty: Boolean? = null,
    var todo: String? = null
) : Serializable
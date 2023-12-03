package com.example.home_management_app.data

import java.io.Serializable

data class UserData (
    var name : String,
    var id : String,
    var google : String,
    var password : String,
    var role : String
) : Serializable
{
    constructor():this("noinfo","noinfo","noinfo","noinfo","noinfo")
}
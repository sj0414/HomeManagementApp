package com.example.home_management_app.data

data class GroupData(
    var GroupManager : String,
    var GroupMember : String

){
    constructor() : this("noinfo","noinfo")
}

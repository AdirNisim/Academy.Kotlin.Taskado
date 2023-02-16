package com.example.taskado.Models
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Users")
data class User(
    @ColumnInfo(name ="UserId")
    @PrimaryKey
    var user_id: String,
    @ColumnInfo (name ="Email")
    var user_email:  String?=null,
    @ColumnInfo (name ="Password")
    var user_password: String?=null,
    @ColumnInfo (name ="FullName")
    var user_full_name: String?=null,
    @ColumnInfo (name ="Phone")
    var user_phone: String?=null,
    @ColumnInfo (name ="Image")
    var user_image: String?=null,
    @ColumnInfo (name ="Organization")
    var user_organization: String?=null,

){
    constructor(): this("",null,null,null,null,null,null)

}



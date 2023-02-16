package com.example.taskado.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Tasks")
data class TaskR(
 @ColumnInfo(name ="taskId")
 @PrimaryKey
    var task_id: String,
 @ColumnInfo (name ="taskTitle")
     var task_title:  String?=null,
 @ColumnInfo (name ="taskStartDate")
     var task_start_date: String?=null,
 @ColumnInfo (name ="taskEndDate")
     var task_end_date: String?=null,
 @ColumnInfo (name ="taskeDescription")
     var task_description: String?=null,
 @ColumnInfo (name ="taskCreator")
     var task_creator: String?=null,
 @ColumnInfo (name ="taskMiniCountDone")
     var mini_task_done: Int?=null,
 @ColumnInfo (name ="taskDoneBoolean")
     var task_done: Boolean?=null,
    @ColumnInfo (name ="taskFavorite")
    var task_favorite: Int?=0
)


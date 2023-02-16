package com.example.taskado.Models
import androidx.room.*
import com.example.taskado.dataclasses.MiniTask
import com.example.taskado.utils.Constants

@Entity(tableName = "Tasks")
data class Task(
    @ColumnInfo(name ="taskId")
    @PrimaryKey
    var task_id: String?="",
    @ColumnInfo (name ="taskTitle")
    var task_title:  String?=null,
    @ColumnInfo (name ="taskStartDate")
    var task_start_date: String?=null,
    @ColumnInfo (name ="taskEndDate")
    var task_end_date: String?=null,
    @ColumnInfo (name ="taskeDescription")
    var task_description: String?=null,
    @ColumnInfo (name ="taskMiniArray")
    var task_mini_array: MutableList<MiniTask>,
    @ColumnInfo (name ="taskCreator")
    var task_creator: String?=null,
    @ColumnInfo (name ="taskMiniCountDone")
    var mini_task_done: Int?=null,
    @ColumnInfo (name ="taskDoneBoolean")
    var task_done: Boolean?=null,
    @ColumnInfo (name ="taskFavorite")
    var task_favorite: Int?=0
) {
    constructor() : this(Constants.DEFAULTORGANIZATION, null, null, null, null, mutableListOf(), null, 0, false,0)
    init {
        mini_task_done=0
        task_done = false
    }
}


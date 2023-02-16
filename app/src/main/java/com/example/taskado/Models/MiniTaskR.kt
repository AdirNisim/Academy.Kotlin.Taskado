package com.example.taskado.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.taskado.dataclasses.STATUS


@Entity(tableName = "MiniTasks")
data class MiniTaskR(

    @PrimaryKey(autoGenerate = true)
    var miniTaskid: Int =0,
    @ColumnInfo(name ="parentTaskId")
    var parentId: String,
    @ColumnInfo (name ="miniTaskTitle")
    var miniTaskName:  String?=null,
    @ColumnInfo (name ="miniTaskStatus")
    var status: com.example.taskado.dataclasses.STATUS? = STATUS.NOT_FINISHED,
    @ColumnInfo (name ="miniTaskCreator")
    var taskCloser: String?=null,
)



package com.example.taskado.dataclasses

enum class STATUS {
    NOT_FINISHED,
    FINISHED
}

data class MiniTask(var miniTaskName: String?=null, var status:STATUS?=STATUS.NOT_FINISHED, var taskCloser: String?=null){
}

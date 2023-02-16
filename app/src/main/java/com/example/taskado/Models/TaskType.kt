package com.example.taskado.Models

enum class ClickedType {
    SingleClick,
    DoubleClick,
    LongClick,
}

data class TaskType(var task:Task,var type:ClickedType)

data class TaskIndex(var index:Int,var type:ClickedType)

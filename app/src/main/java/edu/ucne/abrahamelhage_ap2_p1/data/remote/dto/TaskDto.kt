package com.ucne.myapplication.data.remote.dto

data class TaskDto(
    val id: Int,
    val title: String,
    val dueDate: String,
    val completed: Boolean
)
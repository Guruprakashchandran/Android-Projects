package com.example.todoapp.data.dataclasses

data class UserToDoList(val no: Long,val text: String,val category: String,var status: String,val createdTime: String,val modifiedTime: String,val createdId: Long,var rowId: Long)
package com.example.todoapp.intent.event

import com.example.todoapp.data.dataclasses.UserToDoList

sealed class CategoryBasedTodoEvents {

    data class AddToDo(val category: String,val todoList: String) : CategoryBasedTodoEvents()
    data class DeleteToDo(val category: String, val UserToDo: UserToDoList) : CategoryBasedTodoEvents()
    data class UpdateToDo(val category: String,val todoList: UserToDoList) : CategoryBasedTodoEvents()
    data class LoadData(val category: String): CategoryBasedTodoEvents()
}
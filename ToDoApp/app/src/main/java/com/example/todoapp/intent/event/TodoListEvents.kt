package com.example.todoapp.intent.event

import com.example.todoapp.data.dataclasses.UserToDoList

sealed class TodoListEvents {
    data object LoadToDoData: TodoListEvents()
    data class AddToDoData(var toDoList: String) : TodoListEvents()
    data class UpdateData(var toDoList: UserToDoList): TodoListEvents()
    data class DeleteData(var toDoList: UserToDoList): TodoListEvents()
}
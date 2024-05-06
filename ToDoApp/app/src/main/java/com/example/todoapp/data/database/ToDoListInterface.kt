package com.example.todoapp.data.database

import com.example.todoapp.data.dataclasses.UserToDoList

interface ToDoListInterface{
    suspend fun insertToDo(toDoList: UserToDoList): Any
    suspend fun getAllTodos(): Any
    suspend fun updateToDo(toDoList: UserToDoList): Any?
    suspend fun deleteDoTo(no: Long): Any?
    suspend fun getTodoBasedOnCategory(category: String): List<UserToDoList>
}
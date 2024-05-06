package com.example.todoapp.data.database

import com.example.todoapp.data.dataclasses.UserToDoList

interface CategoryBasedToDoListInterface {
    suspend fun getFilteredToDoList(category: String): List<UserToDoList>
    suspend fun addFilteredToDoList(userToDoList: UserToDoList): Any
    suspend fun updateFilteredToDoList(userToDoList: UserToDoList): Boolean
    suspend fun deleteFilteredToDoList(userToDoList: UserToDoList): Boolean
}
package com.example.todoapp.data.database

import com.example.todoapp.data.dataclasses.Categories
import com.example.todoapp.data.repository.ToDoListRepository

interface ToDoCategoryInterface {

    suspend fun addCategory(category: Categories): Boolean
    suspend fun deleteCategory(category: Categories)
    suspend fun getAllCategories(): List<Categories>
}
package com.example.todoapp.intent.state

import com.example.todoapp.data.dataclasses.UserToDoList

sealed class CategoryBasedToDoStates {

    data object Loading : CategoryBasedToDoStates()
    data object AddedItemLoading: CategoryBasedToDoStates()
    data class ItemLoading(val toDoList: UserToDoList, val isLoading: Boolean) :
        CategoryBasedToDoStates()
    data class Success(val toDoList: List<UserToDoList>) : CategoryBasedToDoStates()
    data class Error(val message: String) : CategoryBasedToDoStates()
}
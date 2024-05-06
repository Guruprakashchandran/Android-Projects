package com.example.todoapp.intent.state

import com.example.todoapp.data.dataclasses.Categories

sealed class ToDoCategoryStates {

    data object Loading : ToDoCategoryStates()
    data class Error(val message: String) : ToDoCategoryStates()
    data class Success(val listOfCategory: List<Categories>, var isLoading: Boolean = false) : ToDoCategoryStates()
}
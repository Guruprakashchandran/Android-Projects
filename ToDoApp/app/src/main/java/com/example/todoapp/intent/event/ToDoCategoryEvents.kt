package com.example.todoapp.intent.event

import com.example.todoapp.data.dataclasses.Categories

sealed class ToDoCategoryEvents{

    data object LoadToDoData: ToDoCategoryEvents()
    data class AddToDoData(val category: Categories): ToDoCategoryEvents()
}
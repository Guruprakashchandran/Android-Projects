package com.example.todoapp.intent.state

import com.example.todoapp.data.dataclasses.UserToDoList

data class TodoUiState(var loading: String = "", var success: Boolean = false, var failure: Boolean = false, var data: List<UserToDoList>? = null, val exception: String = "", var singleItem: UserToDoList? = null)
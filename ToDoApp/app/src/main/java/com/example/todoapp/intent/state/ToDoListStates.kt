package com.example.todoapp.intent.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.todoapp.data.dataclasses.UserToDoList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

sealed class ToDoListStates {
    data object Loading: ToDoListStates()
    data class Error(val message: String): ToDoListStates()
    data object AddedItemLoading: ToDoListStates()
    data class ItemLoading(val toDoList: UserToDoList,var isLoading: Boolean): ToDoListStates()
    data class Success(val toDoLists: List<UserToDoList>): ToDoListStates()
}

//data class TodoUiState(
//    val isTodoListLoading:Boolean,
//    val isTodoItemLoading:Boolean,
//    val isAddTodoLoading:Boolean,
//    val todoList :List<String> = emptyList()
//)
//
//val uiState = MutableStateFlow(TodoUiState(isTodoListLoading = false,false,false))
//fun c(){
//    uiState.update {value->
//        value.copy(
//            isTodoListLoading = true
//        )
//    }
//
//}

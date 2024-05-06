package com.example.todoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.dataclasses.UserToDoList
import com.example.todoapp.data.repository.ToDoListRepository
import com.example.todoapp.intent.event.CategoryBasedTodoEvents
import com.example.todoapp.intent.state.CategoryBasedToDoStates
import com.zoho.catalyst.exception.ZCatalystException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryBasedTodoViewModel @Inject constructor(private val toDoListRepository: ToDoListRepository) :
    ViewModel() {

    private var _state = MutableStateFlow<CategoryBasedToDoStates>(CategoryBasedToDoStates.Loading)
    val state = _state
    private var listOfToDos = mutableListOf<UserToDoList>()

    fun handleEvent(event: CategoryBasedTodoEvents) {
        when (event) {
            is CategoryBasedTodoEvents.AddToDo -> {

                categoryBasedAddTodo(event.category, event.todoList)
            }

            is CategoryBasedTodoEvents.DeleteToDo -> {

                categoryBasedDeleteTodo(event.category, event.UserToDo)
            }

            is CategoryBasedTodoEvents.LoadData -> {

                categoryBasedLoadTodos(event.category)
            }

            is CategoryBasedTodoEvents.UpdateToDo -> {

                categoryBasedUpdateTodo(event.category, event.todoList)
            }
        }
    }

    private fun categoryBasedAddTodo(category: String, todoList: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _state.value = CategoryBasedToDoStates.AddedItemLoading
                val toDoData =
                    toDoListRepository.addNewToDoData(category = category, toDoList = todoList)
                if (toDoData is UserToDoList) {
                    listOfToDos.add(0,toDoData)
                    _state.value = CategoryBasedToDoStates.Success(listOfToDos)
                } else {
                    _state.value = CategoryBasedToDoStates.Error(
                        (toDoData as ZCatalystException).getErrorMsg().toString()
                    )
                }
            } catch (e: Exception) {
                _state.value = CategoryBasedToDoStates.Success(listOfToDos)
            }
        }
    }

    private fun categoryBasedUpdateTodo(category: String, todoList: UserToDoList) {
        viewModelScope.launch {
            try {
                _state.value =
                    CategoryBasedToDoStates.ItemLoading(toDoList = todoList, isLoading = true)
                val status = toDoListRepository.updateCategoryBasedToDoListByStatus(
                    category = category,
                    toDoList = todoList
                )
                if (status) {
                    listOfToDos.forEach {
                        if (it.no == todoList.no) {
                            if (it.status == "Completed") {
                                listOfToDos[listOfToDos.indexOf(it)].status = "InCompleted"
                            } else {
                                listOfToDos[listOfToDos.indexOf(it)].status = "Completed"
                            }
                        }

                    }
                    val tempList = listOfToDos
                    listOfToDos = tempList.filter { it.status == "InCompleted"  }.sortedByDescending { it.no }.toMutableList()
                    listOfToDos.addAll(tempList.filter { it.status == "Completed" }.sortedByDescending { it.no }.toMutableList())
                    _state.value = CategoryBasedToDoStates.Success(listOfToDos)
                } else {
                    _state.value = CategoryBasedToDoStates.Error(
                        "Un Wanted Error!!!"
                    )
                }
            } catch (e: Exception) {
                _state.value = CategoryBasedToDoStates.Error(e.message.toString())
            }
        }
    }

    private fun categoryBasedDeleteTodo(category: String, userToDo: UserToDoList) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                _state.value = CategoryBasedToDoStates.ItemLoading(userToDo, true)

                when (val status =
                    toDoListRepository.deleteFilteredToDoList(
                        category = category,
                        rowId = userToDo.rowId
                    )) {

                    is Boolean -> {
                        var i: Int = -1
                        listOfToDos.forEachIndexed { index, it ->
                            if (it.no == userToDo.no) {
                                i = index
                            }
                        }
                        if (i > -1) {
                            listOfToDos.removeAt(i)
                        }
                        _state.value = CategoryBasedToDoStates.Success(listOfToDos)
                    }

                    is ZCatalystException -> {
                        _state.value = CategoryBasedToDoStates.Error(
                            (status as ZCatalystException).getErrorMsg().toString()
                        )
                    }

                    else -> {
                        _state.value =
                            CategoryBasedToDoStates.Error((status as Exception).message.toString())
                    }
                }
            } catch (e: Exception) {
                _state.value = CategoryBasedToDoStates.Error("Un Wanted Error!!!")
            }
        }

    }

    private fun categoryBasedLoadTodos(category: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _state.value = CategoryBasedToDoStates.Loading
                listOfToDos =
                    toDoListRepository.getFilteredToDo(category = category).toMutableList()
                _state.value = CategoryBasedToDoStates.Success(listOfToDos)
            } catch (e: Exception) {

            }
        }
    }
}
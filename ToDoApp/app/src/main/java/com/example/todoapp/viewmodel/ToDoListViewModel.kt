package com.example.todoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.dataclasses.UserToDoList
import com.example.todoapp.data.repository.ToDoListRepository
import com.example.todoapp.intent.event.TodoListEvents
import com.example.todoapp.intent.state.ToDoListStates
import com.example.todoapp.intent.state.TodoUiState
import com.zoho.catalyst.exception.ZCatalystException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToDoListViewModel @Inject constructor(private val toDoListRepository: ToDoListRepository) :
    ViewModel() {

    private var _state = MutableStateFlow<ToDoListStates>(ToDoListStates.Loading)
    val state: StateFlow<ToDoListStates> = _state
    private var listOfToDos = mutableListOf<UserToDoList>()
    val uiState = MutableStateFlow(
        TodoUiState()
    )

    fun handleEvent(event: TodoListEvents) {

        when (event) {
            is TodoListEvents.AddToDoData -> {
                addToDoData(event.toDoList)
            }

            TodoListEvents.LoadToDoData -> {
                getAllToDoLists()
            }

            is TodoListEvents.UpdateData -> {
                updateToDoData(event.toDoList)
            }

            is TodoListEvents.DeleteData -> {
                deleteToDoData(event.toDoList)
            }
        }
    }

    private fun deleteToDoData(toDoList: UserToDoList) {
        try {

            viewModelScope.launch(Dispatchers.IO) {
                _state.value = ToDoListStates.ItemLoading(toDoList = toDoList, true)
                uiState.update {
                    it.copy(loading = "ItemLoading", singleItem = toDoList)
                }
                when (val status = toDoListRepository.deleteToDoList(toDoList.rowId)) {
                    is Boolean -> {
                        var i =  -1
                        listOfToDos.forEachIndexed {index,it ->
                            if (it.no == toDoList.no) {
                               i = index
                            }
                        }
                        if(i > -1){
                            listOfToDos.removeAt(i)
                        }
                        _state.value = ToDoListStates.Success(listOfToDos)
                        uiState.update {
                            it.copy(loading = "", success = true, data = listOfToDos, singleItem = null)
                        }
                    }

                    is ZCatalystException -> {
                        _state.value = ToDoListStates.Error(status.getErrorMsg().toString())
                        uiState.update {
                            it.copy(loading = "",success = true, exception = status.getErrorMsg().toString(), singleItem = null)
                        }
                    }

                    else -> {
                        _state.value =
                            ToDoListStates.Error((status as Exception).message.toString())
                        uiState.update {
                            it.copy(loading = "",success = true, exception = status.message.toString(), singleItem = null)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            _state.value = ToDoListStates.Error("Un Wanted Error!!!")
            uiState.update {
                it.copy(loading = "",success = true, exception = "Un Wanted Error!!!", singleItem = null)
            }
        }
    }

    private fun updateToDoData(toDo: UserToDoList) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                uiState.update {
                    it.copy(
                        loading = "ItemLoading",
                        success = false,
                        failure = false,
                        singleItem = toDo
                    )
                }
                _state.value = ToDoListStates.ItemLoading(toDoList = toDo, isLoading = true)
                val isUpdatedToDoData = toDoListRepository.updateToDoListByStatus(toDo)
                println("Used For output: $isUpdatedToDoData")
                if (isUpdatedToDoData) {
                    listOfToDos.forEach {
                        if (it.no == toDo.no) {
                            if (it.status == "Completed") {
                                listOfToDos[listOfToDos.indexOf(it)].status = "InCompleted"

                            } else {
                                listOfToDos[listOfToDos.indexOf(it)].status = "Completed"
                            }

                        }
                    }
                    val tempList = listOfToDos
                    listOfToDos = (tempList.filter { it.status == "InCompleted"  }.sortedByDescending { it.no }.toMutableList())
                    listOfToDos.addAll(tempList.filter { it.status == "Completed" }.sortedByDescending { it.no }.toMutableList())
                    _state.value = ToDoListStates.Success(listOfToDos)
                    uiState.update {
                        it.copy(
                            loading = "",
                            success = true,
                            data = listOfToDos,
                            singleItem = toDo

                        )
                    }
                } else {
                    _state.value =
                        ToDoListStates.Error("Un Wanted Error!!!")
                    uiState.update {
                        it.copy(
                            loading = "",
                            failure = true,
                            exception = "Un Wanted Error!!!",
                            singleItem = null
                        )
                    }
                }

            } catch (e: Exception) {
                _state.value = ToDoListStates.Error("Un Wanted Error!!!")
                uiState.update {
                    it.copy(
                        loading = "",
                        failure = true,
                        exception = "Un Wanted Error!!!",
                        singleItem = null
                    )
                }
            }
        }
    }


    private fun getAllToDoLists() {

        try {

            viewModelScope.launch {

                _state.value = ToDoListStates.Loading
                uiState.update {
                    it.copy(
                        loading = "PageLoading",
                        success = false,
                        failure = false,
                    )
                }
                val result = toDoListRepository.getAllToDoLists()
                val tempList = result.toMutableList()
                listOfToDos = tempList.filter { it.status == "InCompleted"  }.sortedByDescending { it.no }.toMutableList()
                listOfToDos.addAll(tempList.filter { it.status == "Completed" }.sortedByDescending { it.no }.toMutableList())
                _state.value = ToDoListStates.Success(listOfToDos)
                uiState.update {
                    it.copy(
                        loading = "",
                        success = true,
                        failure = false,
                        data = listOfToDos,
                        singleItem = null,
                    )
                }
            }
        } catch (e: Exception) {
            _state.value = ToDoListStates.Error("Un Wanted Error!!!")
            uiState.update {
                it.copy(
                    loading = "",
                    success = false,
                    failure = true,
                    exception = "Un Wanted Error!!!",
                    singleItem = null
                )
            }
        }
    }

    private fun addToDoData(toDoList: String) {

        try {
            viewModelScope.launch(Dispatchers.IO) {
                _state.value = ToDoListStates.AddedItemLoading
                uiState.update {
                    it.copy(
                        loading = "AddedItemLoading",
                        success = false,
                        failure = false,
                    )
                }
                val toDoData = toDoListRepository.addToDoList(toDoList)
                if (toDoData is UserToDoList) {
                    listOfToDos.add(0,toDoData)

                    uiState.update {
                        it.copy(
                            loading = "",
                            success = true,
                            data = listOfToDos,
                            singleItem = null
                        )
                    }
                    _state.value = ToDoListStates.Success(listOfToDos)
                } else {
                    _state.value = ToDoListStates.Error(
                        (toDoData as ZCatalystException).getErrorMsg().toString()
                    )
                    uiState.update {
                        it.copy(
                            loading = "",
                            failure = true,
                            exception = (toDoData as ZCatalystException).getErrorMsg().toString(),
                            singleItem = null
                        )
                    }
                }
            }
        } catch (e: Exception) {
            _state.value = ToDoListStates.Error("Un Wanted Error!!!")
            uiState.update {
                it.copy(
                    loading = "",
                    failure = true,
                    exception = "Un Wanted Error!!!",
                    singleItem = null
                )
            }
        }
    }
}
package com.example.todoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.dataclasses.Categories
import com.example.todoapp.data.repository.ToDoListRepository
import com.example.todoapp.intent.event.ToDoCategoryEvents
import com.example.todoapp.intent.state.ToDoCategoryStates
import com.zoho.catalyst.setup.ZCatalystApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoCategoryViewModel @Inject constructor(private val toDoListRepository: ToDoListRepository) :
    ViewModel() {

    private var _state = MutableStateFlow<ToDoCategoryStates>(ToDoCategoryStates.Loading)
    val state = _state
    private var listOfCategories = mutableListOf<Categories>()

//    val getCategories = toDoListRepository.allCategories

    fun handleEvent(event: ToDoCategoryEvents) {

        when (event) {
            is ToDoCategoryEvents.AddToDoData -> {

                addToDoCategory(event.category)
            }

            ToDoCategoryEvents.LoadToDoData -> {

                _state.value = ToDoCategoryStates.Loading
                getAllToDoCategories()
            }
        }
    }

    private fun addToDoCategory(category: Categories) {
        try {

            viewModelScope.launch {

                _state.value = ToDoCategoryStates.Success(listOfCategory = listOfCategories)
                    .copy(isLoading = true)
                val isAdded = toDoListRepository.addToDoCategory(category = category)
                if (isAdded) {
                    listOfCategories.add(category)
                    _state.value = ToDoCategoryStates.Success(listOfCategory = listOfCategories)
                        .copy(isLoading = false)
                    println("Used For Success get for ${state.value}")
                } else {
                    _state.value = ToDoCategoryStates.Error("Un Wanted Error!!!")
                }
            }
        } catch (e: Exception) {

            _state.value = ToDoCategoryStates.Error("Un Wanted Error!!!")
        }
    }

    private fun getAllToDoCategories() {
        viewModelScope.launch {
            try {
                _state.value = ToDoCategoryStates.Loading
                listOfCategories = toDoListRepository.getAllToDoCategories().toMutableList()
                println("Used For Category $listOfCategories")
                _state.value = ToDoCategoryStates.Success(listOfCategory = listOfCategories)
            } catch (e: Exception) {
                _state.value = ToDoCategoryStates.Error("Un Wanted Error!!!")
            }
        }
    }

    suspend fun login(): Boolean {

        val outputDeferred = CompletableDeferred<Boolean>()
        ZCatalystApp.getInstance().login(
            success = {
                outputDeferred.complete(true)
            },
            failure = {
                outputDeferred.complete(false)
            }
        )
        return outputDeferred.await()
    }
}
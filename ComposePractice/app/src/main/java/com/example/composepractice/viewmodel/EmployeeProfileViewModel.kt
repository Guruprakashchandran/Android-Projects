package com.example.composepractice.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composepractice.EmployeeEntity
import com.example.composepractice.events.EmployeeListEvents
import com.example.composepractice.repository.EmployeeRepo
import com.example.composepractice.states.EmployeeProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmployeeProfileViewModel @Inject constructor(private val employeeRepo: EmployeeRepo) :
    ViewModel() {
    private var employeeDetail: EmployeeEntity? = null
    private var _state = MutableStateFlow<EmployeeProfileState>(EmployeeProfileState.Loading)
    val state = _state.asStateFlow()


    private fun getEmployeeDetailById(id: Long) {
        _state.value = EmployeeProfileState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            employeeDetail = employeeRepo.getEmployeeDetailById(id)
                _state.value = EmployeeProfileState.Success(employeeDetail!!)
        }
    }

    fun getImageId(id: Long): Int {

        return employeeRepo.getImageId(id)
    }

    fun handleEvent(events: EmployeeListEvents) {

        when(events) {
            is EmployeeListEvents.LoadData -> TODO()
            is EmployeeListEvents.ManipulateData -> TODO()
            is EmployeeListEvents.LoadProfileData -> {

                getEmployeeDetailById(events.employeeId)
            }
        }
    }
}
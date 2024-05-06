package com.example.composepractice.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composepractice.EmployeeEntity
import com.example.composepractice.events.EmployeeListEvents
import com.example.composepractice.repository.EmployeeRepo
import com.example.composepractice.states.EmployeesListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject


@HiltViewModel
class EmployeeDetailViewModel @Inject constructor(private var employeeRepo: EmployeeRepo) :
    ViewModel() {

    private val _employeesList = MutableStateFlow<EmployeesListState>(EmployeesListState.Loading)
    val employeesList: StateFlow<EmployeesListState> = _employeesList

    private val designationValue = mutableStateOf("ALL")
    private val searchingName = mutableStateOf("")

    var selectedFilterType = "ALL"



    fun handleEvent(
        event: EmployeeListEvents
    ) {

        when (event) {
            is EmployeeListEvents.LoadData -> {

                designationValue.value = selectedFilterType
                this.searchingName.value = event.searchingName
                getAllEmployeeDetails()
            }

            is EmployeeListEvents.ManipulateData -> TODO()
            is EmployeeListEvents.LoadProfileData -> TODO()

        }

    }


    fun loadEmployeeDetails(
        event: EmployeeListEvents
    ) {
        when (event) {
            is EmployeeListEvents.LoadData -> {
                designationValue.value = selectedFilterType
                this.searchingName.value = event.searchingName
                _employeesList.value = EmployeesListState.Loading
                getAllEmployeeDetails()

            }
            is EmployeeListEvents.ManipulateData -> TODO()
            is EmployeeListEvents.LoadProfileData -> TODO()
        }
    }

    private fun getAllEmployeeDetails() {

        viewModelScope.launch(Dispatchers.IO) {
            _employeesList.value = EmployeesListState.Loading

            try {
                employeeRepo.getAllEmployeeDetails(
                    searchingName = searchingName.value,
                    designationValue = selectedFilterType
                )?.collect {
                    _employeesList.value = EmployeesListState.Success(it)
                }
            } catch (e: Exception) {
                this@EmployeeDetailViewModel._employeesList.value = EmployeesListState.Error("Un Wanted Error!!!")
            }
        }
    }


    fun getImageId(id: Long): Int {

        return employeeRepo.getImageId(id)
    }

    fun deleteEmployeeDetailsById(id: Long) {

        viewModelScope.launch {
            employeeRepo.deleteEmployeeDetailById(id)
            _employeesList.value = EmployeesListState.Loading
        }
    }

    fun deleteFromList(data: EmployeeEntity) {

    }
}
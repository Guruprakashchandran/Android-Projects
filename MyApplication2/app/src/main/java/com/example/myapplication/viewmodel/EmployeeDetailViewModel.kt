package com.example.myapplication.viewmodel

import android.widget.ImageView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.repository.EmployeeRepo
import com.example.myapplication2.EmployeeEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EmployeeDetailViewModel @Inject constructor(private var employeeRepo: EmployeeRepo) : ViewModel() {


    private val _listOfEmployee = MutableStateFlow<List<EmployeeEntity>>(emptyList())
    val listOfEmployee = _listOfEmployee.asStateFlow()

    fun addEmployeeDetail(employee: EmployeeEntity) {

        viewModelScope.launch {
            employeeRepo.addEmployeeDetails(employee)
        }
    }

//    fun getEmployeeDetail(id: Long) {}

    fun getAllEmployeeDetails(searchQuery: String = "") {

        viewModelScope.launch(Dispatchers.IO) {
            employeeRepo.empDetailManipulate.getAllEmployeeDetails()?.collectLatest {

                _listOfEmployee.value = it
            }
        }
    }

    fun updateEmployeeDetail(employee: EmployeeEntity) {

        viewModelScope.launch {
            employeeRepo.updateEmployeeDetails(employee)
        }
    }

    fun deleteEmployeeDetails(employeeEntity: EmployeeEntity) {

        viewModelScope.launch {
            employeeRepo.deleteEmployeeDetailById(employeeEntity.id)
        }
    }

    fun setProfileImage(image: ImageView, id: Long) {
        employeeRepo.setProfileImage(image,id)
    }
}
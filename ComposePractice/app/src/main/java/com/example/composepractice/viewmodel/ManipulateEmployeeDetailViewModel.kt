package com.example.composepractice.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composepractice.EmployeeEntity
import com.example.composepractice.events.EmployeeListEvents
import com.example.composepractice.repository.EmployeeRepo
import com.example.composepractice.states.EmployeeProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ManipulateEmployeeDetailViewModel @Inject constructor(private val employeeRepo: EmployeeRepo) :
    ViewModel() {

    var id = MutableStateFlow("")
    var name = MutableStateFlow("")
    var employeeId = MutableStateFlow("")
    var dateOfBirth = MutableStateFlow("")
    var emailId = MutableStateFlow("")
    var mobileNo = MutableStateFlow("")
    var salary = MutableStateFlow("")
    var experience = MutableStateFlow("")
    var selectedGender = MutableStateFlow("")
    var selectedDepartment = MutableStateFlow("")
    var selectedDesignation = MutableStateFlow("")
    var address = MutableStateFlow("")

    private var _state = MutableStateFlow<EmployeeProfileState>(EmployeeProfileState.Loading)
    val state = _state
    private var employeeEntity: EmployeeEntity? = null

    private fun getEmployeeDetailById(id: Long) {
        _state.value = EmployeeProfileState.Loading
        viewModelScope.launch {
            employeeEntity = employeeRepo.getEmployeeDetailById(id)
            setEmployeeDetails(employeeEntity!!)
            _state.value = EmployeeProfileState.Success(employeeEntity!!)
        }
    }

    private fun setEmployeeDetails(employeeEntity: EmployeeEntity) {

        name.value = employeeEntity.name
        address.value = employeeEntity.address
        emailId.value = employeeEntity.mail_id
        mobileNo.value = employeeEntity.mobile_no.toString()
        salary.value = employeeEntity.salary.toString()
        experience.value = employeeEntity.year_of_experience.toString()
        selectedGender.value = employeeEntity.gender
        selectedDepartment.value = employeeEntity.department
        selectedDesignation.value = employeeEntity.designation
        dateOfBirth.value = employeeEntity.date_of_birth
        employeeId.value = employeeEntity.employee_id
    }

    fun handleEvent(event: EmployeeListEvents) {
        when (event) {
            is EmployeeListEvents.LoadData -> TODO()
            is EmployeeListEvents.LoadProfileData -> {

                getEmployeeDetailById(event.employeeId)
            }

            is EmployeeListEvents.ManipulateData -> {

                addEmployeeDetails(event.employeeEntity)
            }
        }
    }

    private fun addEmployeeDetails(employeeEntity: EmployeeEntity) {

        try {

            _state.value = EmployeeProfileState.Loading
            viewModelScope.launch {
                employeeRepo.addEmployeeDetails(employeeEntity)
                _state.value = EmployeeProfileState.Success(employeeEntity)
            }
        } catch (e: Exception) {
            _state.value = EmployeeProfileState.Error("Un Wanted Error!!!")
        }
    }


    /* private fun convertEmployeeDetailClass(
         empId: Long,
         name: String,
         emailId: String,
         mobileNo: String,
         employeeId: String,
         salary: String,
         experience: String,
         gender: String,
         department: String,
         designation: String,
         dateOfBirth: String,
         address: String
     ): EmployeeEntity {

         return EmployeeEntity(
             id = empId,
             name = name,
             address = address,
             mail_id = emailId,
             mobile_no = mobileNo.toLong(),
             employee_id = employeeId,
             year_of_experience = experience.toLong(),
             salary = salary.toLong(),
             department = department,
             designation = designation,
             date_of_birth = dateOfBirth,
             gender = gender
         )
     }*/
}
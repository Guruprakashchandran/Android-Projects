package com.example.composepractice.events

import com.example.composepractice.EmployeeEntity

sealed class EmployeeListEvents {

    data class LoadData(val designation:String,val searchingName: String): EmployeeListEvents()
    data class LoadProfileData(val employeeId: Long): EmployeeListEvents()
    data class ManipulateData(val employeeEntity: EmployeeEntity): EmployeeListEvents()
}
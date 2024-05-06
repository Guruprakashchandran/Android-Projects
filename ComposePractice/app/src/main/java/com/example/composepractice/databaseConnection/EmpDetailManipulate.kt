package com.example.composepractice.databaseConnection

import com.example.composepractice.EmployeeEntity
import  kotlinx.coroutines.flow.Flow

interface EmpDetailManipulate {

    suspend fun getEmployeeDetailById(id: Long): EmployeeEntity

    fun getAllEmployeeDetails(searchingName: String, designationValue: String): Flow<List<EmployeeEntity>>?

    suspend fun deleteEmployeeDetailById(id: Long)

    suspend fun addEmployeeDetail(employeeEntity: EmployeeEntity)

    suspend fun updateEmployeeDetail(employeeEntity: EmployeeEntity)

    suspend fun addEmployeeDetailWithId(employeeEntity: EmployeeEntity)

    suspend fun getAllEmployeeDetailsByDesignation(
        search: String,
        searchingName: String
    ): Flow<List<EmployeeEntity>>
}
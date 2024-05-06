package com.example.myapplication.databaseConnection

import com.example.myapplication2.EmployeeEntity
import  kotlinx.coroutines.flow.Flow

interface EmpDetailManipulate {

    suspend fun getEmployeeDetailById(id: Long): EmployeeEntity

    fun getAllEmployeeDetails(): Flow<List<EmployeeEntity>>?

    suspend fun deleteEmployeeDetailById(id: Long)

    suspend fun addEmployeeDetail(employeeEntity: EmployeeEntity)

    suspend fun updateEmployeeDetail(employeeEntity: EmployeeEntity)

    suspend fun addEmployeeDetailWithId(employeeEntity: EmployeeEntity)
}
package com.example.myapplication.databaseConnection

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.example.myapplication2.EmployeeEntity
import com.example.myapplication2.Database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EmpDetailsManipulateImpl @Inject constructor(private val database: Database) : EmpDetailManipulate {

    private val queries = database.employeeEntityQueries
    override suspend fun getEmployeeDetailById(id: Long): EmployeeEntity {
        return queries.getEmployeeDetailById(id).executeAsOneOrNull()!!
    }

    override fun getAllEmployeeDetails(): Flow<List<EmployeeEntity>> {
        return queries.getAllEmployeeDetails().asFlow().mapToList(Dispatchers.Default)
    }

    override suspend fun deleteEmployeeDetailById(id: Long) {
        queries.deleteEmployeeById(id)
    }

    override suspend fun addEmployeeDetail(employeeEntity: EmployeeEntity) {
        queries.addEmployeeDetail(
            employeeEntity.name,
            employeeEntity.address,
            employeeEntity.mail_id,
            employeeEntity.mobile_no,
            employeeEntity.year_of_experience,
            employeeEntity.salary,
            employeeEntity.department,
            employeeEntity.designation,
            employeeEntity.employee_id,
            employeeEntity.date_of_birth,
            employeeEntity.gender
        )
    }

    override suspend fun updateEmployeeDetail(employeeEntity: EmployeeEntity) {
        queries.updateEmployeeDetail(
            employeeEntity.name,
            employeeEntity.address,
            employeeEntity.mail_id,
            employeeEntity.mobile_no,
            employeeEntity.year_of_experience,
            employeeEntity.salary,
            employeeEntity.department,
            employeeEntity.designation,
            employeeEntity.employee_id,
            employeeEntity.date_of_birth,
            employeeEntity.gender,
            employeeEntity.id
        )
    }

    override suspend fun addEmployeeDetailWithId(employeeEntity: EmployeeEntity) {

        queries.addEmployeeDetailWithId(
            employeeEntity.id,
            employeeEntity.name,
            employeeEntity.address,
            employeeEntity.mail_id,
            employeeEntity.mobile_no,
            employeeEntity.year_of_experience,
            employeeEntity.salary,
            employeeEntity.department,
            employeeEntity.designation,
            employeeEntity.employee_id,
            employeeEntity.date_of_birth,
            employeeEntity.gender
        )
    }
}
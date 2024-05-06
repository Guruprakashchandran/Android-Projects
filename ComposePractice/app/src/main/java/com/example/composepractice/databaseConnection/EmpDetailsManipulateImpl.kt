package com.example.composepractice.databaseConnection

import android.util.Log
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.example.composepractice.Database
import com.example.composepractice.EmployeeEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EmpDetailsManipulateImpl @Inject constructor(private val database: Database) : EmpDetailManipulate {

    private val queries = database.employeeEntityQueries
    override suspend fun getEmployeeDetailById(id: Long): EmployeeEntity {
        return queries.getEmployeeDetailById(id).executeAsOneOrNull()!!
    }

    override fun getAllEmployeeDetails(
        searchingName: String,
        designationValue: String
    ): Flow<List<EmployeeEntity>> {
        return queries.getAllEmployeeDetails(name = searchingName, designation = designationValue).asFlow().mapToList(Dispatchers.Default)
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

    override suspend fun getAllEmployeeDetailsByDesignation(
        search: String,
        searchingName: String
    ) : Flow<List<EmployeeEntity>>{

        Log.i("SearchName",searchingName)
        return queries.getAllEmployeeDetailsByDesignation(search,searchingName).asFlow().mapToList(Dispatchers.IO)
    }
}
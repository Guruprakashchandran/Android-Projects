package com.example.composepractice.states

import androidx.compose.runtime.State
import com.example.composepractice.EmployeeEntity

sealed class EmployeesListState {
    object Loading : EmployeesListState()

    data class Success(val employees: List<EmployeeEntity>) : EmployeesListState()
    data class Error(val message: String) : EmployeesListState()
}
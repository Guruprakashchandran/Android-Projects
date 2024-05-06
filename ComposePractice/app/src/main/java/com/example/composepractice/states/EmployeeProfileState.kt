package com.example.composepractice.states

import com.example.composepractice.EmployeeEntity
import com.example.composepractice.viewmodel.EmployeeProfileViewModel

sealed class EmployeeProfileState {

    data object Loading: EmployeeProfileState()
    data class Success(val employee: EmployeeEntity): EmployeeProfileState()
    data class Error(val message: String): EmployeeProfileState()
}

enum class FilterTypes(val type: String) {
    ALL("ALL"),
    TRAINEE("TRAINEE"),
    EMPLOYEE("EMPLOYEE"),
    TEAM_LEADER("TEAM_LEADER"),
    MANAGER("MANAGER"),
    ADMIN("ADMIN"),
    SUPER_ADMIN("SUPER_ADMIN"),
}

data class FilterUiData(
    val key: String,
    val value: String
)
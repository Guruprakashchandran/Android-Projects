package com.example.composepractice.navigation.screen

sealed class Screen(var route: String) {

    data object EmployeeProfilePage: Screen("EmployeeProfilePage")
    data object InsertOrModifyEmployeeDetail: Screen("ManipulateEmployeeDetail")
    data object EmployeeDetailList: Screen("EmployeeList ")
}
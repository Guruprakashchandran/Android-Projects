package com.example.composepractice.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.composepractice.model.Employee
import com.example.composepractice.navigation.screen.Screen
import com.example.composepractice.view.EmployeeProfile

//@Composable
//fun AppNavHost(navController: NavHostController){
//    NavHost(navController = navController, startDestination = Screen.EmployeeDetailList.route) {
////        composable(Screen.EmployeeDetailList.route) {
////            EmployeeList(navController, employeeDetailViewModel)
////        }
//        composable(Screen.EmployeeProfilePage.route) {
//            val employeeId =
//                navController.previousBackStackEntry?.savedStateHandle?.get<Employee>("employeeId")
//            if (employeeId != null) {
//                EmployeeProfile(navController, employeeId)
//            }
//        }
//        composable(Screen.InsertOrModifyEmployeeDetail.route) {
//            InsertAndModifyEmployeeDetail(navController)
//        }
//    }
//}
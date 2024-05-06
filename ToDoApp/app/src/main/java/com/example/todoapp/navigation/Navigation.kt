package com.example.todoapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.todoapp.ui.theme.view.CategoryBasedToDoList
import com.example.todoapp.ui.theme.view.LoginOrSignUpPage
import com.example.todoapp.ui.theme.view.ToDoListPage
import com.example.todoapp.ui.theme.view.ToDoMyLists

@Composable
fun Navigation(navController: NavHostController) {

    NavHost(navController = navController, startDestination = Screen.ToDoSignInOrSignUp.route) {

        composable(Screen.ToDoSignInOrSignUp.route){
            LoginOrSignUpPage( navController = navController)
        }
        composable(Screen.ToDoListCategory.route) {
            ToDoMyLists(navController = navController)
        }
        composable(Screen.ToDoList.route) {
            ToDoListPage(navController)
        }
        composable(Screen.CategoryBasedToDoList.route){
            val category = navController.previousBackStackEntry?.savedStateHandle?.get<String>("category")
            if(category != null){
                CategoryBasedToDoList(navController,category)
            }
        }
    }
}
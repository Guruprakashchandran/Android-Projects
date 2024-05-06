package com.example.todoapp.navigation

sealed class Screen(val route: String) {
    data object ToDoSignInOrSignUp: Screen("SignInOrSignOut")
    data object ToDoListCategory: Screen("ToDoListCategory")
    data object ToDoList: Screen("ToDoList")
    data object CategoryBasedToDoList: Screen("CategoryBasedToDoList")
}
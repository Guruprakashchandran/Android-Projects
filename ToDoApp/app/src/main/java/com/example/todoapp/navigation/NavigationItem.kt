package com.example.todoapp.navigation

import com.example.todoapp.R

sealed class NavigationItem(var name: String, var icon: Int, var route: String) {

    data object Home : NavigationItem("Home", R.drawable.bullseye, "Home")
    data object Profile : NavigationItem("Profile",R.drawable.bullseye,"Profile")
    data object History: NavigationItem("History",R.drawable.bullseye,"History")
}
package com.moetaz.moviesapp.navigation

sealed class Screen(val route: String) {
    object List : Screen("list")
    object Detail : Screen("detail")
}

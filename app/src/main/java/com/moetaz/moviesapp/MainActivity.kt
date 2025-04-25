package com.moetaz.moviesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.moetaz.moviesapp.navigation.Screen
import com.moetaz.moviesapp.screens.detail.MovieDetailScreen
import com.moetaz.moviesapp.screens.now_playing.MoviesListScreen
import com.moetaz.moviesapp.ui.theme.MoviesAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoviesAppTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.List.route) {

        composable(Screen.List.route) {
            MoviesListScreen(onItemClick = {
                navController.navigate(Screen.Detail.createRoute(it))
            })
        }
        composable(
            Screen.Detail.route,
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })

        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId") ?: 0
            MovieDetailScreen(movieId = movieId)
        }
    }
}


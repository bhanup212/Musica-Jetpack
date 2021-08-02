package com.best.music.musica.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.best.music.musica.ui.view.Screen
import com.best.music.musica.ui.view.screen.HomeScreen
import com.best.music.musica.ui.view.screen.SplashScreen
import com.best.music.musica.viewmodel.SongsViewModel

@Composable
fun MusicNavigation(songsViewModel: SongsViewModel, toggleTheme:() -> Unit) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Splash.name){
        composable(Screen.Splash.name){
            SplashScreen(navHostController = navController)
        }
        composable(Screen.Home.name){
            HomeScreen(navHostController = navController, songsViewModel)
        }
    }
}
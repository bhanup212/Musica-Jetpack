package com.best.music.musica.ui.view

internal sealed class Screen(val name: String){
    object Splash: Screen("splash")
    object Home: Screen("home")
    object Settings: Screen("settings")
    object NowPlaying: Screen("now_playing")
}
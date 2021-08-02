package com.best.music.musica

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.best.music.musica.navigation.MusicNavigation
import com.best.music.musica.ui.theme.MusicaTheme
import com.best.music.musica.ui.view.screen.SplashScreen
import com.best.music.musica.viewmodel.SongsViewModel

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: SongsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SongsViewModel::class.java)
        setContent {
            MusicaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MusicNavigation(viewModel) {

                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MusicaTheme {
       // SplashScreen()
    }
}
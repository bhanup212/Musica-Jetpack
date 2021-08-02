package com.best.music.musica.ui.view.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.best.music.musica.model.Song
import com.best.music.musica.utils.toDate
import com.best.music.musica.viewmodel.SongsViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomeScreen(navHostController: NavHostController, songsViewModel: SongsViewModel) {
    songsViewModel.getAllSongs()
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar {
                MusicAppBar(scaffoldState = scaffoldState)
            }
        },
    ) {
        BodyContent(songsViewModel)
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BodyContent(songsViewModel: SongsViewModel) {
    val isLoading = songsViewModel.isLoading.observeAsState()
    val songList = songsViewModel.getMusicList().observeAsState()

    Box(
        modifier = Modifier
            .background(color = Color(0xFF4C5897))
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        AnimatedVisibility(visible = (isLoading.value == true)) {
            CircularProgressIndicator()
        }
        LazyColumn {
            songList.value?.forEach {
                item {
                    SongCard(song = it) {

                    }
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(2.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SongCard(song: Song, onSongClick: () -> Unit) {
    Card(
        onClick = {

        }, modifier = Modifier
            .fillMaxWidth()
            .padding(start = 6.dp, end = 6.dp, bottom = 4.dp), backgroundColor = Color(0xFF7A82AC)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = rememberImagePainter(data = song.file, builder = {
                    crossfade(true)
                    placeholder(com.best.music.musica.R.drawable.svara)
                    error(com.best.music.musica.R.drawable.svara)
                    transformations(CircleCropTransformation())
                }), contentDescription = song.name,
                modifier = Modifier
                    .height(52.dp)
                    .width(53.dp)
            )
            Column(modifier = Modifier.padding(4.dp)) {
                Text(
                    text = song.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(text = song.timInMills.toDate(), style = TextStyle(fontSize = 12.sp))
            }
        }
    }
}

@Composable
fun MusicAppBar(scaffoldState: ScaffoldState) {
    val scope = rememberCoroutineScope()
    TopAppBar(title = { Text("Music Player") },
        backgroundColor = Color(0xFF3F51B5),
        navigationIcon = {
            Icon(
                Icons.Default.Menu, "",
                modifier = Modifier.clickable(onClick = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                })
            )
        })
}
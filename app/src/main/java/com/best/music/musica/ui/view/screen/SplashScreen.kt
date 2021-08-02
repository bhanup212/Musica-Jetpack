package com.best.music.musica.ui.view.screen

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.best.music.musica.ui.view.Screen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SplashScreen(navHostController: NavHostController) {
    /*val isAllSongsLoaded = songsViewModel.isAllSongsLoaded.observeAsState()
    val progressbar= songsViewModel.isLoading.observeAsState()
    if (isAllSongsLoaded.value == true){

    }*/
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val isPermissionGranted = remember { mutableStateOf(ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)}
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission(), onResult ={ isGranted ->
        if (isGranted){
            isPermissionGranted.value = isGranted
        }else{
            scope.launch {
                delay(2000)
                navHostController.navigate(route = Screen.Home.name)
            }
        }
    } )
    if (isPermissionGranted.value){
        LaunchedEffect(true){
            scope.launch {
                delay(2000)
                navHostController.navigate(route = Screen.Home.name, builder = {
                    
                })
            }
        }
    }
    Box(modifier = Modifier
        .fillMaxHeight()
        .background(Color.DarkGray)) {
        ConstraintLayout(
            Modifier
                .fillMaxSize()
                .fillMaxHeight()
        ) {
            val (logoRef, titleRef, buttonRef, progressRef) = createRefs()
            Image(
                contentDescription = "App Logo",
                painter = painterResource(com.best.music.musica.R.drawable.svara),
                modifier = Modifier
                    .height(100.dp)
                    .width(100.dp)
                    .constrainAs(logoRef) {
                        top.linkTo(parent.top, margin = 16.dp)
                        start.linkTo(parent.start, 16.dp)
                        end.linkTo(parent.end, 16.dp)
                        bottom.linkTo(parent.bottom, 16.dp)
                    },
                contentScale = ContentScale.Fit
            )
            Text(text = "Musica",
                style = typography.h4,
                color = Color.White,
                modifier = Modifier.constrainAs(titleRef) {
                    top.linkTo(logoRef.bottom, margin = 14.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })
            CircularProgressIndicator(modifier = Modifier.constrainAs(progressRef){
                top.linkTo(titleRef.bottom, margin = 16.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })
            Box(modifier = Modifier.constrainAs(buttonRef) {
                bottom.linkTo(parent.bottom, margin = 24.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }){
                AnimatedVisibility(visible = !isPermissionGranted.value) {
                    Button(onClick = {
                        when (PackageManager.PERMISSION_GRANTED) {
                            ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                            ) -> {
                                //
                            }
                            else -> {
                                launcher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                            }
                        }
                    }) {
                        Text(text = "Start")
                    }
                }
            }
        }
    }
}
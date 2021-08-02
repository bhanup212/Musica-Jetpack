package com.best.music.musica.model

import java.io.File

data class Song(
    val path: String,
    val file: File,
    val timInMills: Long,
    val name: String,
    val albumId: Long
)

package com.best.music.musica.viewmodel

import android.app.Application
import android.content.Context
import android.provider.MediaStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.best.music.musica.model.Song
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class SongsViewModel (application: Application): AndroidBaseViewModel(application) {

    private val contentResolver = application.contentResolver

    private val _musicList = MutableLiveData<ArrayList<Song>>()
    fun getMusicList(): LiveData<ArrayList<Song>> = _musicList

    val isAllSongsLoaded = MutableLiveData<Boolean>()

     fun getAllSongs(){
         viewModelScope.launch {
             isAllSongsLoaded.value = false
             showProgressBar()
             withContext(Dispatchers.IO){
                 val selection = MediaStore.Audio.Media.IS_MUSIC + " != 0"

                 val projection = arrayOf(
                     MediaStore.Audio.Media._ID,
                     MediaStore.Audio.Media.TITLE,
                     MediaStore.Audio.Media.DATA,
                     MediaStore.Audio.Media.DISPLAY_NAME,
                     MediaStore.Audio.Media.ALBUM_ID
                 )

                 // MediaStore.Audio.Media.DATE_ADDED + " DESC"
                 val cursor = contentResolver.query(
                     MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                     projection,
                     selection,
                     null,
                     null
                 )
                 var list = ArrayList<Song>()
                 cursor?.let {
                     while (cursor.moveToNext()){
                         val id = cursor.getString(0)
                         val title = cursor.getString(1)
                         val data = cursor.getString(2)
                         val displayName = cursor.getString(3)
                         val albumId = cursor.getLong(4)

                         val file = File(data)
                         val musicInfo = Song(data, file, file.lastModified(),file.name, albumId)

                         //Log.d(TAG,"mu: $musicInfo")
                         list.add(musicInfo)
                     }
                     list.sortByDescending { it.timInMills }
                     _musicList.postValue(list)
                 }
                 cursor?.close()
                 dismissProgress()
                 delay(10000)
                 isAllSongsLoaded.postValue(true)
             }
         }
     }
}
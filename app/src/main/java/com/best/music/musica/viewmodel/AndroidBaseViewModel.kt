package com.best.music.musica.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

open class AndroidBaseViewModel(application: Application): AndroidViewModel(application) {
    protected val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun showProgressBar(){
        _isLoading.postValue(true)
    }

    fun dismissProgress(){
        _isLoading.postValue(false)
    }
}
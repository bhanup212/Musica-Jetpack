package com.best.music.musica.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel: ViewModel() {
    protected val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun showProgressBar(){
        _isLoading.postValue(true)
    }

    fun dismissProgress(){
        _isLoading.postValue(false)
    }
}
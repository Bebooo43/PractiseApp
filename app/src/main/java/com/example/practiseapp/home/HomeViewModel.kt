package com.example.practiseapp.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.practiseapp.models.MovieItem
import com.example.practiseapp.retrofit.RetrofitService
import kotlinx.coroutines.*

class HomeViewModel : ViewModel() {

    val movies = MutableLiveData<List<MovieItem>>()
    val errorMessage = MutableLiveData<String>()

    private val service: RetrofitService by lazy { RetrofitService.getInstance() }
    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        errorMessage.value = throwable.localizedMessage
    }

    fun fetchMovies() {
        CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = service.getAllMovies()
            if (response.isSuccessful) {
                movies.postValue(response.body())
            } else {
                errorMessage.postValue(response.message())
            }
        }
    }
}
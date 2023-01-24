package com.example.practiseapp.retrofit

import com.example.practiseapp.models.MovieItem
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RetrofitService {

    @GET("movielist.json")
    suspend fun getAllMovies(): Response<List<MovieItem>>

    companion object {
        var retrofitService: RetrofitService? = null

        fun getInstance() : RetrofitService {
            if (retrofitService == null){
                Retrofit.Builder()
                    .baseUrl("https://howtodoandroid.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().also {
                        retrofitService = it.create(RetrofitService::class.java)
                    }
            }
            return retrofitService!!
        }
    }

}
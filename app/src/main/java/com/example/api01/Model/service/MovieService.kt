package com.example.api01.Model.service


import com.example.api01.Model.ResponseMovie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import retrofit2.http.QueryMap

//interface is for defining the endpoints of the API and the HTTP methods that will be used to access them
interface MovieService {
    @JvmSuppressWildcards
    @GET("?s=thor&page=tt3896198")
    suspend fun getMovie(@Query("apikey") apiKey: String): ResponseMovie
}
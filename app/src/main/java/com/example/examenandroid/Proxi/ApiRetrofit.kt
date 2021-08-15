package com.example.examenandroid.Proxi


import com.example.examenandroid.models.ResponseModel
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiRetrofit {

    @GET("{path}")
    fun getAllPost(@Path("path") stringOfPath:String, @Query("q") stringQuery:String): Observable<Response<String>>

}
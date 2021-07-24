package com.example.examenandroid.Proxi

import com.example.examenandroid.DialogPersonalized
import com.example.examenandroid.models.ResponseModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList

class RetrofitController (val retrofitStrings: RetrofitStrings){

    fun executeAPI(
        stringOfPath: String,
        goodFunction: (response:Response<ArrayList<ResponseModel>>) -> Any,
        badFunction: () -> Any
    ): @NonNull Disposable? {
       return Retrofit.Builder()
            .baseUrl(retrofitStrings.webApiURL())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build().create(ApiRetrofit::class.java).getAllPost(stringOfPath)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        goodFunction(it)
                    },
                    {
                        badFunction()
                    }
                )
    }

}
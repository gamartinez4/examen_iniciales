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
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*
import kotlin.collections.ArrayList

class RetrofitController (val retrofitStrings: RetrofitStrings){

    fun executeAPI(
        url: String,
        goodFunction: (response:Response<String>) -> Any,
        badFunction: () -> Any
    ): @NonNull Disposable? {
       return Retrofit.Builder()
            .baseUrl(retrofitStrings.webApiURL())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build().create(ApiRetrofit::class.java).getAllPost(url)
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
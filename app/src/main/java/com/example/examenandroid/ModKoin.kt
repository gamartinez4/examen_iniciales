package com.example.examenandroid

import com.example.examenandroid.Proxi.RetrofitController
import com.example.examenandroid.Proxi.RetrofitStrings
import org.koin.dsl.module

val modKoin = module{
    single { RetrofitStrings() }
    single { RetrofitController(get()) }
    single {DialogPersonalized()}
}
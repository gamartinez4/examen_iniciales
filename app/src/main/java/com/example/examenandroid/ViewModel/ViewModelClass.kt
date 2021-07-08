package com.example.examenandroid.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.examenandroid.models.ResponseModel

class ViewModelClass : ViewModel(){

    val listaPost = MutableLiveData<ArrayList<ResponseModel>>()
    val selectedElement = MutableLiveData<ResponseModel?>()
}
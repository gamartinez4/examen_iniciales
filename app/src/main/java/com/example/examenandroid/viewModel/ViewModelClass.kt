package com.example.examenandroid.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewModelClass : ViewModel(){

    var search = MutableLiveData("")

   fun fieldTextChanged(charSequence: CharSequence, idField: Int){
        search.value = charSequence.toString()
       Log.e("prueba",charSequence.toString())
    }
}
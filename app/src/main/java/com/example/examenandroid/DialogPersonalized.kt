package com.example.examenandroid

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.Fragment

class DialogPersonalized() {

    var contenido:String = ""
    var context: Context? =  null
    var funcion:()->Unit = {}


    fun showDialog(): Dialog {
        return (AlertDialog.Builder(context))
            .setTitle("ALERTA!")
            .setMessage(contenido)
            .setPositiveButton(
                "OK"
            ) { _: DialogInterface, i: Int ->
                funcion()
                if( funcion != {})funcion = {}
            }.show()
    }
}
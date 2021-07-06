package com.example.examenandroid.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class ResponseModel:RealmObject(){
    @PrimaryKey var id:Int? = null
    var userId:Int? = null
    var title:String? = null
    var completed:Boolean? = null
    var isFavourite:Boolean? = false
    var viewed:Boolean = false
}
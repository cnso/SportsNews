package org.jash.mylibrary.model

import java.util.Date

data class Res<T> (
    val code:Int,
    val msg:String,
    val data: T
)
package org.jash.mylibrary.logging

import android.util.Log

private val log_enable = true
val Any.TAG
    get() = javaClass.simpleName

fun Any.logging(msg:Any?) {
    if (log_enable)
        Log.d(TAG, msg.toString())
}
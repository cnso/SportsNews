package org.jash.mylibrary

import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener

fun Animation.setListener(start: ((Animation?) -> Unit)? = null,end: ((Animation?) -> Unit)? = null,repeat: ((Animation?) -> Unit)? = null) {
    setAnimationListener(object :AnimationListener{
        override fun onAnimationStart(animation: Animation?) {
            if (start != null) {
                start(animation)
            }
        }

        override fun onAnimationEnd(animation: Animation?) {
            if (end != null) {
                end(animation)
            }
        }

        override fun onAnimationRepeat(animation: Animation?) {
            if (repeat != null) {
                repeat(animation)
            }
        }

    })

}
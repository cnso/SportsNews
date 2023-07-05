package org.jash.sportsnews.util

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ScrollView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.isVisible
import org.jash.mylibrary.R
import org.jash.mylibrary.logging.logging
import org.jash.mylibrary.setListener

class MyBehavior(context: Context?, attrs: AttributeSet?):CoordinatorLayout.Behavior<View>(context, attrs) {

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        return true
    }
    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int,
        consumed: IntArray
    ) {
        if (dyConsumed > 10 && child.isVisible) {
           child.startAnimation(AnimationUtils.loadAnimation(child.context, org.jash.sportsnews.R.anim.exit).apply {
               setListener(end = {child.visibility = View.INVISIBLE})
           })
       }
        if (dyConsumed < 10 && !child.isVisible) {
           child.startAnimation(AnimationUtils.loadAnimation(child.context, org.jash.sportsnews.R.anim.enter).apply {
               setListener(start = {child.isVisible = true})
           })
       }

    }
}
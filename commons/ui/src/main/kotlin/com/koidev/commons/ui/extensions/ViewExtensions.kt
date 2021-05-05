package com.koidev.commons.ui.extensions

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat

fun TextView.setFadedBackgroundTextColor(
    @ColorInt colorNew: Int,
    @ColorInt colorOld: Int? = null,
    duration: Long = 2500L,
    delay: Long = 0
) {
    val colorOldFinal = colorOld ?:  ContextCompat.getColor(
        this.context,
        android.R.color.transparent
    )
    val animator = ValueAnimator.ofFloat(0f, .8f, 0f)
    animator.duration = duration
    animator.startDelay = delay
    animator.start()

    val argbEvaluator = ArgbEvaluator()
    animator.addUpdateListener { animation ->
        val animatedValue = animation.animatedValue as Float

        val colorBackground = argbEvaluator.evaluate(animatedValue, colorOldFinal, colorNew) as Int
        setBackgroundColor(colorBackground)
        val colorText = argbEvaluator.evaluate(animatedValue, this.currentTextColor, colorNew) as Int
        setTextColor(colorText)
    }
}

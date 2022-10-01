package com.molitics.molitician.customView.molitics

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.molitics.molitician.R

class RegisterConstraintLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        this.setBackgroundColor(context.getColor(R.color.hard_white))
        setPadding()
        setAppLogo(context)
    }

    private fun setPadding() {
        val paddingDp = 20
        val density = context.resources.displayMetrics.density
        val paddingPixel = (paddingDp * density).toInt()
        setPadding(paddingPixel, 0, paddingPixel, 0)
    }

    private fun setAppLogo(context: Context) {
        val set = ConstraintSet()

        val childView = AppCompatImageView(context)
        this.addView(childView)

        childView.setImageResource(R.drawable.small_logo)
        childView.id = View.generateViewId()

        set.clone(this)
        set.connect(childView.id, ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 0)
        set.connect(childView.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 70)
        set.applyTo(this)
    }
}
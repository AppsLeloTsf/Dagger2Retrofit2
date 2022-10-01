package com.molitics.molitician.customView.molitics

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.molitics.molitician.R

class PoppinsRegularTextView : AppCompatTextView {

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        initView(context, attributeSet)
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int) : super(context, attributeSet, defStyle) {
        initView(context, attributeSet)
    }

    private fun initView(context: Context, attributeSet: AttributeSet) {
        val typeface = ResourcesCompat.getFont(context, R.font.poppins_regular)
        this.typeface = typeface
        setTextColor(ContextCompat.getColor(context,R.color.profile_name))
    }
}
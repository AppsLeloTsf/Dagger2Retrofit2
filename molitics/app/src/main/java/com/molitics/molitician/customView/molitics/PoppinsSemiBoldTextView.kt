package com.molitics.molitician.customView.molitics

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import com.molitics.molitician.R

class PoppinsSemiBoldTextView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyle: Int = 0) :
        AppCompatTextView(context, attributeSet, defStyle) {

    init {
        val typeface = ResourcesCompat.getFont(context, R.font.poppins_semi_bold)
        this.typeface = typeface
    }
}
package com.molitics.molitician.customView.molitics

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.molitics.molitician.R

class CustomTextInputEditText @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = R.style.Widget_MaterialComponents_TextInputLayout_OutlinedBox) :
        TextInputLayout(context, attrs, defStyleAttr) {

    private var editText: TextInputEditText? = null

    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        setWillNotDraw(false)
        editText = TextInputEditText(context)
        editText?.let {
            createEditBox(it)
        }
    }

    private fun createEditBox(editText: TextInputEditText) {
        val layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        editText.setPadding(0, 10, 0, 0)
        editText.layoutParams = layoutParams
        addView(editText)
    }

    init {
        init(context, attrs, defStyleAttr)
    }
}
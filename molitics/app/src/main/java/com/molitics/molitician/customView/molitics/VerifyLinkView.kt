package com.molitics.molitician.customView.molitics

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.molitics.molitician.R

class VerifyLinkView @JvmOverloads constructor(
    context: Context,
    val attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    RelativeLayout(context, attrs, defStyleAttr) {

    init {
        initView()
    }

    private fun initView() {
        val typeArray: TypedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.VerifyLinkView, 0, 0
        )
        val platformLinkTypeView: String? = typeArray.getString(R.styleable.VerifyLinkView_LinkName)

        typeArray.recycle()

        val inflater: LayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val childView = inflater.inflate(R.layout.verify_link_layout, this, true)

        val platformUrlTV = childView.findViewById<PoppinsRegularTextView>(R.id.platformUrlTV)
        platformUrlTV.text = platformLinkTypeView
    }
}
package com.molitics.molitician.ui.dashboard.login

import android.content.Context
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialog
import com.molitics.molitician.R
import com.molitics.molitician.util.Constant
import com.molitics.molitician.util.PrefUtil
import kotlinx.android.synthetic.main.dialog_term_condition.*

class TermConditionDialog(context: Context) : AppCompatDialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        super.onCreate(savedInstanceState)
        setCancelable(false)
        setContentView(R.layout.dialog_term_condition)

        termTextView.movementMethod = LinkMovementMethod.getInstance()
        agreeButton.setOnClickListener {
            PrefUtil.putBoolean(Constant.PreferenceKey.IS_USER_AGREE_TERM_CONDITION, true)
            dismiss()
        }
    }
}
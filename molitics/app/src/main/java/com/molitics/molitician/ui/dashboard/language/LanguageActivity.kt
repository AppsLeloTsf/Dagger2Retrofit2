package com.molitics.molitician.ui.dashboard.language

import android.content.Intent
import android.os.Bundle
import com.molitics.molitician.BR
import com.molitics.molitician.BaseActivity
import com.molitics.molitician.R
import com.molitics.molitician.databinding.ActivityLanguageBinding
import com.molitics.molitician.ui.dashboard.language.videModel.LanguageViewModel
import javax.inject.Inject
import android.widget.RadioButton
import com.molitics.molitician.ui.dashboard.ActivityFragment
import com.molitics.molitician.ui.dashboard.DashBoardActivity
import com.molitics.molitician.util.Constant
import com.molitics.molitician.util.ExtraUtils.setAppLanguage
import com.molitics.molitician.util.PrefUtil
import kotlinx.android.synthetic.main.activity_language.*

class LanguageActivity : BaseActivity<ActivityLanguageBinding, LanguageViewModel>() {

    @Inject
    lateinit var languageViewModel: LanguageViewModel

    override fun getBindingVariable(): Int = BR.langViewModel

    override fun getLayoutId(): Int = R.layout.activity_language

    override fun getViewModel(): LanguageViewModel = languageViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRadioListener()
        handleIntent()
    }

    private fun handleIntent() {
        if (intent.hasExtra(Constant.IntentKey.FROM)) {
            selectLangView.text = getString(R.string.change_language)
        }
    }

    private fun setRadioListener() {
        val selectedLanguage = PrefUtil.getConstantString(Constant.PreferenceKey.LANGUAGE)

        if (selectedLanguage.equals(Constant.Language.HINDI_STRING))
            languageGroupRadio.check(R.id.hindiRadio)
        else languageGroupRadio.check(R.id.engRadio)

        doneBtn.setOnClickListener {
            val radioButtonID = languageGroupRadio.checkedRadioButtonId
            val radioButton = languageGroupRadio.findViewById<RadioButton>(radioButtonID)
            val idx = languageGroupRadio.indexOfChild(radioButton)

            if (idx == 0) {
                setAppLanguage(this, Constant.Language.SYSTEM_ENGLISH)
                PrefUtil.putConstantString(Constant.PreferenceKey.LANGUAGE, Constant.Language.ENGLISH_STRING)
            } else {
                setAppLanguage(this, Constant.Language.SYSTEM_HINDI)
                PrefUtil.putConstantString(Constant.PreferenceKey.LANGUAGE, Constant.Language.HINDI_STRING)
            }
            if (!intent.hasExtra(Constant.IntentKey.FROM)) {
                val intent = Intent(this, ActivityFragment::class.java)
                intent.putExtra(Constant.INTENT_FROM, Constant.From.ACTIVITY_SIGN_IN_FRAGMENT)
                startActivity(intent)
            } else {
                val intent = Intent(this, DashBoardActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }

            finish()
        }
    }
}
package com.molitics.molitician.ui.dashboard.language.videModel

import com.molitics.molitician.BaseViewModel
import com.molitics.molitician.ui.dashboard.BaseNavigation
import com.molitics.molitician.util.Util

class LanguageViewModel : BaseViewModel<BaseNavigation>() {



    fun selelctLanguage(value: Int) {
        Util.showLog("LanguageViewModel", "${value}")
    }
}
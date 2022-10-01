package com.molitics.molitician.ui.dashboard.language.module

import com.molitics.molitician.ui.dashboard.language.videModel.LanguageViewModel
import dagger.Module
import dagger.Provides

@Module
class LanguageModule {

    @Provides
    fun provideViewModule(): LanguageViewModel {
        return LanguageViewModel()
    }
}
package com.molitics.molitician.ui.dashboard.newsDetail.module;

import com.molitics.molitician.ui.dashboard.newsDetail.viewModel.DetailNewsViewModel;
import com.molitics.molitician.ui.dashboard.voice.model.VoiceViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class NewsDetailsActivtyModule {

    @Provides
    DetailNewsViewModel provideLoginViewModel() {
        return new DetailNewsViewModel();
    }
}

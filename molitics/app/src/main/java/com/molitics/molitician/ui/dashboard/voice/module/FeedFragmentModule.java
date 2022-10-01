package com.molitics.molitician.ui.dashboard.voice.module;


import com.molitics.molitician.ui.dashboard.voice.model.FeedViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class FeedFragmentModule {

    @Provides
    FeedViewModel provideFeedViewModel() {
        return new FeedViewModel();
    }


}

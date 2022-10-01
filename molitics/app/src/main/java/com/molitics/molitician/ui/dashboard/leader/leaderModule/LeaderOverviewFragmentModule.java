package com.molitics.molitician.ui.dashboard.leader.leaderModule;

import com.molitics.molitician.ui.dashboard.voice.model.VoiceViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class LeaderOverviewFragmentModule {

    @Provides
    VoiceViewModel provideVoiceViewModel() {
        return new VoiceViewModel();
    }
}

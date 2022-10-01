package com.molitics.molitician.ui.dashboard.voice.voiceDetail.module;


import com.molitics.molitician.ui.dashboard.voice.model.VoiceViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class VoiceDetailActivityModule {
    @Provides
    VoiceViewModel provideLoginViewModel() {
        return new VoiceViewModel();
    }
}

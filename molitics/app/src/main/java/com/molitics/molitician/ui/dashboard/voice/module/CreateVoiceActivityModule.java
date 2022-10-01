package com.molitics.molitician.ui.dashboard.voice.module;

import com.molitics.molitician.ui.dashboard.voice.model.VoiceViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class CreateVoiceActivityModule {

    @Provides
    VoiceViewModel provideLoginViewModel() {
        return new VoiceViewModel();
    }
}

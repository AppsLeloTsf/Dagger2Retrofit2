package com.molitics.molitician.ui.dashboard.userProfile.module;

import com.molitics.molitician.ui.dashboard.userProfile.UserProfileFragment;
import com.molitics.molitician.ui.dashboard.voice.module.CreateVoiceActivityModule;
import com.molitics.molitician.ui.dashboard.voice.module.UserProfileFragmentModule;
import com.molitics.molitician.ui.dashboard.voice.provider.CreateVoiceFragmentModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class UserProfileModule {

    @ContributesAndroidInjector(modules = {UserProfileFragmentModule.class, CreateVoiceFragmentModule.class})
    abstract UserProfileFragment provideUserProfileFragmentFactory();

}

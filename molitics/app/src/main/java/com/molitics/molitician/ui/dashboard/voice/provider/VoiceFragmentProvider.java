package com.molitics.molitician.ui.dashboard.voice.provider;

import com.molitics.molitician.ui.dashboard.leader.leaderModule.LeaderOverviewFragmentModule;
import com.molitics.molitician.ui.dashboard.leader.newleaderprofile.LeaderOverviewFragment;
import com.molitics.molitician.ui.dashboard.login.LoginDialogFragment;
import com.molitics.molitician.ui.dashboard.login.module.LoginDialogModule;
import com.molitics.molitician.ui.dashboard.userProfile.UserProfileFragment;
import com.molitics.molitician.ui.dashboard.voice.HomeFeedFragment;
import com.molitics.molitician.ui.dashboard.voice.VoiceFragment;
import com.molitics.molitician.ui.dashboard.voice.module.FeedFragmentModule;
import com.molitics.molitician.ui.dashboard.voice.module.UserProfileFragmentModule;
import com.molitics.molitician.ui.dashboard.voice.module.VoiceFragmentModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class VoiceFragmentProvider {

    @ContributesAndroidInjector(modules = FeedFragmentModule.class)
    abstract HomeFeedFragment provideFeedFragmentFactory();

    @ContributesAndroidInjector(modules = VoiceFragmentModule.class)
    abstract VoiceFragment provideVoiceFragmentFactory();

    @ContributesAndroidInjector(modules = LeaderOverviewFragmentModule.class)
    abstract LeaderOverviewFragment provideLeaderOverviewFragmentFactory();


    @ContributesAndroidInjector(modules = UserProfileFragmentModule.class)
    abstract UserProfileFragment provideUserProfileFragmentFactory();


    @ContributesAndroidInjector(modules = LoginDialogModule.class)
    abstract LoginDialogFragment provideUserLoginDialogFragmentFactory();

}

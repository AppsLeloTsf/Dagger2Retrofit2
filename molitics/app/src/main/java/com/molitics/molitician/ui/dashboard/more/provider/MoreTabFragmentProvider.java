package com.molitics.molitician.ui.dashboard.more.provider;

import com.molitics.molitician.ui.dashboard.more.MoreTabFragment;
import com.molitics.molitician.ui.dashboard.more.module.MoreTabFragmentModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MoreTabFragmentProvider {

    @ContributesAndroidInjector(modules = MoreTabFragmentModule.class)
    abstract MoreTabFragment provideMoreFragmentFactory();
}

package com.molitics.molitician.ui.dashboard.newsDetail.viewModel;

import com.molitics.molitician.ui.dashboard.login.LoginDialogFragment;
import com.molitics.molitician.ui.dashboard.login.module.LoginDialogModule;
import com.molitics.molitician.ui.dashboard.newsDetail.NewsDetailFragment;
import com.molitics.molitician.ui.dashboard.newsDetail.module.NewsDetailFragmentModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class NewsDetailProvider {

    @ContributesAndroidInjector(modules = NewsDetailFragmentModule.class)
    abstract NewsDetailFragment provideNewsDetailFragment();

    @ContributesAndroidInjector(modules = LoginDialogModule.class)
    abstract LoginDialogFragment provideUserLoginDialogFragmentFactory();
}

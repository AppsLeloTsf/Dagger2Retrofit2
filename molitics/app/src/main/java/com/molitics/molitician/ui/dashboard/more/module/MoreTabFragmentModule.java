package com.molitics.molitician.ui.dashboard.more.module;

import com.molitics.molitician.ui.dashboard.more.viewModel.MoreTabViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class MoreTabFragmentModule {

    @Provides
    MoreTabViewModel provideMoreTabViewModel() {
        return new MoreTabViewModel();
    }
}

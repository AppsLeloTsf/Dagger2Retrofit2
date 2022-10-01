package com.molitics.molitician.ui.dashboard.channels.module

import com.molitics.molitician.ui.dashboard.channels.viewModel.ChannelBankViewModel
import dagger.Module
import dagger.Provides

@Module
class ChannelBankModule {

    @Provides
    fun channelBank(): ChannelBankViewModel = ChannelBankViewModel()
}
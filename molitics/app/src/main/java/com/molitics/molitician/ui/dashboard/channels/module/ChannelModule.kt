package com.molitics.molitician.ui.dashboard.channels.module

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.molitics.molitician.ui.dashboard.channels.ChannelListActivity
import com.molitics.molitician.ui.dashboard.channels.viewModel.ChannelListViewModel
import com.molitics.molitician.ui.dashboard.home.HomeFragment
import dagger.Module
import dagger.Provides


@Module
class ChannelModule {

    @Provides
    fun provideViewModule(): ChannelListViewModel {
        return ChannelListViewModel()
    }

    @Provides
    fun provideManager(activity: ChannelListActivity): LinearLayoutManager {
        return LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
    }
}
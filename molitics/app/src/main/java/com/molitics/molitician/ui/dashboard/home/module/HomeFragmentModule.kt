package com.molitics.molitician.ui.dashboard.home.module

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.molitics.molitician.ui.dashboard.home.HomeFragment
import com.molitics.molitician.ui.dashboard.home.adapter.HomeBrowseAdapter
import com.molitics.molitician.ui.dashboard.home.adapter.TopHomeAdapter
import com.molitics.molitician.ui.dashboard.home.viewModel.HomeViewModel
import dagger.Module
import dagger.Provides

@Module
class HomeFragmentModule {

    @Provides
    fun provideViewModel() = HomeViewModel()

    @Provides
    fun provideHomeTopAdapter() = TopHomeAdapter()

    @Provides
    fun provideLinearLayoutManager(fragment: HomeFragment): LinearLayoutManager {
        return LinearLayoutManager(fragment.context, RecyclerView.HORIZONTAL, false)
    }

    @Provides
    fun provideHomeBrowseAdapter() = HomeBrowseAdapter()


}
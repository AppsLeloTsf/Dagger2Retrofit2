package com.molitics.molitician.ui.dashboard.liveVideo.module

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.molitics.molitician.ui.dashboard.home.adapter.HomeBrowseAdapter
import com.molitics.molitician.ui.dashboard.home.adapter.TopHomeAdapter
import com.molitics.molitician.ui.dashboard.liveVideo.LiveVideoFragment
import com.molitics.molitician.ui.dashboard.liveVideo.videModel.LiveVideoViewModel
import dagger.Module
import dagger.Provides

@Module
class LiveVideoModule {

    @Provides
    fun provideViewModel() = LiveVideoViewModel()

    @Provides
    fun provideHomeTopAdapter() = TopHomeAdapter()

    @Provides
    fun provideLinearLayoutManager(fragment: LiveVideoFragment): LinearLayoutManager {
        return LinearLayoutManager(fragment.context, RecyclerView.HORIZONTAL, false)
    }


    @Provides
    fun provideHomeBrowseAdapter() = HomeBrowseAdapter()

}
package com.molitics.molitician.ui.dashboard.youtubeMoreDetail.module

import androidx.recyclerview.widget.LinearLayoutManager
import com.molitics.molitician.ui.dashboard.youtubeMoreDetail.YoutubeMoreActivity
import com.molitics.molitician.ui.dashboard.youtubeMoreDetail.adapter.YoutubeMoreAdapter
import com.molitics.molitician.ui.dashboard.youtubeMoreDetail.viewModel.YoutubeMoreViewModel
import dagger.Module
import dagger.Provides

@Module
class YoutubeMoreModule {

    @Provides
    fun provideYoutubeViewModule(): YoutubeMoreViewModel {
        return YoutubeMoreViewModel()
    }

    @Provides
    fun provideLinearLayoutManager(activity: YoutubeMoreActivity): LinearLayoutManager {
        return LinearLayoutManager(activity)
    }

    @Provides
    fun provideYoutubeAdapter(activity: YoutubeMoreActivity): YoutubeMoreAdapter {
        return YoutubeMoreAdapter(activity.lifecycle)
    }
}
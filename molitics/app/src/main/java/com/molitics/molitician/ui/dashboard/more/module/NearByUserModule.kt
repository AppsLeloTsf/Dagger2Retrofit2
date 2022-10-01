package com.molitics.molitician.ui.dashboard.userProfile.module

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.molitics.molitician.ui.dashboard.more.NearbyUserFragment
import com.molitics.molitician.ui.dashboard.more.adapter.ContactListAdapter
import com.molitics.molitician.ui.dashboard.more.viewModel.NearByUserViewModel
import dagger.Module
import dagger.Provides

@Module
class NearByUserModule {

    @Provides
    internal fun provideNearByUserViewModel(): NearByUserViewModel {
        return NearByUserViewModel()
    }

    @Provides
    internal fun provideLinearLayoutManager(context: NearbyUserFragment): GridLayoutManager {
        return GridLayoutManager(context.context,2)
    }


    @Provides
    internal fun provideContactAdapter(): ContactListAdapter {
        return ContactListAdapter()
    }
}
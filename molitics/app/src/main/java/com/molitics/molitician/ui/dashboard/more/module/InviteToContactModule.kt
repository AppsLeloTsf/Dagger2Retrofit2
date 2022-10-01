package com.molitics.molitician.ui.dashboard.more.module

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.molitics.molitician.ui.dashboard.more.InviteToContactActivity
import com.molitics.molitician.ui.dashboard.more.adapter.ContactListAdapter
import com.molitics.molitician.ui.dashboard.more.viewModel.InviteToContactViewModel
import com.molitics.molitician.ui.dashboard.more.viewModel.NearByUserViewModel
import dagger.Module
import dagger.Provides

@Module
class InviteToContactModule {

    @Provides
    internal fun provideInviteToContactViewModel(): InviteToContactViewModel {
        return InviteToContactViewModel()
    }

    @Provides
    internal fun provideLinearLayoutManager(context: InviteToContactActivity): GridLayoutManager {
        return GridLayoutManager(context,2)
    }


    @Provides
    internal fun provideContactAdapter(): ContactListAdapter {
        return ContactListAdapter()
    }
}
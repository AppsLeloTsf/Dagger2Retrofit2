package com.molitics.molitician.ui.dashboard.register.location.provider

import androidx.recyclerview.widget.LinearLayoutManager
import com.molitics.molitician.MolticsApplication
import com.molitics.molitician.base.MyViewModelFactory
import com.molitics.molitician.ui.dashboard.register.location.LocationFragment
import com.molitics.molitician.ui.dashboard.register.location.adapter.LocationAdapter
import com.molitics.molitician.ui.dashboard.register.register.RegisterRepo
import com.molitics.molitician.ui.dashboard.register.register.viewModel.RegisterViewModel
import dagger.Module
import dagger.Provides

@Module
class LocationFragmentProvider {

    @Provides
    fun locationRepo() = RegisterRepo()

    @Provides
    fun locationViewModel(application: MolticsApplication, locationRepo: RegisterRepo) = RegisterViewModel(application, locationRepo)

    @Provides
    fun locationFactory(viewModel: RegisterViewModel) = MyViewModelFactory(viewModel)

    @Provides
    fun locationAdapter(fragment: LocationFragment) = LocationAdapter(fragment)

    @Provides
    fun locationLinearLayout(fragment: LocationFragment) = LinearLayoutManager(fragment.requireContext())
}
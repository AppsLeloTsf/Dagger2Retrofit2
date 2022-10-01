package com.molitics.molitician.ui.dashboard.register.location.provider

import androidx.recyclerview.widget.LinearLayoutManager
import com.molitics.molitician.MolticsApplication
import com.molitics.molitician.base.MyViewModelFactory
import com.molitics.molitician.ui.dashboard.register.location.DistrictLocationFragment
import com.molitics.molitician.ui.dashboard.register.location.adapter.DistrictLocationAdapter
import com.molitics.molitician.ui.dashboard.register.register.RegisterRepo
import com.molitics.molitician.ui.dashboard.register.register.viewModel.RegisterViewModel
import dagger.Module
import dagger.Provides

@Module
class DistrictLocationFragmentProvider {

    @Provides
    fun districtLocationRepo() = RegisterRepo()

    @Provides
    fun districtLocationViewModel(application: MolticsApplication, locationRepo: RegisterRepo) = RegisterViewModel(application, locationRepo)

    @Provides
    fun districtLocationFactory(viewModel: RegisterViewModel) = MyViewModelFactory(viewModel)

    @Provides
    fun districtLocationAdapter(fragment: DistrictLocationFragment) = DistrictLocationAdapter(fragment)

    @Provides
    fun districtLocationLinearLayout(fragment: DistrictLocationFragment) = LinearLayoutManager(fragment.requireContext())
}
package com.molitics.molitician.ui.dashboard.home.viewModel

import androidx.databinding.ObservableArrayList
import com.molitics.molitician.BaseViewModel
import com.molitics.molitician.ui.dashboard.home.interfacess.HomeNavigation
import com.molitics.molitician.ui.dashboard.home.model.HomeBrowseModel
import com.molitics.molitician.ui.dashboard.home.repo.HomeFragmentRepo

class HomeViewModel : BaseViewModel<HomeNavigation>() {

    val homeTopRepo = HomeFragmentRepo()

    var browseList = ObservableArrayList<HomeBrowseModel>()

    init {
        homeTopRepo.getBrowseData()
        browseList = homeTopRepo.getBrowseList()
    }
}
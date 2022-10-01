package com.molitics.molitician.ui.dashboard.liveVideo.videModel

import androidx.lifecycle.LiveData
import com.molitics.molitician.BaseViewModel
import com.molitics.molitician.model.News
import com.molitics.molitician.model.NewsVideoModel
import com.molitics.molitician.ui.dashboard.liveVideo.interfacess.LiveVideoNavigation
import com.molitics.molitician.ui.dashboard.liveVideo.repo.LiveVideoRepo
import com.molitics.molitician.util.Constant
import com.molitics.molitician.util.PrefUtil

class LiveVideoViewModel : BaseViewModel<LiveVideoNavigation>() {

    /// private var liveVideoRepo
    val liveVideoRepo = LiveVideoRepo()
    var page: Int = 0
    var stateId = PrefUtil.getInt(Constant.PreferenceKey.DEFAULT_STATE_VALUE)


    var nationalVideoRepo: LiveData<MutableList<NewsVideoModel>>
    var localVideoRepo: LiveData<MutableList<NewsVideoModel>>

    var moliticsVideoRepo: LiveData<MutableList<News>>

    init {
        nationalVideoRepo = liveVideoRepo.getNationalVideo()
        localVideoRepo = liveVideoRepo.getLocalVideo()
        moliticsVideoRepo = liveVideoRepo.getMoliticsVideo()
    }


    fun getData() {
        page++
        setIsLoading(true)
        liveVideoRepo.getLiveDataRequest(compositeDisposable, page, stateId)
    }

}
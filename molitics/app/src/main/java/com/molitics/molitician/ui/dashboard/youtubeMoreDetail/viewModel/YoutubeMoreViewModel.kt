package com.molitics.molitician.ui.dashboard.youtubeMoreDetail.viewModel

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import com.molitics.molitician.BaseViewModel
import com.molitics.molitician.httpapi.RetrofitRestClient
import com.molitics.molitician.model.APIResponse
import com.molitics.molitician.model.NewsVideoModel
import com.molitics.molitician.ui.dashboard.youtubeMoreDetail.YoutubeMoreNavigation
import com.molitics.molitician.util.Util
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class YoutubeMoreViewModel : BaseViewModel<YoutubeMoreNavigation>() {
    private val TAG = "YoutubeMoreViewModel"
    val youtubeObsList: ObservableArrayList<NewsVideoModel> = ObservableArrayList()
    val youtubeLiveList: MutableLiveData<List<NewsVideoModel>> = MutableLiveData()
    var page: Int = 1

    fun setYoutubeList(youList: List<NewsVideoModel>) {
        youtubeObsList.addAll(youList)
    }

    fun getVideoListFromServer(sendDataBody: SendDataBody) {
        setIsLoading(true)
        getYoutubeObservable(page, sendDataBody)?.subscribe(
                { apiResponse ->
                    setIsLoading(false)
                    apiResponse?.data.let {
                        page++
                        youtubeLiveList.value = apiResponse?.data?.news_video
                    }
                },
                { error ->
                    Util.showLog(TAG, error.message ?: "onUnknownError")
                }
        )?.let {
            compositeDisposable.add(
                    it)
        }
    }

    private fun getYoutubeObservable(page: Int, sendDataBody: SendDataBody): Single<APIResponse?>? {
        return RetrofitRestClient.instance
                ?.getYoutubeVideos(page, sendDataBody)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
    }
}

data class SendDataBody(val id: Int, val screenType: String, val candidateId: Int = 0, val trendingId: Int = 0)
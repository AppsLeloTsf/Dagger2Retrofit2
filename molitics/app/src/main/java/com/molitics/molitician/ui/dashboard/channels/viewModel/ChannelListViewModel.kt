package com.molitics.molitician.ui.dashboard.channels.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.molitics.molitician.BaseViewModel
import com.molitics.molitician.httpapi.RetrofitRestClient
import com.molitics.molitician.ui.dashboard.BaseNavigation
import com.molitics.molitician.ui.dashboard.hotTopics.model.HotTopicModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ChannelListViewModel : BaseViewModel<BaseNavigation>() {

    val channelList = MutableLiveData<List<HotTopicModel>>()
    var pageNo = 1

    fun getChannelsListRequest(channelName: String) {
        setIsLoading(true)
        compositeDisposable.add(RetrofitRestClient.instance?.getChannelList(pageNo, channelName)!!
                !!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ apiResponse ->
                    setIsLoading(false)

                    channelList.value = apiResponse?.data?.channelDataModel?.channels
                    pageNo++
                }, { error ->
                    setIsLoading(false)
                }))
    }

    public fun followRequest(channelId: Int, action: Int) {
        compositeDisposable.add(RetrofitRestClient.instance?.followChannel(channelId, action)!!
                !!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ apiResponse ->
                    channelList.value?.find { it.id == channelId }?.isFollow = apiResponse?.data?.isChannelFollowing == true
                    //   isFollowing.set(apiResponse.data.isChannelFollowing)
                }, { error ->

                }))
    }

}
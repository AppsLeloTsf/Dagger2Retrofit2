package com.molitics.molitician.ui.dashboard.channels.viewModel

import android.view.View
import android.widget.TextView
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.molitics.molitician.BaseViewModel
import com.molitics.molitician.MolticsApplication
import com.molitics.molitician.R
import com.molitics.molitician.httpapi.RetrofitRestClient
import com.molitics.molitician.model.News
import com.molitics.molitician.ui.dashboard.BaseNavigation
import com.molitics.molitician.util.Constant
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ChannelBankViewModel : BaseViewModel<BaseNavigation>() {


    val channelNews = MutableLiveData<List<News>>()
    val isFollowing = ObservableField<Boolean>(false)
    var pageNo = 1
    var type = "news"
    var channelId = 0
    var errorText = ObservableField<String>("")

    fun getChannelsNewsRequest() {
        setIsLoading(true)
        compositeDisposable.add(RetrofitRestClient.instance?.getChannelNews(channelId, pageNo, type)
                !!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ apiResponse ->
                    setIsLoading(false)

                    apiResponse?.data?.channelsNews?.data?.let { channelNews.value = it }

                    isFollowing.set(apiResponse?.data?.sourceDetailModel?.isFollow)

                    if ((apiResponse?.data?.channelsNews?.data == null || apiResponse.data?.channelsNews?.data!!.isEmpty()) && pageNo == 1) {
                        errorText.set(MolticsApplication.getAppContaxt().getString(R.string.no_data_available))
                    } else errorText.set("")

                    pageNo++
                }, { error ->
                    setIsLoading(false)
                }))
    }

    private fun followRequest(action: Int) {

        compositeDisposable.add(RetrofitRestClient.instance?.followChannel(channelId, action)!!
                !!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ apiResponse ->
                    isFollowing.set(apiResponse?.data?.isChannelFollowing)
                }, { error ->

                }))
    }


    fun onFollowChannelClick(view: View) {
        val action = if (isFollowing.get() == true) 0   // 1 for follow
        else 1 // 0 for unfollow

        followRequest(action)
    }
}
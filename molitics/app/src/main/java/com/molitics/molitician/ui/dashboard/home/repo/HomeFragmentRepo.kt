package com.molitics.molitician.ui.dashboard.home.repo

import android.annotation.SuppressLint
import androidx.databinding.ObservableArrayList
import com.molitics.molitician.MolticsApplication
import com.molitics.molitician.R
import com.molitics.molitician.database.Database
import com.molitics.molitician.httpapi.RetrofitRestClient
import com.molitics.molitician.ui.dashboard.home.model.HomeBrowseModel
import com.molitics.molitician.util.Constant.HomeBrowseItem.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragmentRepo {

    val browseItemList = ObservableArrayList<HomeBrowseModel>()

    public fun getBrowseList(): ObservableArrayList<HomeBrowseModel> = browseItemList

    fun getBrowseData() {
        GlobalScope.launch(Dispatchers.Main) {

            val leadersList: MutableList<HomeBrowseModel> = withContext(Dispatchers.Default) { getTrendingLeaderDatabase() }
            leadersList.add(HomeBrowseModel(MolticsApplication.getAppContaxt().getString(R.string.molitics_tv), "https://molitics.s3.amazonaws.com/images/news_original/6455_48_1567498011.jpg", LIVE_TV_type))
            leadersList.add(HomeBrowseModel(MolticsApplication.getAppContaxt().getString(R.string.caricature), "https://s3-ap-southeast-1.amazonaws.com/molitics/images/cartoons/791_1567503463.jpeg", CARICATURE_TYPE))
            leadersList.add(HomeBrowseModel(MolticsApplication.getAppContaxt().getString(R.string.election_result), "", ELECTION_RESULT_TYPE))
            leadersList.add(HomeBrowseModel(MolticsApplication.getAppContaxt().getString(R.string.near_by), "", NEARBY_USER_TYPE))
            browseItemList.addAll(leadersList)
            getBrowseDataFromServer()
        }
    }

    @SuppressLint("CheckResult")
    fun getBrowseDataFromServer() {
        RetrofitRestClient.instance?.browseList!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ apiResponse ->
                    browseItemList.clear()
                    apiResponse?.data?.hometabsModel?.let {
                        browseItemList.addAll(it)
                    }
                }, { error ->
                    //   setIsLoading(false)
                    //     Util.showLog(TAG, error.message)
                })
    }

    private suspend fun getTrendingLeaderDatabase(): MutableList<HomeBrowseModel> = Database.getHomeBrowseSinlgeEntry()

}
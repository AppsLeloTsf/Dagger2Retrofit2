package com.molitics.molitician.ui.dashboard.liveVideo.repo

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.molitics.molitician.httpapi.RetrofitRestClient
import com.molitics.molitician.model.News
import com.molitics.molitician.model.NewsVideoModel
import com.molitics.molitician.util.Util
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class LiveVideoRepo {
    val TAG = "LiveVideoRepo"


    val nationalVideoRepo = MutableLiveData<MutableList<NewsVideoModel>>()
    val localVideoRepo = MutableLiveData<MutableList<NewsVideoModel>>()
    val moliticsVideoRepo = MutableLiveData<MutableList<News>>()


    public fun getNationalVideo(): LiveData<MutableList<NewsVideoModel>> = nationalVideoRepo
    public fun getLocalVideo(): LiveData<MutableList<NewsVideoModel>> = localVideoRepo
    public fun getMoliticsVideo(): LiveData<MutableList<News>> = moliticsVideoRepo

    @SuppressLint("CheckResult")
    fun getLiveDataRequest(compositeDisposable: CompositeDisposable, page: Int, stateId: Int) {
        compositeDisposable.add(RetrofitRestClient.instance?.getLiveVideos(page, stateId)!!
                !!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ apiResponse ->
                    if (page == 1) {
                        nationalVideoRepo.value = apiResponse?.data?.nationalLiveVideos
                        localVideoRepo.value = apiResponse?.data?.localLiveVideos
                    }
                    moliticsVideoRepo.value = apiResponse?.data?.moliticsVideoModel?.moliticsLiveVideos
                }, { error ->

                    Util.showLog(TAG, error.message)
                }))
    }
}
package com.molitics.molitician.ui.dashboard.more.viewModel

import android.annotation.SuppressLint
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.molitics.molitician.BaseViewModel
import com.molitics.molitician.error.ApiException
import com.molitics.molitician.httpapi.RetrofitRestClient
import com.molitics.molitician.rxJava.BaseRxView
import com.molitics.molitician.ui.dashboard.more.interfacess.NearbyUserNavigagtion
import com.molitics.molitician.ui.dashboard.more.model.MyContactListModel
import com.molitics.molitician.util.Util
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class NearByUserViewModel : BaseViewModel<NearbyUserNavigagtion>(), BaseRxView {

    val TAG = "NearByUserViewModel"

    val contactItemViewModels = ObservableArrayList<MyContactListModel>()
    val message = ObservableField<String>()

    val contactLiveData: MutableLiveData<List<MyContactListModel>> = MutableLiveData()

    var pageCount: Int = 1

    fun addContactToList(contactList: List<MyContactListModel>) {
        contactItemViewModels.addAll(contactList)
    }

    @SuppressLint("CheckResult")
    fun getContactFromServer(pageNo: Int, search: String, type: Int) {
        setIsLoading(true)
        RetrofitRestClient.instance?.getNearByUser(pageNo, search, type)!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ apiResponse ->
                    setIsLoading(false)
                    apiResponse?.data?.let {
                        if (apiResponse?.data.current_page == 1 && apiResponse.data?.nearByUser?.isEmpty() == true) {
                            contactItemViewModels.clear()
                            message.set("No Data Found")
                        } else {
                            message.set("")
                            if (pageNo == 1) {
                                contactItemViewModels.clear()
                            }
                            contactLiveData.value = apiResponse.data?.nearByUser
                            ++pageCount
                            navigator.onContactSyncComplete()
                        }
                    } ?: run {
                        apiResponse?.message?.let {

                            message.set(it)
                        }
                    }
                }, { error ->
                    setIsLoading(false)
                    Util.showLog(TAG, error.message)
                })
    }


    override fun showLoading(message: String?) {
    }

    override fun onApiError(error: ApiException?) {

    }

    override fun onUnknownError(error: String?) {
    }

    override fun onTimeout() {
    }

    override fun onRequestComplete() {
    }

    override fun onNetworkError() {
    }
}
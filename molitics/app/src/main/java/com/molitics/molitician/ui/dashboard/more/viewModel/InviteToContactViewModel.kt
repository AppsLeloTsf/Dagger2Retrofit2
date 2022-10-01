package com.molitics.molitician.ui.dashboard.more.viewModel

import android.annotation.SuppressLint
import android.content.Context
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.molitics.molitician.BaseViewModel
import com.molitics.molitician.R
import com.molitics.molitician.error.ApiException
import com.molitics.molitician.httpapi.RetrofitRestClient
import com.molitics.molitician.model.APIResponse
import com.molitics.molitician.rxJava.BaseRxView
import com.molitics.molitician.rxJava.CallbackWrapper
import com.molitics.molitician.ui.dashboard.more.interfacess.InviteToContactNavigation
import com.molitics.molitician.ui.dashboard.more.model.MyContactListModel
import com.molitics.molitician.util.RxContactsGet
import com.molitics.molitician.util.Util
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody

class InviteToContactViewModel : BaseViewModel<InviteToContactNavigation>(), BaseRxView {

    val TAG = "InviteToContactViewModel"

    val contactItemViewModels = ObservableArrayList<MyContactListModel>()
    val message = ObservableField<String>()

    val contactLiveData: MutableLiveData<List<MyContactListModel>> = MutableLiveData()

    var pageCount: Int = 1

    fun addContactToList(contactList: List<MyContactListModel>) {
        contactItemViewModels.addAll(contactList)
    }

    fun syncContactWithServer(context: Context) {
        setIsLoading(true)
        RxContactsGet.fetch(context).subscribe { contactFile ->
            val reqFile = RequestBody.create(MediaType.parse("text/*"), contactFile)
            val body = MultipartBody.Part.createFormData("file", contactFile.name, reqFile)
            compositeDisposable.addAll(RetrofitRestClient.instance?.exportContactRx(body)!!
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : CallbackWrapper<APIResponse>(this) {
                        override fun onSuccess(t: APIResponse?) {
                            getContactFromServer(pageCount, "")
                        }
                    }))
        }
    }

    @SuppressLint("CheckResult")
    fun getContactFromServer(pageNo: Int, search: String) {
        setIsLoading(true)
        RetrofitRestClient.instance?.getContacts(pageNo, search)!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ apiResponse ->
                    setIsLoading(false)
                    if (apiResponse?.data?.current_page == 1 && apiResponse?.data?.myContactListModels?.isEmpty() == true) {
                        if (apiResponse.data.current_page == 1) {
                            contactItemViewModels.clear()
                        }
                        message.set("No Data Found")
                    } else {
                        message.set("")
                        if (apiResponse?.data?.current_page == 1) {
                            contactItemViewModels.clear()
                        }
                        contactLiveData.value = apiResponse?.data?.myContactListModels
                        ++pageCount
                        navigator.onContactSyncComplete()
                    }
                }, { error ->
                    setIsLoading(false)
                    Util.showLog(TAG, error.message)
                })
    }


    override fun showLoading(message: String?) {
    }

    override fun onApiError(error: ApiException?) {
        setIsLoading(false)
        Util.showLog(TAG, error?.message)
        if (error!!.code == 500) {
            message.set("Something went wrong")
        } else {
            message.set(error.message)
        }
    }

    override fun onUnknownError(error: String?) {
        setIsLoading(false)
        message.set("Something went wrong")
    }

    override fun onTimeout() {
    }

    override fun onRequestComplete() {
    }

    override fun onNetworkError() {
    }

}
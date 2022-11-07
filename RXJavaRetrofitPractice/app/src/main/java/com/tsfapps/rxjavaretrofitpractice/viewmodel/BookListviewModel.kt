package com.tsfapps.rxjavaretrofitpractice.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tsfapps.rxjavaretrofitpractice.network.BookListModel
import com.tsfapps.rxjavaretrofitpractice.network.RetroInstance
import com.tsfapps.rxjavaretrofitpractice.network.RetroService
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class BookListviewModel: ViewModel() {
    var bookList: MutableLiveData<BookListModel> = MutableLiveData()

    fun getBookListObserver(): MutableLiveData<BookListModel>{
        return bookList
    }
    fun makeApiCall(query: String){
       val retroInstance = RetroInstance.getRetroInstance().create(RetroService::class.java)
        retroInstance.getBookListFromApi(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getBookListObserverRx())
    }

    private fun getBookListObserverRx(): Observer<BookListModel> {
        return object :Observer<BookListModel>{
            override fun onSubscribe(d: Disposable) {
               //progressbar visible
            }

            override fun onError(e: Throwable) {
                bookList.postValue(null)
            }

            override fun onComplete() {
                //progressbar hide
            }

            override fun onNext(t: BookListModel) {
                bookList.postValue(t)
            }

        }
    }
}
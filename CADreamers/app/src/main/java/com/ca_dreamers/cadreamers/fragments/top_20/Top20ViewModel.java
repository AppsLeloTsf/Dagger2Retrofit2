package com.ca_dreamers.cadreamers.ui.top_20;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Top20ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public Top20ViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
package com.molitics.molitician.interfaces;


import com.molitics.molitician.network.NetworkChangeReceiver;

/**
 * Created by rahul on 7/12/2016.
 */
public interface ViewRefreshListener extends NetworkChangeReceiver.NetworkCallback{

    void onRefereshClick();
}

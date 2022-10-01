package com.molitics.molitician.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by rahul on 7/12/2016.
 */
public class NetworkChangeReceiver {

    public interface NetworkCallback {
        void onNetworkAvailable();
    }

    public static void setmNetworkCallback(NetworkCallback mNetworkCallback) {
        NetworkChangeReceiver.mNetworkCallback = mNetworkCallback;
    }

    private static NetworkCallback mNetworkCallback;

    public static class ConnectivityChange extends BroadcastReceiver {

        public void onReceive(Context context, Intent intent) {

            if (NetworkUtil.isNetworkAvailable(context) && mNetworkCallback != null)
                mNetworkCallback.onNetworkAvailable();
        }
    }

}

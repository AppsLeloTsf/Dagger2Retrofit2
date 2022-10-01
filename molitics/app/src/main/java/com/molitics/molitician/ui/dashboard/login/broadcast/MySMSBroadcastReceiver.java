package com.molitics.molitician.ui.dashboard.login.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Status;
import com.molitics.molitician.util.Constant;

public class MySMSBroadcastReceiver extends BroadcastReceiver {
    Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        if (SmsRetriever.SMS_RETRIEVED_ACTION.equals(intent.getAction())) {
            Bundle extras = intent.getExtras();
            Status status = (Status) extras.get(SmsRetriever.EXTRA_STATUS);

            switch (status.getStatusCode()) {
                case CommonStatusCodes.SUCCESS:
                    // Get SMS message contents
                    String message = (String) extras.get(SmsRetriever.EXTRA_SMS_MESSAGE);
                    // Extract one-time code from the message and complete verification
                    // by sending the code back to your server.
                    sendBroadcast(true, message);
                    break;
                case CommonStatusCodes.TIMEOUT:
                    // Waiting for SMS timed out (5 minutes)
                    // Handle the error ...
                    sendBroadcast(false, "");

                    break;
            }
        }
    }

    private void sendBroadcast(boolean success, String message) {

        Intent intent = new Intent(Constant.OTP_BROADCAST); //put the same message as in the filter you used in the activity when registering the receiver
        intent.putExtra(Constant.IntentKey.RESPONSE, success);
        intent.putExtra(Constant.IntentKey.OTP, message);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
    }

}

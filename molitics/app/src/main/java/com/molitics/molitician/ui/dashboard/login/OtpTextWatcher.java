package com.molitics.molitician.ui.dashboard.login;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.molitics.molitician.R;

/**
 * Created by rahul on 6/2/2017.
 */

public class OtpTextWatcher implements TextWatcher {
    private View view;
    private View nextView;
    private View preview;

    public OtpTextWatcher(View view, View nextView, View preview) {
        this.view = view;
        this.nextView = nextView;
        this.preview = preview;
    }

    @Override
    public void afterTextChanged(Editable editable) {
        // TODO Auto-generated method stub
        String text = editable.toString();

        if (text.length() == 1 && nextView != null) {
            nextView.requestFocus();
        }
        if (nextView != null) {
            switch (view.getId()) {

                case R.id.otp1:
                    if (text.length() == 1)
                        nextView.requestFocus();
                    else
                        preview.requestFocus();
                    break;
                case R.id.otp2:
                    if (text.length() == 1)
                        nextView.requestFocus();
                    else
                        preview.requestFocus();
                    break;
                case R.id.otp3:
                    if (text.length() == 1)
                        nextView.requestFocus();
                    else
                        preview.requestFocus();
                    break;
                case R.id.otp4:
                    if (text.length() == 1)
                        nextView.requestFocus();
                    else preview.requestFocus();
                    break;
                case R.id.otp5:
                    if (text.length() == 1)
                        nextView.requestFocus();
                    else preview.requestFocus();
                    break;
                case R.id.otp6:
                    if (text.length() != 1)
                        preview.requestFocus();

                    break;
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
        // TODO Auto-generated method stub
    }
}


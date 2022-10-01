package com.molitics.molitician.UiMediator;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;

import com.molitics.molitician.R;

/**
 * Created by lenovo on 12/14/2016.
 */

public class MyDialog extends Dialog {

    Activity ctx;

    public MyDialog(Activity ctx) {
        super(ctx);
        this.ctx = ctx;

    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.progress_dialog_layout);


        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    }
}
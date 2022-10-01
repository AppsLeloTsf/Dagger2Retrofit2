package com.molitics.molitician.util;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.molitics.molitician.R;
import com.molitics.molitician.interfaces.MCustomAlertInterface;

/**
 * Created by rahul on 3/8/2017.
 */
public class CustomAlert extends DialogFragment {
    private static final String EXTRA_ALERT_ID = "extra_alert_id";
    private static final String EXTRA_TITLE = "extra_title";
    private static final String EXTRA_MESSAGE = "extra_message";
    private static final String EXTRA_POSITIVE_BUTTON_TEXT = "extra_positive_button_text";
    private static final String EXTRA_NEGATIVE_BUTTON_TEXT = "extra_negative_button_text";
    MCustomAlertInterface mListener;

    /**
     * @param
     * @param
     * @return
     */

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MCustomAlertInterface) {
            mListener = (MCustomAlertInterface) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement MCustomAlertInterface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void show(FragmentActivity pFragmentActivity, String pDialogTag) {
        FragmentManager supportFragmentManager = pFragmentActivity.getSupportFragmentManager();
        Bundle args = new Bundle();
        args.putInt(EXTRA_ALERT_ID, alertId);
        args.putString(EXTRA_TITLE, title);
        args.putString(EXTRA_MESSAGE, message);
        args.putString(EXTRA_POSITIVE_BUTTON_TEXT, positiveButton);
        args.putString(EXTRA_NEGATIVE_BUTTON_TEXT, negativeButton);
        setArguments(args);
        show(supportFragmentManager, pDialogTag);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove title and frame from dialog-fragment
        setStyle(STYLE_NO_TITLE, 0);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View dialogRootView = inflater.inflate(R.layout.dialog_custom_alert, container, false);

        Bundle bundle = getArguments();

        assert bundle != null;
        String title = bundle.getString(EXTRA_TITLE);
        if (StringUtils.isNullOrEmpty(title)) {
            dialogRootView.findViewById(R.id.txv_alert_title).setVisibility(View.GONE);
        } else {
            ((TextView) dialogRootView.findViewById(R.id.txv_alert_title)).setText(title);
        }

        final String message = bundle.getString(EXTRA_MESSAGE);
        if (StringUtils.isNullOrEmpty(message)) {
            dialogRootView.findViewById(R.id.txv_alert_message).setVisibility(View.GONE);
        } else {
            ((TextView) dialogRootView.findViewById(R.id.txv_alert_message)).setText(message);
        }

        final Button btnPositive = dialogRootView.findViewById(R.id.btn_positive);
        String positiveButtonText = bundle.getString(EXTRA_POSITIVE_BUTTON_TEXT);
        if (StringUtils.isNullOrEmpty(positiveButtonText)) {
            btnPositive.setText(android.R.string.ok);
        } else {
            btnPositive.setText(positiveButtonText);
        }

        btnPositive.setOnClickListener(v -> {
            mListener.onPositiveAlertClick(tabType);
            dismiss();
        });
        Button btnNegative = dialogRootView.findViewById(R.id.btn_negative);
        String negativeButtonText = bundle.getString(EXTRA_NEGATIVE_BUTTON_TEXT);
        if (StringUtils.isNullOrEmpty(negativeButtonText)) {
            btnNegative.setVisibility(View.GONE);
        } else {
            btnNegative.setVisibility(View.VISIBLE);
            btnNegative.setText(negativeButtonText);
        }

        btnNegative.setOnClickListener(v -> {
            mListener.onNegativeAlertClick(tabType);
            dismiss();
        });

        return dialogRootView;
    }

    /**
     * Dependencies should not be in constructor
     */
    private int alertId;
    private String title;
    private String message;
    private String positiveButton;
    private String negativeButton;
    private String tabType;


    /**
     * @param alertId the alertId to set
     * @return this to allow method chaining
     */
    public CustomAlert setAlertId(int alertId) {
        this.alertId = alertId;
        return this;
    }

    /**
     * @param title the title to set
     * @return this to allow method chaining
     */
    public CustomAlert setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * @param message the message to set
     * @return this to allow method chaining
     */
    public CustomAlert setMessage(String message) {
        this.message = message;
        return this;
    }


    public CustomAlert setTabType(String tabType) {
        this.tabType = tabType;
        return this;
    }

    /**
     * @param positiveButton the positiveButton to set
     * @return this to allow method chaining
     */
    public CustomAlert setPositiveButton(String positiveButton) {
        this.positiveButton = positiveButton;
        return this;
    }


    /**
     * @param negativeButton the negativeButton to set
     * @return this to allow method chaining
     */
    public CustomAlert setNegativeButton(String negativeButton) {
        this.negativeButton = negativeButton;
        return this;
    }
}
package com.molitics.molitician.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.AppCompatRadioButton;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.molitics.molitician.R;

public class ThemeUtils {


    public static void setWhiteText(Context mContext, View mView) {
        if (mView instanceof TextView) {
            ((TextView) mView).setTextColor(ContextCompat.getColor(mContext,R.color.hard_white));
        } else if (mView instanceof RadioButton) {
            ((AppCompatRadioButton) mView).setTextColor(ContextCompat.getColor(mContext,R.color.hard_white));
            /// ((RadioButton) mView).setButtonTintList(getRadioList());
            mView.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(mContext, R.color.hard_white)));
            ((AppCompatRadioButton) mView).setHighlightColor(ContextCompat.getColor(mContext,R.color.hard_white));
        }
    }

    public static ColorStateList getRadioList() {
        return new ColorStateList(
                new int[][]{

                        new int[]{-android.R.attr.state_enabled}, //disabled
                        new int[]{android.R.attr.state_enabled} //enabled
                },
                new int[]{
                        Color.WHITE //disabled
                        , Color.BLUE //enabled
                }
        );
    }
}

package com.molitics.molitician.ui.dashboard.commanviews.textviews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * Created by rohitkumar on 10/18/16.
 */
public class TextRobotoRegular extends AppCompatTextView {
    public TextRobotoRegular(Context context) {
        super(context);

        applyCustomFont(context);
    }

    public TextRobotoRegular(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context);
    }

    public TextRobotoRegular(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = FontCache.getTypeface("fonts/roboto_regular.ttf", context);
        setTypeface(customFont);
    }

}

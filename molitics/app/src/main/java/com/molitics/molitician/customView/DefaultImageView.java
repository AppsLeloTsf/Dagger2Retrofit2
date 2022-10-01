package com.molitics.molitician.customView;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.molitics.molitician.R;

/**
 * Created by rahul on 25/06/18.
 */

public class DefaultImageView extends androidx.appcompat.widget.AppCompatImageView{


    public DefaultImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBackgroundResource(R.drawable.image_placeholder);
    }

    @Override
    public void setBackground(Drawable background) {
        super.setBackground(background);

    }
}

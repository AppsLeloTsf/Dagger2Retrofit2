package com.molitics.molitician.customView;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.molitics.molitician.R;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.PrefUtil;

/**
 * Created by rahul on 26/06/18.
 */

public class VolumeImageView extends androidx.appcompat.widget.AppCompatImageView {
    public VolumeImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        if (PrefUtil.getBoolean(Constant.PreferenceKey.VIDEO_MUTE)) {
            setImageResource(R.drawable.mute_white);
        } else {
            setImageResource(R.drawable.volume_white);
        }
    }
}

package com.molitics.molitician.customView;

import android.text.TextPaint;
import android.text.style.URLSpan;

/**
 * Created by rahul on 05/06/18.
 */

public class URLSpanNoUnderline extends URLSpan {

    public URLSpanNoUnderline(String url) {
        super(url);
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setUnderlineText(false);
    }
}
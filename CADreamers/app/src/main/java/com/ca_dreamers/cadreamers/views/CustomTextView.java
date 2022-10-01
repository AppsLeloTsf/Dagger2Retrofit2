package com.cadreamrs.views;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.cadreamrs.common.CustomFontHelper;


public class CustomTextView extends AppCompatTextView {

  public CustomTextView(Context context) {
    super(context);
  }

  public CustomTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
    CustomFontHelper.setCustomTextViewFont(this, context, attrs);
  }

  public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    CustomFontHelper.setCustomTextViewFont(this, context, attrs);
  }
}

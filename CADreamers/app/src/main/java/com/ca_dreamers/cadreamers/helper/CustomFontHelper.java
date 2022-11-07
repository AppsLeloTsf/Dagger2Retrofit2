package com.ca_dreamers.cadreamers.helper;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import com.ca_dreamers.cadreamers.R;


public class CustomFontHelper {

  public static void setCustomFont(Button button, Context context, AttributeSet attrs) {
    TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomFont);
    String font = a.getString(R.styleable.CustomFont_fontName);
    setCustomFont(button, font, context);
    a.recycle();
  }


  public static void setCustomFont(Button button, String font, Context context) {
    if (font == null) {
      return;
    }
    Typeface tf = FontCache.get(font, context);
    if (tf != null) {
      button.setTypeface(tf);
    }
  }

  public static void setCustomTextViewFont(TextView textView, Context context, AttributeSet attrs) {
    TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomFont);
    String font = a.getString(R.styleable.CustomFont_fontName);
    setCustomTextViewFont(textView, font, context);
    a.recycle();
  }


  public static void setCustomTextViewFont(TextView textview, String font, Context context) {
    if (font == null) {
      return;
    }
    Typeface tf = FontCache.get(font, context);
    if (tf != null) {
      textview.setTypeface(tf);
    }
  }

  public static void setEditTextCustomFont(EditText editText, Context context, AttributeSet attrs) {
    TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomFont);
    String font = a.getString(R.styleable.CustomFont_fontName);
    setEditTextCustomFont(editText, font, context);
    a.recycle();
  }


  public static void setEditTextCustomFont(EditText editText, String font, Context context) {
    if (font == null) {
      return;
    }
    Typeface tf = FontCache.get(font, context);
    if (tf != null) {
      editText.setTypeface(tf);
    }
  }

  public static void setAutoCompTextViewCustomFont(AutoCompleteTextView autoCompleteTextView, Context context, AttributeSet attrs) {
    TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomFont);
    String font = a.getString(R.styleable.CustomFont_fontName);
    setAutoCompTextViewCustomFont(autoCompleteTextView, font, context);
    a.recycle();
  }
  public static void setMultiAutoCompTextViewCustomFont(MultiAutoCompleteTextView multiAutoCompleteTextView, Context context, AttributeSet attrs) {
    TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomFont);
    String font = a.getString(R.styleable.CustomFont_fontName);
    setMultiAutoCompTextViewCustomFont(multiAutoCompleteTextView, font, context);
    a.recycle();
  }

  public static void setMultiAutoCompTextViewCustomFont(MultiAutoCompleteTextView multiAutoCompleteTextView, String font, Context context) {
    if (font == null) {
      return;
    }
    Typeface tf = FontCache.get(font, context);
    if (tf != null) {
      multiAutoCompleteTextView.setTypeface(tf);
    }
  }


  public static void setAutoCompTextViewCustomFont(AutoCompleteTextView autoCompleteTextView, String font, Context context) {
    if (font == null) {
      return;
    }
    Typeface tf = FontCache.get(font, context);
    if (tf != null) {
      autoCompleteTextView.setTypeface(tf);
    }
  }

}

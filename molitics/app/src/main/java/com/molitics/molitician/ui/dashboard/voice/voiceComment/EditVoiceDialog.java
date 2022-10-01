package com.molitics.molitician.ui.dashboard.voice.voiceComment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.molitics.molitician.R;
import com.molitics.molitician.util.Util;

import butterknife.ButterKnife;

/**
 * Created by rahul on 21/03/18.
 */

public class EditVoiceDialog extends Dialog {

    static onEditCommentListener editCommentListener;

    EditText edit_comment_view;
    static String edit_comment;

    public EditVoiceDialog(@NonNull Context context) {
        super(context);
    }

    public static Dialog getInstance(Context mContext, String edit_text, onEditCommentListener commentListener) {

        edit_comment = edit_text;
        editCommentListener = commentListener;
        return new EditVoiceDialog(mContext);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_edit_comment);
        ButterKnife.bind(this);

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        edit_comment_view = findViewById(R.id.edit_comment_view);
        if (!TextUtils.isEmpty(edit_comment)) {
            edit_comment_view.setText(edit_comment);
            edit_comment_view.setSelection(edit_comment_view.getText().length());
        }

        Button submit_comment_button = findViewById(R.id.submit_comment_button);
        submit_comment_button.setOnClickListener(v -> {
            if (TextUtils.isEmpty(edit_comment_view.getText().toString()))
                Util.showToastLong(getContext(), getContext().getString(R.string.cannot_empty));
            else {
                dismiss();
                editCommentListener.onEditCommentSubmit(edit_comment_view.getText().toString());
            }
        });
        Button cancel_button = findViewById(R.id.cancel_button);
        cancel_button.setOnClickListener(v -> dismiss());
    }


    public interface onEditCommentListener {
        void onEditCommentSubmit(String text);
    }
}

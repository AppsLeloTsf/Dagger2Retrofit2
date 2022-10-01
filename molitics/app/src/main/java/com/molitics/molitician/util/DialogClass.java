package com.molitics.molitician.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import com.molitics.molitician.R;

/**
 * Created by rahul on 20/12/16.
 */

public class DialogClass {

    public static ProgressDialog progressDialog;

    public static void showDialog(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getString(R.string.please_wait));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public static void dismissDialog() {
        if (progressDialog != null)
            progressDialog.dismiss();
    }

    public static void showAlert(Context context, String message) {
        if (context != null) {
            AlertDialog dialog;

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(message)
                    .setPositiveButton(context.getString(R.string.exit), (dialog1, id) -> dialog1.dismiss());

            dialog = builder.create();

            if (!((Activity) context).isFinishing()) {
                dialog.show();
            }
        }
    }

    public static void showSessionOut(Context context, String message) {
        PrefUtil.putInt(Constant.PreferenceKey.DEFAULT_STATE_VALUE, 0);
        PrefUtil.putString(Constant.PreferenceKey.AUTH_KEY, "");
        AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setPositiveButton(context.getString(R.string.exit), (dialog1, id) -> {
                    MoliticsActivity.Companion.startSignInActivity(context);
                });
        dialog = builder.create();
        if (!((Activity) context).isFinishing()) {
            dialog.show();
        }
    }

    public static void showConfirmationDialog(Context context, String message, final OnDialogClickListener onDialogClickListener) {
        AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setPositiveButton(context.getString(R.string.exit), (dialog12, id) -> {
                    onDialogClickListener.onOkClick();
                    dialog12.dismiss();

                })
                .setNegativeButton(context.getString(R.string.cancel), (dialog1, id) -> {
                    onDialogClickListener.onCancelClick();
                    dialog1.dismiss();

                });

        dialog = builder.create();
        dialog.show();
    }

    public static void showConfirmationDialogYes(Context context, String message, final OnDialogClickListener onDialogClickListener) {
        AlertDialog dialog;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setPositiveButton(context.getString(R.string.yes), (dialog12, id) -> {
                    onDialogClickListener.onOkClick();
                    dialog12.dismiss();
                })
                .setNegativeButton(context.getString(R.string.yes), (dialog1, id) -> {
                    onDialogClickListener.onCancelClick();
                    dialog1.dismiss();

                });

        dialog = builder.create();
        dialog.show();
    }

}

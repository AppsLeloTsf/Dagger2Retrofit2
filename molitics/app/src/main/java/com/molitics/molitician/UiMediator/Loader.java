package com.molitics.molitician.UiMediator;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.WindowManager;

/**
 * Created by lenovo on 12/14/2016.
 */

public class Loader {


    public static MyDialog myDialog;
    public static ProgressDialog pDialog;

    public static void showProgressDialog(Context mContext) {
        try {
            if (pDialog == null) {
                pDialog = new ProgressDialog(mContext);
                pDialog.setMessage("Loading...");
                pDialog.setCancelable(false);
                pDialog.show();
            } else {
                pDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            pDialog = null;
            showProgressDialog(mContext);
        }

    }

    public static void dismissProgressDialog() {
        try {
            if (pDialog != null)
                pDialog.dismiss();

            pDialog = null;
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public static void showMyDialog(Activity ctx) {
        try {
            if (myDialog == null) {
                myDialog = new MyDialog(ctx);
                myDialog.setCancelable(false);
            }

            if (!myDialog.isShowing())
                myDialog.show();

        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
            myDialog = null;
            showMyDialog(ctx);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void dismissMyDialog(Activity ctx) {
        try {
            if (myDialog != null)
                myDialog.dismiss();

            myDialog = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

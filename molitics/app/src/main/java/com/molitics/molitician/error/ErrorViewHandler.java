package com.molitics.molitician.error;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.molitics.molitician.R;
import com.molitics.molitician.interfaces.ViewNoDataActionInterface;
import com.molitics.molitician.interfaces.ViewRefreshListener;
import com.molitics.molitician.network.NetworkUtil;


/**
 * Created by rahul on 7/12/2016.
 */
public class ErrorViewHandler {

    public enum errorType {
        NETWORK_NOT_FOUND_ERROR,
        SERVER_ERROR_DIALOG,
        NO_DATA_FOUND_ERROR_MESSAGE,
        NO_RESULTS,
        SETTING_ERROR, NO_INTERNET,
        NO_NOTIFICATION,
        NO_DATA_WITH_ICON
    }

    public static void errorNoData(final errorType e, final ViewGroup viewGroup, Context context, final String message, Object icon) {
        switch (e) {
            case NO_RESULTS:
                noResultsView(viewGroup, context, message);
                break;
            case NO_NOTIFICATION:
                noNotificationView(viewGroup, context, message);
                break;
            case NO_DATA_WITH_ICON:
                noResultsIconView(viewGroup, context, message, icon);
                break;
        }
    }


    public static void showError(final errorType e, final ViewGroup viewGroup, Context context, final ViewRefreshListener viewRefreshListner,
                                 final String message, ProgressDialog progressDialog) {
        switch (e) {
            case NETWORK_NOT_FOUND_ERROR:
                addNetworkNotFoundView(viewGroup, context, viewRefreshListner);
                break;
            case SERVER_ERROR_DIALOG:
                serverErrorDialog(viewGroup, context, message, viewRefreshListner, progressDialog);
                break;
            case NO_DATA_FOUND_ERROR_MESSAGE:
                noDataMessageView(viewGroup, context, message);
                break;
            case NO_RESULTS:
                noResultsView(viewGroup, context, message);
                break;
            case SETTING_ERROR:
                settingErrorLayout(viewGroup, message, context, viewRefreshListner);
                break;
            case NO_INTERNET:
                notNetwork(viewGroup, context);
                break;
        }
    }

    private static void addNetworkNotFoundView(final ViewGroup viewGroup, Context context, final ViewRefreshListener viewRefreshListner) {
        try {
            if (viewGroup != null) {
                viewGroup.removeAllViews();
                viewGroup.setVisibility(View.VISIBLE);
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                assert inflater != null;
                View v = inflater.inflate(R.layout.network_error_layout, viewGroup);
                TextView btnRefresh = v.findViewById(R.id.btn_refresh);
                TextView network_error = v.findViewById(R.id.network_error);
                network_error.setText("Had some trouble loading this\r\n page. Please try again");
                btnRefresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        viewGroup.setVisibility(View.GONE);
                        viewRefreshListner.onRefereshClick();
                    }
                });
                viewGroup.addView(v, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void settingErrorLayout(final ViewGroup viewGroup, String message, Context context, final ViewRefreshListener viewRefreshListner) {
        try {
            if (viewGroup != null) {
                viewGroup.removeAllViews();
                viewGroup.setVisibility(View.VISIBLE);
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View v = inflater.inflate(R.layout.setting_error_layout, null);
                TextView btnRefresh = v.findViewById(R.id.btn_refresh);
                btnRefresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        viewGroup.setVisibility(View.GONE);
                        viewRefreshListner.onRefereshClick();
                    }
                });
                viewGroup.addView(v, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void serverErrorDialog(final ViewGroup viewGroup, Context context, String message, final ViewRefreshListener viewRefreshListner, ProgressDialog progressDialog) {
        try {

            if (progressDialog != null)
                progressDialog.dismiss();

            if (viewGroup != null) {
                viewGroup.removeAllViews();
                viewGroup.setVisibility(View.VISIBLE);
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                //   View v = inflater.inflate(R.layout.error_dialog_layout, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void noNotificationView(final ViewGroup viewGroup, Context context, final String message) {
        try {

            if (viewGroup != null) {

                viewGroup.removeAllViews();
                viewGroup.setVisibility(View.VISIBLE);
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View v = inflater.inflate(R.layout.no_result_layout, null);
                ImageView ivMessageImage = v.findViewById(R.id.ivMessageImage);

                TextView tvMsg = v.findViewById(R.id.no_result_message);

                ivMessageImage.setImageResource(R.drawable.ic_notification_bell);
                if (message != null && !message.equals(""))
                    tvMsg.setText(message);

                viewGroup.addView(v, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void noResultsIconView(final ViewGroup viewGroup, Context context, final String message, Object icon) {
        try {

            if (viewGroup != null) {

                viewGroup.removeAllViews();
                viewGroup.setVisibility(View.VISIBLE);
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View v = inflater.inflate(R.layout.no_result_layout, null);

                ImageView ivMessageImage = v.findViewById(R.id.ivMessageImage);

                TextView tvMsg = v.findViewById(R.id.no_result_message);

                if (icon instanceof Integer)
                    ivMessageImage.setImageResource((Integer) icon);
                if (message != null && !message.equals(""))
                    tvMsg.setText(message);

                viewGroup.addView(v, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void noResultsView(final ViewGroup viewGroup, Context context, final String message) {
        try {

            if (viewGroup != null) {

                viewGroup.removeAllViews();
                viewGroup.setVisibility(View.VISIBLE);
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View v = inflater.inflate(R.layout.no_result_layout, null);

                TextView tvMsg = v.findViewById(R.id.no_result_message);

                if (message != null && !message.equals(""))
                    tvMsg.setText(message);

                viewGroup.addView(v, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void noResultsAction(final ViewGroup viewGroup, Context context, final String message, String buttonTxt, ViewNoDataActionInterface actionInterface) {
        try {

            if (viewGroup != null) {

                viewGroup.removeAllViews();
                viewGroup.setVisibility(View.VISIBLE);
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View v = inflater.inflate(R.layout.no_result_action, null);

                TextView tvMsg = v.findViewById(R.id.no_result_message);
                Button ivMessageButton = v.findViewById(R.id.ivMessageButton);

                ivMessageButton.setText(buttonTxt);

                if (message != null && !message.equals(""))
                    tvMsg.setText(message);

                ivMessageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        viewGroup.setVisibility(View.GONE);
                        actionInterface.onNoDataActionClick();
                    }
                });

                viewGroup.addView(v, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void noDataMessageView(final ViewGroup viewGroup, Context context, String message) {
        try {

            if (viewGroup != null) {
                if (message == null || message.equals(""))
                    message = "Hi ! seems like we have no data to show here";
                viewGroup.removeAllViews();
                viewGroup.setVisibility(View.VISIBLE);
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View v = inflater.inflate(R.layout.no_data_message_layout, null);
                ImageView msg = v.findViewById(R.id.ivMessageImage);

                if (message != null) {
                    Bitmap dr = NetworkUtil.getKiMessage(context, message);
                    if (dr != null) {
                        msg.setImageBitmap(dr);
                    }
                }
                viewGroup.addView(v, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void notNetwork(final ViewGroup viewGroup, Context context) {
        try {
            if (viewGroup != null) {
                viewGroup.removeAllViews();
                viewGroup.setVisibility(View.VISIBLE);
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View v = inflater.inflate(R.layout.error_no_internet, null);
                viewGroup.addView(v, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


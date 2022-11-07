package com.ca_dreamers.cadreamers.fragments.notification.notification_read;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ca_dreamers.cadreamers.activity.MainActivity;
import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.api.Api;
import com.ca_dreamers.cadreamers.api.RetrofitClient;
import com.ca_dreamers.cadreamers.models.notification.notification_read.ModelNotificationRead;
import com.ca_dreamers.cadreamers.storage.SharedPrefManager;
import com.ca_dreamers.cadreamers.utils.Constant;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ca_dreamers.cadreamers.utils.Constant.TAG;

public class FragmentNotificationRead extends Fragment {

    private String strUserId;
    private String strNotificationId;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tvNotificationReadTitle)
    protected TextView tvNotificationReadTitle;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tvNotificationRead)
    protected TextView tvNotificationRead;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tvNotificationReadDate)
    protected TextView tvNotificationReadDate;

    public static FragmentNotificationRead newInstance() {
        return new FragmentNotificationRead();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification_read, container, false);
        ButterKnife.bind(this, view);
        SharedPrefManager sharedPrefManager = new SharedPrefManager(getContext());
        strUserId = sharedPrefManager.getUserId();
        assert getArguments() != null;
        strNotificationId = getArguments().getString(Constant.NOTIFICATION_ID);
        String strNotificationTitle = getArguments().getString(Constant.NOTIFICATION_TITLE);
        String strNotification = getArguments().getString(Constant.NOTIFICATION_CONTENT);
        String strNotificationDate = getArguments().getString(Constant.NOTIFICATION_DATE);
        tvNotificationReadTitle.setText(strNotificationTitle);
        tvNotificationRead.setText(strNotification);
        tvNotificationReadDate.setText(strNotificationDate);
        callNotificationReadApi();
        return view;
    }

    public void callNotificationReadApi(){
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        JsonObject gsonObject;
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("user_id", strUserId);
            paramObject.put("notification_id", strNotificationId);

            gsonObject = (JsonObject) JsonParser.parseString(paramObject.toString());
            Call<ModelNotificationRead> call = api.getNotificationRead(gsonObject);
            call.enqueue(new Callback<ModelNotificationRead>() {
                @Override
                public void onResponse(@NonNull Call<ModelNotificationRead> call, @NonNull Response<ModelNotificationRead> response) {
                    ModelNotificationRead modelNotificationRead = response.body();
                    assert modelNotificationRead != null;
                    if (modelNotificationRead.getStatus().getStatuscode().equals("200")) {
                        if (getActivity() instanceof MainActivity) {
                            ((MainActivity) getActivity()).callGetNotificationCountApi();
                        }
                        Log.d(TAG, "Success: " + modelNotificationRead.getMessage().getMessage());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ModelNotificationRead> call, @NonNull Throwable t) {
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
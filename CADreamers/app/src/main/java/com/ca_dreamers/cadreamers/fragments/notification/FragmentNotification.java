package com.ca_dreamers.cadreamers.fragments.notification;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.adapter.notification.AdapterNotification;
import com.ca_dreamers.cadreamers.api.Api;
import com.ca_dreamers.cadreamers.api.RetrofitClient;
import com.ca_dreamers.cadreamers.models.notification.ModelNotification;
import com.ca_dreamers.cadreamers.storage.SharedPrefManager;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentNotification extends Fragment {

    private String strUserId;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rvNotification)
    protected RecyclerView rvNotification;

    public static FragmentNotification newInstance() {
        return new FragmentNotification();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        ButterKnife.bind(this, view);
        SharedPrefManager sharedPrefManager = new SharedPrefManager(getContext());
        strUserId = sharedPrefManager.getUserId();
        rvNotification.setLayoutManager(new LinearLayoutManager(getContext()));
        callGetNotificationApi();
        return view;
    }

    public void callGetNotificationApi(){
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        JsonObject gsonObject;
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("user_id", strUserId);

            gsonObject = (JsonObject) JsonParser.parseString(paramObject.toString());
            Call<ModelNotification> call = api.getNotification(gsonObject);

            call.enqueue(new Callback<ModelNotification>() {
                @Override
                public void onResponse(@NonNull Call<ModelNotification> call, @NonNull Response<ModelNotification> response) {
                    ModelNotification modelNotification = response.body();
                    assert modelNotification != null;
                    AdapterNotification adapterNotification = new AdapterNotification(modelNotification.getData());
                    rvNotification.setAdapter(adapterNotification);

                }


                @Override
                public void onFailure(@NonNull Call<ModelNotification> call, @NonNull Throwable t) {
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
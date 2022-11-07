package com.ca_dreamers.cadreamers.fragments.combo_package;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.activity.LoginActivity;
import com.ca_dreamers.cadreamers.adapter.books.AdapterBooksList;
import com.ca_dreamers.cadreamers.adapter.combo_package.AdapterComboPackage;
import com.ca_dreamers.cadreamers.api.Api;
import com.ca_dreamers.cadreamers.api.RetrofitClient;
import com.ca_dreamers.cadreamers.models.books.ModelBooksList;
import com.ca_dreamers.cadreamers.models.combo_package.ModelCombo;
import com.ca_dreamers.cadreamers.models.logged_out.ModelLogout;
import com.ca_dreamers.cadreamers.models.user_token.ModelToken;
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

public class FragmentCombo extends Fragment {

    private Context context;
    private String strUserId;
    private String strToken;
    private SharedPrefManager sharedPrefManager;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rvComboPackage)
    protected RecyclerView rvComboPackage;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_combo, container, false);
        ButterKnife.bind(this, view);
        context = (Activity)view.getContext();
        sharedPrefManager = new SharedPrefManager(getContext());
        strUserId = sharedPrefManager.getUserId();
        rvComboPackage.setLayoutManager(new LinearLayoutManager(getContext()));
        callTokenApi();
        callComboApi();
        return view;
    }


    private void callComboApi(){
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        JsonObject gsonObject;
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("type", "package");

            gsonObject = (JsonObject) JsonParser.parseString(paramObject.toString());
            Call<ModelCombo> call = api.getComboPackage(gsonObject);

            call.enqueue(new Callback<ModelCombo>() {
                @Override
                public void onResponse(@NonNull Call<ModelCombo> call, @NonNull Response<ModelCombo> response) {
                    ModelCombo modelFetchCart = response.body();

                    assert modelFetchCart != null;
                    AdapterComboPackage adapterComboPackage = new AdapterComboPackage(modelFetchCart.getData());
                    rvComboPackage.setAdapter(adapterComboPackage);
                }

                @Override
                public void onFailure(@NonNull Call<ModelCombo> call, @NonNull Throwable t) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void callTokenApi(){
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        JsonObject gsonObject;
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("user_id", strUserId);
            gsonObject = (JsonObject) JsonParser.parseString(paramObject.toString());
            Call<ModelToken> call = api.getTokenApi(gsonObject);
            call.enqueue(new Callback<ModelToken>() {
                @Override
                public void onResponse(@NonNull Call<ModelToken> call, @NonNull Response<ModelToken> response) {
                    ModelToken modelToken = response.body();
                    assert modelToken != null;
                    strToken = modelToken.getData().getToken();
                    if (! strToken.equals(getUserDeviceId())){
                        callLogoutApi();
                        Toast.makeText(context, "Some on logged in with your credentials.", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(@NonNull Call<ModelToken> call, @NonNull Throwable t) {
                    Log.d("TSF_APPS", "TOKEN Error: "+t);

                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void callLogoutApi(){
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        JsonObject gsonObject;
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("user_id", strUserId);
            gsonObject = (JsonObject) JsonParser.parseString(paramObject.toString());
            Call<ModelLogout> call = api.getLogout(gsonObject);
            call.enqueue(new Callback<ModelLogout>() {
                @Override
                public void onResponse(@NonNull Call<ModelLogout> call, @NonNull Response<ModelLogout> response) {
                    sharedPrefManager.clearUserData();
                    startActivity(new Intent(context, LoginActivity.class));
                }

                @Override
                public void onFailure(@NonNull Call<ModelLogout> call, @NonNull Throwable t) {

                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @SuppressLint("HardwareIds")
    public String getUserDeviceId() {
        String deviceId;

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            deviceId = Settings.Secure.getString(
                    context.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        } else {
            final TelephonyManager mTelephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (mTelephony.getDeviceId() != null) {
                deviceId = mTelephony.getDeviceId();
            } else {
                deviceId = Settings.Secure.getString(
                        context.getContentResolver(),
                        Settings.Secure.ANDROID_ID);
            }
        }

        return deviceId;
    }

}
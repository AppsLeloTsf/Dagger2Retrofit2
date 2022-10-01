package com.ca_dreamers.cadreamers.fragments.user_info;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.api.Api;
import com.ca_dreamers.cadreamers.api.RetrofitClient;
import com.ca_dreamers.cadreamers.models.profile.ModelUserInfo;
import com.ca_dreamers.cadreamers.models.profile.user_image.ModelProfileImage;
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

public class ProfileFragment extends Fragment {

    private ProfileViewModel mViewModel;
    private SharedPrefManager sharedPrefManager;

    @BindView(R.id.ivProfilePic)
    protected ImageView ivProfilePic;
    @BindView(R.id.tvUserNameProfile)
    protected TextView tvUserNameProfile;
    @BindView(R.id.tvEmailProfile)
    protected TextView tvEmailProfile;
    @BindView(R.id.tvStateProfile)
    protected TextView tvStateProfile;
    @BindView(R.id.tvPinProfile)
    protected TextView tvPinProfile;
    @BindView(R.id.tvAddressProfile)
    protected TextView tvAddressProfile;
    @BindView(R.id.tvPhoneProfile)
    protected TextView tvPhoneProfile;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);
        ButterKnife.bind(this, view);
        sharedPrefManager = new SharedPrefManager(getContext());
        callProfileImageApi(getContext());
        callProfileApi();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        // TODO: Use the ViewModel
    }
    private void callProfileImageApi(Context context){
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("userid", sharedPrefManager.getUserId());


            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(paramObject.toString());
            Call<ModelProfileImage> call = api.getUserImage(gsonObject);
            call.enqueue(new Callback<ModelProfileImage>() {
                @Override
                public void onResponse(Call<ModelProfileImage> call, Response<ModelProfileImage> response) {
                    ModelProfileImage modelProfileImage = response.body();
                    Glide.with(context).load(modelProfileImage.getData().getFileUrl()).into(ivProfilePic);

                }

                @Override
                public void onFailure(Call<ModelProfileImage> call, Throwable t) {

                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void callProfileApi(){
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("userid", sharedPrefManager.getUserId());


            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(paramObject.toString());
            Call<ModelUserInfo> call = api.getUserInfo(gsonObject);
            call.enqueue(new Callback<ModelUserInfo>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(Call<ModelUserInfo> call, Response<ModelUserInfo> response) {
                    ModelUserInfo modelUserInfo = response.body();
                    tvUserNameProfile.setText("User Name: "+modelUserInfo.getData().getUsername());
                    tvEmailProfile.setText("Email: "+modelUserInfo.getData().getEmail());
                    tvStateProfile.setText("State: "+modelUserInfo.getData().getState());
                    tvPinProfile.setText("Pin Code"+modelUserInfo.getData().getPincode());
                    tvAddressProfile.setText("Address: "+modelUserInfo.getData().getAddress());
                    tvPhoneProfile.setText("Phone: "+modelUserInfo.getData().getPhone());
                }

                @Override
                public void onFailure(Call<ModelUserInfo> call, Throwable t) {

                    Log.d("TSF_PRO", "Failure: "+t.getMessage());
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
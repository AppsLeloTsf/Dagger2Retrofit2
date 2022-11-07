package com.ca_dreamers.cadreamers.fragments.side_nav.contact_us;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.api.Api;
import com.ca_dreamers.cadreamers.api.RetrofitClient;
import com.ca_dreamers.cadreamers.models.contact_us.ModelContactUs;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactUsFragment extends Fragment {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tvContactMobile)
    protected TextView tvContactMobile;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tvContactEmail)
    protected TextView tvContactEmail;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);
        ButterKnife.bind(this, view);
        callContactUs();
        return view;
    }

    private void callContactUs(){
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        Call<ModelContactUs> call = api.getContactUs();
        call.enqueue(new Callback<ModelContactUs>() {
            @Override
            public void onResponse(@NonNull Call<ModelContactUs> call, @NonNull Response<ModelContactUs> response) {
                ModelContactUs modelContactUs = response.body();
                assert modelContactUs != null;
                tvContactMobile.setText(modelContactUs.getData().getPhone());
                tvContactEmail.setText(modelContactUs.getData().getEmail());
            }

            @Override
            public void onFailure(@NonNull Call<ModelContactUs> call, @NonNull Throwable t) {

            }
        });


    }


}
package com.indianjourno.indianjourno.activity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.indianjourno.indianjourno.api.RetrofitClient;
import com.indianjourno.indianjourno.model.static_page.PrivacyModels;
import com.indianjourno.indianjourno.utils.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import indianjourno.indianjourno.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrivacyActivity extends AppCompatActivity {

    private String strId;
    @BindView(R.id.tvPrivacyTitle)
    protected TextView tvPrivacyTitle;
    @BindView(R.id.tvPrivacyBody)
    protected TextView tvPrivacyBody;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);
        ButterKnife.bind(this);
        strId = getIntent().getStringExtra(Constant.CAT_ID);
        callPrivacyApi();
    }

    private void callPrivacyApi() {
        Call<PrivacyModels> call = RetrofitClient.getInstance().getApi().getPrivacy(strId);
        call.enqueue(new Callback<PrivacyModels>() {
            @Override
            public void onResponse(Call<PrivacyModels> call, Response<PrivacyModels> response) {
                PrivacyModels tModel = response.body();
                if (tModel != null) {
                    if (!tModel.getError()) {
                        tvPrivacyTitle.setText(tModel.getStaticPage().get(0).getPageTitle());
                        tvPrivacyBody.setText(tModel.getStaticPage().get(0).getPageContent());
                    }else {
                        Toast.makeText(PrivacyActivity.this, tModel.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<PrivacyModels> call, Throwable t) {

            }
        });
    }
}
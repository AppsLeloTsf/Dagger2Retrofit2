package com.indianjourno.indianjourno.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.indianjourno.indianjourno.adapter.AdapterNewsList;
import com.indianjourno.indianjourno.api.RetrofitClient;
import com.indianjourno.indianjourno.model.feature.FeatureNews;
import com.indianjourno.indianjourno.utils.Constant;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import indianjourno.indianjourno.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeatureActivity extends AppCompatActivity {

    private static final String TSF_TAG = "TsfTag";
    String strCatId;
    String strVideoCatId;
    String strCatName;
    @BindView(R.id.toolbarMainFeature)
    protected Toolbar toolbarMainFeature;

    @BindView(R.id.rvMainFeature)
    protected RecyclerView rvMainFeature;
    @BindView(R.id.tvToolbarFeature)
    protected TextView tvToolbarFeature;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feature);
        ButterKnife.bind(this);
        setSupportActionBar(toolbarMainFeature);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        strCatId = getIntent().getStringExtra(Constant.CAT_ID);
        strVideoCatId = getIntent().getStringExtra(Constant.VIDEO_ID);
        strCatName = getIntent().getStringExtra(Constant.CAT_NAME);
        tvToolbarFeature.setText(strCatName);
        rvMainFeature.setLayoutManager(new LinearLayoutManager(this));
       callApi();

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void callApi(){
        Call<FeatureNews> call = RetrofitClient.getInstance().getApi().getNewsByFeatureId(strCatId);
        call.enqueue(new Callback<FeatureNews>() {
            @Override
            public void onResponse(Call<FeatureNews> call, Response<FeatureNews> response) {
                FeatureNews  tModelAllNews = response.body();
                AdapterNewsList tAdapterNewsList = new AdapterNewsList(tModelAllNews.getNewsFeatures(), strCatId);
                rvMainFeature.setAdapter(tAdapterNewsList);
            }

            @Override
            public void onFailure(Call<FeatureNews> call, Throwable t) {

            }
        });    }
}

package com.indianjourno.indianjourno.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


import com.indianjourno.indianjourno.adapter.AdapterHomeCategory;
import com.indianjourno.indianjourno.adapter.AdapterHomeFeature;
import com.indianjourno.indianjourno.adapter.AdapterHomeVideoCat;
import com.indianjourno.indianjourno.adapter.SliderAdapter;
import com.indianjourno.indianjourno.api.RetrofitClient;
import com.indianjourno.indianjourno.model.category.CategoryList;
import com.indianjourno.indianjourno.model.feature.FeatureMessage;
import com.indianjourno.indianjourno.model.video_cat.ModelVideoCat;

import butterknife.BindView;
import butterknife.ButterKnife;
import indianjourno.indianjourno.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentHome extends Fragment {

    @BindView(R.id.tvFeatureTitle)
    protected TextView tvFeatureTitle;
    @BindView(R.id.tvTopTitle)
    protected TextView tvTopTitle;
    @BindView(R.id.tvVideoTitle)
    protected TextView tvVideoTitle;
    @BindView(R.id.rvHomeVideoCat)
    protected RecyclerView rvHomeVideoCat;
    @BindView(R.id.rvHomeFeatureCat)
    protected RecyclerView rvHomeFeatureCat;
    @BindView(R.id.rvHomeNewsCat)
    protected RecyclerView rvHomeNewsCat;
     @BindView(R.id.viewPagerMain)
    protected ViewPager viewPagerMain;
    @BindView(R.id.vfMain)
    protected ViewFlipper vfMain;
    @BindView(R.id.banner)
    protected ImageView banner;




    private SliderAdapter sliderAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        rvHomeVideoCat.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        rvHomeFeatureCat.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rvHomeNewsCat.setLayoutManager(new GridLayoutManager(getContext(), 3));

        tvTopTitle.setVisibility(View.GONE);
        tvVideoTitle.setVisibility(View.GONE);
        tvFeatureTitle.setVisibility(View.GONE);


        callHomeVideo(getContext());
        callHomeFeature(getContext());
        callHomeCat(getContext());
        return view;
    }

    private void callHomeVideo(Context context) {
        Call<ModelVideoCat> call = RetrofitClient.getInstance().getApi().getVideoCatList();
        call.enqueue(new Callback<ModelVideoCat>() {
            @Override
            public void onResponse(Call<ModelVideoCat> call, Response<ModelVideoCat> response) {
                ModelVideoCat  tModelVideos = response.body();
                tvVideoTitle.setVisibility(View.VISIBLE);
                rvHomeVideoCat.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
                AdapterHomeVideoCat tAdapterVideoCatList = new AdapterHomeVideoCat(context, tModelVideos.getVideoCategory());
                rvHomeVideoCat.setAdapter(tAdapterVideoCatList);
            }

            @Override
            public void onFailure(Call<ModelVideoCat> call, Throwable t) {

            }
        });
    }

    private void callHomeFeature(Context context) {
        Call<FeatureMessage> call = RetrofitClient.getInstance().getApi().getFeatureCategory();
        call.enqueue(new Callback<FeatureMessage>() {
            @Override
            public void onResponse(Call<FeatureMessage> call, Response<FeatureMessage> response) {
                FeatureMessage tModelsCat = response.body();
                tvFeatureTitle.setVisibility(View.VISIBLE);

                sliderAdapter = new SliderAdapter(getContext(), tModelsCat.getFeaturesID());
                viewPagerMain.setAdapter(sliderAdapter);

                AdapterHomeFeature adapterHomeFeature = new AdapterHomeFeature(context, tModelsCat.getFeaturesID());
                rvHomeFeatureCat.setAdapter(adapterHomeFeature);
            }
            @Override
            public void onFailure(Call<FeatureMessage> call, Throwable t) {

            }
        });
    }

    private void callHomeCat(Context context){
        Call<CategoryList> call = RetrofitClient.getInstance().getApi().getCategory();
        call.enqueue(new Callback<CategoryList>() {
            @Override
            public void onResponse(Call<CategoryList> call, Response<CategoryList> response) {
                CategoryList tModel = response.body();
                tvTopTitle.setVisibility(View.VISIBLE);
                AdapterHomeCategory tAdapter = new AdapterHomeCategory(context, tModel.getCategory());
                rvHomeNewsCat.setAdapter(tAdapter);
            }

            @Override
            public void onFailure(Call<CategoryList> call, Throwable t) {

            }
        });
    }
}
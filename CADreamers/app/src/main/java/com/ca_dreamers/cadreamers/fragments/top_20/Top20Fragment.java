package com.ca_dreamers.cadreamers.fragments.top_20;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.adapter.top_20.AdapterTop20List;
import com.ca_dreamers.cadreamers.adapter.top_20.AutoSliderTop20Adapter;
import com.ca_dreamers.cadreamers.api.Api;
import com.ca_dreamers.cadreamers.api.RetrofitClient;
import com.ca_dreamers.cadreamers.models.top_20.ModelTop20;
import com.ca_dreamers.cadreamers.models.top_20.banners.ModelTop20Banner;
import com.ca_dreamers.cadreamers.utils.AutoScrollViewPager;
import com.ca_dreamers.cadreamers.utils.Constant;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Top20Fragment extends Fragment {


    AutoSliderTop20Adapter autoScrollPagerAdapter;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rvTop20List)
    protected RecyclerView rvTop20List;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.sliderTop20)
    protected AutoScrollViewPager sliderTop20;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tabsTop20)
    protected TabLayout tabsTop20;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_top_20, container, false);
        ButterKnife.bind(this, root);
        rvTop20List.setLayoutManager(new LinearLayoutManager(getContext()));
        callBannersApi();
        callTop20Api();
        return root;
    }

    private void callBannersApi(){
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        Call<ModelTop20Banner> bannersCall = api.getTop20Banner();

        bannersCall.enqueue(new Callback<ModelTop20Banner>() {
            @Override
            public void onResponse(@NonNull Call<ModelTop20Banner> call, @NonNull Response<ModelTop20Banner> response) {
                ModelTop20Banner modelBanners = response.body();
                assert modelBanners != null;
                Log.d("TSF_COURSE", "Banners: "+modelBanners.getData().get(0).getImageUrl());
                autoScrollPagerAdapter =
                        new AutoSliderTop20Adapter(modelBanners.getData(), getContext());
                sliderTop20.setAdapter(autoScrollPagerAdapter);

                tabsTop20.setupWithViewPager(sliderTop20);

                sliderTop20.startAutoScroll();

                sliderTop20.setInterval(Constant.SLIDER_SPEED);

                sliderTop20.setCycle(true);
            }

            @Override
            public void onFailure(@NonNull Call<ModelTop20Banner> call, @NonNull Throwable t) {

            }
        });
    }


    private void callTop20Api(){
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
            Call<ModelTop20> call = api.getTop20();
            call.enqueue(new Callback<ModelTop20>() {
                @Override
                public void onResponse(@NonNull Call<ModelTop20> call, @NonNull Response<ModelTop20> response) {
                    ModelTop20 modelTop20 = response.body();
                    assert modelTop20 != null;
                    Log.d("TSF_TOP", modelTop20.getData().get(0).getDescription());
                    AdapterTop20List adapterCourseList = new AdapterTop20List(modelTop20.getData());
                    rvTop20List.setAdapter(adapterCourseList);
                }

                @Override
                public void onFailure(@NonNull Call<ModelTop20> call, @NonNull Throwable t) {
                    Log.d("TSF_TOP", "Top 20 Failure"+t.getMessage());
                }
            });

    }


}
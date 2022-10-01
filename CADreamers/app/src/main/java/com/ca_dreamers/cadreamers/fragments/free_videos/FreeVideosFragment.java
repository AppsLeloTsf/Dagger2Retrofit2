package com.ca_dreamers.cadreamers.ui.free_videos;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.adapter.courses.AdapterCoursesBanner;
import com.ca_dreamers.cadreamers.adapter.free_videos.AdapterFreeVideosBanner;
import com.ca_dreamers.cadreamers.adapter.free_videos.AdapterFreeVideosList;
import com.ca_dreamers.cadreamers.api.Api;
import com.ca_dreamers.cadreamers.api.RetrofitClient;
import com.ca_dreamers.cadreamers.models.courses.banners.ModelCoursesBanner;
import com.ca_dreamers.cadreamers.models.free_videos.ModelFreeVideo;
import com.ca_dreamers.cadreamers.models.free_videos.banners.ModelBanners;
import com.ca_dreamers.cadreamers.utils.AutoScrollViewPager;
import com.ca_dreamers.cadreamers.utils.Constant;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FreeVideosFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;

    @BindView(R.id.sliderFreeVideos)
    protected AutoScrollViewPager sliderFreeVideos;
    @BindView(R.id.tabsFreeVideos)
    protected TabLayout tabsFreeVideos;

    @BindView(R.id.rvFreeVideosList)
    protected RecyclerView rvFreeVideosList;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_free_videos, container, false);
        ButterKnife.bind(this, root);
        rvFreeVideosList.setLayoutManager(new LinearLayoutManager(getContext()));
        callBannersApi();
        callFreeVideosApi();
//        slideshowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }
    private void callBannersApi(){
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        Call<ModelBanners> bannersCall = api.getBanners();

        bannersCall.enqueue(new Callback<ModelBanners>() {
            @Override
            public void onResponse(Call<ModelBanners> call, Response<ModelBanners> response) {
                ModelBanners modelBanners = response.body();
                AdapterFreeVideosBanner adapterCoursesBanner =
                        new AdapterFreeVideosBanner(modelBanners.getData(), getContext());
                sliderFreeVideos.setAdapter(adapterCoursesBanner);

                tabsFreeVideos.setupWithViewPager(sliderFreeVideos);
                // start auto scroll
                sliderFreeVideos.startAutoScroll();
                // set auto scroll time in mili
                sliderFreeVideos.setInterval(Constant.SLIDER_SPEED);
                // enable recycling using true
                sliderFreeVideos.setCycle(true);
            }

            @Override
            public void onFailure(Call<ModelBanners> call, Throwable t) {

            }
        });
    }
    private void callFreeVideosApi(){
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        Call<ModelFreeVideo> call = api.getFreeVideos();
        call.enqueue(new Callback<ModelFreeVideo>() {
            @Override
            public void onResponse(Call<ModelFreeVideo> call, Response<ModelFreeVideo> response) {
                ModelFreeVideo modelFreeVideo = response.body();
                Log.d("TSF_FREE_VIDEOS", modelFreeVideo.getMessage().getMessage());
                AdapterFreeVideosList adapterFreeVideosList =
                        new AdapterFreeVideosList(modelFreeVideo.getData(), getContext());
                rvFreeVideosList.setAdapter(adapterFreeVideosList);
            }

            @Override
            public void onFailure(Call<ModelFreeVideo> call, Throwable t) {
                Log.d("TSF_FREE_VIDEOS", "Failure: "+t.getMessage());

            }
        });

    }

}
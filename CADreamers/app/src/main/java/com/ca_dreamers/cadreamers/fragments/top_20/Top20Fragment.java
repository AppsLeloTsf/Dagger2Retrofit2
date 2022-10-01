package com.ca_dreamers.cadreamers.ui.top_20;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
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

    private Top20ViewModel top20ViewModel;
    @BindView(R.id.rvTop20List)
    protected RecyclerView rvTop20List;
    @BindView(R.id.sliderTop20)
    protected AutoScrollViewPager sliderTop20;
    @BindView(R.id.tabsTop20)
    protected TabLayout tabsTop20;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        top20ViewModel =
                new ViewModelProvider(this).get(Top20ViewModel.class);
        View root = inflater.inflate(R.layout.fragment_top_20, container, false);
        ButterKnife.bind(this, root);
        rvTop20List.setLayoutManager(new LinearLayoutManager(getContext()));
        callBannersApi();
        callTop20Api();


//        top20ViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }

    private void callBannersApi(){
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        Call<ModelTop20Banner> bannersCall = api.getTop20Banner();

        bannersCall.enqueue(new Callback<ModelTop20Banner>() {
            @Override
            public void onResponse(Call<ModelTop20Banner> call, Response<ModelTop20Banner> response) {
                ModelTop20Banner modelBanners = response.body();
                Log.d("TSF_COURSE", "Banners: "+modelBanners.getData().get(0).getImageUrl());
                AutoSliderTop20Adapter autoScrollPagerAdapter =
                        new AutoSliderTop20Adapter(modelBanners.getData(), getContext());
                sliderTop20.setAdapter(autoScrollPagerAdapter);

                tabsTop20.setupWithViewPager(sliderTop20);
                // start auto scroll
                sliderTop20.startAutoScroll();
                // set auto scroll time in mili
                sliderTop20.setInterval(Constant.SLIDER_SPEED);
                // enable recycling using true
                sliderTop20.setCycle(true);
            }

            @Override
            public void onFailure(Call<ModelTop20Banner> call, Throwable t) {

            }
        });
    }


    private void callTop20Api(){
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
            Call<ModelTop20> call = api.getTop20();
            call.enqueue(new Callback<ModelTop20>() {
                @Override
                public void onResponse(Call<ModelTop20> call, Response<ModelTop20> response) {
                    ModelTop20 modelTop20 = response.body();
                    Log.d("TSF_TOP", modelTop20.getData().get(0).getDescription());
                    AdapterTop20List adapterCourseList = new AdapterTop20List(modelTop20.getData(), getActivity().getApplicationContext());
                    rvTop20List.setAdapter(adapterCourseList);
                }

                @Override
                public void onFailure(Call<ModelTop20> call, Throwable t) {
                    Log.d("TSF_TOP", "Top 20 Failure"+t.getMessage());
                }
            });

    }

}
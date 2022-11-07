package com.ca_dreamers.cadreamers.fragments.batch;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.adapter.batch.AdapterBatch;
import com.ca_dreamers.cadreamers.adapter.courses.AdapterCoursesBanner;
import com.ca_dreamers.cadreamers.api.Api;
import com.ca_dreamers.cadreamers.api.RetrofitClient;
import com.ca_dreamers.cadreamers.models.combo_package.ModelCombo;
import com.ca_dreamers.cadreamers.models.courses.banners.ModelCoursesBanner;
import com.ca_dreamers.cadreamers.utils.AutoScrollViewPager;
import com.ca_dreamers.cadreamers.utils.Constant;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentBatch extends Fragment {

    private LayoutInflater layoutInflater;
    private Context context;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rvBatchList)
    protected RecyclerView rvBatchList;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.sliderBatch)
    protected AutoScrollViewPager sliderBatch;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tabsBatch)
    protected TabLayout tabsBatch;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_batch, container, false);
        context = (Activity)root.getContext();
        layoutInflater = getLayoutInflater();
        ButterKnife.bind(this, root);
        callBannersApi();
        rvBatchList.setLayoutManager(new LinearLayoutManager(getContext()));
        callBatchApi();

        return root;
    }


    private void callBannersApi(){
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        Call<ModelCoursesBanner> bannersCall = api.getCoursesBanners();
        bannersCall.enqueue(new Callback<ModelCoursesBanner>() {
            @Override
            public void onResponse(@NonNull Call<ModelCoursesBanner> call, @NonNull Response<ModelCoursesBanner> response) {
                ModelCoursesBanner modelCoursesBanner = response.body();
                assert modelCoursesBanner != null;
                AdapterCoursesBanner adapterCoursesBanner =
                        new AdapterCoursesBanner(modelCoursesBanner.getData(), getContext(), layoutInflater);
                sliderBatch.setAdapter(adapterCoursesBanner);

                tabsBatch.setupWithViewPager(sliderBatch);
                sliderBatch.startAutoScroll();
                sliderBatch.setInterval(Constant.SLIDER_SPEED);
                sliderBatch.setCycle(true);
            }

            @Override
            public void onFailure(@NotNull Call<ModelCoursesBanner> call, @NotNull Throwable t) {

            }
        });

    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void callBatchApi(){
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        JsonObject gsonObject;
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("type", "batch");

            gsonObject = (JsonObject) JsonParser.parseString(paramObject.toString());
            Call<ModelCombo> call = api.getComboPackage(gsonObject);

            call.enqueue(new Callback<ModelCombo>() {
                @Override
                public void onResponse(@NotNull Call<ModelCombo> call, @NotNull Response<ModelCombo> response) {
                    ModelCombo modelCombo = response.body();

                    assert modelCombo != null;
                    AdapterBatch adapterBatch = new AdapterBatch(modelCombo.getData(), context);
                   rvBatchList.setAdapter(adapterBatch);
                }

                @Override
                public void onFailure(@NotNull Call<ModelCombo> call, @NotNull Throwable t) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
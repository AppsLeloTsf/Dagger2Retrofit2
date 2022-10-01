package com.ca_dreamers.cadreamers.ui.books;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.adapter.books.AdapterBooksBanner;
import com.ca_dreamers.cadreamers.adapter.books.AdapterBooksList;
import com.ca_dreamers.cadreamers.adapter.courses.AdapterCourseList;
import com.ca_dreamers.cadreamers.api.Api;
import com.ca_dreamers.cadreamers.api.RetrofitClient;
import com.ca_dreamers.cadreamers.models.books.ModelBooksList;
import com.ca_dreamers.cadreamers.models.books.banners.ModelBooksBanners;
import com.ca_dreamers.cadreamers.models.courses.ModelCourse;
import com.ca_dreamers.cadreamers.utils.AutoScrollViewPager;
import com.ca_dreamers.cadreamers.utils.Constant;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BooksFragment extends Fragment {

    private BooksViewModel mViewModel;

    @BindView(R.id.sliderBooks)
    protected AutoScrollViewPager sliderBooks;
    @BindView(R.id.tabsBooks)
    protected TabLayout tabsBooks;
    @BindView(R.id.rvBooksList)
    protected RecyclerView rvBooksList;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_books, container, false);
        ButterKnife.bind(this, view);
        rvBooksList.setLayoutManager(new LinearLayoutManager(getContext()));
        callBannersApi();
        callBooksListApi();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(BooksViewModel.class);
        // TODO: Use the ViewModel
    }

    private void callBannersApi(){
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        Call<ModelBooksBanners> bannersCall = api.getBooksBanner();
        bannersCall.enqueue(new Callback<ModelBooksBanners>() {
            @Override
            public void onResponse(Call<ModelBooksBanners> call, Response<ModelBooksBanners> response) {
                ModelBooksBanners modelBanners = response.body();
                AdapterBooksBanner adapterCoursesBanner =
                        new AdapterBooksBanner(modelBanners.getData(), getContext());
                sliderBooks.setAdapter(adapterCoursesBanner);
                tabsBooks.setupWithViewPager(sliderBooks);
                // start auto scroll
                sliderBooks.startAutoScroll();
                // set auto scroll time in mili
                sliderBooks.setInterval(Constant.SLIDER_SPEED);
                // enable recycling using true
                sliderBooks.setCycle(true);
            }

            @Override
            public void onFailure(Call<ModelBooksBanners> call, Throwable t) {

            }
        });

    }

    private void callBooksListApi(){
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("user_id", "4");

            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(paramObject.toString());
            Call<ModelBooksList> call = api.getBooksList(gsonObject);
            call.enqueue(new Callback<ModelBooksList>() {
                @Override
                public void onResponse(Call<ModelBooksList> call, Response<ModelBooksList> response) {
                    ModelBooksList modelBooksList = response.body();
                    AdapterBooksList adapterBooksList = new AdapterBooksList(modelBooksList.getData(), getActivity().getApplicationContext());
                    rvBooksList.setAdapter(adapterBooksList);
                }

                @Override
                public void onFailure(Call<ModelBooksList> call, Throwable t) {

                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



}
package com.indianjourno.indianjourno.activity.ij.news;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.indianjourno.indianjourno.adapter.ij.news.AdapterAllNewsVp;
import com.indianjourno.indianjourno.api.RetrofitClient;
import com.indianjourno.indianjourno.model.ij_news.all_news.ModelAllNews;

import butterknife.BindView;
import butterknife.ButterKnife;
import indianjourno.indianjourno.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllNewsActivity extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.vpAllNews)
    protected ViewPager2 vpAllNews;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_news);

        ButterKnife.bind(this);

        callAllNews();

        // To get swipe event of viewpager2
        vpAllNews.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            // This method is triggered when there is any scrolling activity for the current page
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            // triggered when you select a new page
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }

            // triggered when there is
            // scroll state will be changed
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }

    private void callAllNews() {
        Call<ModelAllNews> call = RetrofitClient.getInstance().getApi().getAllNewsIj();
        call.enqueue(new Callback<ModelAllNews>() {
            @Override
            public void onResponse(Call<ModelAllNews> call, Response<ModelAllNews> response) {
                ModelAllNews tModel = response.body();

                AdapterAllNewsVp tAdapter = new AdapterAllNewsVp(AllNewsActivity.this, tModel.getNews());

                // adding the adapter to viewPager2
                // to show the views in recyclerview
                vpAllNews.setAdapter(tAdapter);
            }

            @Override
            public void onFailure(Call<ModelAllNews> call, Throwable t) {

            }
        });
    }


}
package com.indianjourno.indianjourno.activity.ij.news;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.indianjourno.indianjourno.activity.NewsDetailCategoryActivity;
import com.indianjourno.indianjourno.adapter.AdapterNewsListByCatDetail;
import com.indianjourno.indianjourno.adapter.ij.news.AdapterNewsByCat;
import com.indianjourno.indianjourno.adapter.ij.stories_news.AdapterStoriesNews;
import com.indianjourno.indianjourno.api.RetrofitClient;
import com.indianjourno.indianjourno.model.category.CategoryMessage;
import com.indianjourno.indianjourno.model.ij_news.ModelNewsByCatId;
import com.indianjourno.indianjourno.model.ij_news.ModelNewsById;
import com.indianjourno.indianjourno.model.ij_news.ModelStoriesNews;
import com.indianjourno.indianjourno.utils.Constant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import indianjourno.indianjourno.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsByCatActivity extends AppCompatActivity {

    private String strCatId;
    @BindView(R.id.rvNewsByCatList)
    protected RecyclerView rvNewsByCatList;
    @BindView(R.id.tbToolbarNewsByCat)
    protected Toolbar tbToolbarNewsByCat;
    @BindView(R.id.tvToolbarNewsByCat)
    protected TextView tvToolbarNewsByCat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_by_cat);
        ButterKnife.bind(this);
        setSupportActionBar(tbToolbarNewsByCat);

        strCatId = getIntent().getStringExtra(Constant.CAT_ID);
        Log.d("TSF_CAT_ID", "Cat Id: "+strCatId);
        rvNewsByCatList.setLayoutManager(new LinearLayoutManager(this));
        callNewsByCatApi(strCatId);
    }

    private void callNewsByCatApi(String strCatId) {
        Call<List<ModelNewsByCatId>> call = RetrofitClient.getInstance().getApi().getNewsByCatIdIj(strCatId);
        call.enqueue(new Callback<List<ModelNewsByCatId>>() {
            @Override
            public void onResponse(Call<List<ModelNewsByCatId>> call, Response<List<ModelNewsByCatId>> response) {
                List<ModelNewsByCatId> tModel = response.body();
                AdapterNewsByCat tAdapter = new AdapterNewsByCat(tModel);
                rvNewsByCatList.setAdapter(tAdapter);
            }

            @Override
            public void onFailure(Call<List<ModelNewsByCatId>> call, Throwable t) {

            }
        });
    }

}
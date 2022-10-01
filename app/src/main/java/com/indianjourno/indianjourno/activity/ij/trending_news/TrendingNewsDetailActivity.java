package com.indianjourno.indianjourno.activity.ij.breaking_news;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.indianjourno.indianjourno.activity.LoginActivity;
import com.indianjourno.indianjourno.activity.MainActivity;
import com.indianjourno.indianjourno.adapter.AdapterNewsListByFeatureDetail;
import com.indianjourno.indianjourno.adapter.ij.breaking_news.AdapterBreakingNewsDetails;
import com.indianjourno.indianjourno.api.RetrofitClient;
import com.indianjourno.indianjourno.model.ModelFeatureIdNews;
import com.indianjourno.indianjourno.model.bookmarks.BookmarkInsertion;
import com.indianjourno.indianjourno.model.feature.FeatureNews;
import com.indianjourno.indianjourno.model.ij_news.ModelBreakingNew;
import com.indianjourno.indianjourno.storage.SharedPrefManager;
import com.indianjourno.indianjourno.utils.Constant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import indianjourno.indianjourno.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BreakingNewsDetailActivity extends AppCompatActivity {
    private String strCatId;
    private String strCatName;
    private String strUserId;
    private String strNewsId;
    private SharedPrefManager tSharedPrefManager;
    private List<ModelFeatureIdNews> tModels;


    private String strTitle;
    private String strDate;
    private String strDetail;

    @BindView(R.id.tvDietDetailTitleCat)
    protected TextView tvDietDetailTitleCat;
    @BindView(R.id.ivNewsDetailCat)
    protected ImageView ivNewsDetailCat;
    @BindView(R.id.tvDatePublishedCat)
    protected TextView tvDatePublishedCat;
    @BindView(R.id.tvDietDetailDetailCat)
    protected TextView tvDietDetailDetailCat;
    @BindView(R.id.rvNewsDetailFeature)
    protected RecyclerView rvNewsDetailFeature;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail_feature);
        ButterKnife.bind(this);
        initActivity();
    }
    private void initActivity() {
        tSharedPrefManager = new SharedPrefManager(this);
        strUserId = tSharedPrefManager.getUserId();



        strNewsId = getIntent().getStringExtra(Constant.NEWS_ID);
        strCatId = getIntent().getStringExtra(Constant.CAT_ID);
        strTitle = getIntent().getStringExtra(Constant.NEWS_TITLE);
        strDate = getIntent().getStringExtra(Constant.NEWS_DATE);
        strDetail = getIntent().getStringExtra(Constant.NEWS_CONTENT);
        String strImgUrl = getIntent().getStringExtra(Constant.NEWS_IMAGE);
        Context tContext = getApplicationContext();
        Glide.with(tContext)
                .load(strImgUrl)
                .skipMemoryCache(true)
                .into(ivNewsDetailCat);
        tvDietDetailTitleCat.setText(strTitle);
        tvDatePublishedCat.setText(strDate);
        Spanned htmlAsSpanned = Html.fromHtml(strDetail);
        tvDietDetailDetailCat.setText(htmlAsSpanned);
        rvNewsDetailFeature.setLayoutManager(new LinearLayoutManager(this));
       callHomeBreakingNews(tContext);
        FloatingActionButton fab = findViewById(R.id.fabActivityFeatureDetail);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(BreakingNewsDetailActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.ivNewsDetailShareCat)
    public void ivShareCatClicked(){
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Janata Suddi");
            String shareMessage= "\n"+strDate+"\n"+strTitle+"\n\nDownload the app for more news\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + Constant.APP_ID +"\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "Share with"));
        } catch(Exception e) {
            //e.toString();
        }
    }
    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.ivNewsDetailBookmarksCat)
    public void ivBookmarkFeatureClicked(){
        if(!tSharedPrefManager.getUserId().equalsIgnoreCase("")){ Toast.makeText(BreakingNewsDetailActivity.this, "Please login for save bookmark", Toast.LENGTH_SHORT).show();
            Call<BookmarkInsertion> call = RetrofitClient.getInstance().getApi().bookmarkInsert(strUserId, strNewsId);
            call.enqueue(new Callback<BookmarkInsertion>() {
                @Override
                public void onResponse(Call<BookmarkInsertion> call, Response<BookmarkInsertion> response) {
                    BookmarkInsertion tModel = response.body();
                    Toast.makeText(BreakingNewsDetailActivity.this, tModel.getMessage(), Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onFailure(Call<BookmarkInsertion> call, Throwable t) {
                    Log.d(Constant.TAG, "Failure Bookmarks Insertion: "+t.getMessage());
                }
            });
        } else {
            Toast.makeText(BreakingNewsDetailActivity.this, "Please login for save bookmark", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(BreakingNewsDetailActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }

    private void callCatApi(){
        Call<FeatureNews> call = RetrofitClient.getInstance().getApi().getNewsByFeatureId(strCatId);
        call.enqueue(new Callback<FeatureNews>() {
            @Override
            public void onResponse(Call<FeatureNews> call, Response<FeatureNews> response) {
                FeatureNews tModel = response.body();
                if(!tModel.getError()) {
                    AdapterNewsListByFeatureDetail tAdapter = new AdapterNewsListByFeatureDetail(tModel.getNewsFeatures(), strCatId);
                    rvNewsDetailFeature.setAdapter(tAdapter);
                }
                else {
                    Toast.makeText(BreakingNewsDetailActivity.this, tModel.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FeatureNews> call, Throwable t) {

            }
          });
    }


    private void callHomeBreakingNews(Context context) {
        Call<List<ModelBreakingNew>> call = RetrofitClient.getInstance().getApi().getAllBreakingNewsIj();
        call.enqueue(new Callback<List<ModelBreakingNew>>() {
            @Override
            public void onResponse(Call<List<ModelBreakingNew>> call, Response<List<ModelBreakingNew>> response) {
                List<ModelBreakingNew> tModel = response.body();
                AdapterBreakingNewsDetails tAdapter = new AdapterBreakingNewsDetails(tModel);
                rvNewsDetailFeature.setAdapter(tAdapter);
            }

            @Override
            public void onFailure(Call<List<ModelBreakingNew>> call, Throwable t) {

            }
        });
    }

}

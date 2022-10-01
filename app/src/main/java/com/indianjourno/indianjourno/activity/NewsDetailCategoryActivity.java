package com.indainjourno.indianjourno.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.indainjourno.indianjourno.R;
import com.indainjourno.indianjourno.api.RetrofitClient;
import com.indainjourno.indianjourno.storage.SharedPrefManager;
import com.indainjourno.indianjourno.utils.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsDetailCategoryActivity extends AppCompatActivity {
    private String strUserId;
    private SharedPrefManager tSharedPrefManager;
    private String strNewsId;
    private String strCatId;
    private String strTitle;
    private String strDate;
    private String strDetail;
    @BindView(R.id.tvDietDetailTitle)
    protected TextView tvDietDetailTitle;
    @BindView(R.id.ivNewsDetail)
    protected ImageView ivNewsDetail;
    @BindView(R.id.tvDatePublished)
    protected TextView tvDatePublished;
    @BindView(R.id.tvDietDetailDetail)
    protected TextView tvDietDetailDetail;
    @BindView(R.id.rvNewsDetailCat)
    protected RecyclerView rvNewsDetailCat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail_category);
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
                .load(Constant.IMG_URL + strImgUrl)
                .skipMemoryCache(true)
                .into(ivNewsDetail);
        tvDietDetailTitle.setText(strTitle);
        tvDatePublished.setText(strDate);
        Spanned htmlAsSpanned = Html.fromHtml(strDetail);
        tvDietDetailDetail.setText(htmlAsSpanned);
        rvNewsDetailCat.setLayoutManager(new LinearLayoutManager(this));

        callCatApi();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabActivityCatDetail);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                Intent intent = new Intent(NewsDetailCategoryActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @OnClick(R.id.ivNewsDetailShare)
    public void ivShareClicked(){
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
    @OnClick(R.id.ivNewsDetailBookmarks)
    public void ivNewsDetailBookmarksClicked(){
        if(!tSharedPrefManager.getUserId().equalsIgnoreCase("")){
            Call<BookmarkInsertion> call = RetrofitClient.getInstance().getApi().bookmarkInsert(strUserId, strNewsId);
            call.enqueue(new Callback<BookmarkInsertion>() {
                @Override
                public void onResponse(Call<BookmarkInsertion> call, Response<BookmarkInsertion> response) {
                    BookmarkInsertion tModel = response.body();
                    Toast.makeText(NewsDetailCategoryActivity.this, tModel.getMessage(), Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onFailure(Call<BookmarkInsertion> call, Throwable t) {
                    Log.d(Constant.TAG, "Failure Bookmarks Insertion: "+t.getMessage());
                }
            });
        } else {
            Toast.makeText(NewsDetailCategoryActivity.this, "Please login for save bookmark", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(NewsDetailCategoryActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }


    }

    private void callCatApi(){
        Call<CategoryMessage> call = RetrofitClient.getInstance().getApi().getNewsById(strCatId);
        call.enqueue(new Callback<CategoryMessage>() {
            @Override
            public void onResponse(Call<CategoryMessage> call, Response<CategoryMessage> response) {
                CategoryMessage tModel = response.body();
                if (!tModel.getError()) {
                    AdapterNewsListByCatDetail tAdapter = new AdapterNewsListByCatDetail(tModel.getNewsCategory(), strCatId);
                    rvNewsDetailCat.setAdapter(tAdapter);
                }
                else {
                    Toast.makeText(NewsDetailCategoryActivity.this, tModel.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CategoryMessage> call, Throwable t) {

            }
        });
    }

}

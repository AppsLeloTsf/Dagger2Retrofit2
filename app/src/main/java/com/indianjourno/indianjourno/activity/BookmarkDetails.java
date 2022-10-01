package com.indainjourno.indianjourno.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.janatasuddi.janatasuddinews.R;
import com.janatasuddi.janatasuddinews.api.RetrofitClient;
import com.janatasuddi.janatasuddinews.model.bookmarks.BookmarksDelete;
import com.janatasuddi.janatasuddinews.storage.SharedPrefManager;
import com.janatasuddi.janatasuddinews.utils.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookmarkDetails extends AppCompatActivity {
    private String strCatId;
    private String strCatName;
    private String strUserId;
    private String strNewsId;


    private String strTitle;
    private String strDate;
    private String strDetail;

    @BindView(R.id.tvBookmarkDetailTitle)
    protected TextView tvBookmarkDetailTitle;
    @BindView(R.id.ivBookmarkDetailImage)
    protected ImageView ivBookmarkDetailImage;
    @BindView(R.id.tvBookmarkDetailDate)
    protected TextView tvBookmarkDetailDate;
    @BindView(R.id.tvBookmarkDetail)
    protected TextView tvBookmarkDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark_details);
        ButterKnife.bind(this);
        initActivity();
    }
    private void initActivity() {
        SharedPrefManager tSharedPrefManager = new SharedPrefManager(this);
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
                .into(ivBookmarkDetailImage);
        tvBookmarkDetailTitle.setText(strTitle);
        tvBookmarkDetailDate.setText(strDate);
        Spanned htmlAsSpanned = Html.fromHtml(strDetail);
        tvBookmarkDetail.setText(htmlAsSpanned);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabActivityBookmarkDetail);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                Intent intent = new Intent(BookmarkDetails.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @OnClick(R.id.ivBookmarkDetailShare)
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
    @OnClick(R.id.ivBookmarkDetailDelete)
    public void ivBookmarkDetailDeleteClicked(){
     Call<BookmarksDelete> call = RetrofitClient.getInstance().getApi().bookmarkDelete(strUserId,strNewsId);
     call.enqueue(new Callback<BookmarksDelete>() {
         @Override
         public void onResponse(Call<BookmarksDelete> call, Response<BookmarksDelete> response) {
             BookmarksDelete tModel = response.body();
             if (!tModel.getError()) {
                 Toast.makeText(BookmarkDetails.this, tModel.getMessage(), Toast.LENGTH_SHORT).show();
                 startActivity(new Intent(BookmarkDetails.this, BookmarkActivity.class));
                 finishAffinity();
             } else{
                 Toast.makeText(BookmarkDetails.this, tModel.getMessage(), Toast.LENGTH_SHORT).show();
         }
         }

         @Override
         public void onFailure(Call<BookmarksDelete> call, Throwable t) {

         }
     });
    }

}

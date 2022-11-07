package com.indianjourno.indianjourno.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.indianjourno.indianjourno.adapter.AdapterBookmarkShow;
import com.indianjourno.indianjourno.api.RetrofitClient;
import com.indianjourno.indianjourno.model.bookmarks.BookmarksMessage;
import com.indianjourno.indianjourno.storage.SharedPrefManager;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import indianjourno.indianjourno.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookmarkActivity extends AppCompatActivity {
    private SharedPrefManager tSharedPrefManager;
    private String strUserId;
    @BindView(R.id.toolbarBookmark)
    protected Toolbar toolbarBookmark;
    @BindView(R.id.rvBookmarkNews)
    protected RecyclerView rvBookmarkNews;
    @BindView(R.id.pbBookmarkNews)
    protected ProgressBar pbBookmarkNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);
        ButterKnife.bind(this);
        setSupportActionBar(toolbarBookmark);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        pbBookmarkNews.setVisibility(View.VISIBLE);
        tSharedPrefManager = new SharedPrefManager(this);
        strUserId = tSharedPrefManager.getUserId();
        rvBookmarkNews.setLayoutManager(new LinearLayoutManager(this));
        callBookmarkApi();

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void callBookmarkApi(){
        Call<BookmarksMessage> call = RetrofitClient.getInstance().getApi().bookmarkFetch(strUserId);
        call.enqueue(new Callback<BookmarksMessage>() {
            @Override
            public void onResponse(Call<BookmarksMessage> call, Response<BookmarksMessage> response) {
                BookmarksMessage tModel = response.body();
                pbBookmarkNews.setVisibility(View.GONE);
                if (!tModel.getError()){
                AdapterBookmarkShow tAdapter = new AdapterBookmarkShow(tModel.getBookmark());
                rvBookmarkNews.setAdapter(tAdapter);
                }else {
                    Toast.makeText(BookmarkActivity.this, tModel.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BookmarksMessage> call, Throwable t) {

            }
        });
    }
}
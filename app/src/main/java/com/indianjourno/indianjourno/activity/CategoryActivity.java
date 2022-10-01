package com.indainjourno.indianjourno.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.janatasuddi.janatasuddinews.R;
import com.janatasuddi.janatasuddinews.adapter.AdapterNewsListByCat;
import com.janatasuddi.janatasuddinews.adapter.AdapterVideoCatList;
import com.janatasuddi.janatasuddinews.adapter.AdapterVideoList;
import com.janatasuddi.janatasuddinews.api.RetrofitClient;
import com.janatasuddi.janatasuddinews.model.ModelAllNews;
import com.janatasuddi.janatasuddinews.model.category.CategoryMessage;
import com.janatasuddi.janatasuddinews.model.video_cat.ModelVideoCat;
import com.janatasuddi.janatasuddinews.model.videos.ModelVideos;
import com.janatasuddi.janatasuddinews.utils.Constant;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends AppCompatActivity {

    private static final String TSF_TAG = "TsfTag";
    String strCatId;
    String strVideoCatId;
    String strCatName;
    private AlertDialog.Builder tBuilder;
    private List<ModelAllNews> tModels;
    @BindView(R.id.toolbarMainCat)
    protected Toolbar toolbarMainCat;

    @BindView(R.id.rvMainCategory)
    protected RecyclerView rvMainCategory;
    @BindView(R.id.tvToolbarCat)
    protected TextView tvToolbarCat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);
        setSupportActionBar(toolbarMainCat);
        tBuilder = new AlertDialog.Builder(this);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        strCatId = getIntent().getStringExtra(Constant.CAT_ID);
        strVideoCatId = getIntent().getStringExtra(Constant.VIDEO_ID);
        strCatName = getIntent().getStringExtra(Constant.CAT_NAME);
        tvToolbarCat.setText(strCatName);
        if (strCatId.equals("18")){
            callVideoCatListApi(this);
        }else if (strCatId.equals("CALL_VIDEO_API")){
            callVideoApi(this);
        } else {
            callCatApi(this);
        }

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void callCatApi(Context context){
        Call<CategoryMessage> call = RetrofitClient.getInstance().getApi().getNewsById(strCatId);
       call.enqueue(new Callback<CategoryMessage>() {
           @Override
           public void onResponse(Call<CategoryMessage> call, Response<CategoryMessage> response) {
               Log.d(TSF_TAG, "Cat Api Called");
               CategoryMessage tModel = response.body();
               if (!tModel.getError()) {
                   rvMainCategory.setLayoutManager(new LinearLayoutManager(context));
                   AdapterNewsListByCat tAdapter = new AdapterNewsListByCat(tModel.getNewsCategory(), strCatId);
                   rvMainCategory.setAdapter(tAdapter);
               }
               else {
                   tBuilder.setMessage("News related to this will publish soon.")
                           .setPositiveButton("Back", (dialog, which) -> {
                               startActivity(new Intent(CategoryActivity.this, MainActivity.class));
                               finish();
                           });
                   AlertDialog tAlert = tBuilder.create();
                   tAlert.setTitle("Coming soon");
                   tAlert.show();
                   Toast.makeText(CategoryActivity.this, "News related to this will publish soon. ", Toast.LENGTH_SHORT).show();
               }
           }

           @Override
           public void onFailure(Call<CategoryMessage> call, Throwable t) {

           }
       });
    }
    private void callVideoCatListApi(Context context) {
        Call<ModelVideoCat> call = RetrofitClient.getInstance().getApi().getVideoCatList();
        call.enqueue(new Callback<ModelVideoCat>() {
            @Override
            public void onResponse(Call<ModelVideoCat> call, Response<ModelVideoCat> response) {
                ModelVideoCat  tModelVideos = response.body();
                setProgressBarIndeterminate(false);
                rvMainCategory.setLayoutManager(new GridLayoutManager(context, 2));
                AdapterVideoCatList tAdapterVideoCatList = new AdapterVideoCatList(CategoryActivity.this,tModelVideos.getVideoCategory());
                rvMainCategory.setAdapter(tAdapterVideoCatList);
            }

            @Override
            public void onFailure(Call<ModelVideoCat> call, Throwable t) {

            }
        });    }
    private void callVideoApi(Context context) {

        {
            Call<ModelVideos> call = RetrofitClient.getInstance().getApi().getVideos(strVideoCatId);
            call.enqueue(new Callback<ModelVideos>() {
                @Override
                public void onResponse(Call<ModelVideos> call, Response<ModelVideos> response) {
                    Log.d(TSF_TAG, "Video Api Called");
                    ModelVideos tModel = response.body();
                    if (!(tModel != null ? tModel.getError() : null)) {
                        rvMainCategory.setLayoutManager(new LinearLayoutManager(context));
                        AdapterVideoList tAdapter = new AdapterVideoList(tModel.getVideos(), strVideoCatId);
                        rvMainCategory.setAdapter(tAdapter);
                    } else {
                        tBuilder.setMessage("News related to this will publish soon.")
                                .setPositiveButton("Back", (dialog, which) -> {
                                    startActivity(new Intent(CategoryActivity.this, MainActivity.class));
                                    finish();
                                });
                        AlertDialog tAlert = tBuilder.create();
                        tAlert.setTitle("Coming soon");
                        tAlert.show();
                        Toast.makeText(CategoryActivity.this, "News related to this will publish soon. ", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ModelVideos> call, Throwable t) {

                }
            });
        }
    }
}

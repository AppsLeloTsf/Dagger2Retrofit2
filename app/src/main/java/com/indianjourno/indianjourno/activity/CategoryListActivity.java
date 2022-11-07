package com.indianjourno.indianjourno.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.indianjourno.indianjourno.adapter.AdapterHomeCategory;
import com.indianjourno.indianjourno.adapter.ij.AdapterCategoryList;
import com.indianjourno.indianjourno.api.RetrofitClient;
import com.indianjourno.indianjourno.model.category.CategoryList;
import com.indianjourno.indianjourno.model.ij_category.ModelCategory;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import indianjourno.indianjourno.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryListActivity extends AppCompatActivity {

    @BindView(R.id.rvNewsCat)
    protected RecyclerView rvNewsCat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list_activiy);
        ButterKnife.bind(this);
        rvNewsCat.setLayoutManager(new LinearLayoutManager(this));

        callAllCatList();
    }
//        private void callHomeCat(Context context){
//        Call<CategoryList> call = RetrofitClient.getInstance().getApi().getCategory();
//        call.enqueue(new Callback<CategoryList>() {
//            @Override
//            public void onResponse(Call<CategoryList> call, Response<CategoryList> response) {
//                CategoryList tModel = response.body();
//                AdapterHomeCategory tAdapter = new AdapterHomeCategory(context, tModel.getCategory());
//                rvNewsCat.setAdapter(tAdapter);
//            }
//
//            @Override
//            public void onFailure(Call<CategoryList> call, Throwable t) {
//
//            }
//        });
//    }
        private void callAllCatList(){
        Call <ModelCategory> call = RetrofitClient.getInstance().getApi().getAllCategoryIj();

        call.enqueue(new Callback<ModelCategory>() {
            @Override
            public void onResponse(Call<ModelCategory> call, Response<ModelCategory> response) {
               ModelCategory tModel = response.body();
                AdapterCategoryList tAdapter = new AdapterCategoryList(tModel.getCategory());
                rvNewsCat.setAdapter(tAdapter);
            }

            @Override
            public void onFailure(Call<ModelCategory> call, Throwable t) {

            }
        });
    }
}
package com.indianjourno.indianjourno.adapter.ij;

import static java.security.AccessController.getContext;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.indianjourno.indianjourno.activity.CategoryActivity;

import com.indianjourno.indianjourno.activity.ij.news.NewsByCatActivity;
import com.indianjourno.indianjourno.model.ij_category.Category;
import com.indianjourno.indianjourno.utils.Constant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import indianjourno.indianjourno.R;

public class AdapterCategoryList extends RecyclerView.Adapter<AdapterCategoryList.MenuCategoryViewHolder>{

    private Context tContext;
    private final List<Category> tModels;

    public AdapterCategoryList(List<Category> tModels) {
        this.tModels = tModels;
    }

    @NonNull
    @Override
    public MenuCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.frag_home_cat_item, viewGroup, false);
       tContext = (Activity)view.getContext();
        return new MenuCategoryViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull final MenuCategoryViewHolder menuCategoryViewHolder, final int i) {
        final Category tModel = tModels.get(i);
        menuCategoryViewHolder.tvFragHome.setText(tModel.getCategoryName());
        String strImgUrl = tModel.getCatImage();
        Log.d(Constant.TAG, "Image Url: "+strImgUrl);
        Glide.with(tContext)
                .load(Constant.IMAGE_CATEGORY_IJ+strImgUrl)
                .skipMemoryCache(true)
                .into(menuCategoryViewHolder.ivFragHome);
        menuCategoryViewHolder.ivFragHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             final String  strCatId = tModel.getCategoryId();
             final String  strCatName = tModel.getCategoryName();
                Intent tIntent = new Intent(tContext, NewsByCatActivity.class);
                tIntent.putExtra(Constant.CAT_ID, strCatId);
                tIntent.putExtra(Constant.CAT_NAME, strCatName);
                tIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                tContext.startActivity(tIntent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return tModels.size();
    }

    public class MenuCategoryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivFragHomeCat)
        protected ImageView ivFragHome;
        @BindView(R.id.tvFragHomeCat)
        protected TextView tvFragHome;
        public MenuCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

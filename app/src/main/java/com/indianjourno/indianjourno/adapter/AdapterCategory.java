package com.indianjourno.indianjourno.adapter;

import android.content.Context;
import android.content.Intent;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.indianjourno.indianjourno.activity.CategoryActivity;
import com.indianjourno.indianjourno.model.category.Category;
import com.indianjourno.indianjourno.utils.Constant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import indianjourno.indianjourno.R;

public class AdapterCategory extends RecyclerView.Adapter<AdapterCategory.MenuCategoryViewHolder>{

    private String strWeekId;
    private Context tContext;
    private List<Category> tModelsCategory;
    private FragmentManager tFragmentManager;
    private DrawerLayout drawerLayout;
    private int weekSize;

    public AdapterCategory(Context tContext, List<Category> tModelsCategory, DrawerLayout drawerLayout) {
        this.tContext = tContext;
        this.tModelsCategory = tModelsCategory;
        this.drawerLayout = drawerLayout;
    }

    @NonNull
    @Override
    public MenuCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_menu, viewGroup, false);
        return new MenuCategoryViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull final MenuCategoryViewHolder menuCategoryViewHolder, final int i) {
        final Category tModelCat = tModelsCategory.get(i);
        menuCategoryViewHolder.tvTitleNavMenu.setText(tModelCat.getCategoryName());
        String strImgUrl = tModelCat.getCategoryIcon();
        Log.d(Constant.TAG, "Image Url: "+strImgUrl);
        Glide.with(tContext)
                .load(Constant.IMG_URL_CATEGORY+strImgUrl)
                .skipMemoryCache(true)
                .into(menuCategoryViewHolder.ivCatIcon);
        menuCategoryViewHolder.tvTitleNavMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
             final String  strCatId = tModelCat.getCategoryId();
             final String  strCatName = tModelCat.getCategoryName();
                Intent tIntent = new Intent(tContext, CategoryActivity.class);
                tIntent.putExtra(Constant.CAT_ID, strCatId);
                tIntent.putExtra(Constant.CAT_NAME, strCatName);
                tIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                tContext.startActivity(tIntent);
            }
        });
    }
    @Override
    public int getItemCount() {
        Log.d(Constant.TAG, "Cat Size : "+tModelsCategory.size());
        return tModelsCategory.size();
    }

    public class MenuCategoryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivCatIcon)
        protected ImageView ivCatIcon;
        @BindView(R.id.tvTitleNavMenu)
        protected TextView tvTitleNavMenu;
        public MenuCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

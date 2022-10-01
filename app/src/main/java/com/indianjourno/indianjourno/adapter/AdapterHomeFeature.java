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
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.indianjourno.indianjourno.activity.FeatureActivity;
import com.indianjourno.indianjourno.model.feature.FeaturesID;
import com.indianjourno.indianjourno.utils.Constant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import indianjourno.indianjourno.R;

public class AdapterHomeFeature extends RecyclerView.Adapter<AdapterHomeFeature.MenuCategoryViewHolder>{

    private String strWeekId;
    private Context tContext;
    private List<FeaturesID> tModels;
    private FragmentManager tFragmentManager;
    private DrawerLayout drawerLayout;
    private int weekSize;

    public AdapterHomeFeature(Context tContext, List<FeaturesID> tModels) {
        this.tContext = tContext;
        this.tModels = tModels;
        this.drawerLayout = drawerLayout;
    }

    @NonNull
    @Override
    public MenuCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.frag_home_feature_item, viewGroup, false);
        return new MenuCategoryViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull final MenuCategoryViewHolder menuCategoryViewHolder, final int i) {
        final FeaturesID tModel = tModels.get(i);
        menuCategoryViewHolder.tvFragHome.setText(tModel.getNewsTypeName());
        String strImgUrl = tModel.getImage();
        Glide.with(tContext)
                .load(Constant.IMG_URL_FEATURE_IMAGES+strImgUrl)
                .skipMemoryCache(true)
                .into(menuCategoryViewHolder.ivFragHome);
        menuCategoryViewHolder.ivFragHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             final String  strCatId = tModel.getNewsTypeId();
             final String  strCatName = tModel.getNewsTypeName();
                Intent tIntent = new Intent(tContext, FeatureActivity.class);
                tIntent.putExtra(Constant.CAT_ID, strCatId);
                tIntent.putExtra(Constant.CAT_NAME, strCatName);
                tIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                tContext.startActivity(tIntent);
            }
        });
    }
    @Override
    public int getItemCount() {
        Log.d(Constant.TAG, "Cat Size : "+tModels.size());
        return tModels.size();
    }

    public class MenuCategoryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivFragHomeFeature)
        protected ImageView ivFragHome;
        @BindView(R.id.tvFragHomeFeature)
        protected TextView tvFragHome;
        public MenuCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

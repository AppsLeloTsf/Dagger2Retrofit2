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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.indianjourno.indianjourno.activity.CategoryActivity;
import com.indianjourno.indianjourno.activity.VideoPlayActivity;
import com.indianjourno.indianjourno.model.video_cat.VideoCategory;
import com.indianjourno.indianjourno.utils.Constant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import indianjourno.indianjourno.R;

public class AdapterVideoCatList extends RecyclerView.Adapter<AdapterVideoCatList.MenuCategoryViewHolder>{

    private Context tContext;
    private List<VideoCategory> tModels;

    public AdapterVideoCatList(Context tContext, List<VideoCategory> tModels) {
        this.tContext = tContext;
        this.tModels = tModels;
    }

    @NonNull
    @Override
    public MenuCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_video_list, viewGroup, false);
        return new MenuCategoryViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull final MenuCategoryViewHolder menuCategoryViewHolder, final int i) {
        final VideoCategory tModel = tModels.get(i);
        menuCategoryViewHolder.tvNewsTitle.setText(tModel.getVidCatName());
        String strImgUrl = tModel.getVidCatImage();
        Log.d(Constant.TAG, "Image Url: "+strImgUrl);
        Glide.with(tContext)
                .load(Constant.IMG_URL_CATEGORY_IMAGES+strImgUrl)
                .skipMemoryCache(true)
                .into(menuCategoryViewHolder.ivNewsImage);
        menuCategoryViewHolder.ivNewsImage.setOnClickListener(v -> {
         final String  strCatId = tModel.getVidCatId();
         final String  strCatName = tModel.getVidCatName();
            Intent tIntent = new Intent(tContext, VideoPlayActivity.class);
            tIntent.putExtra(Constant.CAT_ID, tContext.getString(R.string.call_video_api));
            tIntent.putExtra(Constant.VIDEO_ID, strCatId);
            tIntent.putExtra(Constant.CAT_NAME, strCatName);
            tIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            tContext.startActivity(tIntent);
        });
    }

    @Override
    public int getItemCount() {
        Log.d(Constant.TAG, "Cat Size : "+tModels.size());
        return tModels.size();
    }

    public static class MenuCategoryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ivVideoListImage)
        protected ImageView ivNewsImage;
        @BindView(R.id.tvVideoListTitle)
        protected TextView tvNewsTitle;
        public MenuCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

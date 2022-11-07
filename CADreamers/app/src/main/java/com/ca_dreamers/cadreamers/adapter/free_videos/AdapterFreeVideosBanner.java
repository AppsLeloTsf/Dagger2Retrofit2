package com.ca_dreamers.cadreamers.adapter.free_videos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.models.free_videos.banners.Datum;

import java.util.List;
import java.util.Objects;

public class AdapterFreeVideosBanner extends PagerAdapter {


    private List<Datum> images;
    private Context context;
    private Datum tModel;


    public AdapterFreeVideosBanner(List<Datum> images, Context context) {

        this.images = images;
        this.context = context;
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.banners_free_videos, container, false);

        tModel = images.get(position);
        // referencing the image view from the item.xml file
        ImageView imageView = (ImageView) itemView.findViewById(R.id.ivBannerFreeVideos);
        Glide.with(context)
                .load(tModel.getImageUrl())
                .skipMemoryCache(true)
                .into(imageView);
        // Adding the View
        Objects.requireNonNull(container).addView(itemView);
        return itemView;

    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((CardView) object);

    }

    @Override
    public int getCount() {

        return images.size();
//        return images.size();
    }
    @Override
    public void destroyItem(View container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

}

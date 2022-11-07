package com.ca_dreamers.cadreamers.adapter.top_20;

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
import com.ca_dreamers.cadreamers.models.top_20.banners.Datum;

import java.util.List;
import java.util.Objects;

public class AutoSliderTop20Adapter extends PagerAdapter {


    private final List<Datum> images;
    private final Context context;


    public AutoSliderTop20Adapter(List<Datum> images, Context context) {

        this.images = images;
        this.context = context;
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.banners_top_20, container, false);

        Datum tModel = images.get(position);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.ivTop20Banner);
        Glide.with(context)
                .load(tModel.getImageUrl())
                .skipMemoryCache(true)
                .into(imageView);
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
    }


    @Override
    public void destroyItem (@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((ViewPager) container).removeView((View) object);
    }

}

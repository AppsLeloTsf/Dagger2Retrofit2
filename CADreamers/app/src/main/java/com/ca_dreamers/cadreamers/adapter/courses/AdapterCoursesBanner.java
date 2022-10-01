package com.ca_dreamers.cadreamers.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.models.banners.Datum;
import com.ca_dreamers.cadreamers.utils.Constant;


import java.util.List;
import java.util.Objects;

public class AutoScrollPagerAdapter extends PagerAdapter {


    private List<Datum> images;
    private Context context;
    private Datum tModel;
    private LayoutInflater mLayoutInflater;


    public AutoScrollPagerAdapter(List<Datum> images, Context context) {

        this.images = images;
        this.context = context;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
//    @Override
//    public Fragment getItem(int position) {
//        // Return a SlideFragment (defined as a static inner class below).
//
//        return SlideFragment.newInstance(position + 1);
//
//    }



    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.fragment_slide, container, false);

        tModel = images.get(position);
        // referencing the image view from the item.xml file
        ImageView imageView = (ImageView) itemView.findViewById(R.id.ivFragSlider);
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

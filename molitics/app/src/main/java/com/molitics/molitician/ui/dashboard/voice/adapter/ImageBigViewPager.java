package com.molitics.molitician.ui.dashboard.voice.adapter;

import android.content.Context;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.molitics.molitician.R;
import com.molitics.molitician.customView.TouchImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rahul on 27/11/17.
 */

public class ImageBigViewPager extends PagerAdapter {

    @BindView(R.id.issue_image)
    TouchImageView issue_image;
    @BindView(R.id.download_image)
    ImageView download_image;
    @BindView(R.id.share_image)
    ImageView share_image;

    ImageListener imageListener;

    Context mContext;

    List<String> imageList;

    public ImageBigViewPager(Context mContext, List<String> imageList, ImageListener imageListener) {
        this.mContext = mContext;
        this.imageList = imageList;
        this.imageListener = imageListener;
    }


    public interface ImageListener {
        void imageAction(int action, String url);
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.issue_image, container, false);
        ButterKnife.bind(this, itemView);


        if (!TextUtils.isEmpty(imageList.get(position)))
            Picasso.with(mContext).load(imageList.get(position)).placeholder(R.drawable.image_placeholder).error(R.drawable.internet_no_cloud).into(issue_image);
        else
            issue_image.setImageResource(R.drawable.image_placeholder);

        download_image.setOnClickListener(v -> imageListener.imageAction(1, imageList.get(position)));

        share_image.setOnClickListener(v -> imageListener.imageAction(0, imageList.get(position)));

        container.addView(itemView, 0);
        return itemView;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}

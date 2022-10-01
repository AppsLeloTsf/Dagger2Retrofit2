package com.molitics.molitician.ui.dashboard.national_news.placeholder;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Build;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.molitics.molitician.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rahul on 12/11/17.
 */

public class LeaderViewPlaceholderAdapter extends RecyclerView.Adapter<LeaderViewPlaceholderAdapter.LeaderPlaceViewHolder> {

    Context mContext;

    public LeaderViewPlaceholderAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public LeaderPlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.placeholder_leader_trending_adapter, parent, false);
        return new LeaderPlaceViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final LeaderPlaceViewHolder holder, int position) {
        // Check if we're running on Android 5.0 or higher
        if (Build.VERSION.SDK_INT >= 16) {
            // Call some material design APIs here
            holder.shine.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.placeholder_null_view_animatioin));
        } else {
            // Implement this feature without material design
            holder.shine.setImageDrawable(mContext.getResources().getDrawable(R.drawable.placeholder_null_view_animatioin));
        }
        // Check if we're running on Android 5.0 or higher
        if (Build.VERSION.SDK_INT >= 16) {
            // Call some material design APIs here
            holder.leader_name.setBackground(ContextCompat.getDrawable(mContext, R.drawable.placeholder_null_view_animatioin));
        } else {
            // Implement this feature without material design
            holder.leader_name.setBackground(mContext.getResources().getDrawable(R.drawable.placeholder_null_view_animatioin));
        }

        ObjectAnimator imageAnimator = ObjectAnimator.ofFloat(holder.shine, "translationX", 250f);
        imageAnimator.setDuration(1000);
        imageAnimator.setInterpolator(new LinearInterpolator());
        imageAnimator.setRepeatCount(Animation.INFINITE);
        imageAnimator.start();


        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(holder.leader_name, "translationX", 250f);
        objectAnimator.setDuration(1000);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.setRepeatCount(Animation.INFINITE);
        objectAnimator.start();
    }

    @Override
    public int getItemCount() {
        return 7;
    }

    public class LeaderPlaceViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ll_main)
        LinearLayout ll_main;
        @BindView(R.id.leader_name)
        TextView leader_name;
        @BindView(R.id.shine)
        ImageView shine;

        public LeaderPlaceViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

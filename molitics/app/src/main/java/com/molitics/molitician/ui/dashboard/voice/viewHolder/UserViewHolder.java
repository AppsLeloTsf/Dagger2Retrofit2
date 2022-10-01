package com.molitics.molitician.ui.dashboard.voice.viewHolder;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.molitics.molitician.R;
import com.molitics.molitician.ui.dashboard.voice.model.UsersFeedModel;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserViewHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.creator_image)
    public CircleImageView creator_image;
    @BindView(R.id.user_name)
    public TextView user_name;

    public UserViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindView(Context mContext, UserViewHolder viewHolder, UsersFeedModel usersFeedModel) {
        if (usersFeedModel != null) {
           if (!TextUtils.isEmpty(usersFeedModel.getImage())) {
                Picasso.with(mContext).load(usersFeedModel.getImage()).into(viewHolder.creator_image);
            } else {
               viewHolder.creator_image.setImageResource(R.drawable.sample_image);
            }
            if (!TextUtils.isEmpty(usersFeedModel.getName())) {
                viewHolder.user_name.setText(usersFeedModel.getName().trim());
            } else {
                viewHolder.user_name.setText(mContext.getString(R.string.anonymous));
            }
        }
    }
}

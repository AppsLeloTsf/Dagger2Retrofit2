package com.molitics.molitician.ui.dashboard.voice.feedAction;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.molitics.molitician.R;
import com.molitics.molitician.ui.dashboard.voice.VoiceCreatorProfile;
import com.molitics.molitician.util.Constant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by rahul on 03/04/18.
 */

public class FeedActionParticipantAdapter extends RecyclerView.Adapter<FeedActionParticipantAdapter.FeedActionViewHolder> {

    List<FeedActionParticipantModel> participantModelList = new ArrayList<>();

    Context mContext;
    FeedActionParticipantAdapter.UserProfileListener userProfileListener;

    public FeedActionParticipantAdapter(Context mContext, FeedActionParticipantAdapter.UserProfileListener userProfileListener) {
        this.mContext = mContext;
        this.userProfileListener = userProfileListener;
    }

    public interface UserProfileListener {

        void onUserFollow(int user_id, int position);

        void onUserUnFollow(int user_id, int position);

        void onProfileImageClick(String image);
    }


    public void addFeesList(List<FeedActionParticipantModel> participantModelList) {
        this.participantModelList = participantModelList;
        notifyDataSetChanged();
    }

    public void followUser(Boolean following, int count, int position) {
        if (following) {

            participantModelList.get(position).setIsFollowing(1);
        } else {
            participantModelList.get(position).setIsFollowing(0);

        }

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FeedActionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_feed_action_participant,
                parent, false);
        return new FeedActionViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedActionViewHolder holder, int position) {

        FeedActionParticipantModel participantModel = participantModelList.get(position);

        holder.user_name_view.setText(participantModel.getUserName());

        holder.user_location_view.setText(participantModel.getUserLocation());

        if (participantModel.getIsFollowing() == 1) {
            holder.follow_unfollow_button.setText("Following");
            holder.follow_unfollow_button.setBackgroundColor(mContext.getResources().getColor(R.color.following_leader));

        } else {
            holder.follow_unfollow_button.setText("Follow");
            holder.follow_unfollow_button.setBackgroundColor(mContext.getResources().getColor(R.color.follow_leader));
        }

        if (!TextUtils.isEmpty(participantModel.getUserImage())) {
            Picasso.with(mContext).load(participantModel.getUserImage()).placeholder(R.drawable.sample_image).into(holder.user_image_view);
        } else {
            holder.user_image_view.setImageResource(R.drawable.sample_image);
        }
        holder.user_image_view.setOnClickListener(v -> {
            Intent mIntent = new Intent(mContext, VoiceCreatorProfile.class);
            mIntent.putExtra(Constant.IntentKey.USER_ID, participantModel.getUser_id());
            mContext.startActivity(mIntent);
        });
        holder.user_name_view.setOnClickListener(v -> {
            Intent mIntent = new Intent(mContext, VoiceCreatorProfile.class);
            mIntent.putExtra(Constant.IntentKey.USER_ID, participantModel.getUser_id());
            mContext.startActivity(mIntent);
        });

        holder.follow_unfollow_button.setOnClickListener(v -> {

            if (participantModel.getIsFollowing() == 0) {
                userProfileListener.onUserFollow(participantModel.getUser_id(), position);
            } else {
                userProfileListener.onUserUnFollow(participantModel.getUser_id(), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return participantModelList.size();
    }

    class FeedActionViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.user_image_view)
        CircleImageView user_image_view;
        @BindView(R.id.follow_unfollow_button)
        TextView follow_unfollow_button;
        @BindView(R.id.user_location_view)
        TextView user_location_view;
        @BindView(R.id.user_name_view)
        TextView user_name_view;


        public FeedActionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

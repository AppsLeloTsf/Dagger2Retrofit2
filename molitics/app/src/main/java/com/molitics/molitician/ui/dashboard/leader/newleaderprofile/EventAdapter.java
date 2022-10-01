package com.molitics.molitician.ui.dashboard.leader.newleaderprofile;

import android.content.Context;
import android.net.Uri;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.molitics.molitician.R;
import com.molitics.molitician.ui.dashboard.leader.leaderProfile.CandidateProfileModel;
import com.molitics.molitician.ui.dashboard.leader.leaderProfile.Event;
import com.molitics.molitician.ui.dashboard.voice.adapter.ImageViewPager;
import com.molitics.molitician.ui.dashboard.voice.adapter.VoiceAllAdapter;
import com.molitics.molitician.ui.dashboard.voice.model.VoiceAllModel;
import com.molitics.molitician.ui.dashboard.voice.viewHolder.VoiceAllViewHolder;
import com.molitics.molitician.util.ExtraUtils;
import com.molitics.molitician.util.StringUtils;
import com.molitics.molitician.util.Util;
import com.molitics.molitician.util.VideoExpoPlayer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rahul on 6/28/2017.
 */

public class EventAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements
        ImageViewPager.VoiceImageListener, ExtraUtils.NotifyCustomDataSetChanged {

    private String TAG = "EventAdapter";
    private List<Event> mEventList = new ArrayList<>();
    private Context mContext;
    private LeaderEventListener mListener;
    private int image_position = 0;
    private int EVENT = 1;
    private int VOICE = 2;
    private int STATUS = 3;
    private VoiceAllAdapter.VoiceInterFace voiceInterFace;
    boolean isReadMoreVisible = true;
    private SimpleExoPlayer player;


    @Override
    public void onImageClick(int position, int issue_id, List<String> imageList) {
        voiceInterFace.onImageClick(null, issue_id, position, imageList);
    }

    @Override
    public void onVideoPLay() {

    }

    @Override
    public void onVideoPause() {

    }

    @Override
    public void onShareClick(String url) {
    }

    @Override
    public void onDownloadClick(String url) {
    }


    @Override
    public void notifyCustom(int image_position) {
        notifyDataSetChanged();
    }

    public interface LeaderEventListener {
        void onRemindMeClick(String time, String name, String address);

        void shareEvent(String title, String address, String date);

        void onMoreFeedClick(int page_count);
    }

    public EventAdapter(Context mContext, LeaderEventListener mListener, VoiceAllAdapter.VoiceInterFace voiceInterFace) {
        this.mContext = mContext;
        this.mListener = mListener;
        this.voiceInterFace = voiceInterFace;
    }

    public void setEvent(List<Event> mEvent) {

        this.mEventList.addAll(mEvent);
        notifyDataSetChanged();
    }

    public void clearAdapter() {
        mEventList.clear();
    }

    public void setVoice(List<VoiceAllModel> voiceAllModels) {
        isReadMoreVisible = voiceAllModels.size() > 9;
        for (VoiceAllModel voiceAllModel : voiceAllModels) {
            Event mEvent = new Event();
            mEvent.setVoiceAllModel(voiceAllModel);
            mEventList.add(mEvent);
            notifyDataSetChanged();
        }
    }

    public void setStatus(CandidateProfileModel candidateProfileModel) {
        Event mEvent = new Event();
        mEvent.setCandidateProfileModel(candidateProfileModel);
        mEventList.add(mEvent);
    }

    public void updateVoiceModel(int position, VoiceAllModel voiceAllModel) {
        if (mEventList.size() != 0) {
            Event mEvent = new Event();
            mEvent.setVoiceAllModel(voiceAllModel);
            mEventList.set(position, mEvent);
            notifyItemChanged(position);
        } else {
            Event mEvent = new Event();
            mEvent.setVoiceAllModel(voiceAllModel);
            mEventList.add(mEvent);
            notifyItemChanged(position);
        }
    }

    public void removeVoiceOnTop(int position) {
        mEventList.remove(position);
        notifyDataSetChanged();
    }

    public void addVoiceModel(int position, VoiceAllModel voiceAllModel) {
        if (mEventList.size() != 0) {
            Event mEvent = new Event();
            mEvent.setVoiceAllModel(voiceAllModel);
            mEventList.add(position, mEvent);
            notifyItemChanged(position);
        }
    }

    public ArrayList<Event> getEventList() {
        return (ArrayList<Event>) mEventList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == EVENT) {
            View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_event, parent, false);
            return new EventViewHolder(mView);
        } else if (viewType == STATUS) {
            View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_status_leader, parent, false);
            return new StatusViewHolder(mView);
        } else {
            View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_voice_all, parent, false);
            return new VoiceAllViewHolder(mView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Event singleEvent = mEventList.get(position);
        if (holder.getItemViewType() == EVENT) {
            EventViewHolder mHolder = (EventViewHolder) holder;
            mHolder.event_heading.setText(singleEvent.getName());
            mHolder.event_location.setText(singleEvent.getAddress());
            mHolder.event_date.setText(singleEvent.getTime());
        } else if (holder.getItemViewType() == VOICE) {
            VoiceAllModel voiceModel = singleEvent.getVoiceAllModel();
            VoiceAllViewHolder viewHolder = (VoiceAllViewHolder) holder;

            if (voiceModel != null) {
                viewHolder.bindView(mContext, TAG, viewHolder, voiceModel, holder.getAdapterPosition(),
                        position, voiceInterFace, this, image_position);


                if (holder.getAdapterPosition() >= 9 && holder.getAdapterPosition() == mEventList.size() - 1) {
                    mListener.onMoreFeedClick(0);

                }
            }
        } else if (holder.getItemViewType() == STATUS) {
            StatusViewHolder statusViewHolder = (StatusViewHolder) holder;

            CandidateProfileModel voiceModel = singleEvent.getCandidateProfileModel();

            if (!StringUtils.isNullOrEmpty(voiceModel.getCandidate_status())) {
                statusViewHolder.status_description.setVisibility(View.VISIBLE);
                statusViewHolder.status_description.setText(voiceModel.getCandidate_status());
            } else {
                statusViewHolder.status_description.setVisibility(View.GONE);
            }

            if (!StringUtils.isNullOrEmpty(voiceModel.getStatus_url())) {
                if (voiceModel.getStatus_url().contains(".mp4")) {
                    statusViewHolder.fb_video_player.setVisibility(View.VISIBLE);
                    if (player == null)
                        player = VideoExpoPlayer.prepareVideo(mContext, statusViewHolder.fb_video_player,
                                Uri.parse(voiceModel.getStatus_url()));

                    onVideoMuteClick(statusViewHolder.video_mute_button);
                    statusViewHolder.video_mute_button.setOnClickListener(v -> onVideoMuteClick(statusViewHolder.video_mute_button));

                } else {
                    statusViewHolder.status_image.setVisibility(View.VISIBLE);
                    Picasso.with(mContext).load(voiceModel.getStatus_url())
                            .placeholder(R.drawable.image_placeholder).error(R.drawable.error_placeholder).into(statusViewHolder.status_image);
                }
            } else {
                statusViewHolder.fb_video_player.setVisibility(View.GONE);
                statusViewHolder.status_image.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mEventList.get(position).getCandidateProfileModel() != null) {
            return STATUS;
        } else if (mEventList.get(position).getVoiceAllModel() == null)
            return EVENT;
        else
            return VOICE;
    }

    @Override
    public int getItemCount() {
        return mEventList.size();
    }

    public class StatusViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.status_description)
        TextView status_description;

        @BindView(R.id.status_image)
        ImageView status_image;

        @BindView(R.id.fb_video_player)
        PlayerView fb_video_player;

        @BindView(R.id.video_mute_button)
        ImageView video_mute_button;


        public StatusViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public class EventViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.event_heading)
        TextView event_heading;
        @BindView(R.id.event_location)
        TextView event_location;
        @BindView(R.id.event_date)
        TextView event_date;

        public EventViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void updateLikeDislike(int position, int like_count, int dislike_count, int my_action, int image_position) {
        this.image_position = image_position;
        Event localEvent = mEventList.get(position);
        VoiceAllModel voiceAllModel = localEvent.getVoiceAllModel();
        voiceAllModel.setLikes(like_count);
        voiceAllModel.setDislikes(dislike_count);
        voiceAllModel.setMyAction(my_action);
        localEvent.setVoiceAllModel(voiceAllModel);
        mEventList.set(position, localEvent);
    }

    public void onFollowResponse(int position, int count, int image_position) {
        this.image_position = image_position;
        Event localEvent = mEventList.get(position);
        VoiceAllModel voiceAllModel = localEvent.getVoiceAllModel();
        voiceAllModel.setFollowing(count);
        voiceAllModel.setIsFollowing(1);
        localEvent.setVoiceAllModel(voiceAllModel);
        mEventList.set(position, localEvent);
    }

    public void onUnFollowResponse(int position, int count, int image_position) {
        this.image_position = image_position;
        Event localEvent = mEventList.get(position);
        VoiceAllModel voiceAllModel = localEvent.getVoiceAllModel();
        voiceAllModel.setFollowing(count);
        voiceAllModel.setIsFollowing(0);
        localEvent.setVoiceAllModel(voiceAllModel);
        mEventList.set(position, localEvent);
    }

    public void deleteVoice(int position) {
        mEventList.remove(position);
        notifyDataSetChanged();
    }


    public void addCommentCount(int position, int count, int image_position) {
        this.image_position = image_position;
        Event localEvent = mEventList.get(position);
        VoiceAllModel voiceAllModel = localEvent.getVoiceAllModel();

        voiceAllModel.setComments(voiceAllModel.getComments() + count);

        localEvent.setVoiceAllModel(voiceAllModel);
        mEventList.set(position, localEvent);

        notifyDataSetChanged();
    }

    void onVideoMuteClick(ImageView video_mute_button) {
        if (player != null) {
            if (player.getVolume() == 1.0f) {
                player.setVolume(0.0f);
                video_mute_button.setImageResource(R.drawable.mute_white);
            } else {
                player.setVolume(1.0f);
                video_mute_button.setImageResource(R.drawable.volume_white);
            }
        }
    }

    public void releaseVideo() {
        if (player != null) {
            player.release();
            player = null;
        }
    }
}

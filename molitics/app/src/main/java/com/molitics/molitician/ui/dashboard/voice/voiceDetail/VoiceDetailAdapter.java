package com.molitics.molitician.ui.dashboard.voice.voiceDetail;

import android.content.Context;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.molitics.molitician.R;
import com.molitics.molitician.ui.dashboard.voice.adapter.AllCommentAdapter;
import com.molitics.molitician.ui.dashboard.voice.adapter.VoiceAllAdapter;
import com.molitics.molitician.ui.dashboard.voice.model.CommentModel;
import com.molitics.molitician.ui.dashboard.voice.model.VoiceAllModel;
import com.molitics.molitician.ui.dashboard.voice.viewHolder.VoiceAllViewHolder;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.ExtraUtils;
import com.molitics.molitician.util.PrefUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by rahul on 12/01/18.
 */

public class VoiceDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ExtraUtils.NotifyCustomDataSetChanged {

    public static final String TAG = "VoiceDetailAdapter";
    List<CommentModel> commentModels = new ArrayList<>();
    public static int VOICE_DETAILS = 0;
    public static int VOICE_COMMENT = 1;
    private VoiceAllModel voiceAllModel;
    private AllCommentAdapter.CommentAdapterListener commentAdapterListener;
    private Context mContext;
    private VoiceAllAdapter.VoiceInterFace voiceInterFace;
    private int image_position = 0;

    VoiceDetailAdapter(Context mContext, VoiceAllAdapter.VoiceInterFace voiceInterFace, AllCommentAdapter.CommentAdapterListener commentAdapterListener) {
        this.mContext = mContext;
        this.voiceInterFace = voiceInterFace;
        this.commentAdapterListener = commentAdapterListener;
    }

    public void setVoiceDetail(VoiceAllModel voiceDetail) {
        this.voiceAllModel = voiceDetail;
        notifyDataSetChanged();
    }

    public VoiceAllModel getVoiceDetail() {
        return voiceAllModel;
    }

    public void addAllComments(List<CommentModel> commentModels) {
        Collections.reverse(commentModels);
        this.commentModels.addAll(commentModels);
        notifyDataSetChanged();
    }

    public void editCommentModel(int position, CommentModel commentModel) {
        commentModel.setEdited(1);
        commentModels.set(position, commentModel);
        notifyDataSetChanged();
    }

    public void deleteComment(int position) {
        commentModels.remove(position);
        notifyDataSetChanged();
    }

    public void addCommentBottom(CommentModel commentModel) {
        this.commentModels.add(commentModel);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == VOICE_DETAILS) {
            View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_voice_all, parent, false);

            return new VoiceAllViewHolder(mView);
        } else {
            View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_comments, parent, false);

            return new CommentViewHolder(mView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        if (viewHolder.getItemViewType() == VOICE_DETAILS) {

            VoiceAllViewHolder holder = (VoiceAllViewHolder) viewHolder;

            if (voiceAllModel != null) {

                holder.bindView(mContext, TAG, holder, voiceAllModel, viewHolder.getAdapterPosition(),
                        0, voiceInterFace, this, image_position);
            }
        } else if (viewHolder.getItemViewType() == VOICE_COMMENT) {

            CommentViewHolder holder = (CommentViewHolder) viewHolder;

            CommentModel commentModel = commentModels.get(position - VOICE_COMMENT);

            if (!TextUtils.isEmpty(commentModel.getName()))
                holder.user_name.setText(commentModel.getName());
            else holder.user_name.setText(mContext.getString(R.string.anonymous));
            holder.user_comment.setText(commentModel.getComment());
            holder.time_txt.setText(commentModel.getTime());


            if (commentModel.getLeaderReply() == 1) {
                //ToDo: comment from leader show some icon or diff
                holder.user_name.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_green_tick, 0);
            } else {
                holder.user_name.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }

            holder.user_image.setOnClickListener(v -> commentAdapterListener.onCreatorImageClick(position, commentModel.getUserId(), commentModel.getLeaderReply()));
            holder.user_name.setOnClickListener(v -> commentAdapterListener.onCreatorImageClick(position, commentModel.getUserId(), commentModel.getLeaderReply()));
            holder.ll_main.setOnClickListener(v -> {
                if (commentModel.getUserId() == PrefUtil.getInt(Constant.PreferenceKey.USER_ID))
                    commentAdapterListener.onLongClick(mContext, holder.ll_main, position, commentModel.getId(), commentModel.getComment());
            });
            holder.user_name.setOnClickListener(v -> commentAdapterListener.onCreatorImageClick(position, commentModel.getUserId(), commentModel.getLeaderReply()));

            if (TextUtils.isEmpty(commentModel.getImage())) {
                holder.user_image.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.sample_image));
            } else {
                if (commentModel.getImage().contains("http"))
                    Picasso.with(mContext).load(commentModel.getImage()).into(holder.user_image);
                else
                    ExtraUtils.setBitmapImage(mContext, holder.user_image, commentModel.getImage());
            }
            if (position == 0 && commentModels.size() > 9) {
                commentAdapterListener.onLoadMore(commentModels.size());
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == VOICE_DETAILS) {
            return VOICE_DETAILS;
        } else {
            return VOICE_COMMENT;
        }
    }

    @Override
    public int getItemCount() {
        return commentModels.size() != 0 ? commentModels.size() + VOICE_COMMENT : 1;
    }

    @Override
    public void notifyCustom(int image_position) {
        notifyDataSetChanged();
    }


    public class CommentViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.user_image)
        CircleImageView user_image;
        @BindView(R.id.user_name)
        TextView user_name;
        @BindView(R.id.user_comment)
        TextView user_comment;
        @BindView(R.id.time_txt)
        TextView time_txt;
        @BindView(R.id.ll_main)
        LinearLayout ll_main;
        @BindView(R.id.leader_star_view)
        ImageView leader_star_view;

        public CommentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public void updateLikeDislike(int like_count, int dislike_count, int my_action, int image_position) {

        this.image_position = image_position;
        voiceAllModel.setLikes(like_count);
        voiceAllModel.setDislikes(dislike_count);
        voiceAllModel.setMyAction(my_action);
    }

    public void onUnFollowResponse(int count, int image_position) {
        this.image_position = image_position;
        voiceAllModel.setFollowing(count);
        voiceAllModel.setIsFollowing(0);
    }

    public void onFollowResponse(int count, int image_position) {
        this.image_position = image_position;
        voiceAllModel.setFollowing(count);
        voiceAllModel.setIsFollowing(1);
    }

}

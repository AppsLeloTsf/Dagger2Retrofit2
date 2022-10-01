package com.molitics.molitician.ui.dashboard.leader;

import android.content.Context;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.molitics.molitician.R;
import com.molitics.molitician.interfaces.LeaderAdapterInterface;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.ExtraUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.molitics.molitician.util.Util.formatNumberToShort;
import static com.molitics.molitician.util.Util.showProgressAnimation;

public class LeaderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    @BindView(R.id.name_view)
    TextView name_view;
    @BindView(R.id.pos_view)
    TextView pos_view;

    @BindView(R.id.leader_constituency_view)
    TextView leader_constituency_view;
    @BindView(R.id.follow_button)
    TextView follow_button;

    @BindView(R.id.circle_image)
    CircleImageView circle_image;
    @BindView(R.id.LL_main_layout)
    LinearLayout LL_main_layout;
    @BindView(R.id.upcoming_event_view)
    TextView upcoming_event_view;
    @BindView(R.id.leader_verified_view)
    ImageView leader_verified_view;
    @BindView(R.id.candidate_like_view)
    TextView candidate_like_view;
    @BindView(R.id.candidate_dislike_view)
    TextView candidate_dislike_view;
    @BindView(R.id.leader_party_view)
    TextView leader_party_view;
    @BindView(R.id.party_logo)
    ImageView party_logo;
    @BindView(R.id.vote_progress_bar)
    ProgressBar vote_progress_bar;

    Context mContext;
    AllLeaderModel leaderModel;
    int position;

    LeaderAdapterInterface adapterInterface;

    public LeaderViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        follow_button.setOnClickListener(this);
        candidate_like_view.setOnClickListener(this);
        LL_main_layout.setOnClickListener(this);
        candidate_dislike_view.setOnClickListener(this);
    }

    public void onBind(Context mContext, int position, AllLeaderModel leaderModel, LeaderAdapterInterface adapterInterface) {

        this.mContext = mContext;
        this.leaderModel = leaderModel;
        this.adapterInterface = adapterInterface;
        this.position = position;

        Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "fonts/roboto_regular.ttf");
        name_view.setTypeface(tf);
        name_view.setText(leaderModel.getName());
        pos_view.setText(leaderModel.getPosition());
        leader_party_view.setText(leaderModel.getPartyCode());
        if (!TextUtils.isEmpty(leaderModel.getPartyLogo()))
            Picasso.with(mContext).load(leaderModel.getPartyLogo()).into(party_logo);

        candidate_dislike_view.setText(String.format(mContext.getString(R.string.downvote_string), formatNumberToShort(leaderModel.getDislike_count())));
        candidate_like_view.setText(String.format(mContext.getString(R.string.upvote_string), formatNumberToShort(leaderModel.getLike_count())));
        leader_constituency_view.setText(leaderModel.getLocation());


        setVoteProgressBar(leaderModel.getUser_vote_action(), leaderModel.getLike_count(), leaderModel.getDislike_count());
        if (leaderModel.getUpcoming_event() == 0)
            upcoming_event_view.setVisibility(View.GONE);
        else
            upcoming_event_view.setVisibility(View.VISIBLE);

        Picasso.with(mContext).cancelRequest(circle_image);
        setVoteView(leaderModel.getUser_vote_action(), leaderModel.getLike_count(), leaderModel.getDislike_count());

        if (leaderModel.getIsVerify() == 1) {
            leader_verified_view.setVisibility(View.VISIBLE);
        } else
            leader_verified_view.setVisibility(View.GONE);

        if (leaderModel.getProfileImage() != null && !TextUtils.isEmpty(leaderModel.getProfileImage()) && leaderModel.getProfileImage().length() > 4) {
            Picasso.with(mContext).load(leaderModel.getProfileImage()).placeholder(R.drawable.sample_image).error(R.drawable.error_placeholder).into(circle_image);
        } else {
            circle_image.setImageResource(R.drawable.sample_image);
        }


        if (leaderModel.getIsFollowing() == 1) {
            onFollow(leaderModel.getFollowers());
        } else {
            notFollow(leaderModel.getFollowers());
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.follow_button:
                if (leaderModel.getIsFollowing() == 1) {
                    //leaderModel.getFollowers() - 1
                    leaderModel.setIsFollowing(0);

                    notFollow(leaderModel.getFollowers());
                    adapterInterface.onUnFollowClick(leaderModel.getId(), position);
                } else {
                    //leaderModel.getFollowers() + 1
                    leaderModel.setIsFollowing(1);
                    MediaPlayer follow_media_player = MediaPlayer.create(mContext, R.raw.notification);
                    follow_media_player.start();
                    onFollow(leaderModel.getFollowers());
                    adapterInterface.onFollowClick(leaderModel.getId(), position);
                }
                break;

            case R.id.candidate_like_view:
                if (leaderModel.getUser_vote_action() == Constant.Action.LIKE) {
                    leaderModel.setUser_vote_action(Constant.ZERO);
                    ///setVoteView(Constant.ZERO, leaderModel.getLike_count(), leaderModel.getDislike_count());
                    adapterInterface.onDeleteClick(position, leaderModel.getId());
                } else {
                    MediaPlayer fb_likePlayer = MediaPlayer.create(mContext, R.raw.fb_like);
                    fb_likePlayer.start();
                    leaderModel.setUser_vote_action(Constant.Action.LIKE);
                    ///setVoteView(Constant.Action.LIKE, leaderModel.getLike_count(), leaderModel.getDislike_count());

                    adapterInterface.onLikeDisClick(position, leaderModel.getId(), Constant.Action.LIKE);
                }
                break;
            case R.id.candidate_dislike_view:
                if (leaderModel.getUser_vote_action() == Constant.Action.DISLIKE) {
                    leaderModel.setUser_vote_action(0);
                  //  setVoteView(Constant.ZERO, leaderModel.getLike_count(), leaderModel.getDislike_count());
                    adapterInterface.onDeleteClick(position, leaderModel.getId());
                } else {
                    MediaPlayer fb_likePlayer = MediaPlayer.create(mContext, R.raw.fb_like);
                    fb_likePlayer.start();
                    leaderModel.setUser_vote_action(Constant.Action.DISLIKE);
                  //  setVoteView(Constant.Action.DISLIKE, leaderModel.getLike_count(), leaderModel.getDislike_count());
                    adapterInterface.onLikeDisClick(position, leaderModel.getId(), Constant.Action.DISLIKE);
                }
                break;

            case R.id.LL_main_layout:
                if (leaderModel.getDisplayFeedsTab() == 1)
                    adapterInterface.onLeaderClick(getAdapterPosition(), leaderModel);
                else if (leaderModel.getDisplayNewsTab() == 1)
                    adapterInterface.onLeaderNewsClick(getAdapterPosition(), leaderModel);
                else adapterInterface.onLeaderClick(getAdapterPosition(), leaderModel);
                break;

        }

    }

    private void notFollow(String follow_count) {
        follow_button.setBackground(mContext.getResources().getDrawable(R.drawable.leader_downvote_bg));
        follow_button.setText("Follow");
    }

    private void onFollow(String follow_count) {
        follow_button.setBackground(mContext.getResources().getDrawable(R.drawable.leader_unfollow_btn));
        follow_button.setText("Following");
    }

    public void setVoteProgressBar(int action, int upvote_count, int downvote_count) {

        int totoalVoteCount = upvote_count + downvote_count;
        if (totoalVoteCount != 0) {
            ////// left to right or right to left progress bar with diff color
            if (action == Constant.Action.LIKE || action == Constant.ZERO) {
                float floatPercent = (upvote_count * 100f) / (totoalVoteCount);
                int finalPercent = Math.round(floatPercent);
                vote_progress_bar.setProgress(finalPercent);
                showProgressAnimation(vote_progress_bar, finalPercent);
                vote_progress_bar.setProgressDrawable(ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.vote_progress_bar_horizontal, null));
                vote_progress_bar.setRotation(0);
            } else {
                float floatPercent = (downvote_count * 100f) / (totoalVoteCount);
                int finalPercent = Math.round(floatPercent);
                vote_progress_bar.setProgress(finalPercent);
                showProgressAnimation(vote_progress_bar, finalPercent);
                vote_progress_bar.setProgressDrawable(ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.downvote_progress_horizontal, null));
                vote_progress_bar.setRotation(180);
            }
        } else {
            vote_progress_bar.setProgressDrawable(ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.gray_progress_horizontal, null));
        }
    }


    public void setVoteView(int action, int upvote, int downvote) {
        String upVoteText = formatNumberToShort(upvote);
        String downVoteText = formatNumberToShort(downvote);
        candidate_like_view.setText(String.format(mContext.getString(R.string.upvote_string), upVoteText));
        candidate_dislike_view.setTypeface(ExtraUtils.getRobotoRegular(mContext));

        candidate_dislike_view.setText(String.format(mContext.getString(R.string.downvote_string), downVoteText));
        candidate_dislike_view.setTypeface(ExtraUtils.getRobotoMedium(mContext));

        if (action == Constant.Action.LIKE) {
            candidate_like_view.setCompoundDrawablesWithIntrinsicBounds(R.drawable.upvote_active, 0, 0, 0);
            candidate_dislike_view.setCompoundDrawablesWithIntrinsicBounds(R.drawable.downvote_deactive, 0, 0, 0);
            candidate_like_view.setTypeface(ExtraUtils.getRobotoMedium(mContext));
            candidate_dislike_view.setTypeface(ExtraUtils.getRobotoRegular(mContext));
        } else if (action == Constant.Action.DISLIKE) {
            candidate_like_view.setCompoundDrawablesWithIntrinsicBounds(R.drawable.upvote_deactive, 0, 0, 0);
            candidate_dislike_view.setCompoundDrawablesWithIntrinsicBounds(R.drawable.downvote_active, 0, 0, 0);
            candidate_like_view.setTypeface(ExtraUtils.getRobotoRegular(mContext));
            candidate_dislike_view.setTypeface(ExtraUtils.getRobotoMedium(mContext));
        } else {
            candidate_like_view.setCompoundDrawablesWithIntrinsicBounds(R.drawable.upvote_deactive, 0, 0, 0);
            candidate_dislike_view.setCompoundDrawablesWithIntrinsicBounds(R.drawable.downvote_deactive, 0, 0, 0);
            candidate_like_view.setTypeface(ExtraUtils.getRobotoRegular(mContext));
            candidate_dislike_view.setTypeface(ExtraUtils.getRobotoRegular(mContext));
        }
    }
}



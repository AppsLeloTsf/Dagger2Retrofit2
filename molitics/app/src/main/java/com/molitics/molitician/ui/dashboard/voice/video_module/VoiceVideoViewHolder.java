package com.molitics.molitician.ui.dashboard.voice.video_module;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.ui.PlayerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.molitics.molitician.R;
import com.molitics.molitician.httpapi.RetrofitRestClient;
import com.molitics.molitician.model.CommonKeyModel;
import com.molitics.molitician.ui.dashboard.leader.AllLeaderModel;
import com.molitics.molitician.ui.dashboard.nationalNews.YoutubePlayActivity;
import com.molitics.molitician.ui.dashboard.termCondition.TermConditionActivity;
import com.molitics.molitician.ui.dashboard.voice.adapter.VoiceAllAdapter;
import com.molitics.molitician.ui.dashboard.voice.model.VoiceAllModel;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.PrefUtil;
import com.molitics.molitician.util.Util;
import com.squareup.picasso.Picasso;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import im.ene.toro.ToroPlayer;
import im.ene.toro.ToroUtil;
import im.ene.toro.exoplayer.ExoPlayerViewHelper;
import im.ene.toro.exoplayer.Playable;
import im.ene.toro.media.PlaybackInfo;
import im.ene.toro.media.VolumeInfo;
import im.ene.toro.widget.Container;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.molitics.molitician.util.Constant.SHARE_VOICE_TITLE_LENTH;
import static com.molitics.molitician.util.VideoExpoPlayer.muteVideo;

public class VoiceVideoViewHolder extends RecyclerView.ViewHolder implements ToroPlayer, View.OnClickListener {
    public static final String TAG = "VoiceVideoViewHolder";

    @BindView(R.id.fb_video_player)
    public PlayerView fb_video_player;
    @BindView(R.id.creator_image)
    public CircleImageView creator_image;
    @BindView(R.id.description_txt)
    public TextView description_txt;
    @BindView(R.id.tag_leader_txt)
    public TextView tag_leader_txt;
    @BindView(R.id.like_image)
    public TextView like_image;
    @BindView(R.id.dislike_image)
    public TextView dislike_image;
    @BindView(R.id.share_image)
    public TextView share_image;
    @BindView(R.id.follow_txt)
    public TextView follow_txt;
    @BindView(R.id.three_dot)
    public ImageView three_dot;
    @BindView(R.id.issue_time)
    public TextView issue_time;
    @BindView(R.id.user_name)
    public TextView user_name;
    @BindView(R.id.user_location)
    public TextView user_location;
    @Nullable
    @BindView(R.id.read_more_issue)
    public TextView read_more_issue;
    @BindView(R.id.video_mute_button)
    ImageView video_mute_button;
    @BindView(R.id.like_count_view)
    public TextView like_count_view;
    @BindView(R.id.dislike_count_view)
    public TextView dislike_count_view;
    @BindView(R.id.comment_count_view)
    public TextView comment_count_view;
    @Nullable
    @BindView(R.id.comment_view)
    TextView comment_view;
    @BindView(R.id.edit_voice_view)
    public ImageView edit_voice_view;

    @BindView(R.id.delete_voice_view)
    public ImageView delete_voice_view;
    @BindView(R.id.edit_divider_line)
    public TextView edit_divider_line;
    @Nullable
    @BindView(R.id.ll_url_view)
    public LinearLayout ll_url_view;

    @Nullable
    @BindView(R.id.url_card_progress)
    public ProgressBar url_card_progress;

    @BindView(R.id.rl_url_view)
    public RelativeLayout rl_url_view;
    @BindView(R.id.source_title)
    public TextView source_title;
    @BindView(R.id.source_name)
    public TextView source_name;

    @BindView(R.id.over_lay)
    public View overLay;

    @BindView(R.id.video_uploading_bar)
    public ProgressBar video_uploading_bar;
    @BindView(R.id.vote_progress_bar)
    public ProgressBar vote_progress_bar;

    @Nullable
    @BindView(R.id.voice_main_layout)
    public ConstraintLayout voice_main_layout;

    @Nullable
    @BindView(R.id.attachedLeaderView)
    public LinearLayout attachedLeaderView;

    @BindView(R.id.video_placeholder_view)
    ImageView video_placeholder_view;

    private String holderFrom;
    private Context holderContext;
    private int holderActualPosition;
    @Nullable
    private ExoPlayerViewHelper helper;
    @Nullable
    private Uri mediaUri;

    @BindView(R.id.followUserView)
    TextView followUserView;
    @BindView(R.id.viewCountTextView)
    public TextView viewCountTextView;

    public VoiceVideoViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        description_txt.setOnClickListener(this);
        rl_url_view.setOnClickListener(this);
        user_name.setOnClickListener(this);
        like_count_view.setOnClickListener(this);
        creator_image.setOnClickListener(this);
        dislike_count_view.setOnClickListener(this);
        like_image.setOnClickListener(this);
        dislike_image.setOnClickListener(this);
        edit_voice_view.setOnClickListener(this);
        delete_voice_view.setOnClickListener(this);
        three_dot.setOnClickListener(this);
        comment_count_view.setOnClickListener(this);
        if (comment_view != null)
            comment_view.setOnClickListener(this);
        share_image.setOnClickListener(this);
        follow_txt.setOnClickListener(this);
        if (voice_main_layout != null)
            voice_main_layout.setOnClickListener(this);
        if (overLay != null)
            overLay.setOnClickListener(this);
        followUserView.setOnClickListener(this);

    }

    @NonNull
    @Override
    public View getPlayerView() {
        return this.fb_video_player;
    }

    @NonNull
    @Override
    public PlaybackInfo getCurrentPlaybackInfo() {
        return helper != null ? helper.getLatestPlaybackInfo() : new PlaybackInfo();

    }

    private final Playable.EventListener listener = new Playable.DefaultEventListener() {
        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            Util.showLog("playbackState", "" + playbackState);
            if (playbackState == 3) {
                video_placeholder_view.setVisibility(View.GONE);
            }
         /*   if (playbackState == 2)
                video_placeholder_view.setVisibility(View.GONE);
            else video_placeholder_view.setVisibility(View.VISIBLE);*/
            super.onPlayerStateChanged(playWhenReady, playbackState);
        }
    };

    @Override
    public void initialize(@NonNull Container container, @Nullable PlaybackInfo playbackInfo) {
        if (mediaUri == null) throw new IllegalStateException("mediaUri is null.");
        if (helper == null) {
            helper = new ExoPlayerViewHelper(this, mediaUri);
            helper.addEventListener(listener);
        }
        helper.initialize(container, playbackInfo);
    }

    @Override
    public void play() {
        if (helper != null) {
            helper.play();
            fb_video_player.getPlayer().setPlayWhenReady(true);
            fb_video_player.setUseController(true);
            checkVolumeOption();
        }
    }

    @OnClick(R.id.video_mute_button)
    public void onVideoMutClick() {
        muteVideo(helper, video_mute_button);
    }

    @Override
    public void pause() {
        fb_video_player.setUseController(false);
        // voiceImageListener.onVideoPause();
        if (helper != null) helper.pause();
    }

    @Override
    public boolean isPlaying() {
        return helper != null && helper.isPlaying();
    }

    @Override
    public void release() {
        if (helper != null) {
            helper.removeEventListener(listener);
            // helper.removePlayerEventListener(eventListener);
            helper.release();
            helper = null;
        }
    }

    @Override
    public boolean wantsToPlay() {
        return ToroUtil.visibleAreaOffset(this, itemView.getParent()) >= 0.75;
    }

    @Override
    public int getPlayerOrder() {
        return getAdapterPosition();
    }

    private void checkVolumeOption() {
        if (helper != null) {
            video_mute_button.setVisibility(View.VISIBLE);
            if (PrefUtil.getBoolean(Constant.PreferenceKey.VIDEO_MUTE)) {
                video_mute_button.setImageResource(R.drawable.mute_white);
                helper.setVolumeInfo(new VolumeInfo(true, 0.0f));
            } else {
                video_mute_button.setImageResource(R.drawable.volume_white);
                helper.setVolumeInfo(new VolumeInfo(false, 1.0f));
            }
        }
    }


    private VoiceAllAdapter.VoiceInterFace voiceInterFace;
    private VoiceAllModel holderVoiceModel;
    private int holderTempPosition;


    public void bindView(Context mContext,
                         VoiceAllModel voiceModel, int actual_position, VoiceAllAdapter.VoiceInterFace vInterFace) {
        if (voiceModel != null) {
            if (voice_main_layout != null) {
                voice_main_layout.setVisibility(View.VISIBLE);
            }
            holderVoiceModel = voiceModel;
            holderContext = mContext;
            holderActualPosition = actual_position;
            holderTempPosition = actual_position;


            mediaUri = Uri.parse(voiceModel.getImages().get(0));

            String finalDescription = voiceModel.getContent().trim();
            voiceInterFace = vInterFace;
            overLay.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(voiceModel.getImage())) {
                Picasso.with(mContext).load(voiceModel.getImage()).into(creator_image);
            } else {
                creator_image.setImageResource(R.drawable.sample_image);
            }

            tag_leader_txt.setMovementMethod(LinkMovementMethod.getInstance());

            showVideoPlaceholder();

            if (!TextUtils.isEmpty(voiceModel.getUserName())) {
                user_name.setText(voiceModel.getUserName());
            } else {
                user_name.setText(mContext.getString(R.string.anonymous));
            }

            if (voiceModel.getUserId() == PrefUtil.getInt(Constant.PreferenceKey.USER_ID)) {
                followUserView.setVisibility(View.GONE);
            } else {
                followUserView.setVisibility(View.VISIBLE);
                if (voiceModel.getIsFollowing() == 0) {
                    followUserView.setText(mContext.getString(R.string.follow_tag));
                } else followUserView.setText(mContext.getString(R.string.following));
            }

            viewCountTextView.setText(mContext.getResources().getQuantityString(R.plurals.plural_view_count, voiceModel.getViewsCount(), voiceModel.getViewsCount()));

            setUpvoteText(mContext, like_count_view, voiceModel.getLikes());
            setDownvoteText(mContext, dislike_count_view, voiceModel.getDislikes());

            if (voiceModel.getComments() == 0 || voiceModel.getComments() == 1) {
                comment_count_view.setText(String.format(mContext.getString(R.string.comment_count), voiceModel.getComments()));
            } else {
                comment_count_view.setText(String.format(mContext.getString(R.string.comments_count), voiceModel.getComments()));
            }
            three_dot.setTag(voiceModel);
            issue_time.setText(voiceModel.getTime());
            if (!TextUtils.isEmpty(voiceModel.getUrlSource())) {
                rl_url_view.setVisibility(View.VISIBLE);
                source_title.setText(voiceModel.getContent());
                source_name.setText(voiceModel.getUrlSource());

            } else {
                rl_url_view.setVisibility(View.GONE);
            }
            if (voiceModel.getUploadedPercent() != -1) {
                video_uploading_bar.setVisibility(View.VISIBLE);
                video_uploading_bar.setProgress(voiceModel.getUploadedPercent());
            } else {
                video_uploading_bar.setVisibility(View.GONE);
            }
            int totoalVoteCount = voiceModel.getLikes() + voiceModel.getDislikes();
            if (totoalVoteCount != 0) {
                vote_progress_bar.setProgressDrawable(ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.vote_progress_bar_horizontal, null));
                vote_progress_bar.setProgress((voiceModel.getLikes() * 100) / (totoalVoteCount));
            } else {
                vote_progress_bar.setProgressDrawable(ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.gray_progress_horizontal, null));
            }

            if (voiceModel.getMyAction() == Constant.Action.LIKE) {
                like_image.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.drawable.upvote_active),
                        null, null, null);
                dislike_image.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.drawable.downvote_deactive), null, null, null);

            } else if (voiceModel.getMyAction() == Constant.Action.DISLIKE) {
                like_image.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.drawable.upvote_deactive),
                        null, null, null);
                dislike_image.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.drawable.downvote_active),
                        null, null, null);
            } else {
                like_image.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.drawable.upvote_deactive),
                        null, null, null);
                dislike_image.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.drawable.downvote_deactive), null, null, null);
            }

            description_txt.setVisibility(View.VISIBLE);
            int VOICE_DESCRIPTION = 150;
            if (voiceModel.getContent().length() > VOICE_DESCRIPTION) {
                String local_txt = (voiceModel.getContent().substring(0, VOICE_DESCRIPTION) + mContext.getString(R.string.read_more));
                SpannableStringBuilder des_spanable_string = new SpannableStringBuilder(local_txt);
                StyleSpan iss = new StyleSpan(android.graphics.Typeface.ITALIC); //Span to make text italic
                des_spanable_string.setSpan(iss, VOICE_DESCRIPTION, local_txt.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                des_spanable_string.setSpan(new ForegroundColorSpan(ResourcesCompat.getColor(mContext.getResources(), R.color.white, null)), VOICE_DESCRIPTION, local_txt.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                description_txt.setText(des_spanable_string);
            } else if (!TextUtils.isEmpty(finalDescription)) {
                description_txt.setText(finalDescription);
            } else {
                description_txt.setVisibility(View.GONE);
            }

            if (voiceModel.getTag() != null) {
                description_txt.setText(addHashClickablePart(mContext, voiceModel.getTagName() + " ." + finalDescription, voiceModel.getTag()));
            }

            if (!TextUtils.isEmpty(voiceModel.getLocation()))
                user_location.setText(voiceModel.getLocation());

            if (voiceModel.getCandidateLeader().size() != 0) {
                tag_leader_txt.setText(addClickablePart(mContext, voiceModel));
            } else {
                tag_leader_txt.setText("");
            }
            if (voiceModel.getImages() != null && !TextUtils.isEmpty(voiceModel.getSharedImageUrl())) {
                List<String> url_image = voiceModel.getImages();
                url_image.remove(voiceModel.getSharedImageUrl());
                url_image.add(voiceModel.getSharedImageUrl());
            }

            if (voiceModel.getId() == PrefUtil.getInt(Constant.PreferenceKey.ISSUE_ID)) {
                String local_url = PrefUtil.getString(Constant.PreferenceKey.VIDEO_URL_LIST);
                if (!TextUtils.isEmpty(local_url)) {
                    Gson gson = new Gson();
                    String jsonText = PrefUtil.getString(Constant.PreferenceKey.VIDEO_URL_LIST);
                    List<String> pref_video_url = gson.fromJson(jsonText, new TypeToken<List<String>>() {
                    }.getType());

                    voiceModel.getImages().addAll(pref_video_url);
                    List<String> al = voiceModel.getImages();
                    Set<String> hs = new HashSet<>(al);
                    al.clear();
                    voiceModel.getImages().addAll(hs);
                }
            }

            if (voiceModel.getUserId() == PrefUtil.getInt(Constant.PreferenceKey.USER_ID)) {
                three_dot.setVisibility(View.VISIBLE);
                share_image.setVisibility(View.GONE);
            } else {
                three_dot.setVisibility(View.GONE);
                share_image.setVisibility(View.VISIBLE);
            }
            //ToDO: like sound mediaplayer
            String trending_position_tag = String.format(mContext.getString(R.string.hash_tag), actual_position + 1);
            edit_divider_line.setText(trending_position_tag);
        }
    }

    private SpannableStringBuilder addHashClickablePart(Context mContext, String str, int id) {

        SpannableStringBuilder ssb = new SpannableStringBuilder(str);
        //idx1 same index indicator, starting point is @
        int idx1 = str.indexOf("#");
        int idx2 = 0;
        //end point is double space
        if (idx1 != -1) {
            idx2 = str.indexOf(" .", idx1);
            final String clickString = str.substring(idx1, idx2);
            ssb.setSpan(new ClickableSpan() {

                @Override
                public void onClick(View widget) {
                    voiceInterFace.onHashTagClick(id, clickString);
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    ds.setUnderlineText(false);
                }
            }, idx1, idx2, 0);

            ssb.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.button_red)), idx1, idx2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ssb.setSpan(new UnderlineSpan(), idx1, idx2, 0);
        }
        //starting point is @
        return ssb;
    }

    private SpannableStringBuilder addClickablePart(Context mContext, VoiceAllModel voiceModel) {
        StringBuilder str = new StringBuilder();
        for (AllLeaderModel candidateLeaderModel : voiceModel.getCandidateLeader()) {
            str.append("@").append(candidateLeaderModel.getName()).append("  ");
        }
        SpannableStringBuilder ssb = new SpannableStringBuilder(str.toString());
        int idx1 = str.indexOf("@");
        int idx2 = 0;
        int count = 0;
        while (idx1 != -1) {
            //end point is double space
            idx2 = str.indexOf("  ", idx1) + 1;

            final int click_position = count++;
            ssb.setSpan(new ClickableSpan() {

                @Override
                public void onClick(View widget) {
                    voiceInterFace.onTagLeaderClick(voiceModel.getCandidateLeader().get(click_position).getId());
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    ds.setUnderlineText(false);
                    // super.updateDrawState(ds);
                }
            }, idx1, idx2, 0);

            ssb.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.leader_tab_bck)), idx1, idx2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            idx1 = str.indexOf("@", idx2);
        }
        return ssb;
    }

    private void onLike(Context mContext, int dislike_count, int like_count) {
        if (!TextUtils.isEmpty(PrefUtil.getString(Constant.PreferenceKey.AUTH_KEY))) {

            like_image.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.drawable.upvote_active),
                    null, null, null);
            dislike_image.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.drawable.downvote_deactive),
                    null, null, null);

            setUpvoteText(mContext, like_count_view, like_count);
            setDownvoteText(mContext, dislike_count_view, dislike_count);
        }
    }

    private void onDelete(Context mContext, int like_count, int dislike_count) {

        if (!TextUtils.isEmpty(PrefUtil.getString(Constant.PreferenceKey.AUTH_KEY))) {
            like_image.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.drawable.upvote_deactive), null,
                    null, null);
            dislike_image.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.drawable.downvote_deactive), null,
                    null, null);
            setUpvoteText(mContext, like_count_view, like_count);
            setDownvoteText(mContext, dislike_count_view, dislike_count);
        }
    }

    private void onDisLike(Context mContext, int dislike_count, int like_count) {
        if (!TextUtils.isEmpty(PrefUtil.getString(Constant.PreferenceKey.AUTH_KEY))) {

            dislike_image.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.drawable.downvote_active), null, null, null);
            like_image.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.drawable.upvote_deactive), null
                    , null, null);

            setUpvoteText(mContext, like_count_view, like_count);
            setDownvoteText(mContext, dislike_count_view, dislike_count);
        }
    }


    private static void clickOnFeedHandler(Context mContext, String source, String url) {
        if (source.contains("youtube")) {
            Intent intent = new Intent(mContext, YoutubePlayActivity.class);
            intent.putExtra(Constant.IntentKey.LINK, url);
            mContext.startActivity(intent);
        } else {
         /*   Intent mIntent = new Intent(mContext, TermConditionActivity.class);
            mIntent.putExtra(Constant.IntentKey.TOOL_BAR, "Molitics");
            mIntent.putExtra(Constant.IntentKey.URL, url);
            mContext.startActivity(mIntent);*/
            Util.openBrowser(mContext,url);
        }
    }

    @Override
    public void onClick(View v) {
        int image_position = 0;
        switch (v.getId()) {
            case R.id.voice_main_layout:
                // voiceInterFace.onDetailClick(holderActualPosition, holderTempPosition, holderVoiceModel);
                break;
            case R.id.description_txt:
                voiceInterFace.onDetailClick(holderActualPosition, holderTempPosition, holderVoiceModel);
                break;

            case R.id.rl_url_view:
                clickOnFeedHandler(holderContext, holderVoiceModel.getUrlSource(), holderVoiceModel.getSharedUrl());
                break;

            case R.id.user_name:
                voiceInterFace.onCreatorImageClick(holderTempPosition, holderVoiceModel.getUserId());

                break;
            case R.id.like_count_view:
                voiceInterFace.onLikeDislikeClick(holderVoiceModel.getId(), 1);
                break;

            case R.id.creator_image:
                voiceInterFace.onCreatorImageClick(holderTempPosition, holderVoiceModel.getUserId());
                break;

            case R.id.dislike_count_view:
                voiceInterFace.onLikeDislikeClick(holderVoiceModel.getId(), 2);
                break;
            case R.id.like_image:

                if (holderVoiceModel.getMyAction() == Constant.Action.LIKE) {
                    holderVoiceModel.setMyAction(0);
                    onDelete(holderContext, holderVoiceModel.getLikes(), holderVoiceModel.getDislikes());
                    voiceInterFace.onLikeDislikeClick(holderFrom, holderActualPosition, holderTempPosition, holderVoiceModel.getId(), Constant.Action.DELETE_LIKE, image_position);
                } else {

                    MediaPlayer fb_likePlayer = MediaPlayer.create(holderContext, R.raw.fb_like);
                    fb_likePlayer.start();
                    holderVoiceModel.setMyAction(Constant.Action.LIKE);
                    onLike(holderContext, holderVoiceModel.getDislikes(), holderVoiceModel.getLikes());
                    voiceInterFace.onLikeDislikeClick(holderFrom, holderActualPosition, holderTempPosition, holderVoiceModel.getId(), Constant.Action.LIKE, image_position);
                }
                break;
            case R.id.dislike_image:
                if (holderVoiceModel.getMyAction() == Constant.Action.DISLIKE) {
                    holderVoiceModel.setMyAction(0);
                    onDelete(holderContext, holderVoiceModel.getLikes(), holderVoiceModel.getDislikes());
                    voiceInterFace.onLikeDislikeClick(holderFrom, holderActualPosition, holderTempPosition, holderVoiceModel.getId(), Constant.Action.DELETE_LIKE, image_position);
                } else {

                    MediaPlayer fb_likePlayer = MediaPlayer.create(holderContext, R.raw.fb_like);
                    fb_likePlayer.start();
                    /// like_sound_view.start();
                    //temp_image_position = image_position;
                    holderVoiceModel.setMyAction(Constant.Action.DISLIKE);
                    onDisLike(holderContext, holderVoiceModel.getDislikes(), holderVoiceModel.getLikes());
                    voiceInterFace.onLikeDislikeClick(holderFrom, holderActualPosition, holderTempPosition, holderVoiceModel.getId(), Constant.Action.DISLIKE, image_position);
                }
                break;
            case R.id.edit_voice_view:
                voiceInterFace.onDotClick(holderTempPosition, holderVoiceModel.getId(), 0, holderVoiceModel);
                break;
            case R.id.delete_voice_view:

                voiceInterFace.onDotClick(holderTempPosition, holderVoiceModel.getId(), 1, holderVoiceModel);
                break;
            case R.id.three_dot:
                List<CommonKeyModel> eventActions = Constant.getStatusValue();
                PopupMenu popup = new PopupMenu(holderContext, three_dot);
                popup.getMenuInflater().inflate(R.menu.pop_up, popup.getMenu());
                popup.getMenu().clear();

                for (int i = 0; i < eventActions.size(); i++) {
                    CommonKeyModel eventAction = eventActions.get(i);
                    popup.getMenu().add(eventAction.getValue(), eventAction.getValue(), i, eventAction.getKey());
                }
                popup.setOnMenuItemClickListener(item -> {
                    VoiceAllModel issue = (VoiceAllModel) three_dot.getTag();
                    voiceInterFace.onDotClick(holderTempPosition, issue.getId(), item.getGroupId(), holderVoiceModel);
                    return true;
                });
                popup.show();
                break;

            case R.id.comment_count_view:
                voiceInterFace.onCommentClick(holderFrom, holderTempPosition, holderVoiceModel.getId(), image_position);
                break;

            case R.id.comment_view:
                voiceInterFace.onCommentClick(holderFrom, holderTempPosition, holderVoiceModel.getId(), image_position);
                break;

            case R.id.share_image:
                if (holderVoiceModel.getContent().length() > SHARE_VOICE_TITLE_LENTH)
                    voiceInterFace.onShareClick(holderVoiceModel.getId(), holderVoiceModel.getUserName(), holderVoiceModel.getContent().substring(SHARE_VOICE_TITLE_LENTH));
                else
                    voiceInterFace.onShareClick(holderVoiceModel.getId(), holderVoiceModel.getUserName(), holderVoiceModel.getContent());
                break;
            case R.id.follow_txt:
                if (holderVoiceModel.getUserId() != PrefUtil.getInt(Constant.PreferenceKey.USER_ID)) {

                    int mm = holderVoiceModel.getIsFollowing();
                    if (mm == 0) {
                        voiceInterFace.onFollowClick(holderFrom, holderActualPosition, holderTempPosition, holderVoiceModel.getId(), image_position);
                    } else {
                        voiceInterFace.onUnFollowClick(holderFrom, holderActualPosition, holderTempPosition, holderVoiceModel.getId(), image_position);
                    }
                }
            case R.id.over_lay:
                Toast.makeText(holderContext, holderContext.getString(R.string.upload_in_progress), Toast.LENGTH_LONG).show();
                break;
            case R.id.followUserView:
                if (holderVoiceModel.getIsFollowing() == 0) {
                    holderVoiceModel.setIsFollowing(1);
                    //voiceInterFace.onUserFollowActionClick(holderVoiceModel.getUserId(), 1);
                    followingUser(holderContext, followUserView);
                    actionUserFollow(holderVoiceModel.getUserId());
                } else {
                    holderVoiceModel.setIsFollowing(0);
                    // voiceInterFace.onUserFollowActionClick(holderVoiceModel.getUserId(), 0);
                    followUser(holderContext, followUserView);
                    actionUserUnFollow(holderVoiceModel.getUserId());
                }
        }
    }

    public static void setUpvoteText(Context mContext, TextView mView, int count) {
        if (count == 0)
            mView.setText(String.format(mContext.getString(R.string.upvote_string_percent), count));
        else {
            mView.setText(mContext.getResources().getQuantityString(R.plurals.plural_upvote_tag, count, count));
        }
    }

    public static void setDownvoteText(Context mContext, TextView mView, int count) {
        if (count == 0)
            mView.setText(String.format(mContext.getString(R.string.downvote_string_percent), count));
        else
            mView.setText(mContext.getResources().getQuantityString(R.plurals.plural_downvote_tag, count, count));
    }

    private void showVideoPlaceholder() {
        video_placeholder_view.setVisibility(View.VISIBLE);
        String mediaUrl = mediaUri.toString();
        String extension = mediaUrl.substring(mediaUrl.lastIndexOf("."));
        String video_placeholder_url = mediaUrl.replace(extension, Constant.VIDEO_PLACEHOLDER);
        Picasso.with(video_placeholder_view.getContext()).load(video_placeholder_url).into(video_placeholder_view);
    }

    private void followingUser(Context mContext, TextView mView) {
        mView.setText(mContext.getString(R.string.following));
    }

    private void followUser(Context mContext, TextView mView) {
        mView.setText(mContext.getString(R.string.follow_tag));
    }

    @SuppressLint("CheckResult")
    public void actionUserFollow(int userId) {
        RetrofitRestClient.getInstance().getUserFollowRx(userId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(result -> {

                }, error -> {

                }
        );
    }

    @SuppressLint("CheckResult")
    public void actionUserUnFollow(int userId) {
        RetrofitRestClient.getInstance().getUserFollowRx(userId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(result -> {

                }, error -> {

                }

        );
    }


}

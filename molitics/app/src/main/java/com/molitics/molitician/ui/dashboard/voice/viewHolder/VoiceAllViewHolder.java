package com.molitics.molitician.ui.dashboard.voice.viewHolder;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.molitics.molitician.R;
import com.molitics.molitician.httpapi.RetrofitRestClient;
import com.molitics.molitician.model.CommonKeyModel;
import com.molitics.molitician.ui.dashboard.leader.AllLeaderModel;
import com.molitics.molitician.ui.dashboard.nationalNews.YoutubePlayActivity;
import com.molitics.molitician.ui.dashboard.voice.adapter.ImageViewPager;
import com.molitics.molitician.ui.dashboard.voice.adapter.ImageVoiceRecyclerView;
import com.molitics.molitician.ui.dashboard.voice.adapter.TrendingVoiceAdapter;
import com.molitics.molitician.ui.dashboard.voice.adapter.VoiceAllAdapter;
import com.molitics.molitician.ui.dashboard.voice.model.VoiceAllModel;
import com.molitics.molitician.ui.dashboard.voice.model.VoiceImageModel;
import com.molitics.molitician.ui.dashboard.voice.video_module.VoiceVideoViewHolder;
import com.molitics.molitician.ui.dashboard.voice.voiceDetail.VoiceDetailAdapter;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.ExtraUtils;
import com.molitics.molitician.util.GalleyImageView;
import com.molitics.molitician.util.PrefUtil;
import com.molitics.molitician.util.Util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import im.ene.toro.ToroPlayer;
import im.ene.toro.ToroUtil;
import im.ene.toro.media.PlaybackInfo;
import im.ene.toro.widget.Container;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.molitics.molitician.util.Constant.SHARE_VOICE_TITLE_LENTH;
import static com.molitics.molitician.util.Util.getImageHeight;

public class VoiceAllViewHolder extends RecyclerView.ViewHolder implements ToroPlayer, View.OnClickListener {

    @BindView(R.id.image_container)
    public Container image_container;
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
    @BindView(R.id.continue_reading_txt)
    public TextView continue_reading_txt;
    @BindView(R.id.issue_time)
    public TextView issue_time;
    @BindView(R.id.user_name)
    public TextView user_name;
    @BindView(R.id.user_location)
    public TextView user_location;
    @Nullable
    @BindView(R.id.read_more_issue)
    public TextView read_more_issue;

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

    @BindView(R.id.viewCountTextView)
    public TextView viewCountTextView;

    @BindView(R.id.reportThreeDot)
    public ImageView reportThreeDot;

    @Nullable
    @BindView(R.id.followUserView)
    TextView followUserView;

    private String holderFrom;
    private Context holderContext;
    private VoiceAllViewHolder viewHolder;
    private int holderActualPosition;
    private final SnapHelper snapHelper = new PagerSnapHelper();

    public void onDetached() {
        snapHelper.attachToRecyclerView(null);
    }

    public void onAttached() {
        snapHelper.attachToRecyclerView(image_container);
    }


    public VoiceAllViewHolder(View itemView) {
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
        reportThreeDot.setOnClickListener(this);
        comment_count_view.setOnClickListener(this);
        if (comment_view != null)
            comment_view.setOnClickListener(this);
        share_image.setOnClickListener(this);
        follow_txt.setOnClickListener(this);
        if (voice_main_layout != null)
            voice_main_layout.setOnClickListener(this);
        if (overLay != null)
            overLay.setOnClickListener(this);
        if (followUserView != null)
            followUserView.setOnClickListener(this);
    }

    // ToroPlayer implementation
    @NonNull
    @Override
    public View getPlayerView() {
        return image_container;
    }


    @NonNull
    @Override
    public PlaybackInfo getCurrentPlaybackInfo() {
        return null;
    }

    @Override
    public void initialize(@NonNull Container container, @Nullable PlaybackInfo playbackInfo) {
    }

    @Override
    public void play() {
    }

    @Override
    public void pause() {
    }

    @Override
    public boolean isPlaying() {
        return false;
    }

    @Override
    public void release() {
    }

    @Override
    public boolean wantsToPlay() {
        return ToroUtil.visibleAreaOffset(this, itemView.getParent()) >= 0.85;
    }

    @Override
    public int getPlayerOrder() {
        return getAdapterPosition();
    }

    ExtraUtils.NotifyCustomDataSetChanged notifyCustomDataSetChanged;
    public VoiceAllAdapter.VoiceInterFace voiceInterFace;
    private int VOICE_DESCRIPTION = 150;
    private ViewPropertyAnimator onPlayAnimator;
    private ViewPropertyAnimator onPauseAnimator;
    private int animatorDuration = 300;

    ToroPlayer.EventListener eventListener;
    int image_position = 0;
    private VoiceAllModel holderVoiceModel;
    private int holderTempPosition;


    public void setListener(ToroPlayer.EventListener eventListener) {
        this.eventListener = eventListener;
    }

    public void bindView(Context mContext, String from, VoiceAllViewHolder viewHolder, VoiceAllModel voiceModel,
                         int actual_position, int temp_position,
                         VoiceAllAdapter.VoiceInterFace vInterFace,
                         ExtraUtils.NotifyCustomDataSetChanged customDataSetChanged, int set_image_position) {
        if (voiceModel != null) {
            if (voice_main_layout != null) {
                voice_main_layout.setVisibility(View.VISIBLE);
            }
            holderFrom = from;
            holderVoiceModel = voiceModel;
            holderContext = mContext;
            holderTempPosition = temp_position;
            this.viewHolder = viewHolder;
            holderActualPosition = actual_position;

            String finalDescription = voiceModel.getContent().trim();
            notifyCustomDataSetChanged = customDataSetChanged;
            voiceInterFace = vInterFace;

            viewHolder.overLay.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(voiceModel.getImage())) {
                Picasso.with(mContext).load(voiceModel.getImage()).into(viewHolder.creator_image);
            } else {
                viewHolder.creator_image.setImageResource(R.drawable.sample_image);
            }

            if (!TextUtils.isEmpty(voiceModel.getUserName())) {
                viewHolder.user_name.setText(voiceModel.getUserName().trim());
            } else {
                viewHolder.user_name.setText(mContext.getString(R.string.anonymous));
            }
            if (followUserView != null) {
                if (voiceModel.getUserId() == PrefUtil.getInt(Constant.PreferenceKey.USER_ID)) {
                    followUserView.setVisibility(View.GONE);
                } else {

                    followUserView.setVisibility(View.VISIBLE);
                    if (voiceModel.getIsFollowing() == 0) {
                        followUserView.setText(mContext.getString(R.string.follow_tag));
                    } else followUserView.setText(mContext.getString(R.string.following));
                }
            }
            viewCountTextView.setText(String.valueOf(voiceModel.getViewsCount()));

            setUpvoteText(mContext, viewHolder.like_count_view, voiceModel.getLikes());
            setDownvoteText(mContext, viewHolder.dislike_count_view, voiceModel.getDislikes());

            if (voiceModel.getComments() == 0 || voiceModel.getComments() == 1) {
                comment_count_view.setText(String.format(mContext.getString(R.string.comment_count), voiceModel.getComments()));
            } else {
                comment_count_view.setText(String.format(mContext.getString(R.string.comments_count), voiceModel.getComments()));
            }
            viewHolder.three_dot.setTag(voiceModel);
            viewHolder.issue_time.setText(voiceModel.getTime());
            if (!TextUtils.isEmpty(voiceModel.getUrlSource())) {
                viewHolder.rl_url_view.setVisibility(View.VISIBLE);
                viewHolder.source_title.setText(voiceModel.getContent());
                viewHolder.source_name.setText(voiceModel.getUrlSource());

            } else {
                viewHolder.rl_url_view.setVisibility(View.GONE);
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
                viewHolder.like_image.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.drawable.ic_like_inactive),
                        null, null, null);
                viewHolder.dislike_image.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.drawable.ic_dislike_inactive), null, null, null);

            } else if (voiceModel.getMyAction() == Constant.Action.DISLIKE) {
                viewHolder.like_image.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.drawable.ic_dislike_inactive),
                        null, null, null);
                viewHolder.dislike_image.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.drawable.ic_like_inactive),
                        null, null, null);
            } else {
                viewHolder.like_image.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.drawable.ic_like_inactive),
                        null, null, null);
                viewHolder.dislike_image.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.drawable.ic_dislike_inactive), null, null, null);
            }

            viewHolder.description_txt.setVisibility(View.VISIBLE);
            if (!from.equals(VoiceDetailAdapter.TAG) && voiceModel.getContent().length() > VOICE_DESCRIPTION) {
                String local_txt = (voiceModel.getContent().substring(0, VOICE_DESCRIPTION) + mContext.getString(R.string.read_more));
                SpannableStringBuilder des_spanable_string = new SpannableStringBuilder(local_txt);
                StyleSpan iss = new StyleSpan(android.graphics.Typeface.ITALIC); //Span to make text italic
                des_spanable_string.setSpan(iss, VOICE_DESCRIPTION, local_txt.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                des_spanable_string.setSpan(new ForegroundColorSpan(ResourcesCompat.getColor(mContext.getResources(), R.color.follow_leader, null)), VOICE_DESCRIPTION, local_txt.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                viewHolder.description_txt.setText(des_spanable_string);
            } else if (!TextUtils.isEmpty(finalDescription)) {
                viewHolder.description_txt.setText(finalDescription);
            } else {
                viewHolder.description_txt.setVisibility(View.GONE);
            }

            if (voiceModel.getTag() != null) {
                viewHolder.description_txt.setText(addHashClickablePart(mContext, voiceModel.getTagName() + " ." + finalDescription, voiceModel.getTag()));
            }
            setTags(viewHolder.description_txt,description_txt.getText().toString());

            if (!TextUtils.isEmpty(voiceModel.getLocation()))
                user_location.setText(voiceModel.getLocation());

            if (voiceModel.getCandidateLeader().size() != 0) {
                tag_leader_txt.setMovementMethod(LinkMovementMethod.getInstance());
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
            if (voiceModel.getImages().size() != 0) {
                viewHolder.image_container.setVisibility(View.VISIBLE);
                if (voiceModel.getImageSizes() != null && !voiceModel.getImageSizes().contains("x") && voiceModel.getImages().size() == 1 && !from.equals("TrendingVoiceAdapter")) {
                    viewHolder.image_container.getLayoutParams().height = getImageHeight(voiceModel.getImageSizes(), mContext);
                }
                if (from.equals("TrendingVoiceAdapter")) {
                    int trendingHeight = (int) mContext.getResources().getDimension(R.dimen.trending_video_height);
                    viewHolder.image_container.getLayoutParams().height = ExtraUtils.convertDpToPx(mContext, trendingHeight);
                }
                List<VoiceImageModel> voiceImageModelList = new ArrayList<>();
                for (int position = 0; position < voiceModel.getImages().size(); position++) {

                    voiceImageModelList.add(new VoiceImageModel(voiceModel.getImages().get(position), 0, 0, 0));
                }
                ImageVoiceRecyclerView imageVoiceRecyclerAdapter = new ImageVoiceRecyclerView(mContext, from, voiceImageModelList, new ImageViewPager.VoiceImageListener() {
                    @Override
                    public void onImageClick(int position, int issue_id, List<String> imageList) {
                        if (!TextUtils.isEmpty(voiceModel.getUrlSource())) {
                            clickOnFeedHandler(mContext, voiceModel.getUrlSource(), voiceModel.getSharedUrl());

                        } else if ((voiceModel.getImages().get(position).toLowerCase()).contains(Constant.MP4) && voiceModel.getImages().size() == 1)
                            voiceInterFace.onVideoClick(viewHolder, from, holderTempPosition, voiceModel.getId());
                        else
                            voiceInterFace.onImageClick(viewHolder, position, voiceModel.getId(), voiceModel.getImages());
                    }

                    @Override
                    public void onVideoPLay() {
                        if (onPlayAnimator != null) onPlayAnimator.cancel();
                        onPlayAnimator = viewHolder.overLay.animate().alpha(0.0f).setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationCancel(Animator animation) {
                                animation.end();
                            }
                        }).setDuration(animatorDuration);
                        onPlayAnimator.start();
                    }

                    @Override
                    public void onVideoPause() {
                        if (onPauseAnimator != null) onPauseAnimator.cancel();
                        onPauseAnimator = viewHolder.overLay.animate().alpha(1.0f).setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationCancel(Animator animation) {
                                animation.end();
                            }
                        }).setDuration(animatorDuration);
                        onPauseAnimator.start();
                    }

                    @Override
                    public void onShareClick(String url) {
                    }

                    @Override
                    public void onDownloadClick(String url) {
                    }
                }, eventListener);

                if (from.equalsIgnoreCase(TrendingVoiceAdapter.TAG)) {
                    viewHolder.image_container.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
                } else {
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 6);
                    gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                        @Override
                        public int getSpanSize(int position) {
                            return GalleyImageView.getImageCol(position, voiceImageModelList.size());
                        }
                    });

                    viewHolder.image_container.setLayoutManager(gridLayoutManager);
                }
                viewHolder.image_container.setAdapter(imageVoiceRecyclerAdapter);
                viewHolder.image_container.setPlayerDispatcher(player -> player.getPlayerOrder() % 3 == 0 ? 250 : 1000);
            } else {
                viewHolder.image_container.setVisibility(View.GONE);
            }

            if (voiceModel.getUserId() == PrefUtil.getInt(Constant.PreferenceKey.USER_ID) && !from.equalsIgnoreCase(VoiceVideoViewHolder.TAG)) {
                viewHolder.three_dot.setVisibility(View.VISIBLE);
                viewHolder.reportThreeDot.setVisibility(View.GONE);
                viewHolder.share_image.setVisibility(View.GONE);
            } else {
                viewHolder.three_dot.setVisibility(View.GONE);
                viewHolder.reportThreeDot.setVisibility(View.VISIBLE);
                viewHolder.share_image.setVisibility(View.VISIBLE);
            }
            //ToDO: like sound mediaplayer
            String trending_position_tag = String.format(mContext.getString(R.string.hash_tag), temp_position + 1);
            viewHolder.edit_divider_line.setText(trending_position_tag);
        }
    }
    private void setTags(TextView pTextView, String pTagString) {
        SpannableString string = new SpannableString(pTagString);

        int start = -1;
        for (int i = 0; i < pTagString.length(); i++) {
            if (pTagString.charAt(i) == '#') {
                start = i;
            } else if (pTagString.charAt(i) == ' ' || pTagString.charAt(i) == '\n' || (i == pTagString.length() - 1 && start != -1)) {
                if (start != -1) {
                    if (i == pTagString.length() - 1) {
                        i++; // case for if hash is last word and there is no
                        // space after word
                    }

                    final String tag = pTagString.substring(start, i);
                    string.setSpan(new ClickableSpan() {

                        @Override
                        public void onClick(View widget) {
                            Log.d("Hash", String.format("Clicked %s!", tag));
                        }

                        @Override
                        public void updateDrawState(TextPaint ds) {
                            // link color
                            ds.setColor(Color.parseColor("#3b5998"));
                            ds.setFakeBoldText(true);
                            ds.setUnderlineText(false);
                        }
                    }, start, i, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    start = -1;
                }
            }
        }

        pTextView.setMovementMethod(LinkMovementMethod.getInstance());
        pTextView.setText(string);
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

            ssb.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.facebook)), idx1, idx2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
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

    private static void onLike(Context mContext, VoiceAllViewHolder mHolder, int dislike_count, int like_count) {
        if (!TextUtils.isEmpty(PrefUtil.getString(Constant.PreferenceKey.AUTH_KEY))) {

            mHolder.like_image.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.drawable.ic_like_inactive),
                    null, null, null);
            mHolder.dislike_image.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.drawable.ic_dislike_inactive),
                    null, null, null);

            setUpvoteText(mContext, mHolder.like_count_view, like_count);
            setDownvoteText(mContext, mHolder.dislike_count_view, dislike_count);
        }
    }

    private static void onDelete(Context mContext, VoiceAllViewHolder mHolder, int like_count, int dislike_count) {

        if (!TextUtils.isEmpty(PrefUtil.getString(Constant.PreferenceKey.AUTH_KEY))) {
            mHolder.like_image.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.drawable.ic_like_inactive), null,
                    null, null);
            mHolder.dislike_image.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.drawable.ic_dislike_inactive), null,
                    null, null);
            setUpvoteText(mContext, mHolder.like_count_view, like_count);
            setDownvoteText(mContext, mHolder.dislike_count_view, dislike_count);
        }
    }

    private static void onDisLike(Context mContext, VoiceAllViewHolder mHolder, int dislike_count, int like_count) {
        if (!TextUtils.isEmpty(PrefUtil.getString(Constant.PreferenceKey.AUTH_KEY))) {

            mHolder.dislike_image.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.drawable.ic_dislike_inactive), null, null, null);
            mHolder.like_image.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources().getDrawable(R.drawable.ic_like_inactive), null
                    , null, null);

            setUpvoteText(mContext, mHolder.like_count_view, like_count);
            setDownvoteText(mContext, mHolder.dislike_count_view, dislike_count);
        }
    }


    private static void clickOnFeedHandler(Context mContext, String source, String url) {
        if (source.contains("youtube")) {
            Intent intent = new Intent(mContext, YoutubePlayActivity.class);
            intent.putExtra(Constant.IntentKey.LINK, url);
            mContext.startActivity(intent);
        } else {
       /*     Intent mIntent = new Intent(mContext, TermConditionActivity.class);
            mIntent.putExtra(Constant.IntentKey.TOOL_BAR, "Molitics");
            mIntent.putExtra(Constant.IntentKey.URL, url);
            mContext.startActivity(mIntent);*/
            Util.openBrowser(mContext, url);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.voice_main_layout:
                voiceInterFace.onDetailClick(holderActualPosition, holderTempPosition, holderVoiceModel);
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
                    onDelete(holderContext, viewHolder, holderVoiceModel.getLikes(), holderVoiceModel.getDislikes());
                    voiceInterFace.onLikeDislikeClick(holderFrom, holderActualPosition, holderTempPosition, holderVoiceModel.getId(), Constant.Action.DELETE_LIKE, image_position);
                } else {

                    MediaPlayer fb_likePlayer = MediaPlayer.create(holderContext, R.raw.fb_like);
                    fb_likePlayer.start();
                    holderVoiceModel.setMyAction(Constant.Action.LIKE);
                    onLike(holderContext, viewHolder, holderVoiceModel.getDislikes(), holderVoiceModel.getLikes());
                    voiceInterFace.onLikeDislikeClick(holderFrom, holderActualPosition, holderTempPosition, holderVoiceModel.getId(), Constant.Action.LIKE, image_position);
                }
                break;
            case R.id.dislike_image:
                if (holderVoiceModel.getMyAction() == Constant.Action.DISLIKE) {
                    holderVoiceModel.setMyAction(0);
                    onDelete(holderContext, viewHolder, holderVoiceModel.getLikes(), holderVoiceModel.getDislikes());
                    voiceInterFace.onLikeDislikeClick(holderFrom, holderActualPosition, holderTempPosition, holderVoiceModel.getId(), Constant.Action.DELETE_LIKE, image_position);
                } else {

                    MediaPlayer fb_likePlayer = MediaPlayer.create(holderContext, R.raw.fb_like);
                    fb_likePlayer.start();
                    /// like_sound_view.start();
                    //temp_image_position = image_position;
                    holderVoiceModel.setMyAction(Constant.Action.DISLIKE);
                    onDisLike(holderContext, viewHolder, holderVoiceModel.getDislikes(), holderVoiceModel.getLikes());
                    voiceInterFace.onLikeDislikeClick(holderFrom, holderActualPosition, holderTempPosition, holderVoiceModel.getId(), Constant.Action.DISLIKE, image_position);
                }
                break;
            case R.id.edit_voice_view:
                voiceInterFace.onDotClick(holderTempPosition, holderVoiceModel.getId(), 0, holderVoiceModel);
                break;
            case R.id.delete_voice_view:

                voiceInterFace.onDotClick(holderTempPosition, holderVoiceModel.getId(), 1, holderVoiceModel);
                break;
            case R.id.reportThreeDot:

                List<CommonKeyModel> report = Constant.getReportUser();
                PopupMenu reportPopup = new PopupMenu(holderContext, viewHolder.reportThreeDot);
                reportPopup.getMenuInflater().inflate(R.menu.pop_up, reportPopup.getMenu());
                reportPopup.getMenu().clear();

                for (int i = 0; i < report.size(); i++) {
                    CommonKeyModel eventAction = report.get(i);
                    reportPopup.getMenu().add(eventAction.getValue(), eventAction.getValue(), i, eventAction.getKey());
                }
                reportPopup.setOnMenuItemClickListener(item -> {
                    voiceInterFace.onReportDialogClick(holderVoiceModel.getUserId(), holderVoiceModel.getId());
                    return true;
                });
                reportPopup.show();
                break;
            case R.id.three_dot:
                List<CommonKeyModel> eventActions = Constant.getStatusValue();
                PopupMenu popup = new PopupMenu(holderContext, viewHolder.three_dot);
                popup.getMenuInflater().inflate(R.menu.pop_up, popup.getMenu());
                popup.getMenu().clear();

                for (int i = 0; i < eventActions.size(); i++) {
                    CommonKeyModel eventAction = eventActions.get(i);
                    popup.getMenu().add(eventAction.getValue(), eventAction.getValue(), i, eventAction.getKey());
                }
                popup.setOnMenuItemClickListener(item -> {
                    VoiceAllModel issue = (VoiceAllModel) viewHolder.three_dot.getTag();
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
                    voiceInterFace.onUserFollowActionClick(holderVoiceModel.getUserId(), 1);
                    followingUser(holderContext, viewHolder.followUserView);
                    actionUserFollow(holderVoiceModel.getUserId());
                } else {
                    holderVoiceModel.setIsFollowing(0);
                    voiceInterFace.onUserFollowActionClick(holderVoiceModel.getUserId(), 0);
                    followUser(holderContext, viewHolder.followUserView);
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
        RetrofitRestClient.getInstance().getUserUnFollowRx(userId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(result -> {

                }, error -> {

                }

        );
    }

}

package com.molitics.molitician.ui.dashboard.voice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.molitics.molitician.R;
import com.molitics.molitician.db.MoliticsDatabase;
import com.molitics.molitician.ui.dashboard.voice.model.FeedActionModel;
import com.molitics.molitician.ui.dashboard.voice.model.FeedModel;
import com.molitics.molitician.ui.dashboard.voice.model.UsersFeedModel;
import com.molitics.molitician.ui.dashboard.voice.viewHolder.HomeFeedViewHolder;
import com.molitics.molitician.util.ExtraUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import im.ene.toro.widget.Container;

/**
 * Created by rahul on 14/11/17.
 */

public class HomeFeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements
        /*ImageViewPager.VoiceImageListener,*/ ExtraUtils.NotifyCustomDataSetChanged {

    public static final String TAG = "HomeFeedAdapter";
    private List<FeedModel> feedModels = new ArrayList<>();
    private List<UsersFeedModel> trendingVoiceModel = new ArrayList<>();

    private Context mContext;
    public static int ALL_ISSUE = 1;
    public static int TRENDING_POSITION = 2;

    private FeedInterFace feedInterFace;
    private List<Integer> trendingPositions = new ArrayList<>();
    private int image_position = 0;
    private UserSuggestionAdapter trendingVoiceAdapter;

    @Override
    public void notifyCustom(int image_position) {
        this.image_position = image_position;
    }

    public interface FeedInterFace {
        void onLoadMore();

        void onCreateVoiceClick(int itemSelection);

        void onLikeDislikeClick(String from, int actual_position, int position, int issue_id, int action, int image_position);

        void onDotClick(int position, int issue_id, int action_type, FeedModel voiceModel);

        void onCommentClick(String from, int position, int issue_id, int image_position);

        void onShareClick(int issue_id, String userName, String issue_title);

        void onFollowClick(String from, int actual_position, int position, int issue_id, int image_position);

        void onUnFollowClick(String from, int actual_position, int position, int issue_id, int image_position);

        void onTagLeaderClick(int leader_id);

        void onImageClick(HomeFeedViewHolder feedViewHolder, int position, int issue_id, List<String> imageList);

        void onCreatorImageClick(int position, int user_id);

        void onHashTagClick(int tag_id, String tag_name);

        void onDetailClick(int position, int tempPosition, FeedModel feedModel);

        void onLikeDislikeClick(int issue_id, int action);

        void onVideoClick(HomeFeedViewHolder feedViewHolder, String from, int clickPosition, int issueId);

        void onUserFollowActionClick(int userId, int action);

        void onReportDialogClick(int userId, int issueId);
    }

    public HomeFeedAdapter(Context mContext, FeedInterFace feedInterFace) {
        this.mContext = mContext;
        this.feedInterFace = feedInterFace;
    }

    public void addFeedList(List<FeedModel> v) {
        this.feedModels.addAll(v);
        notifyDataSetChanged();
    }

    public void addFeedOnPosition(int position, FeedModel voiceAllModel) {
        this.feedModels.set(position, voiceAllModel);
        notifyDataSetChanged();
    }


    public void addFeedOnTop(int position, FeedModel voiceAllModel) {
        this.feedModels.add(position, voiceAllModel);
        notifyDataSetChanged();
    }


    public void updateUserFollow(int userId, int action) {
        //this.voiceAllModels.parallelStream().filter(voiceAllModel -> voiceAllModel.getUserId() == userId).forEach(data -> data.setIsFollowing(action));
        notifyDataSetChanged();
    }

    public void removeFeedOnTop(int position) {
        this.feedModels.remove(position);
        notifyDataSetChanged();
    }


    public void editVideoUrl(int position, List<String> image_list) {
 /*       List<String> locale_url = voiceAllModels.get(position).getImages();

        for (Iterator<String> iterator = locale_url.iterator(); iterator.hasNext(); ) {
            String url = iterator.next();
            if (url.contains("storage")) {
                iterator.remove();
            }
        }
        locale_url.addAll(image_list);*/

        notifyDataSetChanged();
    }

    public void addCommentCount(int position, int count) {
        FeedModel voiceAllModel = feedModels.get(position);
        voiceAllModel.setComments(voiceAllModel.getComments() + count);
        notifyDataSetChanged();
    }

    public void addTrendingCommentCount(int position, int count) {
        UsersFeedModel voiceAllModel = trendingVoiceModel.get(position);
        this.trendingVoiceModel.add(voiceAllModel);
        trendingVoiceAdapter.notifyItemChanged(position);
    }

    public void addTrendingPositions(List<Integer> trendingPositions) {
        this.trendingPositions = trendingPositions;
        notifyDataSetChanged();
    }

    public void addTrendingVoiceList(List<UsersFeedModel> trendingVoiceModel) {
        this.trendingVoiceModel.clear();
        this.trendingVoiceModel.addAll(trendingVoiceModel);
        notifyDataSetChanged();
    }

    public List<UsersFeedModel> getTrendingList() {
        return trendingVoiceModel;
    }

    public List<FeedModel> getFeedList() {
        return feedModels;
    }

    public void updateVideoView(int percentDone) {
//        voiceAllModels.get(0).setUploadedPercent(percentDone);
        notifyDataSetChanged();
    }

    public int getFeedListSize() {
        return feedModels.size();
    }

    public void clearFeedList() {
        feedModels.clear();
        notifyDataSetChanged();
    }

    public void clearTrendingList() {
        trendingVoiceModel.clear();
        if (trendingVoiceAdapter != null)
            trendingVoiceAdapter.notifyDataSetChanged();
    }

    public void deleteFeed(int position) {
        feedModels.remove(position);

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TRENDING_POSITION) {
            View mView = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.recycler_voice_trending, parent, false);
            return new SuggestionUsersHolder(mView);
        }else {
            View mView = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.adapter_voice_all, parent, false);
            return new HomeFeedViewHolder(mView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder.getItemViewType() == ALL_ISSUE && feedModels.size() != 0) {
            HomeFeedViewHolder viewHolder = (HomeFeedViewHolder) holder;
            int set_position = position;
            if (!trendingVoiceModel.isEmpty() && trendingPositions.size() != 0 && trendingPositions.get(0) <= set_position) {
                set_position = set_position - 1;
            }

            int temp_position = set_position;

            FeedModel voiceModel = feedModels.get(temp_position);
            if (voiceModel != null) {
                viewHolder.bindView(mContext, TAG, viewHolder, voiceModel, holder.getAdapterPosition(),
                        temp_position, feedInterFace, this);
            }
        }else if (holder.getItemViewType() == TRENDING_POSITION) {
            SuggestionUsersHolder suggestionHolder = (SuggestionUsersHolder) holder;
            if (trendingVoiceAdapter != null)
                trendingVoiceAdapter = null;
            trendingVoiceAdapter = new UserSuggestionAdapter(mContext, trendingVoiceModel);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
            suggestionHolder.hor_recycler_view.setLayoutManager(mLayoutManager);
            suggestionHolder.hor_recycler_view.setAdapter(trendingVoiceAdapter);
            if (trendingVoiceModel.size() != 0) {
                suggestionHolder.rl_main.setVisibility(View.VISIBLE);
            } else {
                suggestionHolder.rl_main.setVisibility(View.GONE);
            }
        }

       /* if (holder.getItemViewType() == ALL_ISSUE && voiceAllModels.size() != 0) {
            VoiceAllViewHolder viewHolder = (VoiceAllViewHolder) holder;

            int set_position = position;
            if (is_create_visible)
                set_position = set_position - 1;
            if (!trendingVoiceModel.isEmpty() && trendingPositions.size() != 0 && trendingPositions.get(0) <= set_position) {
                set_position = set_position - 1;
            }

            int temp_position = set_position;

            VoiceAllModel voiceModel = voiceAllModels.get(temp_position);
            if (voiceModel != null) {
                viewHolder.bindView(mContext, TAG, viewHolder, voiceModel, holder.getAdapterPosition(),
                        temp_position, voiceInterFace, this, image_position);
            }

        } else if (position == 0 && is_create_visible) {

            CreateIssueViewHolder viewHolder = (CreateIssueViewHolder) holder;
            viewHolder.create_issue.setOnClickListener(v -> voiceInterFace.onCreateVoiceClick(createNormal));

            viewHolder.imageRl.setOnClickListener(v -> voiceInterFace.onCreateVoiceClick(createImage));
            viewHolder.videoRl.setOnClickListener(v -> voiceInterFace.onCreateVoiceClick(createVideo));
            viewHolder.tagLeaderView.setOnClickListener(v -> voiceInterFace.onCreateVoiceClick(createLeader));

            String user_image = PrefUtil.getString(Constant.PreferenceKey.USER_IMAGE);
            if (!TextUtils.isEmpty(user_image))
                Picasso.with(mContext).load(user_image).placeholder(R.drawable.sample_image).into(viewHolder.issue_creator_image);
            else
                viewHolder.issue_creator_image.setImageResource(R.drawable.sample_image);
        }
        */
    }

    @Override
    public int getItemViewType(int position) {
        if (trendingPositions.contains(position) && trendingVoiceModel.size() > 0) {
            return TRENDING_POSITION;
        } else {
            return ALL_ISSUE;
        }
    }

    @Override
    public int getItemCount() {
        return feedModels.size();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        if (holder instanceof HomeFeedViewHolder) {
            HomeFeedViewHolder feed_holder = ((HomeFeedViewHolder) holder);
            feed_holder.onDetached();
        }
    }

    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (holder instanceof HomeFeedViewHolder) {
            HomeFeedViewHolder feed_holder = ((HomeFeedViewHolder) holder);

            feed_holder.onAttached();
        }

    }

    static class SuggestionUsersHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.hor_recycler_view)
        Container hor_recycler_view;
        @BindView(R.id.rl_main)
        RelativeLayout rl_main;

        SuggestionUsersHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        private final SnapHelper snapHelper = new PagerSnapHelper();

        void onDetached() {
            snapHelper.attachToRecyclerView(null);
        }

        void onAttached() {
            snapHelper.attachToRecyclerView(hor_recycler_view);
        }
    }

    public void updateLikeDislike(int position, int like_count, int dislike_count, FeedActionModel feedActionModel, int image_position) {
        if (feedModels.size() != 0) {
            this.image_position = image_position;
            FeedModel feedModel = feedModels.get(position);
            feedModel.setLikes(like_count);
            feedModel.setDislikes(dislike_count);
            feedModel.setFeedActionModel(feedActionModel);
            feedModels.set(position, feedModel);
            new Thread(() -> MoliticsDatabase.getAppDatabase(mContext).getAllFeedsDao().updateLikeAndDislike(like_count, dislike_count, feedActionModel, feedModel.getId())).start();
        }
    }

    public void updateTrendingLikeDislike(int position, int like_count, int dislike_count, int my_action, int image_position) {
        if (trendingVoiceModel.size() != 0) {
            this.image_position = image_position;
            UsersFeedModel voiceAllModel = trendingVoiceModel.get(position);
            trendingVoiceModel.set(position, voiceAllModel);

            if (trendingVoiceAdapter != null)
                trendingVoiceAdapter.notifyItemChanged(position);
        }
    }

    public void onTrendingUnFollowResponse(int position, int count, int image_position) {
        if (trendingVoiceModel.size() != 0) {

            this.image_position = image_position;

            UsersFeedModel voiceAllModel = trendingVoiceModel.get(position);
            //voiceAllModel.setFollowing(count);
            //voiceAllModel.setIsFollowing(0);
            trendingVoiceModel.set(position, voiceAllModel);
            trendingVoiceAdapter.notifyItemChanged(position);
        }
    }

    public void onTrendingFollowResponse(int position, int count, int image_position) {
        if (trendingVoiceModel.size() != 0) {

            this.image_position = image_position;

            UsersFeedModel voiceAllModel = trendingVoiceModel.get(position);
            //voiceAllModel.setFollowing(count);
            //voiceAllModel.setIsFollowing(1);
            trendingVoiceModel.set(position, voiceAllModel);
            trendingVoiceAdapter.notifyItemChanged(position);
        }
    }

    public void onUnFollowResponse(int position, int count, int image_position) {
  /*      if (voiceAllModels.size() != 0) {
            this.image_position = image_position;

            FeedModel voiceAllModel = voiceAllModels.get(position);
            voiceAllModel.setFollowing(count);
            voiceAllModel.setIsFollowing(0);
            voiceAllModels.set(position, voiceAllModel);

        }*/
    }

    public void onFollowResponse(int position, int count, int image_position) {
  /*      if (voiceAllModels.size() != 0) {
            this.image_position = image_position;
            FeedModel voiceAllModel = voiceAllModels.get(position);
            voiceAllModel.setFollowing(count);
            voiceAllModel.setIsFollowing(1);
            voiceAllModels.set(position, voiceAllModel);
        }*/
    }

    public void attachTrendingVideo() {
//        if (trendingVoiceAdapter != null) {
//            trendingVoiceAdapter.attachVideoView();
//        }
    }

    public void detachTrendingVideo() {
//        if (trendingVoiceAdapter != null) {
//            trendingVoiceAdapter.detachVideoView();
//        }
    }
}

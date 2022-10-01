package com.molitics.molitician.ui.dashboard.voice.adapter;

import android.content.Context;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.molitics.molitician.R;
import com.molitics.molitician.db.MoliticsDatabase;
import com.molitics.molitician.ui.dashboard.voice.model.VoiceAllModel;
import com.molitics.molitician.ui.dashboard.voice.viewHolder.VoiceAllViewHolder;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.ExtraUtils;
import com.molitics.molitician.util.PrefUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import im.ene.toro.widget.Container;

import static com.molitics.molitician.util.Constant.createImage;
import static com.molitics.molitician.util.Constant.createLeader;
import static com.molitics.molitician.util.Constant.createNormal;
import static com.molitics.molitician.util.Constant.createVideo;

/**
 * Created by rahul on 14/11/17.
 */

public class VoiceAllAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements
        /*ImageViewPager.VoiceImageListener,*/ ExtraUtils.NotifyCustomDataSetChanged {

    public static final String TAG = "VoiceAllAdapter";
    private List<VoiceAllModel> voiceAllModels = new ArrayList<>();
    private List<VoiceAllModel> trendingVoiceModel = new ArrayList<>();

    private Context mContext;
//    public static int CREATE_ISSUE = 0;
    public static int ALL_ISSUE = 1;
    public static int TRENDING_POSITION = 2;
//    private Boolean is_create_visible = true;

    private VoiceInterFace voiceInterFace;
    private List<Integer> trendingPositions = new ArrayList<>();
    private int image_position = 0;
    private TrendingVoiceAdapter trendingVoiceAdapter;

    @Override
    public void notifyCustom(int image_position) {
        this.image_position = image_position;
    }

    public interface VoiceInterFace {
        void onLoadMore();

        void onCreateVoiceClick(int itemSelection);

        void onLikeDislikeClick(String from, int actual_position, int position, int issue_id, int action, int image_position);

        void onDotClick(int position, int issue_id, int action_type, VoiceAllModel voiceModel);

        void onCommentClick(String from, int position, int issue_id, int image_position);

        void onShareClick(int issue_id, String userName, String issue_title);

        void onFollowClick(String from, int actual_position, int position, int issue_id, int image_position);

        void onUnFollowClick(String from, int actual_position, int position, int issue_id, int image_position);

        void onTagLeaderClick(int leader_id);

        void onImageClick(VoiceAllViewHolder voiceAllViewHolder, int position, int issue_id, List<String> imageList);

        void onCreatorImageClick(int position, int user_id);

        void onHashTagClick(int tag_id, String tag_name);

        void onDetailClick(int position, int tempPosition, VoiceAllModel voiceModel);

        void onLikeDislikeClick(int issue_id, int action);

        void onVideoClick(VoiceAllViewHolder voiceAllViewHolder, String from, int clickPosition, int issueId);

        void onUserFollowActionClick(int userId, int action);

        void onReportDialogClick(int userId, int issueId);
    }

    public VoiceAllAdapter(Context mContext, VoiceInterFace voiceInterFace) {
        this.mContext = mContext;
        this.voiceInterFace = voiceInterFace;
    }

    public void addVoiceList(List<VoiceAllModel> v) {
        this.voiceAllModels.addAll(v);
        notifyDataSetChanged();
    }

    public void addVoiceOnPosition(int position, VoiceAllModel voiceAllModel) {
        this.voiceAllModels.set(position, voiceAllModel);
        notifyDataSetChanged();
    }


    public void addVoiceOnTop(int position, VoiceAllModel voiceAllModel) {
        this.voiceAllModels.add(position, voiceAllModel);
        notifyDataSetChanged();
    }


    public void updateUserFollow(int userId, int action) {
        this.voiceAllModels.parallelStream().filter(voiceAllModel -> voiceAllModel.getUserId() == userId).forEach(data -> data.setIsFollowing(action));
        notifyDataSetChanged();
    }

    public void removeVoiceOnTop(int position) {
        this.voiceAllModels.remove(position);
        notifyDataSetChanged();
    }


    public void editVideoUrl(int position, List<String> image_list) {
        List<String> locale_url = voiceAllModels.get(position).getImages();

        for (Iterator<String> iterator = locale_url.iterator(); iterator.hasNext(); ) {
            String url = iterator.next();
            if (url.contains("storage")) {
                iterator.remove();
            }
        }
        locale_url.addAll(image_list);

        notifyDataSetChanged();
    }

    public void addCommentCount(int position, int count) {
        VoiceAllModel voiceAllModel = voiceAllModels.get(position);

        voiceAllModel.setComments(voiceAllModel.getComments() + count);
        notifyDataSetChanged();
    }

    public void addTrendingCommentCount(int position, int count) {
        VoiceAllModel voiceAllModel = trendingVoiceModel.get(position);

        voiceAllModel.setComments(voiceAllModel.getComments() + count);

        this.trendingVoiceModel.add(voiceAllModel);
        trendingVoiceAdapter.notifyItemChanged(position);
    }

    public void addTrendingPositions(List<Integer> trendingPositions) {
        this.trendingPositions = trendingPositions;
        notifyDataSetChanged();
    }

    public void addTrendingVoiceList(List<VoiceAllModel> trendingVoiceModel) {
        this.trendingVoiceModel.clear();
        this.trendingVoiceModel.addAll(trendingVoiceModel);
        notifyDataSetChanged();
    }

    public List<VoiceAllModel> getTrendingList() {
        return trendingVoiceModel;
    }

    public List<VoiceAllModel> getVoiceList() {
        return voiceAllModels;
    }

    public void updateVideoView(int percentDone) {
        voiceAllModels.get(0).setUploadedPercent(percentDone);
        notifyDataSetChanged();
    }

    public int getVoiceListSize() {
        return voiceAllModels.size();
    }

    public void clearVoiceList() {
        voiceAllModels.clear();
        notifyDataSetChanged();
    }

    public void clearTrendingList() {
        trendingVoiceModel.clear();
        if (trendingVoiceAdapter != null)
            trendingVoiceAdapter.notifyDataSetChanged();
    }

    public void deleteVoice(int position) {
        voiceAllModels.remove(position);

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

  /*      if (viewType == TRENDING_POSITION) {
            View mView = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.recycler_voice_trending, parent, false);
            return new TrendingIssueHolder(mView);
        } else if (viewType == CREATE_ISSUE) {
            View mView = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.adapter_creator_issue, parent, false);
            return new CreateIssueViewHolder(mView);
        } else {
            View mView = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.adapter_voice_all, parent, false);
            return new VoiceAllViewHolder(mView);
        }*/
        if (viewType == TRENDING_POSITION) {
            View mView = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.recycler_voice_trending, parent, false);
            return new TrendingIssueHolder(mView);
        }else {
            View mView = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.adapter_voice_all, parent, false);
            return new VoiceAllViewHolder(mView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder.getItemViewType() == ALL_ISSUE && voiceAllModels.size() != 0) {
            VoiceAllViewHolder viewHolder = (VoiceAllViewHolder) holder;
            int set_position = position;
            if (!trendingVoiceModel.isEmpty() && trendingPositions.size() != 0 && trendingPositions.get(0) <= set_position) {
                set_position = set_position - 1;
            }

            int temp_position = set_position;

            VoiceAllModel voiceModel = voiceAllModels.get(temp_position);
            if (voiceModel != null) {
                viewHolder.bindView(mContext, TAG, viewHolder, voiceModel, holder.getAdapterPosition(),
                        temp_position, voiceInterFace, this, image_position);
            }
        }else if (holder.getItemViewType() == TRENDING_POSITION) {
            TrendingIssueHolder issueHolder = (TrendingIssueHolder) holder;

            if (trendingVoiceAdapter != null)
                trendingVoiceAdapter = null;
            trendingVoiceAdapter = new TrendingVoiceAdapter(mContext, voiceInterFace, trendingVoiceModel);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);

            issueHolder.hor_recycler_view.setLayoutManager(mLayoutManager);
            issueHolder.hor_recycler_view.setAdapter(trendingVoiceAdapter);
            if (trendingVoiceModel.size() != 0) {
                issueHolder.rl_main.setVisibility(View.VISIBLE);
            } else {
                issueHolder.rl_main.setVisibility(View.GONE);
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


        } else if (holder.getItemViewType() == TRENDING_POSITION) {
            TrendingIssueHolder issueHolder = (TrendingIssueHolder) holder;

            if (trendingVoiceAdapter != null)
                trendingVoiceAdapter = null;
            trendingVoiceAdapter = new TrendingVoiceAdapter(mContext, voiceInterFace, trendingVoiceModel);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);

            issueHolder.hor_recycler_view.setLayoutManager(mLayoutManager);
            issueHolder.hor_recycler_view.setAdapter(trendingVoiceAdapter);
            if (trendingVoiceModel.size() != 0) {
                issueHolder.rl_main.setVisibility(View.VISIBLE);
            } else {
                issueHolder.rl_main.setVisibility(View.GONE);
            }
        }*/
    }

    @Override
    public int getItemViewType(int position) {
   /*     if (position == 0 && is_create_visible) {
            return CREATE_ISSUE;
        } else if (trendingPositions.contains(position) && trendingVoiceModel.size() > 0) {
            return TRENDING_POSITION;
        } else {
            return ALL_ISSUE;
        }*/

        if (trendingPositions.contains(position) && trendingVoiceModel.size() > 0) {
            return TRENDING_POSITION;
        } else {
            return ALL_ISSUE;
        }
    }

    @Override
    public int getItemCount() {
 /*       if (is_create_visible)
            return voiceAllModels.size() != 0 ? voiceAllModels.size() + 1 : 1;
        else return voiceAllModels.size() + trendingVoiceModel.size();
*/
        return voiceAllModels.size();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        if (holder instanceof VoiceAllViewHolder) {
            VoiceAllViewHolder voice_holder = ((VoiceAllViewHolder) holder);
            voice_holder.onDetached();
        }
    }

    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (holder instanceof VoiceAllViewHolder) {
            VoiceAllViewHolder voice_holder = ((VoiceAllViewHolder) holder);

            voice_holder.onAttached();
        }

    }

    class TrendingIssueHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.hor_recycler_view)
        Container hor_recycler_view;
        @BindView(R.id.rl_main)
        RelativeLayout rl_main;


        TrendingIssueHolder(View itemView) {
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

  /*  public class CreateIssueViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.create_issue)
        public TextView create_issue;
        @BindView(R.id.imageRl)
        RelativeLayout imageRl;
        @BindView(R.id.videoRl)
        RelativeLayout videoRl;
        @BindView(R.id.tagLeaderView)
        RelativeLayout tagLeaderView;
        @BindView(R.id.issue_creator_image)
        public CircleImageView issue_creator_image;

        public CreateIssueViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }*/

  /*  class VideoUplaodingViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.video_uploading_bar)
        ProgressBar video_uploading_bar;

        public VideoUplaodingViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }*/

    public void updateLikeDislike(int position, int like_count, int dislike_count, int my_action, int image_position) {
        if (voiceAllModels.size() != 0) {

            this.image_position = image_position;
            VoiceAllModel voiceAllModel = voiceAllModels.get(position);
            voiceAllModel.setLikes(like_count);
            voiceAllModel.setDislikes(dislike_count);
            voiceAllModel.setMyAction(my_action);
            voiceAllModels.set(position, voiceAllModel);
            new Thread(() -> MoliticsDatabase.getAppDatabase(mContext).getAllVoiceRecordDao().updateLikeAndDislike(like_count, dislike_count, my_action, voiceAllModel.getId())).start();
        }
    }

    public void updateTrendingLikeDislike(int position, int like_count, int dislike_count, int my_action, int image_position) {
        if (trendingVoiceModel.size() != 0) {
            this.image_position = image_position;
            VoiceAllModel voiceAllModel = trendingVoiceModel.get(position);
            voiceAllModel.setLikes(like_count);
            voiceAllModel.setDislikes(dislike_count);
            voiceAllModel.setMyAction(my_action);
            trendingVoiceModel.set(position, voiceAllModel);

            if (trendingVoiceAdapter != null)
                trendingVoiceAdapter.notifyItemChanged(position);
        }
    }

    public void onTrendingUnFollowResponse(int position, int count, int image_position) {
        if (trendingVoiceModel.size() != 0) {

            this.image_position = image_position;

            VoiceAllModel voiceAllModel = trendingVoiceModel.get(position);
            voiceAllModel.setFollowing(count);
            voiceAllModel.setIsFollowing(0);
            trendingVoiceModel.set(position, voiceAllModel);
            trendingVoiceAdapter.notifyItemChanged(position);
        }
    }

    public void onTrendingFollowResponse(int position, int count, int image_position) {
        if (trendingVoiceModel.size() != 0) {

            this.image_position = image_position;

            VoiceAllModel voiceAllModel = trendingVoiceModel.get(position);
            voiceAllModel.setFollowing(count);
            voiceAllModel.setIsFollowing(1);
            trendingVoiceModel.set(position, voiceAllModel);
            trendingVoiceAdapter.notifyItemChanged(position);
        }
    }

    public void onUnFollowResponse(int position, int count, int image_position) {
        if (voiceAllModels.size() != 0) {
            this.image_position = image_position;

            VoiceAllModel voiceAllModel = voiceAllModels.get(position);
            voiceAllModel.setFollowing(count);
            voiceAllModel.setIsFollowing(0);
            voiceAllModels.set(position, voiceAllModel);

        }
    }

    public void onFollowResponse(int position, int count, int image_position) {
        if (voiceAllModels.size() != 0) {
            this.image_position = image_position;
            VoiceAllModel voiceAllModel = voiceAllModels.get(position);
            voiceAllModel.setFollowing(count);
            voiceAllModel.setIsFollowing(1);
            voiceAllModels.set(position, voiceAllModel);
        }
    }

    public void attachTrendingVideo() {
        if (trendingVoiceAdapter != null) {
            trendingVoiceAdapter.attachVideoView();
        }
    }

    public void detachTrendingVideo() {
        if (trendingVoiceAdapter != null) {
            trendingVoiceAdapter.detachVideoView();
        }
    }
}

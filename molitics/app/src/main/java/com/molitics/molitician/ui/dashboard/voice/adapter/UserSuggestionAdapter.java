package com.molitics.molitician.ui.dashboard.voice.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.molitics.molitician.R;
import com.molitics.molitician.ui.dashboard.voice.model.UsersFeedModel;
import com.molitics.molitician.ui.dashboard.voice.viewHolder.HomeFeedViewHolder;
import com.molitics.molitician.ui.dashboard.voice.viewHolder.UserViewHolder;
import com.molitics.molitician.util.ExtraUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import im.ene.toro.PlayerSelector;
import im.ene.toro.widget.Container;

import static com.molitics.molitician.util.Util.attachSuggestionToLinearView;

/**
 * Created by rahul on 17/01/18.
 */

public class UserSuggestionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements ExtraUtils.NotifyCustomDataSetChanged {
    public static String TAG = "FeedSuggestionAdapter";

    private List<UsersFeedModel> feedModels = new ArrayList<>();
    private final Context mContext;


    UserSuggestionAdapter(Context mContext, List<UsersFeedModel> feedModels) {
        this.mContext = mContext;
        this.feedModels.addAll(feedModels);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.suggestion_user_adapter, parent, false);
        return new UserViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        UserViewHolder mViewHolder = (UserViewHolder) holder;
        UsersFeedModel userFeedModel = feedModels.get(position);
        if (userFeedModel != null) {
            mViewHolder.bindView(mContext, mViewHolder, userFeedModel);
/*            mViewHolder.user_name.setText(userFeedModel.getName());
            mViewHolder.creator_image.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(userFeedModel.getImage()))
                Picasso.with(mContext).load(userFeedModel.getImage()).into(mViewHolder.creator_image);
            else mViewHolder.creator_image.setImageResource(R.drawable.sample_image);*/
        }
    }

    @Override
    public int getItemCount() {
        return feedModels.size();
    }


    @Override
    public void notifyCustom(int image_position) {

    }

    @Override
    public void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        if (holder instanceof HomeFeedAdapter.SuggestionUsersHolder) {
            ((HomeFeedAdapter.SuggestionUsersHolder) holder).onDetached();
        }
    }

    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (holder instanceof HomeFeedAdapter.SuggestionUsersHolder) {
            ((HomeFeedAdapter.SuggestionUsersHolder) holder).onAttached();
        }
    }
}

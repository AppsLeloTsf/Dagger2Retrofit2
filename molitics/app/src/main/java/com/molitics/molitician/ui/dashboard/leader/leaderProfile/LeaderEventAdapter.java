package com.molitics.molitician.ui.dashboard.leader.leaderProfile;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.molitics.molitician.R;
import com.molitics.molitician.interfaces.EventShareListener;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.Util;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rahul on 1/4/2017.
 */

public class LeaderEventAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Event> mEvent = new ArrayList<>();
    private Context mContext;
    private EventShareListener shareListener;
    private String mStatus;
    private String mStatusURL = "";

    LeaderEventAdapter(Context mContext, EventShareListener shareListener) {
        this.mContext = mContext;
        this.shareListener = shareListener;
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0 && (!mStatus.isEmpty() || !mStatusURL.isEmpty())) {
            return Constant.Condition.LEADER_STATUS;
        } else {
            return Constant.Condition.LEADER_EVENT;
        }
        //  return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView;

        if (viewType == Constant.Condition.LEADER_STATUS) {
            mView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.candidate_status, parent, false);
            return new CandidateStatus(mView);
        } else {
            mView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_candidate_events, parent, false);
            return new CandidateEvents(mView);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        int mPosition = 0;
        if ((mStatus != null && !mStatus.isEmpty()) || !mStatusURL.isEmpty())
            mPosition = 1;
        if (holder.getItemViewType() == Constant.Condition.LEADER_EVENT) {

            final Event candidateEvents = mEvent.get(position - mPosition);
            CandidateEvents candidate_event_adapter = (CandidateEvents) holder;

            candidate_event_adapter.event_heading_txt.setText(candidateEvents.getName());
            candidate_event_adapter.event_time_txt.setText(candidateEvents.getTime());
            candidate_event_adapter.event_location_txt.setText(candidateEvents.getAddress());
            candidate_event_adapter.share_event.setOnClickListener(v -> shareListener.onEventShare(candidateEvents.getName() + " " + candidateEvents.getTime() + " " + candidateEvents.getAddress()));
        } else {
            final CandidateStatus candidateStatus = (CandidateStatus) holder;
            if (mStatus.isEmpty()) {
                candidateStatus.status_view.setVisibility(View.GONE);
            } else {
                candidateStatus.status_view.setVisibility(View.VISIBLE);
                candidateStatus.status_view.setText(mStatus);
            }
            if (!mStatusURL.isEmpty() && mStatusURL.length() > 4) {

                candidateStatus.status_image.setVisibility(View.VISIBLE);
                Picasso.with(mContext).load(mStatusURL).into(candidateStatus.status_image);

                candidateStatus.imgDotView.setOnClickListener(view -> {
                    PopupMenu popup = new PopupMenu(mContext, candidateStatus.imgDotView);
                    popup.getMenuInflater().inflate(R.menu.pop_up, popup.getMenu());
                    popup.getMenu().clear();

                    popup.getMenu().add(0, 0, 0, mContext.getString(R.string.share));
                    popup.getMenu().add(1, 1, 1, mContext.getString(R.string.save));
                    popup.setOnMenuItemClickListener(item -> {
                        shareListener.onImageSave();
                        Picasso.with(mContext)
                                .load(mStatusURL)
                                .into(new Target() {
                                    @Override
                                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                        File file = Util.saveImage(mContext, bitmap);
                                        if (item.getItemId() == 0) {
                                            Intent share = new Intent(Intent.ACTION_SEND);
                                            share.setType("image/jpeg");
                                            share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                                            mContext.startActivity(Intent.createChooser(share, "Share Image"));
                                        } else if (item.getItemId() == 1) {
                                            // Show a toast message on successful save
                                            if (file.getPath() != null)
                                                Toast.makeText(mContext, mContext.getString(R.string.image_saved_to_sd_card),
                                                        Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onBitmapFailed(Drawable errorDrawable) {

                                    }

                                    @Override
                                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                                    }
                                });

                        return true;
                    });
                    popup.show();
                });

            } else {
                candidateStatus.status_image.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public int getItemCount() {
        if ((mStatus != null && !mStatus.isEmpty()) || !mStatusURL.isEmpty()) {
            return mEvent.size() + 1;
        } else {
            return mEvent.size();
        }
    }

    public static class CandidateEvents extends RecyclerView.ViewHolder {

        @BindView(R.id.event_heading_txt)
        TextView event_heading_txt;
        @BindView(R.id.event_time_txt)
        TextView event_time_txt;
        @BindView(R.id.event_location_txt)
        TextView event_location_txt;
        @BindView(R.id.share_event)
        ImageView share_event;

        public CandidateEvents(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class CandidateStatus extends RecyclerView.ViewHolder {

        @BindView(R.id.status_view)
        TextView status_view;
        @BindView(R.id.status_image)
        ImageView status_image;
        @BindView(R.id.imgDotView)
        ImageView imgDotView;


        public CandidateStatus(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

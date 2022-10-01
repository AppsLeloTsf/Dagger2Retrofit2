package com.molitics.molitician.ui.dashboard.voice.viewHolder;

import android.content.Context;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.molitics.molitician.R;
import com.molitics.molitician.ui.dashboard.leader.AllLeaderModel;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by rahul on 25/05/18.
 */

public class LeaderListViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.leader_image)
    CircleImageView leader_image;
    @BindView(R.id.leader_name)
    TextView leader_name;
    @BindView(R.id.leader_radio)
    RadioButton leader_radio;
    @BindView(R.id.leader_constituency_view)
    TextView leader_constituency_view;

    public LeaderListViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setClickListener(View.OnClickListener clickListener) {
        this.itemView.setOnClickListener(clickListener);
    }

    public void bind(Context mContext, int position, AllLeaderModel leaderModel) {

        leader_name.setText(leaderModel.getName() + " (" + leaderModel.getPartyCode() + ")");
        leader_constituency_view.setText(leaderModel.getPosition() + " , " + leaderModel.getLocation());

        Picasso.with(mContext).load(leaderModel.getProfileImage()).placeholder(R.drawable.sample_image).into(leader_image);

        if (leaderModel.getSelectedLeader())
            leader_radio.setChecked(true);
        else
            leader_radio.setChecked(false);

    }
}

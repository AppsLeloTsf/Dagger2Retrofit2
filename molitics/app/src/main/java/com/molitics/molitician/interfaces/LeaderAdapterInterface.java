package com.molitics.molitician.interfaces;

import com.molitics.molitician.ui.dashboard.leader.AllLeaderModel;

/**
 * Created by rahul on 1/6/2017.
 */

public interface LeaderAdapterInterface {

    void onFollowClick(int id, int position);

    void onUnFollowClick(int id, int position);

    void onLeaderNewsClick(int position, AllLeaderModel leaderModel);

    void onLeaderClick(int position, AllLeaderModel leaderModel);

    void onLikeDisClick(int position, int candidate_id, int action);

    void onDeleteClick(int position, int candidate_id);

    interface LeaderClickListener {
        void onLeaderClick(int position, AllLeaderModel leaderModel);
    }

}


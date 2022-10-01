package com.molitics.molitician.interfaces;

import com.molitics.molitician.ui.dashboard.leader.leaderProfile.TwitterDataResponse;

/**
 * Created by rahul on 1/5/2017.
 */

public interface TwitterDataListener {

    void onTwitterData(TwitterDataResponse dataResponse);

    void onTwitterNullResponse();

}

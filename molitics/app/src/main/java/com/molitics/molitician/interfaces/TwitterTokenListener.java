package com.molitics.molitician.interfaces;

import com.molitics.molitician.ui.dashboard.leader.leaderProfile.TwitterTokenResponse;

/**
 * Created by rahul on 1/5/2017.
 */

public interface TwitterTokenListener {
    void onToken(TwitterTokenResponse tokenResponse);
}

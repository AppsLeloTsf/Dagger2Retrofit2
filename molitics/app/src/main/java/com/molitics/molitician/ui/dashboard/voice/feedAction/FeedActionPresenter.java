package com.molitics.molitician.ui.dashboard.voice.feedAction;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.model.APIResponse;

/**
 * Created by rahul on 03/04/18.
 */

public class FeedActionPresenter {


    public interface FeedActionUI {

        void onFeedActionResponse(APIResponse apiResponse);

        void onFeedActionError(ApiException apiException);

        void onUserFollowResponse(APIResponse apiResponse);

        void onUserFollowError(ApiException apiException);

        void onUserUnFollowResponse(APIResponse apiResponse);

        void onUserUnFollowError(ApiException apiException);

    }

    public interface FeedActionRequest {
        void getFeedActionList(int feed_id, int action, int total_count);

        void userFollow(int user_id);

        void userUnFollow(int user_id);
    }
}

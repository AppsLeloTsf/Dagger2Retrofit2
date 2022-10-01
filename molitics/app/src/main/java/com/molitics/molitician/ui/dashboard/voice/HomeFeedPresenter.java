package com.molitics.molitician.ui.dashboard.voice;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.model.APIResponse;

/**
 * Created by Rakesh on 14/09/2021.
 */

public class HomeFeedPresenter {

    public interface HomeFeedUI {

        void onFeedResponse(APIResponse apiResponse);

        void onFeedApiError(ApiException apiexception);

        void onUserImageResponse(APIResponse apiResponse);

        void onUserImageError(ApiException apiException);
    }

    public interface FeedRequest {

        void getAllFeeds(int state_id, int page);

        void likeDislike(int issue_id, int action);

        void onFollow(int issue_id);

        void onUnFollow(int issue_id);

        void deleteIssue(int issue_id);


        void getUserImage(int user_id);

    }


    public interface HomeFeedActionUi {
        void onLikeDislikeResponse(APIResponse apiResponse);

        void onLikeDislikeError(ApiException apiException);

        void onFollowResponse(APIResponse apiResponse);

        void onFollowError(ApiException apiException);

        void onUnFollowResponse(APIResponse apiResponse);

        void onUnFollowError(ApiException apiException);

        void onDeleteIssueResponse(APIResponse apiResponse);

        void onDeleteIssueError(ApiException apiException);
    }
}

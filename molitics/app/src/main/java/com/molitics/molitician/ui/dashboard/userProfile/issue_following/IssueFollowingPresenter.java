package com.molitics.molitician.ui.dashboard.userProfile.issue_following;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.model.APIResponse;

/**
 * Created by rahul on 11/12/17.
 */

public class IssueFollowingPresenter {

    public interface IssueFollowingUi {

        void onIssueFollowingResponse(APIResponse apiResponse);

        void onIssueFollowingError(ApiException apiException);


        void onLikeDislikeResponse(APIResponse apiResponse);

        void onLikeDislikeError(ApiException apiException);

        void onFollowResponse(APIResponse apiResponse);

        void onFollowError(ApiException apiException);

        void onUnFollowResponse(APIResponse apiResponse);

        void onUnFollowError(ApiException apiException);

        void onDeleteIssueResponse(APIResponse apiResponse);

        void onDeleteIssueError(ApiException apiException);

        void onUserImageResponse(APIResponse apiResponse);

        void onUserImageError(ApiException apiException);

    }

    public interface IssueFollowingRequest {

        void getFollowingIssue(int count);

        void likeDislike(int issue_id, int action);

        void onFollow(int issue_id);

        void onUnFollow(int issue_id);

        void deleteIssue(int issue_id);


        void getUserImage(int user_id);


    }


}

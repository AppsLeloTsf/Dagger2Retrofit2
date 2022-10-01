package com.molitics.molitician.ui.dashboard.userProfile;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.model.APIResponse;

/**
 * Created by rahul on 1/12/2017.
 */

public class UserProfilePresenter {


    public interface UserProfileView {

        void onProfileDataResponse(APIResponse apiResponse);

        void onProfileDataApiException(ApiException apiException);

        void onProfileDataServerError(ServerException serverException);

        void onProfileResponse(APIResponse apiResponse);

        void onProfileApiException(ApiException apiException);

        void onProfileServerError(ServerException serverException);

        void onUserFollowResponse(APIResponse apiResponse);

        void onUserFollowError(ApiException apiException);

        void onUserUnFollowResponse(APIResponse apiResponse);

        void onUserUnFollowError(ApiException apiException);


        void onUserFollowingListResponse(APIResponse apiResponse);

        void onUserFollowingListError(ApiException apiException);


    }

    public interface FollowActionView {
        void onFeedActionResponse(APIResponse apiResponse);

        void onFeedActionError(ApiException apiException);

        void onUserFollowResponse(APIResponse apiResponse);

        void onUserFollowError(ApiException apiException);

        void onUserUnFollowResponse(APIResponse apiResponse);

        void onUserUnFollowError(ApiException apiException);
    }

    public interface FollowActionRequest {
        void getFeedActionList(int current_user_id, int feed_id, int action, int total_count);

        void userFollow(int user_id);

        void userUnFollow(int user_id);
    }

    public interface UserProfileRequest {

        void getProfileData(int user_id, int count,String device_id);

        void getProfile();

        void userFollow(int user_id);

        void userUnFollow(int user_id);


    }
}

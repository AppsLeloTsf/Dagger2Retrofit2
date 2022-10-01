package com.molitics.molitician.ui.dashboard.voice;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.model.APIResponse;

/**
 * Created by rahul on 10/11/17.
 */

public class VoicePresenter {

    public interface VoiceUI {

        void onVoiceResponse(APIResponse apiResponse);

        void onVoiceApiError(ApiException apiexception);

        void onUserImageResponse(APIResponse apiResponse);

        void onUserImageError(ApiException apiException);
    }

    public interface VoiceRequest {

        void getAllVoice(int isGetTrending, int state_id, int page, String device_id);

        void likeDislike(int issue_id, int action);

        void onFollow(int issue_id);

        void onUnFollow(int issue_id);

        void deleteIssue(int issue_id);


        void getUserImage(int user_id);

    }


    public interface VoiceActionUi {
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

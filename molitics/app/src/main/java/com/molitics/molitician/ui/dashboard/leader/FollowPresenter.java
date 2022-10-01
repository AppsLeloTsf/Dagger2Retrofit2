package com.molitics.molitician.ui.dashboard.leader;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.model.APIResponse;

/**
 * Created by rahul on 6/20/2017.
 */

public class FollowPresenter {

    public interface FollowView {
        void onFollowResponse(APIResponse apiResponse);

        void onUnFollowRespnse(APIResponse apiResponse);

        void onFollowApiException(ApiException apiException);

        void onFollowServerError(ServerException serverException);

        void onLeaderActionResponse(APIResponse apiResponse);

        void onLeaderActionApiError(ApiException apiException);


        void onDeleteLeaderActionApiError(ApiException apiException);

        void onDeleteLeaderAction(APIResponse apiResponse);

    }

    public interface FollowRequest {
        void followCandidate(int candidate_id);

        void unFollowCandidate(int candidate_id);

        void onLeaderAction(int candidate_id, int action);

        void onDeleteLeaderAction(int candidate_id);

    }
}

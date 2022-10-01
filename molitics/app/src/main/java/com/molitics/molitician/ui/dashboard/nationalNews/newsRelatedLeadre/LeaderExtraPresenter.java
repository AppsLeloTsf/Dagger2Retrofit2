package com.molitics.molitician.ui.dashboard.nationalNews.newsRelatedLeadre;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.model.APIResponse;

/**
 * Created by Rahul on 3/10/2017.
 */

public class LeaderExtraPresenter {

    public interface LeaderExtraView {

        void onNewsLeaderActionResponse(APIResponse apiResponse);

        void onNewsLeaderActionApiError(ApiException apiException);


        void onDeleteNewsLeaderActionApiError(ApiException apiException);

        void onDeleteNewsLeaderAction(APIResponse apiResponse);

        void onFollowResponse(APIResponse apiResponse);

        void onUnFollowresponse(APIResponse apiResponse);

        void onFollowedApiException(ApiException apiException);
    }


    public interface LeaderExtraRequest {

        void followCandidate(int id);

        void unFollowCandidate(int id);

        void onNewsLeaderAction(String type, int news_id, int candidate_id, int action);

        void onDeleteNewsLeaderAction(String type, int news_id, int candidate_id);
    }


}

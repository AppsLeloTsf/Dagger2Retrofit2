package com.molitics.molitician.ui.dashboard.leader.newleaderprofile.leaderNews;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.model.APIResponse;

/**
 * Created by rahul on 7/1/2017.
 */

public class LeaderNewsPresenter {

    public interface LeaderNewsView {
        void onNewsResponse(APIResponse apiResponse);

        void onNewsApiException(ApiException apiException);

        void onNewsSeekResponse(APIResponse apiResponse);

        void onNewsSeekException(ApiException apiExeption);

    }

    public interface LeaderNewsRequest {
        void getLeaderNews(int page_no, int leader_id);

        void seekNews(int candidate_id);
    }
}

package com.molitics.molitician.ui.dashboard.hotTopics.hotTopicDetail;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.model.APIResponse;

/**
 * Created by rahul on 28/11/17.
 */

public class HotTopicDetailPresenter {

    public interface HotTTopicDetailUI {

        void onHotTopicDetailResponse(APIResponse apiResponse);

        void onHotTopicDetailError(ApiException apiException);

        void onLikeDislikeResponse(APIResponse apiResponse);

        void onLikeDislikeError(ApiException apiException);

        void onFollowResponse(APIResponse apiResponse);

        void onFollowError(ApiException apiException);

        void onUnFollowResponse(APIResponse apiResponse);

        void onUnFollowError(ApiException apiException);

        void onDeleteIssueResponse(APIResponse apiResponse);

        void onDeleteIssueError(ApiException apiException);



    }

    public interface HotTopicDetailRequest {

        void getHotTopicDetail(int topic_id, int count);


        void likeDislike(int issue_id, int action);

        void onFollow(int issue_id);

        void onUnFollow(int issue_id);

        void deleteIssue(int issue_id);
    }

}

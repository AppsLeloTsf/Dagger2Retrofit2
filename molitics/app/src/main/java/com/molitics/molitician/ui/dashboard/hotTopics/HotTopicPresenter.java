package com.molitics.molitician.ui.dashboard.hotTopics;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.model.APIResponse;

/**
 * Created by rahul on 27/11/17.
 */

public class HotTopicPresenter {

    public interface HotTopicUI {

        void onHotTopicResponse(APIResponse apiResponse);

        void onHotTopicError(ApiException apiException);

    }

    public interface HotTopicRequest {

        void getHotTopic(int count);
    }

}

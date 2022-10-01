package com.molitics.molitician.ui.dashboard.video;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.model.APIResponse;

/**
 * Created by rahul on 07/11/17.
 */

public class VideoPresenter {

    public interface VideoResponse {

        void onVideoUi(APIResponse apiResponse);

        void onVideoApiError(ApiException apiException);

        void onVideoLanguageSelection(APIResponse loginResponse);

    }

    public interface VideoRequest {

        void getAllVideo(int page_no);

        void getVideoByLanguage(int language, int pageNo,int display_type, int is_not_live);

    }
}

package com.molitics.molitician.ui.dashboard.voice.voiceDetail;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.model.APIResponse;

/**
 * Created by rahul on 15/01/18.
 */

public class VoiceDetailPresenter {


    interface VoiceDetailUi {

        void onDetailVoiceResponse(APIResponse apiResponse);

        void onDetailVoiceException(ApiException apiException);

        void onDeleteIssueResponse(APIResponse apiResponse);

        void onDeleteIssueError(ApiException apiException);
    }

    interface VoiceDetailRequest {

        void getDetailVoice(int voice_id);

        void deleteIssue(int issue_id);



    }

}

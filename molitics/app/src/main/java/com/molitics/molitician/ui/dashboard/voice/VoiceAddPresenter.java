package com.molitics.molitician.ui.dashboard.voice;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.ui.dashboard.voice.model.ImageDeleteModel;
import com.molitics.molitician.ui.dashboard.voice.model.VoiceCreateModel;

import okhttp3.MultipartBody;

/**
 * Created by rahul on 16/11/17.
 */

public class VoiceAddPresenter {


    public interface VoiceUI {

        void onSearchResponse(APIResponse apiResponse);

        void onSearchApiError(ApiException apiException);

        void onDeleteResponse(APIResponse apiResponse);

        void onDeleteError(ApiException apiException);


    }

    public interface VoiceRequest {

        void searchCandidateByName(String name);

        void createVoice(VoiceCreateModel voiceCreateModel);


        void editVoice(int issue_id, VoiceCreateModel voiceEditModel);

        void deleteVoiceImage(int issue_id, ImageDeleteModel image_location);

    }


    public interface VoiceVideo {

        void postVideo(int voice_id, MultipartBody.Part[] body);

    }

    public interface VoiceVideoResponse {
        void onVideoResponse(APIResponse apiResponse);

        void onVideoApiException(ApiException apiException);

        void onVideoServerError(ServerException serverException);
    }
}

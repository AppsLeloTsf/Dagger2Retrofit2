package com.molitics.molitician.ui.dashboard.userProfile.editInfo;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.ui.dashboard.userProfile.model.EditUserProfileModel;

import okhttp3.MultipartBody;

/**
 * Created by rahul on 1/13/2017.
 */

public class EditInfoPresenter {

    public interface EditInfoView {

        void onSubmitResponse(APIResponse apiResponse);

        void onSubmitApiException(ApiException apiException);


        void onStateListResponse(APIResponse apiResponse);

        void onStateListApiException(ApiException apiException);

        void onStateSelection(APIResponse onStateSelect);

        void onSubmitServerError(ServerException serverException);

        void onProfileImageResponse(APIResponse apiResponse);

        void onProfileImageError(ApiException apiException);

        void onProfileImageDeleteResponse(APIResponse apiResponse);

        void onProfileImageDeleteError(ApiException apiException);

    }

    public interface EditInfoRequest {

        void getStateList();

        void getAllLocationFromState(int state_id);

        void submitInfo(EditUserProfileModel submitEditProfile);

        void submitProfileImage(MultipartBody.Part image_url);

        void deleteProfileImage();
    }

}

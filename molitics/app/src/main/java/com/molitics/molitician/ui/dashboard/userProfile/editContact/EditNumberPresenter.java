package com.molitics.molitician.ui.dashboard.userProfile.editContact;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.model.APIResponse;

/**
 * Created by rahul on 1/16/2017.
 */

public class EditNumberPresenter {


    public interface EditContactView {

        void onContactResponse(APIResponse apiResponse);

        void onContactApiException(ApiException apiException);

        void onOtpResponse(APIResponse apiResponse);

        void onOtpApiException(ApiException apiException);

        void onContactServerError(ServerException serverException);
    }

    public interface EditContactRequest {

        void submitEditContact(String contact);

        void submitEditOtp(String otp);
    }
}

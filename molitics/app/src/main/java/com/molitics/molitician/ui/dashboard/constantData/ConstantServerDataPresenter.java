package com.molitics.molitician.ui.dashboard.constantData;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.model.Data;

/**
 * Created by rahul on 12/15/2016.
 */

public class ConstantServerDataPresenter {

    public interface ConstantData {

        void onActiveFragmentResponse(Data data);

        void onStateResponse(APIResponse apiResponse);

        void onStateApiException(ApiException apiException);

        void onStateServerException(ServerException serverException);

    }

    public interface UserRequest {
        void getStateList();

        void getActiveFragment();
    }

}

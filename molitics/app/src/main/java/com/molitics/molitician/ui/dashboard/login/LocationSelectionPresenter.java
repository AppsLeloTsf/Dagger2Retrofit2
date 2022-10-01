package com.molitics.molitician.ui.dashboard.login;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.model.DeviceRegistration;
import com.molitics.molitician.ui.dashboard.constantData.ConstantModel;

import java.util.List;

/**
 * Created by rahul on 12/15/2016.
 */

public class LocationSelectionPresenter {

    public interface LocationView {
        void onState(List<ConstantModel> stateList);

        void onStateSelection(APIResponse onStateSelect);

        void onLocationSelectionResponse(APIResponse apiResponse);

        void onLocationSelectionApiException(ApiException apiException);

        void onLocationSelectionServerException(ServerException serverException);
    }

    public interface UserRequest {

        void hitStateList();

        void getAllLocationFromState(int state_id);

        void submitLocation(DeviceRegistration locationModel);
    }
}

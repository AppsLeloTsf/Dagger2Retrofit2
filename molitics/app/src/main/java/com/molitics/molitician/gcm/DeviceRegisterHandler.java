package com.molitics.molitician.gcm;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.httpapi.ApiHandler;
import com.molitics.molitician.httpapi.ApiResponseListener;
import com.molitics.molitician.httpapi.RetrofitRestClient;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.model.RegisterDeviceModel;

import java.util.Map;

import retrofit2.Call;

/**
 * Created by Rahul on 10/12/2016.
 */

public class DeviceRegisterHandler implements DeviceRegisterPresenter.UserRequest {
    DeviceRegisterPresenter.RegisterView mView;

  public DeviceRegisterHandler(DeviceRegisterPresenter.RegisterView mView) {
        this.mView = mView;
    }

    @Override
    public void setRegisterDevice(RegisterDeviceModel registerModelwq) {

        ApiHandler apiHandler = ApiHandler.getInstance();
        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onRegister(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {

                mView.onRegisterApiException(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {
                mView.onRegisterFormValidation(formValidation);
            }

            @Override
            public void onServerError(ServerException serverException) {

                mView.onRegisterServerError(serverException);

            }

        });

        Call<APIResponse> request = RetrofitRestClient.getInstance().registerDevice(registerModelwq);
        apiHandler.getData(request);
    }
}

package com.molitics.molitician.gcm;


import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.model.RegisterDeviceModel;

import java.util.Map;

/**
 * Created by lenovo on 10/12/2016.
 */

public class DeviceRegisterPresenter {

    public interface RegisterView {

         void onRegister(APIResponse loginResponse);
        void onRegisterApiException(ApiException apiException);
        void onRegisterServerError(ServerException serverException);
        void onRegisterFormValidation(Map<String,String> errors);
    }

    public interface UserRequest {

         void setRegisterDevice(RegisterDeviceModel registerModelwq);
    }
}

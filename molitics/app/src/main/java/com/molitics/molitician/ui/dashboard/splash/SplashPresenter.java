package com.molitics.molitician.ui.dashboard.splash;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.model.APIResponse;

/**
 * Created by rahul on 4/17/2017.
 */

public class SplashPresenter {

    public interface SplashRequest {

        void getVersion(int version);
    }

    public interface SplashView {

        void onVersionResponse(APIResponse apiResponse);

        void onVersionException(ApiException apiException);

    }
}

package com.molitics.molitician.ui.dashboard.termCondition;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.model.APIResponse;

/**
 * Created by rahul on 1/18/2017.
 */

public class TermPresenter {


    public interface TermPresenterView {

        void onTermsResponse(APIResponse apiResponse);

        void onTermsApiException(ApiException apiExceptio);
    }

    public interface TermRequest {

        void getTerms();
    }
}

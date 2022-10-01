package com.molitics.molitician.ui.dashboard.leader.newleaderprofile.contactLeader;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.httpapi.ApiHandler;
import com.molitics.molitician.httpapi.ApiResponseListener;
import com.molitics.molitician.httpapi.RetrofitRestClient;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.ui.dashboard.leader.model.ProblemPostModel;

import java.util.Map;

import retrofit2.Call;

/**
 * Created by rahul on 6/30/2017.
 */

public class ContactLeaderHandler implements ContactLeaderPresenter.ContactRequest {

    ContactLeaderPresenter.ContactView mView;

    public ContactLeaderHandler(ContactLeaderPresenter.ContactView mView) {
        this.mView = mView;
    }


    @Override
    public void submitProblem(int candidate_id, ProblemPostModel postModel) {
        ApiHandler apiHandler = ApiHandler.getInstance();
        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onContactResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onContactResponseException(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {
            }
        });
        Call<APIResponse> responseCall = RetrofitRestClient.getInstance().submitProblem(candidate_id, postModel);
        apiHandler.getData(responseCall);
    }
}

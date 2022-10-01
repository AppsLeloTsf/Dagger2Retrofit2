package com.molitics.molitician.ui.dashboard.election.upcoming_election;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.model.APIResponse;

/**
 * Created by rahul on 1/9/2017.
 */

public class UpComingPresenter {


    public interface UpComingView {
        void onUpComingResponse(APIResponse apiResponse);

        void onUpComingApiException(ApiException apiException);

        void onUpComingServerError(ServerException serverException);
    }

    public interface UpComingUserRequest {
        void getUpComingElection(int page_no);
    }
}

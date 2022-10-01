package com.molitics.molitician.ui.dashboard.election.past_election;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.model.APIResponse;

/**
 * Created by rahul on 1/9/2017.
 */

public class PastElectionPresenter {

    public interface PastElectionView {
        void onPastElectionResponse(APIResponse apiResponse);

        void onPastElectionApiException(ApiException apiException);

        void onPastElectionServerError(ServerException serverException);
    }

    public interface PastUserRequest {
        void getPastElection(int page_no,String stateName);
    }
}

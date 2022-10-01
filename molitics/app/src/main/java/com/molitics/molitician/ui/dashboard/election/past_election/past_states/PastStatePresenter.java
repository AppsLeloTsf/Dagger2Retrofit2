package com.molitics.molitician.ui.dashboard.election.past_election.past_states;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.model.APIResponse;

/**
 * Created by rahul on 1/10/2017.
 */

public class PastStatePresenter {

    public interface PastStateView {

        void onConstituencyResponse(APIResponse apiResponse);


        void onConstituencyApiException(ApiException apiException);

        void onStateResponse(APIResponse apiResponse);

        void onStateApiException(ApiException apiException);

        void onStateServerError(ServerException serverException);

    }

    public interface PastStateRequest {

        void getStateData(int state_id);

        void getConstituencyList(int state_id);

    }
}

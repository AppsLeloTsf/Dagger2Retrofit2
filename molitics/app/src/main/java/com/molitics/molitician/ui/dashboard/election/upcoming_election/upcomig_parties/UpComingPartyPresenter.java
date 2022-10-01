package com.molitics.molitician.ui.dashboard.election.upcoming_election.upcomig_parties;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.model.APIResponse;

/**
 * Created by rahul on 1/12/2017.
 */

public class UpComingPartyPresenter {

    public interface UpComingView {

        void onPartyResponse(APIResponse apiResponse);

        void onPartyApiException(ApiException apiException);

        void onConstituencyResponse(APIResponse apiResponse);

        void onConstituencyApiException(ApiException apiException);

        void onPartyServerError(ServerException serverException);
    }

    public interface UpComingRequest {
        void getPartyList(int state_id);

        void getConstituencyList(int election_id);

    }
}

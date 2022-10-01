package com.molitics.molitician.ui.dashboard.election.past_election.past_states;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.model.APIResponse;

/**
 * Created by rahul on 1/10/2017.
 */

public class PartyListPresenter {

    public interface PartListView {

        void onFollowResponse(APIResponse apiResponse);

        void onUnFollowresponse(APIResponse apiResponse);

        void onPartyListResponse(APIResponse apiResponse);

        void onPartyListApiException(ApiException apiException);

        void onUpComingConstituencyResponse(APIResponse apiResponse);

        void onUpComingConstituencyApiException(ApiException apiException);

        void onFollowedResponse(APIResponse apiResponse);

        void onFollowedApiException(ApiException apiException);



        void onPartyListServerException(ServerException serverException);

    }

    public interface PartyUserRequest {
        void getPartyList(int election_id, int party_id, int page_no);

        void getUpcomingConstituency(int election_id, int mla_id, int mp_id);

        void followCandidate(int id);

        void unFollowCandidate(int id);

        void getLeaderFollowed(int page_no);


    }
}

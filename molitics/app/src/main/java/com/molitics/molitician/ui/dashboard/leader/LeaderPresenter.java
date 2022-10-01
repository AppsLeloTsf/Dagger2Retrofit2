package com.molitics.molitician.ui.dashboard.leader;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.model.DeviceRegistration;
import com.molitics.molitician.ui.dashboard.leader.model.LeadersIdModel;

import java.util.Map;

/**
 * Created by Rahul on 10/13/2016.
 */

public class LeaderPresenter {

    public interface LeaderView {

        void onLeaderResponse(APIResponse apiResponse);

        void onLeaderApiException(ApiException apiException);

        void onAssemblyList(APIResponse apiResponse);

        void onAssemblyListException(ApiException apiException);

        void onLocationSelectionResponse(APIResponse apiResponse);

        void onLocationSelectionApiException(ApiException apiException);

        void onLeaderServerError(ServerException serverException);

        void onLeaderFormValidation(Map<String, String> mapValidation);
    }


    public interface UserRequest {

        // void hitLeaderRequest(int page_no);
        void searchCandidate(int page_no, String name);

        void submitLocation(DeviceRegistration locationModel);

        void getFilterCandidates(LeadersIdModel leadersIdModel);

        void getAssemblyList(int state_value);
    }

    public interface LeaderSearchRequest {
        void searchCandidate(int page_no, String name);
    }

    public interface LeaderListResponse {
        void onLeaderSearchSuccess(APIResponse apiResponse);

        void onLeaderSearchError(ApiException apiError);
    }
}

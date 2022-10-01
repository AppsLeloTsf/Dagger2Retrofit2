package com.molitics.molitician.ui.dashboard.election.past_election.about_constituency;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.model.APIResponse;

/**
 * Created by rahul on 1/11/2017.
 */

public class ConstituencyPresenter {


    public interface ConstituencyView {

        void onConstituencyResponse(APIResponse apiResponse);


        void onConstituencyApiException(ApiException apiException);

        void onConstituencyServerError(ServerException serverException);

        void onConstituencyList(APIResponse apiResponse);


        void onConstituencyListApiException(ApiException apiException);


    }

    public interface ConstituencyUserRequest {

        void getConstituencyData(int state_id, int mp_id, int mla_id);

        void getConstituencyList(int state_id);

    }
}

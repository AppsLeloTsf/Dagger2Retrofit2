package com.molitics.molitician.ui.dashboard.leader.newleaderprofile.contactLeader;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.ui.dashboard.leader.model.ProblemPostModel;

/**
 * Created by rahul on 6/30/2017.
 */

public class ContactLeaderPresenter {

    public interface ContactView {
        void onContactResponse(APIResponse apiResponse);

        void onContactResponseException(ApiException apiException);
    }

    public interface ContactRequest {
        void submitProblem(int candidate_id, ProblemPostModel postModel);

    }
}


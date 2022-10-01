package com.molitics.molitician.ui.dashboard.leader.newleaderprofile.handler;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.ui.dashboard.voice.VoicePresenter;

/**
 * Created by Rahul on 6/28/2017.
 */

public class NewCandidateProfilePresenter  extends VoicePresenter {

    public interface LeaderProfileView {

        void onProfileResponse(APIResponse apiResponse);

        void onProfileApiException(ApiException apiException);


        void onMoreLeaderFeedsResponse(APIResponse apiResponse);

        void onMoreLeaderFeedsError(ApiException apiException);

    }

    public interface UserRequest {

        void getProfile(int leader_id);
        void getMoreLeaderFeeds(int leader_id, int total_count,String device_id);

    }

}

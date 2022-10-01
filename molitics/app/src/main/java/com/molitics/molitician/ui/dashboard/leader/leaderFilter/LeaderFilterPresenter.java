package com.molitics.molitician.ui.dashboard.leader.leaderFilter;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.ui.dashboard.constantData.ConstantModel;

import java.util.List;

/**
 * Created by rahul on 1/3/2017.
 */

public class LeaderFilterPresenter {

    public interface LeaderFilterView {

        void onLeaderFilterStateList(List<ConstantModel> stateList);

        void onLeaderFilterStateException(ApiException apiException);

        void onPartyUpdate(List<ConstantModel> leaderPartyList);

        void onMpUpdate(List<ConstantModel> leaderMpList);

        void onMlaUpdate(List<ConstantModel> LeaderMlaList);

        void onDistrictUpdate(List<ConstantModel> leaderDistrictList);

        void onLeaderApiException(ApiException apiException);

        void onLeaderFilterStateEntity(APIResponse apiResponse);

        void onFilterPost(List<ConstantModel> leaderPostList);

        void onFilterPostException(ApiException apiException);

    }

    public interface UserRequest {
        void getStateList();

        void getOtherLocationList(int state_id);

        void getFilterPost();


    }
}

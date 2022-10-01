package com.molitics.molitician.ui.dashboard.cartoon;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.model.APIResponse;

/**
 * Created by rahul on 19/02/18.
 */

public class CartoonPresenter {

    public interface CartoonUI {

        void onCartoonResponse(APIResponse apiResponse);

        void onCartoonError(ApiException apiException);


    }

    public interface CartoonRequest {

        void getCartoon(int page_no);
    }


}

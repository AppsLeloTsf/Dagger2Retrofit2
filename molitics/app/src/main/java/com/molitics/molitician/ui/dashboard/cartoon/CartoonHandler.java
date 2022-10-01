package com.molitics.molitician.ui.dashboard.cartoon;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.httpapi.ApiHandler;
import com.molitics.molitician.httpapi.ApiResponseListener;
import com.molitics.molitician.httpapi.RetrofitRestClient;
import com.molitics.molitician.model.APIResponse;

import java.util.Map;

import retrofit2.Call;

/**
 * Created by rahul on 19/02/18.
 */

public class CartoonHandler implements CartoonPresenter.CartoonRequest {

    CartoonPresenter.CartoonUI mView;

    public CartoonHandler(CartoonPresenter.CartoonUI mView) {
        this.mView = mView;
    }

    @Override
    public void getCartoon(int page_no) {

        ApiHandler apiHandler = ApiHandler.getInstance();

        apiHandler.setApiResponseListener(new ApiResponseListener() {
            @Override
            public void onApiResponse(APIResponse apiResponse) {
                mView.onCartoonResponse(apiResponse);
            }

            @Override
            public void onApiError(ApiException apiException) {
                mView.onCartoonError(apiException);
            }

            @Override
            public void onFormValidationError(Map<String, String> formValidation) {

            }

            @Override
            public void onServerError(ServerException serverException) {

            }
        });

        Call<APIResponse> apiResponseCall = RetrofitRestClient.getInstance().getAllCartoon(page_no);

        apiHandler.getData(apiResponseCall);

    }
}

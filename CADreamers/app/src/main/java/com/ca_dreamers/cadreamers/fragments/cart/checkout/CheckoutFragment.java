package com.ca_dreamers.cadreamers.fragments.cart.checkout;

import static com.ca_dreamers.cadreamers.R.id.tvCheckoutTotalPrice;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.adapter.cart.AdapterFetchCart;
import com.ca_dreamers.cadreamers.api.Api;
import com.ca_dreamers.cadreamers.api.RetrofitClient;
import com.ca_dreamers.cadreamers.models.cart.checkout.ModelCheckout;
import com.ca_dreamers.cadreamers.models.cart.fetch_cart.ModelFetchCart;
import com.ca_dreamers.cadreamers.storage.SharedPrefManager;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutFragment extends Fragment {

    private String strUserId;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tvCheckoutTotalProduct)
    protected TextView tvCheckoutTotalProduct;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tvCheckoutTotalPrice)
    protected TextView tvCheckoutTotalPrice;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tvCheckoutDiscount)
    protected TextView tvCheckoutDiscount;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tvCheckoutGrandTotal)
    protected TextView tvCheckoutGrandTotal;

    public static CheckoutFragment newInstance() {
        return new CheckoutFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.checkout_fragment, container, false);
        ButterKnife.bind(this, view);
        SharedPrefManager sharedPrefManager = new SharedPrefManager(getContext());
        strUserId = sharedPrefManager.getUserId();
        callCheckoutApi();
        return view;
    }

    private void callCheckoutApi(){
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        JsonObject gsonObject;
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("userid", strUserId);

            gsonObject = (JsonObject) JsonParser.parseString(paramObject.toString());
            Call<ModelCheckout> call = api.checkoutCart(gsonObject);

            call.enqueue(new Callback<ModelCheckout>() {
                @Override
                public void onResponse(@NonNull Call<ModelCheckout> call, @NonNull Response<ModelCheckout> response) {
                    ModelCheckout modelCheckout = response.body();
                    assert modelCheckout != null;
                    String strProdTotal = String.valueOf(modelCheckout.getData().getTotalProduct());
                    String strProdTotalPrice = String.valueOf(modelCheckout.getData().getTotalPrice());
                    String strProdTotalDiscount = String.valueOf(modelCheckout.getData().getDiscountamt());
                    String strProdTotalGrand = String.valueOf(modelCheckout.getData().getGrandTotal());

                    tvCheckoutTotalProduct.setText(strProdTotal);
                    tvCheckoutTotalPrice.setText(strProdTotalPrice);
                    tvCheckoutDiscount.setText(strProdTotalDiscount);
                    tvCheckoutGrandTotal.setText(strProdTotalGrand);

                }

                @Override
                public void onFailure(@NonNull Call<ModelCheckout> call, @NonNull Throwable t) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
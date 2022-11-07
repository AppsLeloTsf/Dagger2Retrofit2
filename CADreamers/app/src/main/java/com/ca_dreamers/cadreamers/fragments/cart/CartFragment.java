package com.ca_dreamers.cadreamers.fragments.cart;

import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.adapter.cart.AdapterFetchCart;
import com.ca_dreamers.cadreamers.api.Api;
import com.ca_dreamers.cadreamers.api.RetrofitClient;
import com.ca_dreamers.cadreamers.models.cart.fetch_cart.ModelFetchCart;
import com.ca_dreamers.cadreamers.storage.SharedPrefManager;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartFragment extends Fragment {

    private String strUserId;

    private View view;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btnCheckout)
    protected AppCompatButton btnCheckout;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rvFetchCart)
    protected RecyclerView rvFetchCart;

    public static CartFragment newInstance() {
        return new CartFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.cart_fragment, container, false);
        ButterKnife.bind(this, view);
        SharedPrefManager sharedPrefManager = new SharedPrefManager(getContext());
        strUserId = sharedPrefManager.getUserId();
        rvFetchCart.setLayoutManager(new LinearLayoutManager(getContext()));
        callFetchCartApi();
        return view;
    }

    private void callFetchCartApi(){
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        JsonObject gsonObject;
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("userid", strUserId);

            gsonObject = (JsonObject) JsonParser.parseString(paramObject.toString());
            Call<ModelFetchCart> call = api.fetchCart(gsonObject);

            call.enqueue(new Callback<ModelFetchCart>() {
                @Override
                public void onResponse(@NotNull Call<ModelFetchCart> call, @NotNull Response<ModelFetchCart> response) {
                    ModelFetchCart modelFetchCart = response.body();

                    btnCheckout.setVisibility(View.VISIBLE);
                    assert modelFetchCart != null;
                    String strProMode = modelFetchCart.getData().getpMode();
                    AdapterFetchCart adapterFetchCart = new AdapterFetchCart(modelFetchCart.getData().getProducts(), strProMode, getContext(), getActivity());
                     rvFetchCart.setAdapter(adapterFetchCart);
                }

                @Override
                public void onFailure(@NotNull Call<ModelFetchCart> call, @NotNull Throwable t) {
                    btnCheckout.setVisibility(View.GONE);
                    Navigation.findNavController(view).navigate(R.id.bottom_courses);
                    Toast.makeText(getContext(), "Your cart is empty, please add course or book in your cart.", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btnCheckout)
    public void btnCheckoutClicked(View view){
        Navigation.findNavController(view).navigate(R.id.nav_address);
    }

}
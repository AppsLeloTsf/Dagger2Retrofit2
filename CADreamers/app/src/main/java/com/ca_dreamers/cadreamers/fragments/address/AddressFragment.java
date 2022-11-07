package com.ca_dreamers.cadreamers.fragments.address;
import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.adapter.address.AdapterAddress;
import com.ca_dreamers.cadreamers.api.Api;
import com.ca_dreamers.cadreamers.api.RetrofitClient;
import com.ca_dreamers.cadreamers.models.address.ModelAddress;
import com.ca_dreamers.cadreamers.storage.SharedPrefManager;
import com.ca_dreamers.cadreamers.utils.Constant;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressFragment extends Fragment {

    private SharedPrefManager sharedPrefManager;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.srlAddress)
    protected SwipeRefreshLayout srlAddress;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rvAddress)
    protected RecyclerView rvAddress;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_address, container, false);
        ButterKnife.bind(this, view);
        sharedPrefManager = new SharedPrefManager(getContext());
        rvAddress.setLayoutManager(new LinearLayoutManager(getContext()));
        callAddressApi();
        srlAddress.setOnRefreshListener(this::callAddressApi);
        return view;
    }

    private void callAddressApi(){
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        JsonObject gsonObject;
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("userid", sharedPrefManager.getUserId());

            gsonObject = (JsonObject) JsonParser.parseString(paramObject.toString());
            Call<ModelAddress> userCall = api.getAddress(gsonObject);

            userCall.enqueue(new Callback<ModelAddress>() {
                @Override
                public void onResponse(@NonNull Call<ModelAddress> call, @NonNull Response<ModelAddress> response) {
                    ModelAddress modelAddress = response.body();
                    srlAddress.setRefreshing(false);
                    assert modelAddress != null;
                    AdapterAddress adapterAddress = new AdapterAddress(modelAddress.getData(), getContext());
                    rvAddress.setAdapter(adapterAddress);
                }

                @Override
                public void onFailure(@NonNull Call<ModelAddress> call, @NonNull Throwable t) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.ivAddressAddNew)
    public void btnAddressAddNewClicked(View view){
        Bundle bundle = new Bundle();
        bundle.putString(Constant.ADDRESS_ID, "");
        bundle.putString(Constant.ADDRESS_ACTION, "add");
        bundle.putString(Constant.ADDRESS_NAME, "");
        bundle.putString(Constant.ADDRESS_MOBILE, "");
        bundle.putString(Constant.ADDRESS_ADDRESS, "");
        bundle.putString(Constant.ADDRESS_PIN_CODE, "");
        bundle.putString(Constant.ADDRESS_ADHAR, "");
        Navigation.findNavController(view).navigate(R.id.nav_update_address, bundle);

    }
}
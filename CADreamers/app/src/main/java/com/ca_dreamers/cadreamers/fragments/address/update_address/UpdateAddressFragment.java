package com.ca_dreamers.cadreamers.fragments.address.add_address;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.adapter.courses.AdapterCourseList;
import com.ca_dreamers.cadreamers.api.Api;
import com.ca_dreamers.cadreamers.api.RetrofitClient;
import com.ca_dreamers.cadreamers.models.courses.ModelCourse;
import com.ca_dreamers.cadreamers.storage.SharedPrefManager;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAddressFragment extends Fragment {

    private SharedPrefManager sharedPrefManager;
    private String strUserId;
    private AddAddressViewModel mViewModel;

    @BindView(R.id.etAddAddressName)
    protected EditText etAddAddressName;
    @BindView(R.id.etAddAddressPhone)
    protected EditText etAddAddressPhone;
    @BindView(R.id.etAddAddressAddress)
    protected EditText etAddAddressAddress;
    @BindView(R.id.etAddAddressCity)
    protected EditText etAddAddressCity;
    @BindView(R.id.etAddAddressState)
    protected EditText etAddAddressState;
    @BindView(R.id.etAddAddressCountry)
    protected EditText etAddAddressCountry;
    @BindView(R.id.etAddAddressPinCode)
    protected EditText etAddAddressPinCode;
    @BindView(R.id.etAddAddressAdhar)
    protected EditText etAddAddressAdhar;

    public static AddAddressFragment newInstance() {
        return new AddAddressFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_address_fragment, container, false);
        sharedPrefManager = new SharedPrefManager(getContext());
        strUserId = sharedPrefManager.getUserId();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AddAddressViewModel.class);
        // TODO: Use the ViewModel
    }

    private void callAddAddressApi(){
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("user_id", sharedPrefManager.getUserId());

            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(paramObject.toString());
            Call<ModelCourse> userCall = api.getCourse(gsonObject);

            userCall.enqueue(new Callback<ModelCourse>() {
                @Override
                public void onResponse(Call<ModelCourse> call, Response<ModelCourse> response) {
                    ModelCourse modelCourse = response.body();
                    
                }

                @Override
                public void onFailure(Call<ModelCourse> call, Throwable t) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
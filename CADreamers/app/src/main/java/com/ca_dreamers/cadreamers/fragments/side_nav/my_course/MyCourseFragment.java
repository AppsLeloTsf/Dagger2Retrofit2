package com.ca_dreamers.cadreamers.fragments.side_nav.my_course;

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

import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.adapter.my_course.AdapterMyCourses;
import com.ca_dreamers.cadreamers.api.Api;
import com.ca_dreamers.cadreamers.api.RetrofitClient;
import com.ca_dreamers.cadreamers.models.my_orders.ModelMyOrders;
import com.ca_dreamers.cadreamers.storage.SharedPrefManager;
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

public class MyCourseFragment extends Fragment {

    private String strUserId;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rvMyOrdersCourses)
    protected RecyclerView rvMyOrders;
    public static MyCourseFragment newInstance() {
        return new MyCourseFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.my_course_fragment, container, false);
        ButterKnife.bind(this, view);
        SharedPrefManager sharedPrefManager = new SharedPrefManager(getContext());
        strUserId = sharedPrefManager.getUserId();
        rvMyOrders.setLayoutManager(new LinearLayoutManager(getContext()));
        callCourseApi();
        return view;
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btnBrowsCourseMy)
    public void btnBrowsCourseMyClicked(){
        Navigation.findNavController(requireView()).navigate(R.id.bottom_courses);
    }

    private void callCourseApi(){
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        JsonObject gsonObject;
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("user_id", strUserId);


            gsonObject = (JsonObject) JsonParser.parseString(paramObject.toString());
            Call<ModelMyOrders> call = api.getMyOrders(gsonObject);
            call.enqueue(new Callback<ModelMyOrders>() {
                @Override
                public void onResponse(@NonNull Call<ModelMyOrders> call, @NonNull Response<ModelMyOrders> response) {
                    ModelMyOrders modelMyOrders = response.body();
                    assert modelMyOrders != null;
                    AdapterMyCourses adapterCourseList = new AdapterMyCourses(modelMyOrders.getData(), getContext());
                    rvMyOrders.setAdapter(adapterCourseList);
                }

                @Override
                public void onFailure(@NonNull Call<ModelMyOrders> call, @NonNull Throwable t) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



}
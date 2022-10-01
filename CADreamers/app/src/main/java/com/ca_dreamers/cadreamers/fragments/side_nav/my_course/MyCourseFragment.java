package com.ca_dreamers.cadreamers.fragments.side_nav.my_orders;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.adapter.my_orders.AdapterMyOrdersCourses;
import com.ca_dreamers.cadreamers.adapter.my_orders.AdapterMyOrdersBooks;
import com.ca_dreamers.cadreamers.api.Api;
import com.ca_dreamers.cadreamers.api.RetrofitClient;
import com.ca_dreamers.cadreamers.models.my_orders.ModelMyOrders;
import com.ca_dreamers.cadreamers.models.my_orders.books.ModelMyOrdersBooks;
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

public class MyOrdersFragment extends Fragment {

    private SharedPrefManager sharedPrefManager;
    private MyOrdersViewModel mViewModel;
    @BindView(R.id.tvMyOrdersCourseTag)
    protected TextView tvMyOrdersCourseTag;
    @BindView(R.id.tvMyOrdersBooksTag)
    protected TextView tvMyOrdersBooksTag;
    @BindView(R.id.rvMyOrdersCourses)
    protected RecyclerView rvMyOrders;
    @BindView(R.id.rvMyOrdersBooks)
    protected RecyclerView rvMyOrdersBooks;
    public static MyOrdersFragment newInstance() {
        return new MyOrdersFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.my_orders_fragment, container, false);
        ButterKnife.bind(this, view);
        sharedPrefManager = new SharedPrefManager(getContext());
        rvMyOrders.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMyOrdersBooks.setLayoutManager(new GridLayoutManager(getContext(), 2));
        tvMyOrdersCourseTag.setVisibility(View.GONE);
        tvMyOrdersBooksTag.setVisibility(View.GONE);
        callMyOrdersApi();
        callMyOrdersBooksApi();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MyOrdersViewModel.class);
        // TODO: Use the ViewModel
    }

    private void callMyOrdersApi(){
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("user_id", sharedPrefManager.getUserId());

            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(paramObject.toString());
            Call<ModelMyOrders> call = api.getMyOrders(gsonObject);
            call.enqueue(new Callback<ModelMyOrders>() {
                @Override
                public void onResponse(Call<ModelMyOrders> call, Response<ModelMyOrders> response) {
                    ModelMyOrders modelMyOrders = response.body();
                    tvMyOrdersCourseTag.setVisibility(View.VISIBLE);
                    AdapterMyOrdersCourses adapterCourseList = new AdapterMyOrdersCourses(modelMyOrders.getData(), getActivity().getApplicationContext());
                    rvMyOrders.setAdapter(adapterCourseList);
                }

                @Override
                public void onFailure(Call<ModelMyOrders> call, Throwable t) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void callMyOrdersBooksApi(){
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("user_id", sharedPrefManager.getUserId());

            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(paramObject.toString());
            Call<ModelMyOrdersBooks> call = api.getMyOrdersBooks(gsonObject);
            call.enqueue(new Callback<ModelMyOrdersBooks>() {
                @Override
                public void onResponse(Call<ModelMyOrdersBooks> call, Response<ModelMyOrdersBooks> response) {
                    ModelMyOrdersBooks modelMyOrdersBooks = response.body();
                    tvMyOrdersBooksTag.setVisibility(View.VISIBLE);
                    AdapterMyOrdersBooks adapterCourseList = new AdapterMyOrdersBooks(modelMyOrdersBooks.getData(), getActivity().getApplicationContext());
                    rvMyOrdersBooks.setAdapter(adapterCourseList);
                }

                @Override
                public void onFailure(Call<ModelMyOrdersBooks> call, Throwable t) {

                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
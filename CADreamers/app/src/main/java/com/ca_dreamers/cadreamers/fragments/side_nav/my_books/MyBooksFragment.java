package com.ca_dreamers.cadreamers.fragments.side_nav.my_books;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.adapter.my_books.AdapterMyBooks;
import com.ca_dreamers.cadreamers.api.Api;
import com.ca_dreamers.cadreamers.api.RetrofitClient;
import com.ca_dreamers.cadreamers.models.my_orders.books.ModelMyOrdersBooks;
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

public class MyBooksFragment extends Fragment {

    private String strUserId;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rvMyOrdersBooks)
    protected RecyclerView rvMyOrdersBooks;


    public static MyBooksFragment newInstance() {
        return new MyBooksFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_books_fragment, container, false);
        ButterKnife.bind(this, view);
        SharedPrefManager sharedPrefManager = new SharedPrefManager(getContext());
        strUserId = sharedPrefManager.getUserId();
        rvMyOrdersBooks.setLayoutManager(new GridLayoutManager(getContext(), 2));
        callBooksApi();
        return view;
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btnBrowsBooksMy)
    public void btnBrowsBooksMy(){
        Navigation.findNavController(requireView()).navigate(R.id.bottom_books);
    }
    private void callBooksApi(){
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        JsonObject gsonObject;
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("user_id", strUserId);

            gsonObject = (JsonObject) JsonParser.parseString(paramObject.toString());
            Call<ModelMyOrdersBooks> call = api.getMyOrdersBooks(gsonObject);
            call.enqueue(new Callback<ModelMyOrdersBooks>() {
                @Override
                public void onResponse(@NotNull Call<ModelMyOrdersBooks> call, @NotNull Response<ModelMyOrdersBooks> response) {
                    ModelMyOrdersBooks modelMyOrdersBooks = response.body();
                    assert modelMyOrdersBooks != null;
                    AdapterMyBooks adapterCourseList = new AdapterMyBooks(modelMyOrdersBooks.getData());
                    rvMyOrdersBooks.setAdapter(adapterCourseList);
                }

                @Override
                public void onFailure(@NonNull Call<ModelMyOrdersBooks> call, @NonNull Throwable t) {

                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
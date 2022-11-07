package com.ca_dreamers.cadreamers.fragments.side_nav.my_books.books_details;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.adapter.my_books.AdapterMyBooksPdf;
import com.ca_dreamers.cadreamers.api.Api;
import com.ca_dreamers.cadreamers.api.RetrofitClient;
import com.ca_dreamers.cadreamers.models.my_orders.books.books_details_pdf.ModelBooksDetailsPdf;
import com.ca_dreamers.cadreamers.storage.SharedPrefManager;
import com.ca_dreamers.cadreamers.utils.Constant;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PurchasedBooksDetailsFragment extends Fragment {

    private SharedPrefManager sharedPrefManager;
    private String strBookId;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ivPurchasedBookDetails)
    protected ImageView ivPurchasedBookDetails;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tvPurchasedBookName)
    protected TextView tvPurchasedBookName;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rbPurchasedBookRating)
    protected RatingBar rbPurchasedBookRating;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rvPurchasedBooksPdf)
    protected RecyclerView rvPurchasedBooksPdf;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_books_details, container, false);
        ButterKnife.bind(this, view);
        Context context = (Activity) view.getContext();
        sharedPrefManager = new SharedPrefManager(getContext());
        assert getArguments() != null;
        strBookId = getArguments().getString(Constant.BOOKS_ID);
        String strBookImage = getArguments().getString(Constant.BOOKS_IMAGE);
        String strBookName = getArguments().getString(Constant.BOOKS_TITLE);
        tvPurchasedBookName.setText(strBookName);
        rbPurchasedBookRating.setRating(5);
        Glide.with(context).load(strBookImage).into(ivPurchasedBookDetails);
        rvPurchasedBooksPdf.setLayoutManager(new GridLayoutManager(getContext(), 2));
        callBooksApi();
        return view;
    }


    private void callBooksApi(){
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        JsonObject gsonObject;
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("user_id", sharedPrefManager.getUserId());
            paramObject.put("book_id", strBookId);

            Log.d(Constant.TAG, "User ID:"+sharedPrefManager.getUserId()+" Book Id:"+strBookId);
            gsonObject = (JsonObject) JsonParser.parseString(paramObject.toString());
            Call<ModelBooksDetailsPdf> userCall = api.getMyOrdersBooksPdf(gsonObject);

            userCall.enqueue(new Callback<ModelBooksDetailsPdf>() {
                @Override
                public void onResponse(@NonNull Call<ModelBooksDetailsPdf> call, @NonNull Response<ModelBooksDetailsPdf> response) {
                    ModelBooksDetailsPdf modelBooksDetailsPdf = response.body();
                    assert modelBooksDetailsPdf != null;
                    AdapterMyBooksPdf adapterMyBooksPdf = new AdapterMyBooksPdf(modelBooksDetailsPdf.getData());
                    rvPurchasedBooksPdf.setAdapter(adapterMyBooksPdf);
                }

                @Override
                public void onFailure(@NonNull Call<ModelBooksDetailsPdf> call, @NonNull Throwable t) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
package com.ca_dreamers.cadreamers.fragments.side_nav.my_orders.books_details;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.adapter.my_orders.AdapterPurchasedBooksPdf;
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

    private MyOdersBooksDetailsViewModel mViewModel;
    private SharedPrefManager sharedPrefManager;
    private String strBookId;
    private String strBookImage;
    private String strBookName;
    private String strBookDescription;
    private String strBookRating;
    @BindView(R.id.ivPurchasedBookDetails)
    protected ImageView ivPurchasedBookDetails;
    @BindView(R.id.tvPurchasedBookName)
    protected TextView tvPurchasedBookName;
    @BindView(R.id.rbPurchasedBookRating)
    protected RatingBar rbPurchasedBookRating;
    @BindView(R.id.tvPurchasedBookDescription)
    protected TextView tvPurchasedBookDescription;
    @BindView(R.id.rvPurchasedBooksPdf)
    protected RecyclerView rvPurchasedBooksPdf;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.purchased_books_details_fragment, container, false);
        ButterKnife.bind(this, view);
        sharedPrefManager = new SharedPrefManager(getContext());
        strBookId = getArguments().getString(Constant.BOOKS_ID);
        strBookImage = getArguments().getString(Constant.BOOKS_IMAGE);
        strBookName = getArguments().getString(Constant.BOOKS_TITLE);
        strBookDescription = getArguments().getString(Constant.BOOKS_DESCRIPTION);
        strBookRating = getArguments().getString(Constant.BOOKS_RATING);
        tvPurchasedBookName.setText(strBookName);
        tvPurchasedBookDescription.setText(strBookDescription);
        rbPurchasedBookRating.setRating(Float.parseFloat(strBookRating));
        Glide.with(getContext()).load(strBookImage).into(ivPurchasedBookDetails);
        rvPurchasedBooksPdf.setLayoutManager(new GridLayoutManager(getContext(), 2));
        callBooksApi();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MyOdersBooksDetailsViewModel.class);
        // TODO: Use the ViewModel
    }
    private void callBooksApi(){
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        JsonObject gsonObject = new JsonObject();
        try {
            JSONObject paramObject = new JSONObject();
            paramObject.put("user_id", sharedPrefManager.getUserId());
            paramObject.put("book_id", strBookId);

            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(paramObject.toString());
            Call<ModelBooksDetailsPdf> userCall = api.getMyOrdersBooksPdf(gsonObject);

            userCall.enqueue(new Callback<ModelBooksDetailsPdf>() {
                @Override
                public void onResponse(Call<ModelBooksDetailsPdf> call, Response<ModelBooksDetailsPdf> response) {
                    ModelBooksDetailsPdf modelBooksDetailsPdf = response.body();
                    AdapterPurchasedBooksPdf adapterPurchasedBooksPdf = new AdapterPurchasedBooksPdf(modelBooksDetailsPdf.getData(), getContext());
                    rvPurchasedBooksPdf.setAdapter(adapterPurchasedBooksPdf);
                }

                @Override
                public void onFailure(Call<ModelBooksDetailsPdf> call, Throwable t) {

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
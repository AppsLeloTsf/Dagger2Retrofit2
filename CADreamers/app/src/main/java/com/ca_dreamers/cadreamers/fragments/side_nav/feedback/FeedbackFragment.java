package com.ca_dreamers.cadreamers.fragments.side_nav.feedback;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.adapter.feedback.AdapterFeedback;
import com.ca_dreamers.cadreamers.api.Api;
import com.ca_dreamers.cadreamers.api.RetrofitClient;
import com.ca_dreamers.cadreamers.models.feedback.ModelFeedback;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedbackFragment extends Fragment {
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rvFeedback)
    protected RecyclerView rvFeedback;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);
        ButterKnife.bind(this, view);
        rvFeedback.setLayoutManager(new LinearLayoutManager(getContext()));
        callFeedback();
        return view;
    }



    private void callFeedback(){
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        Call<ModelFeedback> call = api.getFeedback();

        call.enqueue(new Callback<ModelFeedback>() {
            @Override
            public void onResponse(@NonNull Call<ModelFeedback> call, @NonNull Response<ModelFeedback> response) {
                ModelFeedback modelFeedback = response.body();
                assert modelFeedback != null;
                AdapterFeedback adapterFeedback = new AdapterFeedback(modelFeedback.getData(), getContext());
                rvFeedback.setAdapter(adapterFeedback);
            }

            @Override
            public void onFailure(@NonNull Call<ModelFeedback> call, @NonNull Throwable t) {

            }
        });

    }


}
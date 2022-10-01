package com.ca_dreamers.cadreamers.ui.side_nav.about_us;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.adapter.courses.AdapterCoursesBanner;
import com.ca_dreamers.cadreamers.api.Api;
import com.ca_dreamers.cadreamers.api.RetrofitClient;
import com.ca_dreamers.cadreamers.models.about_us.ModelAboutUs;
import com.ca_dreamers.cadreamers.models.courses.banners.ModelCoursesBanner;
import com.ca_dreamers.cadreamers.utils.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutUsFragment extends Fragment {

    private AboutUsViewModel mViewModel;
    @BindView(R.id.tvAboutUs)
    protected TextView tvAboutUs;

    public static AboutUsFragment newInstance() {
        return new AboutUsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about_us_fragment, container, false);
        ButterKnife.bind(this, view);
        callAboutUs();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AboutUsViewModel.class);
        // TODO: Use the ViewModel
    }

    private void callAboutUs(){
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        Call<ModelAboutUs> call = api.getAboutUs();

        call.enqueue(new Callback<ModelAboutUs>() {
            @Override
            public void onResponse(Call<ModelAboutUs> call, Response<ModelAboutUs> response) {
                ModelAboutUs modelAboutUs = response.body();
                tvAboutUs.setText(Html.fromHtml(modelAboutUs.getData().getContent()));
            }

            @Override
            public void onFailure(Call<ModelAboutUs> call, Throwable t) {

            }
        });

    }


}
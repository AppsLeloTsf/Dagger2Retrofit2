package com.indianjourno.indianjourno.fragment;

import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import com.indianjourno.indianjourno.adapter.AdapterNewsList;
import com.indianjourno.indianjourno.api.RetrofitClient;
import com.indianjourno.indianjourno.model.ModelFeatureIdNews;
import com.indianjourno.indianjourno.model.feature.FeatureNews;
import com.indianjourno.indianjourno.model.feature.FeaturesID;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import indianjourno.indianjourno.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFragment extends Fragment {
    private Context tContext;

    private FragmentManager tFragmentManager;
    private List<ModelFeatureIdNews> tModelAllNews;
    private RecyclerView.LayoutManager tLauyoutManager;
    private AdapterNewsList tAdapterNewsList;


    @BindView(R.id.rvFragMain)
    protected RecyclerView rvFragMain;
    @BindView(R.id.pbMainFragment)
    protected ProgressBar pbMainFragment;

    private String strCatId;
    private String strCatName;
    private int tabNo;
    private List<FeaturesID> tModelsCategory;
    public static NewsFragment newInstance(String strCatId, String strCatName, int tabNo, List<FeaturesID> tModelsCategory) {

        NewsFragment fragment = new NewsFragment();
        fragment.strCatId = strCatId;
        fragment.strCatName = strCatName;
        fragment.tabNo = tabNo;
        fragment.tModelsCategory = tModelsCategory;
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_news, container, false);
        ButterKnife.bind(this, view);
        initFrag();
        return  view;
    }
    private void initFrag(){
        tContext = getContext();
        tFragmentManager = getFragmentManager();
        tLauyoutManager = new LinearLayoutManager(tContext);
        rvFragMain.setLayoutManager(tLauyoutManager);
        pbMainFragment.setVisibility(View.VISIBLE);
        callApi();
    }


    private void callApi(){
        Call<FeatureNews> call = RetrofitClient.getInstance().getApi().getNewsByFeatureId(strCatId);
call.enqueue(new Callback<FeatureNews>() {
    @Override
    public void onResponse(Call<FeatureNews> call, Response<FeatureNews> response) {
        FeatureNews  tModelAllNews = response.body();
        pbMainFragment.setVisibility(View.GONE);
        tAdapterNewsList = new AdapterNewsList(tModelAllNews.getNewsFeatures(), strCatId);
        rvFragMain.setAdapter(tAdapterNewsList);
    }

    @Override
    public void onFailure(Call<FeatureNews> call, Throwable t) {

    }
});    }

}

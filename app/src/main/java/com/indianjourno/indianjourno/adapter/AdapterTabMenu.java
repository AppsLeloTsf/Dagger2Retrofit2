package com.indianjourno.indianjourno.adapter;

import android.annotation.SuppressLint;
import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;


import com.indianjourno.indianjourno.api.RetrofitClient;
import com.indianjourno.indianjourno.fragment.NewsFragment;
import com.indianjourno.indianjourno.model.feature.FeatureNews;
import com.indianjourno.indianjourno.model.feature.FeaturesID;
import com.indianjourno.indianjourno.utils.Constant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import indianjourno.indianjourno.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterTabMenu extends RecyclerView.Adapter<AdapterTabMenu.TabViewHolder>{

    private String strWeekId;
    private Context tContext;
    private List<FeaturesID> tModelsCategory;
    private FragmentManager tFragmentManager;
    private int weekSize;

    public AdapterTabMenu(Context tContext, List<FeaturesID> tModelsCategory, FragmentManager tFragmentManager) {
        this.tContext = tContext;
        this.tModelsCategory = tModelsCategory;
        this.tFragmentManager = tFragmentManager;
    }

    @NonNull
    @Override
    public TabViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_tab, viewGroup, false);
        return new TabViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull final TabViewHolder tabViewHolder, @SuppressLint("RecyclerView") final int i) {
        final FeaturesID tModelCat = tModelsCategory.get(i);
        tabViewHolder.tvTabMenu.setText(tModelCat.getNewsTypeId());
        Log.d(Constant.TAG, "Cat Name : "+tModelCat.getNewsTypeName());
        tabViewHolder.tvTabMenu.setOnClickListener(v -> {
         final String  strCatId = tModelCat.getNewsTypeId();
         final String  strCatName = tModelCat.getNewsTypeName();
         Log.d(Constant.TAG, "Cat Id :"+strCatId);
            Call<FeatureNews> call = RetrofitClient.getInstance().getApi().getNewsByFeatureId(strCatId);
call.enqueue(new Callback<FeatureNews>() {
@Override
public void onResponse(Call<FeatureNews> call, Response<FeatureNews> response) {
    Log.d(Constant.TAG, "Cat i :"+i);
        tFragmentManager.beginTransaction().replace(R.id.container_main, NewsFragment.newInstance(strCatId,
                strCatName, i, tModelsCategory)).addToBackStack(null).commit();
}

@Override
public void onFailure(Call<FeatureNews> call, Throwable t) {

}
});            });
    }
    @Override
    public int getItemCount() {
        Log.d(Constant.TAG, "Cat Size : "+tModelsCategory.size());

        return tModelsCategory.size();
    }

    public class TabViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvTabMenu)
        protected TextView tvTabMenu;
        public TabViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

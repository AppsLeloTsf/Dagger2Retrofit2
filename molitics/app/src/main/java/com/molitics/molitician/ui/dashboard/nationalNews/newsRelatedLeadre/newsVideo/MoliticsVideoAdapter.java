package com.molitics.molitician.ui.dashboard.nationalNews.newsRelatedLeadre.newsVideo;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;

import com.molitics.molitician.base.BaseViewHolder;
import com.molitics.molitician.databinding.AdapterNewsVerticalBinding;
import com.molitics.molitician.model.News;
import com.molitics.molitician.ui.dashboard.localNews.LocalNewsViewHolder;
import com.molitics.molitician.ui.dashboard.nationalNews.interfacess.OnNewsItemClick;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class MoliticsVideoAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<News> newsVideoModelList = new ArrayList<>();
    private OnNewsItemClick onNewsItemClick;

    public MoliticsVideoAdapter(OnNewsItemClick onNewsItemClick) {
        this.onNewsItemClick = onNewsItemClick;
    }

    public void addData(List<News> newsVideoModelList) {
        this.newsVideoModelList.addAll(newsVideoModelList);
        notifyDataSetChanged();
    }

    @NotNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        AdapterNewsVerticalBinding itemView = AdapterNewsVerticalBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new LocalNewsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NotNull BaseViewHolder holder, final int position) {

        if (newsVideoModelList != null) {
            holder.onBind(position, newsVideoModelList);
        }
    }

    @Override
    public int getItemCount() {
        return newsVideoModelList.size();
    }

}

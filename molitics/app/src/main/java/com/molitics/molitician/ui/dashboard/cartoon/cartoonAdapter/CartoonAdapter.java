package com.molitics.molitician.ui.dashboard.cartoon.cartoonAdapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.molitics.molitician.R;
import com.molitics.molitician.ui.dashboard.cartoon.cartoonModel.AllCartoonModel;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rahul on 19/02/18.
 */

public class CartoonAdapter extends RecyclerView.Adapter<CartoonAdapter.CartoonViewHolder> {

    private List<AllCartoonModel> allCartoonModels = new ArrayList<>();
    private Context mContext;
    private CartoonAdapterListener cartoonAdapterListener;
    private Boolean isLoadMore = true;

    public CartoonAdapter(Context mContext, CartoonAdapterListener cartoonAdapterListener) {
        this.mContext = mContext;
        this.cartoonAdapterListener = cartoonAdapterListener;
    }

    public void addCartoonList(List<AllCartoonModel> allCartoonModels) {
        this.allCartoonModels.addAll(allCartoonModels);
        notifyDataSetChanged();
    }

    public List<AllCartoonModel> getCartoonList() {
        return allCartoonModels;
    }

    public void isLoadMore(Boolean more) {
        isLoadMore = more;
    }


    public interface CartoonAdapterListener {
        void onCartoonClick(int position, String image_url);

        void loadMore(int total_count);

        void shareCartoon(String url);
    }

    @NonNull
    @Override
    public CartoonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_cartoon, parent, false);
        return new CartoonViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartoonViewHolder holder, int position) {
        AllCartoonModel cartoonModel = allCartoonModels.get(position);

        holder.time_txt.setText(cartoonModel.getUpdated_at());
        holder.shareNewsView.setOnClickListener(v -> {
            cartoonAdapterListener.shareCartoon(cartoonModel.getImage());
        });

        if (!TextUtils.isEmpty(cartoonModel.getImage())) {
            Picasso.with(mContext).load(cartoonModel.getImage()).
                    placeholder(R.drawable.image_placeholder_large).error(R.drawable.no_internet_large).into(holder.cartoon_image_view);

            holder.cartoon_image_view.setOnClickListener(v -> cartoonAdapterListener.onCartoonClick(holder.getAdapterPosition(), cartoonModel.getImage()));
        } else holder.cartoon_image_view.setImageResource(R.drawable.image_placeholder_large);
        if (!TextUtils.isEmpty(cartoonModel.getTitle())) {
            holder.cartoon_txt_view.setVisibility(View.VISIBLE);
            holder.cartoon_txt_view.setText(cartoonModel.getTitle().trim());
        } else
            holder.cartoon_txt_view.setVisibility(View.GONE);

        if (isLoadMore && position >= 19 && position == allCartoonModels.size() - 1) {
            cartoonAdapterListener.loadMore(allCartoonModels.size());
        }
    }

    @Override
    public int getItemCount() {
        return allCartoonModels.size();
    }

    public class CartoonViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.cartoon_image_view)
        ImageView cartoon_image_view;
        @BindView(R.id.cartoon_txt_view)
        TextView cartoon_txt_view;
        @BindView(R.id.time_txt)
        TextView time_txt;
        @BindView(R.id.shareNewsView)
        ImageView shareNewsView;

        public CartoonViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

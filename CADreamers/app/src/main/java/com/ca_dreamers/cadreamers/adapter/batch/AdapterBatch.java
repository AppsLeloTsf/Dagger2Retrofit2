package com.ca_dreamers.cadreamers.adapter.batch;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.models.combo_package.Datum;
import com.ca_dreamers.cadreamers.storage.SharedPrefManager;
import com.ca_dreamers.cadreamers.utils.Constant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterBatch extends RecyclerView.Adapter<AdapterBatch.ComboViewHolder> {

    private SharedPrefManager sharedPrefManager;
    private String strUserId;
    private List<Datum> tModels;
    private Context tContext;
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();


    public AdapterBatch(List<Datum> tModels, Context tContext) {
        this.tModels = tModels;
        this.tContext = tContext;
    }

    @NonNull
    @Override
    public ComboViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_batch, viewGroup, false);
       sharedPrefManager = new SharedPrefManager(tContext);
       strUserId = sharedPrefManager.getUserId();
        return new ComboViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ComboViewHolder comboViewHolder, final int i) {
        final Datum tModel = tModels.get(i);

            comboViewHolder.tvBatchTitle.setText(tModel.getName());
            comboViewHolder.tvBatchDesc.setText(tModel.getShortDesc());
            comboViewHolder.tvBatchPackagePrice.setText(tContext.getString(R.string.RupeeSymbol)+tModel.getProductType().get(0).getPrice());
        Glide.with(tContext).load(tModel.getThumbUrl()).into(comboViewHolder.ivBatchIcon);
        comboViewHolder.rlBatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(Constant.COURSE_ID, tModel.getId());
                bundle.putString(Constant.COURSE_IMAGE, tModel.getThumbUrl());
                bundle.putString(Constant.COURSE_TITLE, tModel.getName());
                bundle.putString(Constant.COURSE_DESCRIPTION, tModel.getShortDesc());
                bundle.putString(Constant.COURSE_PRICE, tModel.getProductType().get(0).getPrice());
                bundle.putString(Constant.COURSE_MRP, tModel.getProductType().get(0).getMrp());
                bundle.putString(Constant.COURSE_DISCOUNT, tModel.getProductType().get(0).getDiscount());
                bundle.putString(Constant.COURSE_PURCHASED, "Not Purchased");
                Navigation.findNavController(comboViewHolder.itemView).navigate(R.id.nav_batch_detail, bundle);
            }
        });

    }

    @Override
    public int getItemCount() {
        return tModels.size();
    }

    public class ComboViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tvBatchTitle)
        protected TextView tvBatchTitle;
        @BindView(R.id.tvBatchPackagePrice)
        protected TextView tvBatchPackagePrice;

        @BindView(R.id.tvBatchDesc)
        protected TextView tvBatchDesc;

        @BindView(R.id.ivBatchIcon)
        protected ImageView ivBatchIcon;

        @BindView(R.id.rlBatch)
        protected RelativeLayout rlBatch;

//        @BindView(R.id.rvComboProductType)
//        protected RecyclerView rvComboProductType;

        public ComboViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}

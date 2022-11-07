package com.ca_dreamers.cadreamers.adapter.combo_package;

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
import com.ca_dreamers.cadreamers.utils.Constant;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
public class AdapterComboPackage extends RecyclerView.Adapter<AdapterComboPackage.ComboViewHolder> {

    private final List<Datum> tModels;
    private Context tContext;


    public AdapterComboPackage(List<Datum> tModels) {
        this.tModels = tModels;
    }

    @NonNull
    @Override
    public ComboViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_combo_package, viewGroup, false);
       tContext = view.getContext();
       return new ComboViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ComboViewHolder comboViewHolder, final int i) {
        final Datum tModel = tModels.get(i);

            comboViewHolder.tvComboTitle.setText(tModel.getName());
            comboViewHolder.tvComboDesc.setText(tModel.getShortDesc());
            comboViewHolder.tvComboPackagePrice.setText(tContext.getString(R.string.RupeeSymbol)+tModel.getProductType().get(0).getPrice());
        Glide.with(tContext).load(tModel.getThumbUrl()).into(comboViewHolder.ivComboIcon);
        comboViewHolder.ivComboIcon.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString(Constant.COURSE_ID, tModel.getId());
            bundle.putString(Constant.COURSE_IMAGE, tModel.getThumbUrl());
            bundle.putString(Constant.COURSE_TITLE, tModel.getName());
            bundle.putString(Constant.COURSE_DESCRIPTION, tModel.getShortDesc());
            bundle.putString(Constant.COURSE_PRICE, tModel.getProductType().get(0).getPrice());
            bundle.putString(Constant.COURSE_MRP, tModel.getProductType().get(0).getMrp());
            bundle.putString(Constant.COURSE_DISCOUNT, tModel.getProductType().get(0).getDiscount());
            bundle.putString(Constant.COURSE_PURCHASED, "Not Purchased");
            Navigation.findNavController(comboViewHolder.itemView).navigate(R.id.nav_combo_detail, bundle);
        });

    }

    @Override
    public int getItemCount() {
        return tModels.size();
    }

    public static class ComboViewHolder extends RecyclerView.ViewHolder{

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tvComboTitle)
        protected TextView tvComboTitle;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tvComboPackagePrice)
        protected TextView tvComboPackagePrice;

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tvComboDesc)
        protected TextView tvComboDesc;

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.ivComboIcon)
        protected ImageView ivComboIcon;

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.llComboPackage)
        protected RelativeLayout llComboPackage;

        public ComboViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}

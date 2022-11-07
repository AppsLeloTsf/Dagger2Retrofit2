package com.ca_dreamers.cadreamers.adapter.combo_package;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.models.combo_package.ProductType;
import com.ca_dreamers.cadreamers.models.courses.Course;
import com.ca_dreamers.cadreamers.utils.Constant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AdapterComboProductType extends RecyclerView.Adapter<AdapterComboProductType.CourseDetailsViewHolder> {

    private Context tContext;
    private List<ProductType> tModels;
    private String strCatId;

    public AdapterComboProductType(List<ProductType> tModels, Context tContext) {
        this.tModels = tModels;
        this.tContext = tContext;
    }

    @NonNull
    @Override
    public CourseDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_combo_product, viewGroup, false);

        return new CourseDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseDetailsViewHolder courseDetailsViewHolder, final int i) {
        final ProductType tModel = tModels.get(i);

            courseDetailsViewHolder.tvComboMode.setText(tModel.getModeD());
            courseDetailsViewHolder.tvComboModeRead.setText(tModel.getModeText());
            courseDetailsViewHolder.tvComboPrice.setText(tContext.getString(R.string.RupeeSymbol)+tModel.getPrice());
            courseDetailsViewHolder.llComboProduct.setOnClickListener(v -> {

                Bundle bundle = new Bundle();
//                bundle.putString(Constant.COURSE_ID, tModel.getId());
//                bundle.putString(Constant.COURSE_IMAGE, tModel.getImage());
//                bundle.putString(Constant.COURSE_TITLE, tModel.getName());
//                bundle.putString(Constant.COURSE_DESCRIPTION, tModel.getShortDesc());
//                bundle.putString(Constant.COURSE_PRICE, tModel.getPrice());
//                bundle.putString(Constant.COURSE_MRP, tModel.getMrp());
//                bundle.putString(Constant.COURSE_DISCOUNT, tModel.getDiscount());
//                bundle.putString(Constant.COURSE_PURCHASED, tModel.getPurchaseStatus());
              //  Navigation.findNavController(courseDetailsViewHolder.itemView).navigate(R.id.nav_course_detail, bundle);
            });
    }

    @Override
    public int getItemCount() {
        return tModels.size();
    }

    public class CourseDetailsViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tvComboMode)
        protected TextView tvComboMode;
        @BindView(R.id.tvComboModeRead)
        protected TextView tvComboModeRead;
        @BindView(R.id.tvComboPrice)
        protected TextView tvComboPrice;
        @BindView(R.id.llComboProduct)
        protected LinearLayout llComboProduct;
        public CourseDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

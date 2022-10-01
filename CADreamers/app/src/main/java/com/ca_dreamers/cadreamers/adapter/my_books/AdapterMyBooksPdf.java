package com.ca_dreamers.cadreamers.adapter.my_orders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.activity.PdfActivity;
import com.ca_dreamers.cadreamers.models.my_orders.books.books_details_pdf.Datum;
import com.ca_dreamers.cadreamers.storage.SharedPrefManager;
import com.ca_dreamers.cadreamers.utils.Constant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterPurchasedBooksPdf extends RecyclerView.Adapter<AdapterPurchasedBooksPdf.MyOrdersViewHolder> {

    private List<Datum> tModels;
    private Context context;

    public AdapterPurchasedBooksPdf(List<Datum> tModels, Context context) {
        this.tModels = tModels;
        this.context = context;
    }

    @NonNull
    @Override
    public MyOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.purchased_books_pdf_item, viewGroup, false);
        return new MyOrdersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrdersViewHolder myOrdersViewHolder, final int i) {
        final Datum tModel = tModels.get(i);
            myOrdersViewHolder.tvPurchasedBooksPdfTitle.setText(tModel.getTitle());
            myOrdersViewHolder.llPurchasedBooksPdf.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(context, PdfActivity.class);
//                    intent.putExtra(Constant.BOOKS_PDF_URL, tModel.getPdfUrl());
//                    context.startActivity(intent);
                    Bundle bundle = new Bundle();
                    bundle.putString(Constant.BOOKS_PDF_URL, tModel.getPdfUrl());
                    Navigation.findNavController(myOrdersViewHolder.itemView).navigate(R.id.nav_books_pdf, bundle);
                }
            });
    }

    @Override
    public int getItemCount() {
        return tModels.size();
    }

    public class MyOrdersViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.llPurchasedBooksPdf)
        protected LinearLayout llPurchasedBooksPdf;
        @BindView(R.id.tvPurchasedBooksPdfTitle)
        protected TextView tvPurchasedBooksPdfTitle;
        public MyOrdersViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}

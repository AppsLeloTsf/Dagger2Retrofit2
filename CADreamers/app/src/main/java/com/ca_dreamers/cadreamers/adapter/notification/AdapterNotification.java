package com.ca_dreamers.cadreamers.adapter.notification;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.models.notification.Datum;
import com.ca_dreamers.cadreamers.utils.Constant;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterNotification extends RecyclerView.Adapter<AdapterNotification.NotificationViewHolder> {

    private final List<Datum> tModels;
    public AdapterNotification(List<Datum> tModels) {
        this.tModels = tModels;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_notification, viewGroup, false);

        return new NotificationViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder notificationViewHolder, final int i) {
        final Datum tModel = tModels.get(i);
            notificationViewHolder.tvNotificationTitle.setText(tModel.getTitle());
            notificationViewHolder.tvNotification.setText(tModel.getNotification()+"...");
            notificationViewHolder.tvNotificationDate.setText(tModel.getCreatedAt());
            notificationViewHolder.rlNotification.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putString(Constant.NOTIFICATION_ID, tModel.getId());
                bundle.putString(Constant.NOTIFICATION_TITLE, tModel.getTitle());
                bundle.putString(Constant.NOTIFICATION_CONTENT, tModel.getNotification());
                bundle.putString(Constant.NOTIFICATION_DATE, tModel.getCreatedAt());
                Navigation.findNavController(notificationViewHolder.itemView).navigate(R.id.menu_notification_read, bundle);

            });

    }

    @Override
    public int getItemCount() {
        return tModels.size();
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder{
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tvNotificationTitle)
        protected TextView tvNotificationTitle;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tvNotification)
        protected TextView tvNotification;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.tvNotificationDate)
        protected TextView tvNotificationDate;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.rlNotification)
        protected RelativeLayout rlNotification;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

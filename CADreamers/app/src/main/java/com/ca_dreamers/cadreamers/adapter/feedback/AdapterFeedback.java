package com.ca_dreamers.cadreamers.adapter.notification;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.api.Api;
import com.ca_dreamers.cadreamers.api.RetrofitClient;
import com.ca_dreamers.cadreamers.models.notification.Datum;
import com.ca_dreamers.cadreamers.models.address.delete_address.ModelDeleteAddress;
import com.ca_dreamers.cadreamers.storage.SharedPrefManager;
import com.ca_dreamers.cadreamers.utils.Constant;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ca_dreamers.cadreamers.utils.Constant.TAG;


public class AdapterNotification extends RecyclerView.Adapter<AdapterNotification.NotificationViewHolder> {

    private SharedPrefManager sharedPrefManager;
    private String strUserId;
    private String strAddId;
    private  AlertDialog.Builder builder;

    private Context tContext;
    private List<Datum> tModels;
    private String strCatId;

    public AdapterNotification(List<Datum> tModels, Context tContext) {
        this.tModels = tModels;
        this.tContext = tContext;
        this.strCatId = strCatId;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_notification, viewGroup, false);

        sharedPrefManager = new SharedPrefManager(tContext);
        strUserId = sharedPrefManager.getUserId();
        builder = new AlertDialog.Builder(view.getContext());

        return new NotificationViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder notificationViewHolder, final int i) {
        final Datum tModel = tModels.get(i);
        strAddId = tModel.getId();

            notificationViewHolder.tvNotificationTitle.setText(tModel.getTitle());
            notificationViewHolder.tvNotification.setText(tModel.getNotification()+"...");
            notificationViewHolder.tvNotificationDate.setText(tModel.getCreatedAt());
            notificationViewHolder.rlNotification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString(Constant.NOTIFICATION_ID, tModel.getId());
                    bundle.putString(Constant.NOTIFICATION_TITLE, tModel.getTitle());
                    bundle.putString(Constant.NOTIFICATION_CONTENT, tModel.getNotification());
                    bundle.putString(Constant.NOTIFICATION_DATE, tModel.getCreatedAt());
                    Navigation.findNavController(notificationViewHolder.itemView).navigate(R.id.menu_notification_read, bundle);

                }
            });

    }

    @Override
    public int getItemCount() {
        return tModels.size();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tvNotificationTitle)
        protected TextView tvNotificationTitle;
        @BindView(R.id.tvNotification)
        protected TextView tvNotification;
        @BindView(R.id.tvNotificationDate)
        protected TextView tvNotificationDate;
        @BindView(R.id.rlNotification)
        protected RelativeLayout rlNotification;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

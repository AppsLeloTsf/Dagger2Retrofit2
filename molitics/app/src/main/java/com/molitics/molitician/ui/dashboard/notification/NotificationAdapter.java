package com.molitics.molitician.ui.dashboard.notification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.molitics.molitician.R;
import com.molitics.molitician.database.NotificatoinModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rahul #rc91108@gmail.com on 9/12/2016.
 */
public class NotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private NotificationUi notificationUi;
    private Context myContext;
    private int count;
    private String display_date = "";
    private List<NotificatoinModel> notification_list;

    public NotificationAdapter(Context context, List<NotificatoinModel> notification_list, int type) {
        myContext = context;
        this.notification_list = notification_list;
        count = notification_list.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View notification_view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_notification, parent, false);
        notificationUi = new NotificationUi(notification_view);
        return notificationUi;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String saved_date = notification_list.get(position).getDate();
            Date date1 = formatter.parse(saved_date);
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df1 = new SimpleDateFormat("dd/MM/yyyy");
            String formateDate1 = df1.format(c.getTime());
            Date newDate1 = formatter.parse(formateDate1);

            int diffInDays = (int) ((newDate1.getTime() - date1.getTime())
                    / (1000 * 60 * 60 * 24));

            if (diffInDays == 0) {
                display_date = notification_list.get(position).getTime();
            } else if (diffInDays == 1) {
                display_date = "Yesterday";
            } else if (diffInDays == 2) {
                display_date = notification_list.get(position).getDate();
            }

        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        NotificationUi notification_holder = (NotificationUi) holder;
        notification_holder.noti_adapter_parent_lay.setBackgroundColor(myContext.getResources().getColor(R.color.read_notification));
        notification_holder.notification_created_at.setText(display_date);
        notification_holder.notification_message.setVisibility(View.GONE);
        notification_holder.notification_header.setText(notification_list.get(position).getMessage());
    }

    @Override
    public int getItemCount() {
        return count;
    }

    public static class NotificationUi extends RecyclerView.ViewHolder {

        @BindView(R.id.notification_header)
        TextView notification_header;
        @BindView(R.id.notification_created_at)
        TextView notification_created_at;
        @BindView(R.id.notification_message)
        TextView notification_message;
        @BindView(R.id.noti_adapter_parent_lay)
        CardView noti_adapter_parent_lay;

        public NotificationUi(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

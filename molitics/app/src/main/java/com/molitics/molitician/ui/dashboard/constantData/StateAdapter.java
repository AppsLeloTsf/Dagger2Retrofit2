package com.molitics.molitician.ui.dashboard.constantData;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.molitics.molitician.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rahul on 12/15/2016.
 */

public class StateAdapter extends RecyclerView.Adapter<StateAdapter.MyViewHolder> {
    private List<ConstantModel> list = new ArrayList<>();
    private RecyclerTouchWithType onItemTouchListener;
    private int type = 0;

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.separate_txt_view)
        TextView separate_txt_view;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public StateAdapter(Context context, List<ConstantModel> list, RecyclerTouchWithType onItemTouchListener) {
        this.list = list;
        this.onItemTouchListener = onItemTouchListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.text_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.separate_txt_view.setText(list.get(position).getKey());
        holder.separate_txt_view.setOnClickListener(v -> onItemTouchListener.onVerticalRecycler(position, type));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}


package com.molitics.molitician.ui.dashboard.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.molitics.molitician.R;
import com.molitics.molitician.ui.dashboard.constantData.ConstantModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rahul on 5/11/2017.
 */

public class SelectStateAdapter extends RecyclerView.Adapter<SelectStateAdapter.ViewHolder> {

    List<ConstantModel> stateList = new ArrayList<>();

    OnItemClick onItemClick;
    int selectPosition = 0;

    public SelectStateAdapter(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public void addStateList(List<ConstantModel> mStateList) {
        this.stateList.addAll(mStateList);
        notifyDataSetChanged();
    }

    public void setSelectedItem(int selectPosition) {
        this.selectPosition = selectPosition;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.filter_state_adapter_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ConstantModel data = stateList.get(position);

        holder.txtName.setText(data.getKey());
        if (selectPosition != 0 && selectPosition == data.getValue()) {
            holder.checkBox.setChecked(true);
        } else
            holder.checkBox.setChecked(false);
        holder.layout.setOnClickListener(view -> onItemClick.onItemClick(data.getValue(), holder.checkBox.isChecked()));

        holder.checkBox.setOnClickListener(view -> {

            if (selectPosition != 0 && selectPosition == data.getValue())
                onItemClick.onItemClick(data.getValue(), true);
            else
                onItemClick.onItemClick(data.getValue(), false);
        });
    }

    @Override
    public int getItemCount() {
        return stateList.size();
    }

    public interface OnItemClick {

        void onItemClick(int value, boolean isChecked);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.chkBox)
        CheckBox checkBox;
        @BindView(R.id.txtName)
        TextView txtName;
        @BindView(R.id.layout)
        RelativeLayout layout;

        public ViewHolder(View primaryLayout) {
            super(primaryLayout);
            ButterKnife.bind(this, primaryLayout);
        }
    }
}

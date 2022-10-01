package com.molitics.molitician.ui.dashboard.survey.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.molitics.molitician.R;
import com.molitics.molitician.interfaces.MOnClickListener;
import com.molitics.molitician.ui.dashboard.survey.model.AllSurveyModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rahul on 10/18/2016.
 */

public class SurveyAdapter extends RecyclerView.Adapter<SurveyAdapter.SurveyViewHolder> {
    Context mContext;
    ArrayList<AllSurveyModel> surveyList = new ArrayList<>();
    MOnClickListener mOnClickTwoInt;
    SurveyAdapterInterface surveyAdapterInterface;

    public interface SurveyAdapterInterface {
        void loadMore();
    }

    public SurveyAdapter(Context mContext, MOnClickListener mOnClickTwoInt, SurveyAdapterInterface surveyAdapterInterface) {
        this.mContext = mContext;
        this.mOnClickTwoInt = mOnClickTwoInt;
        this.surveyAdapterInterface = surveyAdapterInterface;
    }

    @Override
    public SurveyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View adapt_view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_survey, parent, false);
        return new SurveyViewHolder(adapt_view);
    }

    @Override
    public void onBindViewHolder(@NonNull SurveyViewHolder holder, int position) {
        final AllSurveyModel allSurveyModel = surveyList.get(holder.getAdapterPosition());
        holder.survey_qua.setText(allSurveyModel.getSurveyQuestion().trim());
        holder.area_name.setText(allSurveyModel.getLocation());
        if (allSurveyModel.getCanReply() == 0) {
            if (allSurveyModel.getMyResponse() != null) {
                holder.valid_till.setText(allSurveyModel.getMyResponse().getCreatedAt());
            } else {
                holder.valid_till.setText(allSurveyModel.getValidTill());
            }
        } else
            holder.valid_till.setText(allSurveyModel.getValidTill());

        holder.rl_survey_main.setOnClickListener(v -> mOnClickTwoInt.onMClick(holder.getAdapterPosition()));

        if (holder.getAdapterPosition() == surveyList.size() - 1) {
            surveyAdapterInterface.loadMore();
        }
    }

    public void addAllSurvey(List<AllSurveyModel> surveyModels) {
        this.surveyList.addAll(surveyModels);
        notifyDataSetChanged();
    }

    public void clearSurveyList() {
        this.surveyList.clear();
        notifyDataSetChanged();
    }

    public ArrayList<AllSurveyModel> getSurveyList() {
        return surveyList;
    }

    public int getSurveyListSize() {
        return surveyList.size();
    }

    @Override
    public int getItemCount() {
        return surveyList.size();
    }

    public class SurveyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.survey_qua)
        TextView survey_qua;
        @BindView(R.id.area_name)
        TextView area_name;
        @BindView(R.id.valid_till)
        TextView valid_till;
        @BindView(R.id.rl_survey_main)
        RelativeLayout rl_survey_main;

        public SurveyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

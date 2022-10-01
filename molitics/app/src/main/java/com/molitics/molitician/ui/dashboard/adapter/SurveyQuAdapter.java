package com.molitics.molitician.ui.dashboard.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import com.molitics.molitician.R;
import com.molitics.molitician.interfaces.MOnClickListener;
import com.molitics.molitician.ui.dashboard.survey.model.Response;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.ThemeUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.molitics.molitician.util.Constant.SURVEY_PROGRESS;

/**
 * Created by rahul on 12/26/2016.
 */

public class SurveyQuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Response> responseModels = new ArrayList<>();
    private boolean canRespond = false;
    private int total_response = 0;
    private Context mContext;
    private MOnClickListener mOnClickListener;
    private int setSelection = -1;
    private int response_id = -1;
    private int appTheme;
    // int myResponse = -1;

    public SurveyQuAdapter(Context mContext, MOnClickListener mOnClickListener, int appTheme) {
        this.mContext = mContext;
        this.mOnClickListener = mOnClickListener;
        this.appTheme = appTheme;
    }

    public void setSelected(int setSelection) {
        this.setSelection = setSelection;
        notifyDataSetChanged();
    }

    public void setMyResponse(int response_id) {
        this.response_id = response_id;
    }

    public void setCanResponse(boolean canRespond) {
        this.canRespond = canRespond;
        notifyDataSetChanged();
    }

    public boolean getCanReply() {
        return canRespond;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View primary_view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_survey_adapter, parent, false);
        return new MViewHolder(primary_view);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final MViewHolder mViewHolder = (MViewHolder) holder;

        if (appTheme == Constant.AppTheme.WHITE_TEXT) {
            ThemeUtils.setWhiteText(mContext, mViewHolder.percent_view);
            ThemeUtils.setWhiteText(mContext, mViewHolder.option_view);
            ThemeUtils.setWhiteText(mContext, mViewHolder.radio_button);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mViewHolder.radio_button.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(mContext, R.color.hard_white)));
            }
            mViewHolder.radio_button.setHighlightColor(ContextCompat.getColor(mContext,R.color.hard_white));
        }

        Response mResponse = responseModels.get(position);
        nullViewable(mViewHolder.radio_button, mResponse.getTitle());
        ObjectAnimator progressAnimator;
        if (setSelection == position) {
            mViewHolder.radio_button.setChecked(true);
        } else
            mViewHolder.radio_button.setChecked(false);

        if (!canRespond) {
            if (response_id == mResponse.getId()) {
                mViewHolder.progress_bar.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.survey_taken_progress));
            } else {
                mViewHolder.progress_bar.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.survey_progress));
            }
            int result_percent = getPercentage(total_response, mResponse.getCount());

            mViewHolder.progress_bar.setVisibility(View.VISIBLE);
            if (result_percent != 0) {
                progressAnimator = ObjectAnimator.ofInt(mViewHolder.progress_bar, SURVEY_PROGRESS, 0, result_percent);
                progressAnimator.setDuration(500);
                progressAnimator.setInterpolator(new LinearInterpolator());
                progressAnimator.start();

            } else {
                mViewHolder.progress_bar.setProgressDrawable(mContext.getResources().getDrawable(R.drawable.survey_progress));
                /// mViewHolder.progress_bar.setVisibility(View.INVISIBLE);
                progressAnimator = ObjectAnimator.ofInt(mViewHolder.progress_bar, SURVEY_PROGRESS, 0);
            }
            mViewHolder.radio_button.setVisibility(View.GONE);
            mViewHolder.option_view.setVisibility(View.VISIBLE);
            mViewHolder.percent_view.setVisibility(View.VISIBLE);

            nullViewable(mViewHolder.option_view, mResponse.getTitle());
            // mViewHolder.percent_view.setText(String.valueOf(result_percent) + " %");
            mViewHolder.percent_view.setText(getFloatPercentage(total_response, mResponse.getCount()) + " %");

        } else {
            mViewHolder.radio_button.setVisibility(View.VISIBLE);
            mViewHolder.progress_bar.setVisibility(View.GONE);
            mViewHolder.option_view.setVisibility(View.GONE);
            mViewHolder.percent_view.setVisibility(View.GONE);
        }
        mViewHolder.radio_button.setOnClickListener(v -> {
            mOnClickListener.onMClick(mResponse.getId());
            setSelected(position);
            //  surveyAdapter.setSelected(position);
        });
    }

    public void addSurveyList(List<Response> responseModels, boolean canRespond, int total_response) {
        this.canRespond = canRespond;
        this.total_response = total_response;
        this.responseModels.addAll(responseModels);
        notifyDataSetChanged();
    }

    public void clearSurveyList() {
        responseModels.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return responseModels.size();
    }

    public static class MViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ll_parent)
        LinearLayout ll_parent;
        @BindView(R.id.progress_bar)
        ProgressBar progress_bar;
        @BindView(R.id.radio_button)
        RadioButton radio_button;
        @BindView(R.id.option_view)
        TextView option_view;
        @BindView(R.id.percent_view)
        TextView percent_view;


        public MViewHolder(View parentView) {
            super(parentView);
            ButterKnife.bind(this, parentView);
        }
    }

    private void nullViewable(View mView, String text) {
        if (mView instanceof TextView) {
            if (text.isEmpty()) {
                mView.setVisibility(View.GONE);
            } else {
                mView.setVisibility(View.VISIBLE);
                ((TextView) mView).setText(text);
            }
        }
    }

    private int getPercentage(int total, int value) {
        if (value != 0) {
            return value * 100 / total;
        }
        return 0;
    }

    private String getFloatPercentage(int total, int value) {
        if (value != 0) {
            double xyzz = (double) value * 100 / (double) total;
            return new DecimalFormat("##.##").format(xyzz);
        }
        return "0";
    }
}

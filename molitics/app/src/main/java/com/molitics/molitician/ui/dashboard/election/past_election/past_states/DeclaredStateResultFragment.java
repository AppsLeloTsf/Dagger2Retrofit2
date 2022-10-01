package com.molitics.molitician.ui.dashboard.election.past_election.past_states;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.molitics.molitician.R;
import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.interfaces.MOnClickListener;
import com.molitics.molitician.interfaces.ViewRefreshListener;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.ui.dashboard.constantData.ConstantModel;
import com.molitics.molitician.ui.dashboard.election.past_election.adapter.OtherCandidatesAdapter;
import com.molitics.molitician.ui.dashboard.election.past_election.model.PartyList;
import com.molitics.molitician.ui.dashboard.election.past_election.model.PastStateModel;
import com.molitics.molitician.ui.dashboard.leader.newleaderprofile.NewLeaderProfileActivity;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.DialogClass;
import com.molitics.molitician.util.Util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by rahul on 1/10/2017.
 */

public class DeclaredStateResultFragment extends Fragment implements PastStatePresenter.PastStateView, MOnClickListener, ViewRefreshListener, OnChartValueSelectedListener {
    @BindView(R.id.ll_main)
    LinearLayout ll_main;

    @BindView(R.id.state_opposition_image)
    CircleImageView state_opposition_image;
    @BindView(R.id.other_candidate_recycle)
    RecyclerView other_candidate_recycle;

    @BindView(R.id.RL_handler)
    RelativeLayout RL_handler;
    @BindView(R.id.result_pie_chart)
    PieChart result_pie_chart;

    @BindView(R.id.governor_party_view)
    TextView governor_party_view;
    @BindView(R.id.governor_position_view)
    TextView governor_position_view;
    @BindView(R.id.governor_name_view)
    TextView governor_name_view;
    @BindView(R.id.governor_image_view)
    CircleImageView governor_image_view;
    @BindView(R.id.total_seats_view)
    TextView total_seats_view;
    @BindView(R.id.election_date_view)
    TextView election_date_view;
    @BindView(R.id.state_opposition_name_v)
    TextView state_opposition_name_v;
    @BindView(R.id.state_opposition_position_v)
    TextView state_opposition_position_v;
    @BindView(R.id.state_opposition_party_v)
    TextView state_opposition_party_v;
    @BindView(R.id.state_winner_name_v)
    TextView state_winner_name_v;
    @BindView(R.id.state_winner_position_v)
    TextView state_winner_position_v;
    @BindView(R.id.state_winner_party_v)
    TextView state_winner_party_v;
    @BindView(R.id.state_winner_image_v)
    CircleImageView state_winner_image_v;
    @BindView(R.id.state_name_view)
    TextView state_name_view;
    @BindView(R.id.main_progress_bar)
    ProgressBar main_progress_bar;

    private OtherCandidatesAdapter otherCandidatesAdapter;
    private PastStateHandler stateHandler;

    private List<ConstantModel> mla_list = new ArrayList<>();
    private List<ConstantModel> mp_list = new ArrayList<>();
    private PastStateModel pastStateModel;
    private int election_id = 0;

    public static Fragment getInstance(int election_id, int state_id) {
        Fragment mFragment = new DeclaredStateResultFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.IntentKey.ELECTION_ID, election_id);
        bundle.putInt(Constant.IntentKey.PAST_STATE_ID, state_id);
        mFragment.setArguments(bundle);
        return mFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stateHandler = new PastStateHandler(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View primary_layout = inflater.inflate(R.layout.fragment_declared_result, container, false);
        ButterKnife.bind(this, primary_layout);
        Bundle mBundle = getArguments();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        other_candidate_recycle.setLayoutManager(layoutManager);
        otherCandidatesAdapter = new OtherCandidatesAdapter(getContext(), this);
        other_candidate_recycle.setAdapter(otherCandidatesAdapter);
        assert mBundle != null;
        String from = mBundle.getString(Constant.IntentKey.DECLARED_STATE_RESULT_FROM);
        int state_id = mBundle.getInt(Constant.IntentKey.PAST_STATE_ID);
        election_id = mBundle.getInt(Constant.IntentKey.ELECTION_ID);
        main_progress_bar.setVisibility(View.VISIBLE);
        stateHandler.getStateData(election_id);

        return primary_layout;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setPieChart();
    }

    void setPieChart() {

        result_pie_chart.setUsePercentValues(true);
        result_pie_chart.getDescription().setEnabled(false);
        result_pie_chart.setExtraOffsets(5, 10, 5, 5);

        result_pie_chart.setDragDecelerationFrictionCoef(0.95f);
        result_pie_chart.setDrawHoleEnabled(true);
        result_pie_chart.setHoleColor(Color.WHITE);

        result_pie_chart.setTransparentCircleColor(Color.WHITE);
        result_pie_chart.setTransparentCircleAlpha(100);
        result_pie_chart.setHoleRadius(55f);
        result_pie_chart.setTransparentCircleRadius(58f);

        result_pie_chart.setDrawCenterText(true);

        result_pie_chart.setRotationAngle(0);
        // enable rotation of the chart by touch
        result_pie_chart.setRotationEnabled(true);
        result_pie_chart.setHighlightPerTapEnabled(true);
        // result_pie_chart.setUnit(" €");
        // result_pie_chart.setDrawUnitsInChart(true);
        // add a selection listener
        result_pie_chart.setOnChartValueSelectedListener(this);
        result_pie_chart.animateY(1400, Easing.EaseInOutQuad);
        Legend l = result_pie_chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setXEntrySpace(5f);
        l.setYEntrySpace(5f);
        l.setTextSize(18f);
        l.setYOffset(7f);
        l.setXOffset(7f);
        l.setWordWrapEnabled(true);

        // entry label styling
        result_pie_chart.setEntryLabelColor(Color.WHITE);
        //result_pie_chart.setEntryLabelTypeface(mTfRegular);
        result_pie_chart.setEntryLabelTextSize(12f);
    }

    private void setGraphData(List<PartyList> partyLists, Integer count) {

        float other_vote = 0;
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        ArrayList<Integer> colors = new ArrayList<Integer>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        if (count != null && partyLists.size() > count) {
            for (int i = 0; i < count; i++) {
                if (!TextUtils.isEmpty(partyLists.get(i).getVotesShare())) {
                    entries.add(new PieEntry(Float.parseFloat(partyLists.get(i).getVotesShare()), partyLists.get(i).getName()));
                    if (!partyLists.get(i).getParty_color().isEmpty()) {
                        colors.add(Color.parseColor(partyLists.get(i).getParty_color()));
                    } else {
                        colors.add(Util.PASTEL_COLORS[i]);
                    }
                    other_vote += Float.parseFloat(partyLists.get(i).getVotesShare());
                }
            }
            if (partyLists.size() != count && partyLists.size() > count)
                entries.add(new PieEntry(100 - other_vote, getString(R.string.other)));
        } else {
            if (partyLists.size() > 5) {
                for (int i = 0; i < 4; i++) {
                    if (!TextUtils.isEmpty(partyLists.get(i).getVotesShare())) {
                        entries.add(new PieEntry(Float.parseFloat(partyLists.get(i).getVotesShare()), partyLists.get(i).getName()));
                        other_vote += Float.parseFloat(partyLists.get(i).getVotesShare());
                        if (!partyLists.get(i).getParty_color().isEmpty()) {
                            colors.add(Color.parseColor(partyLists.get(i).getParty_color()));
                        } else {
                            colors.add(Util.PASTEL_COLORS[i]);
                        }
                    }
                }
                entries.add(new PieEntry(100 - other_vote, getString(R.string.other)));
            } else {
                for (int i = 0; i < partyLists.size(); i++) {
                    if (!TextUtils.isEmpty(partyLists.get(i).getVotesShare())) {
                        entries.add(new PieEntry(Float.parseFloat(partyLists.get(i).getVotesShare()), partyLists.get(i).getName()));
                        if (!partyLists.get(i).getParty_color().isEmpty()) {
                            colors.add(Color.parseColor(partyLists.get(i).getParty_color()));
                        } else {
                            colors.add(Util.PASTEL_COLORS[i]);
                        }
                    }
                }
            }
        }
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        // add a lot of colors
        //light color code
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        dataSet.setColors(colors);
        dataSet.setSelectionShift(1f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        //  data.setValueTypeface(mTfLight);
        result_pie_chart.setData(data);
        //result_pie_chart.setDrawSliceText(true);
        result_pie_chart.setCenterText(generateCenterSpannableText());

        result_pie_chart.getLegend().setWordWrapEnabled(true);
   /*     result_pie_chart.setPadding(ExtraUtils.convertDpToPx(getContext(), 30), ExtraUtils.convertDpToPx(getContext(), 5),
                ExtraUtils.convertDpToPx(getContext(), 30), ExtraUtils.convertDpToPx(getContext(), 5));*/
        //  result_pie_chart.setExtraOffsets(5, 5, 5, 5);
        // undo all highlights
        Highlight highlight = new Highlight(50f, 0, 0);
        //   result_pie_chart.highlightValues(highlight,true);
        // result_pie_chart.setHighlightPerTapEnabled(true);

        result_pie_chart.invalidate();
    }

    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString(getString(R.string.vote_shate));
        s.setSpan(new RelativeSizeSpan(1.7f), 0, s.length(), 0);
        return s;
    }

    @OnClick(R.id.ll_cm)
    public void onCmClick() {
        Intent mIntent = new Intent(getContext(), NewLeaderProfileActivity.class);
        mIntent.putExtra(Constant.IntentKey.LEADER_PROFILE_ID, pastStateModel.getWinnerId());
        mIntent.putExtra(Constant.IntentKey.FROM, Constant.IntentKey.NOTIFICATION);
        startActivity(mIntent);
    }

    @OnClick(R.id.ll_opp)
    public void onOppClick() {
        Intent mIntent = new Intent(getContext(), NewLeaderProfileActivity.class);
        mIntent.putExtra(Constant.IntentKey.LEADER_PROFILE_ID, pastStateModel.getRunnerUpId());
        mIntent.putExtra(Constant.IntentKey.FROM, Constant.IntentKey.NOTIFICATION);
        startActivity(mIntent);
    }

    @OnClick(R.id.governor_image_view)
    public void onGovClick() {
        Intent mIntent = new Intent(getContext(), NewLeaderProfileActivity.class);
        mIntent.putExtra(Constant.IntentKey.LEADER_PROFILE_ID, pastStateModel.getGovernor_detail().getGov_id());
        mIntent.putExtra(Constant.IntentKey.FROM, Constant.IntentKey.NOTIFICATION);
        startActivity(mIntent);
    }

    @Override
    public void onConstituencyResponse(APIResponse apiResponse) {
        if (apiResponse.getStatus() == 2000) {
            main_progress_bar.setVisibility(View.GONE);
            mla_list.clear();
            mp_list.clear();
            mla_list.addAll(apiResponse.getData().getMla_list());
            mp_list.addAll(apiResponse.getData().getMp_list());
        }
    }

    @Override
    public void onConstituencyApiException(ApiException apiException) {
    }

    @Override
    public void onStateResponse(APIResponse apiResponse) {
        Activity mActivity = getActivity();
        if (mActivity != null && isVisible()) {
            main_progress_bar.setVisibility(View.GONE);
            pastStateModel = apiResponse.getData().getPastStateModel();
            otherCandidatesAdapter.addPartyList(pastStateModel.getPartyList());
            handleUi(pastStateModel);
            setGraphData(pastStateModel.getPartyList(), pastStateModel.getParty_count());
        }
    }

    @Override
    public void onStateApiException(ApiException apiException) {
        if (isVisible())
            Util.validateError(getContext(), apiException, RL_handler, this, null);
    }

    @Override
    public void onStateServerError(ServerException serverException) {
        if (isVisible())
            DialogClass.showAlert(getContext(), getString(R.string.something_went_wrong));
    }

    private void handleUi(PastStateModel pastStateModel) {

        ll_main.setVisibility(View.VISIBLE);

        checkNull(state_name_view, pastStateModel.getState(), null);
        Spannable span = new SpannableString(getString(R.string.total_seats) + pastStateModel.getTotal_seats());
        span.setSpan(new RelativeSizeSpan(1.0f), getString(R.string.total_seats).length(), span.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        span.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.follow_leader)), getString(R.string.total_seats).length(), span.length(), 0);// set color
        total_seats_view.setText(span);
        checkNull(election_date_view, pastStateModel.getElection_year(), null);
        checkNull(governor_name_view, pastStateModel.getGovernor_detail().getName(), null);
        checkNull(governor_position_view, pastStateModel.getGovernor_detail().getPosition(), null);
        checkNull(governor_party_view, pastStateModel.getGovernor_detail().getParty_name(), null);
        checkNull(state_winner_name_v, pastStateModel.getWinnerName(), null);
        checkNull(state_opposition_name_v, pastStateModel.getRunnerUpName(), null);
        checkNull(state_winner_position_v, pastStateModel.getWinner_position(), null);
        checkNull(state_opposition_position_v, getString(R.string.opposition_leader), null);
        checkNull(state_winner_party_v, pastStateModel.getWinnerParty(), null);
        checkNull(state_opposition_party_v, pastStateModel.getRunnerUpParty(), null);
        if (pastStateModel.getGovernor_detail().getGovernorImage() != null && !TextUtils.isEmpty(pastStateModel.getGovernor_detail().getGovernorImage())
                && pastStateModel.getGovernor_detail().getGovernorImage().length() > 4) {
            Picasso.with(getContext()).load(pastStateModel.getGovernor_detail().getGovernorImage()).placeholder(R.drawable.internet_no_cloud).error(R.drawable.error_placeholder).into(governor_image_view);
        }
        if (pastStateModel.getWinnerImage() != null && !TextUtils.isEmpty(pastStateModel.getWinnerImage()) && pastStateModel.getWinnerImage().length() > 4) {
            Picasso.with(getContext()).load(pastStateModel.getWinnerImage()).placeholder(R.drawable.internet_no_cloud).error(R.drawable.error_placeholder).into(state_winner_image_v);
        }
        if (pastStateModel.getRunnerUpImage() != null && !TextUtils.isEmpty(pastStateModel.getRunnerUpImage()) && pastStateModel.getRunnerUpImage().length() > 4) {
            Picasso.with(getContext()).load(pastStateModel.getRunnerUpImage()).placeholder(R.drawable.internet_no_cloud).error(R.drawable.error_placeholder).into(state_opposition_image);
        }
    }

    @Override
    public void onMClick(int position) {

        Intent mIntent = new Intent(getContext(), PartListView.class);

        mIntent.putExtra(Constant.IntentKey.ELECTION_ID, election_id);
        mIntent.putExtra(Constant.IntentKey.PARTY_ID, position);
        mIntent.putExtra(Constant.IntentKey.FROM, Constant.From.FROM_DECLARED_STATE);
        getContext().startActivity(mIntent);

    }

    @Override
    public void onRefereshClick() {
        stateHandler.getConstituencyList(election_id);
        stateHandler.getStateData(election_id);
    }

    @Override
    public void onNetworkAvailable() {
        onRefereshClick();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        if (e == null)
            return;
    }

    @Override
    public void onNothingSelected() {

    }

    private void checkNull(TextView txt_view, String txt, View related_view) {
        if (!txt.isEmpty()) {
            txt_view.setVisibility(View.VISIBLE);
            txt_view.setText(txt);
        } else {
            if (related_view != null)
                related_view.setVisibility(View.GONE);
            txt_view.setVisibility(View.GONE);
        }
    }
}

package com.molitics.molitician.ui.dashboard.election.upcoming_election;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.molitics.molitician.R;
import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.interfaces.MOnClickListener;
import com.molitics.molitician.interfaces.ViewRefreshListener;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.ui.dashboard.election.past_election.model.AllPastElectionList;
import com.molitics.molitician.ui.dashboard.election.upcoming_election.adapter.UpComingElectionAdapter;
import com.molitics.molitician.ui.dashboard.election.upcoming_election.upcomig_parties.UpcomingPartyActivity;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.NetworkUtil;
import com.molitics.molitician.util.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rahul on 1/9/2017.
 */

public class UpComingView extends Fragment implements UpComingPresenter.UpComingView, MOnClickListener, ViewRefreshListener {
    @BindView(R.id.state_recycler)
    RecyclerView state_recycler;
    @BindView(R.id.main_progress_bar)
    ProgressBar main_progress_bar;
    @BindView(R.id.constituency_view)
    TextView constituency_view;
    @BindView(R.id.RL_handler)
    RelativeLayout RL_handler;

    private UpComingHandler comingHandler;
    private int page_no = 1;
    private UpComingElectionAdapter upComingElectionAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        comingHandler = new UpComingHandler(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View primary_view = inflater.inflate(R.layout.fragment_upcoming_election, container, false);
        ButterKnife.bind(this, primary_view);

        constituency_view.setVisibility(View.GONE);
        upComingElectionAdapter = new UpComingElectionAdapter(getContext(), this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        state_recycler.setLayoutManager(layoutManager);
        state_recycler.setAdapter(upComingElectionAdapter);

        if (NetworkUtil.isNetworkConnected(getContext()))
            comingHandler.getUpComingElection(page_no);
        else
            Util.addNetworkNotFoundView(getContext(), RL_handler, this);

        return primary_view;
    }

    @Override
    public void onUpComingResponse(APIResponse apiResponse) {

        state_recycler.setVisibility(View.VISIBLE);
        main_progress_bar.setVisibility(View.GONE);
        List<AllPastElectionList> allPastElectionList = apiResponse.getData().getAllPastElectionLists();
        upComingElectionAdapter.addElectionList(allPastElectionList);
        page_no++;

    }

    @Override
    public void onUpComingApiException(ApiException apiException) {
        Util.validateError(getContext(), apiException, RL_handler,
                this, upComingElectionAdapter.getElectionListSize());

    }

    @Override
    public void onUpComingServerError(ServerException serverException) {
        // DialogClass.showDialog(getActivity());
    }

    @Override
    public void onMClick(int position) {
        Intent mIntent = new Intent(getActivity(), UpcomingPartyActivity.class);
        mIntent.putExtra(Constant.IntentKey.ELECTION_ID, position);
        startActivity(mIntent);
    }

    @Override
    public void onRefereshClick() {
        comingHandler.getUpComingElection(page_no);
    }

    @Override
    public void onNetworkAvailable() {
        onRefereshClick();
    }
}

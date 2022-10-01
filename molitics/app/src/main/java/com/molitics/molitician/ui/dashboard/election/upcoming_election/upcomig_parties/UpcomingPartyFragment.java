package com.molitics.molitician.ui.dashboard.election.upcoming_election.upcomig_parties;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.molitics.molitician.R;
import com.molitics.molitician.UiMediator.Loader;
import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.interfaces.MOnClickListener;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.ui.dashboard.constantData.ConstantModel;
import com.molitics.molitician.ui.dashboard.constantData.RecyclerTouchWithType;
import com.molitics.molitician.ui.dashboard.election.past_election.past_states.PartListView;
import com.molitics.molitician.ui.dashboard.election.upcoming_election.adapter.UpComingPartyAdapter;
import com.molitics.molitician.ui.dashboard.election.upcoming_election.model.UpcomingPartyModel;
import com.molitics.molitician.ui.dashboard.login.SelectLocationDialog;
import com.molitics.molitician.util.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by rahul on 1/12/2017.
 */

public class UpcomingPartyFragment extends Fragment implements UpComingPartyPresenter.UpComingView, MOnClickListener {

    @BindView(R.id.state_recycler)
    RecyclerView state_recycler;
    @BindView(R.id.main_progress_bar)
    ProgressBar main_progress_bar;
    @BindView(R.id.constituency_view)
    TextView constituency_view;

    private UpComingPartyAdapter partyAdapter;
    private UpComingPartyHandler partyHandler;
    private int election_id = 0;
    private List<ConstantModel> mla_list = new ArrayList<>();
    private List<ConstantModel> mp_list = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        partyHandler = new UpComingPartyHandler(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View primary_layout = inflater.inflate(R.layout.fragment_upcoming_election, container, false);
        ButterKnife.bind(this, primary_layout);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        partyAdapter = new UpComingPartyAdapter(getContext(), this);
        state_recycler.setLayoutManager(mLayoutManager);
        state_recycler.setAdapter(partyAdapter);

        Bundle mBundle = getArguments();
        assert mBundle != null;
        election_id = mBundle.getInt(Constant.IntentKey.ELECTION_ID);
        Loader.showMyDialog(getActivity());
        partyHandler.getConstituencyList(election_id);
        partyHandler.getPartyList(election_id);
        return primary_layout;
    }

    @OnClick(R.id.constituency_view)
    public void onConstituencyClick() {
        SelectLocationDialog locationDialog = SelectLocationDialog.getInstance();
        if (mla_list != null) {
            locationDialog.showDialog(getContext(), getString(R.string.mla_constituency), mla_list, new RecyclerTouchWithType() {
                @Override
                public void onVerticalRecycler(int position, int type) {
                    SelectLocationDialog.dismissDialog();

                    Intent mIntent = new Intent(getContext(), PartListView.class);
                    mIntent.putExtra(Constant.IntentKey.ELECTION_ID, election_id);
                    mIntent.putExtra(Constant.IntentKey.PAST_MLA, mla_list.get(position).getValue());
                    mIntent.putExtra(Constant.IntentKey.FROM, Constant.From.FROM_UPCOMING_PARTY);
                    getContext().startActivity(mIntent);
                }

                @Override
                public void onCloseClick() {

                }
            });
        }
    }

    @Override
    public void onPartyResponse(APIResponse apiResponse) {
        partyAdapter.deletePartyList();

        if (main_progress_bar.getVisibility() != View.GONE)
            main_progress_bar.setVisibility(View.GONE);

        state_recycler.setVisibility(View.VISIBLE);
        List<UpcomingPartyModel> partyModels = apiResponse.getData().getUpcomingPartyModels();
        partyAdapter.addPartyList(partyModels);

    }

    @Override
    public void onPartyApiException(ApiException apiException) {

    }

    @Override
    public void onConstituencyResponse(APIResponse apiResponse) {
        Loader.dismissMyDialog(getActivity());
        if (apiResponse.getStatus() == 2000) {
            mla_list.addAll(apiResponse.getData().getMla_list());
            mp_list.addAll(apiResponse.getData().getMp_list());
        }
    }

    @Override
    public void onConstituencyApiException(ApiException apiException) {

    }

    @Override
    public void onPartyServerError(ServerException serverException) {

    }

    @Override
    public void onMClick(int position) {

        Intent mIntent = new Intent(getContext(), PartListView.class);
        mIntent.putExtra(Constant.IntentKey.ELECTION_ID, election_id);
        mIntent.putExtra(Constant.IntentKey.PARTY_ID, position);
        mIntent.putExtra(Constant.IntentKey.FROM, Constant.From.FROM_DECLARED_STATE);
        getContext().startActivity(mIntent);
    }
}

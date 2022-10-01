package com.molitics.molitician.ui.dashboard.election.past_election.about_constituency;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.molitics.molitician.MolticsApplication;
import com.molitics.molitician.R;
import com.molitics.molitician.UiMediator.Loader;
import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.interfaces.MOnClickListener;
import com.molitics.molitician.interfaces.ViewRefreshListener;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.ui.dashboard.constantData.ConstantModel;
import com.molitics.molitician.ui.dashboard.constantData.RecyclerTouchWithType;
import com.molitics.molitician.ui.dashboard.election.past_election.adapter.AboutOtherCandidateAdapter;
import com.molitics.molitician.ui.dashboard.election.past_election.model.AboutConstituencyModel;
import com.molitics.molitician.ui.dashboard.leader.newleaderprofile.NewLeaderProfileActivity;
import com.molitics.molitician.ui.dashboard.login.SelectLocationDialog;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.DialogClass;
import com.molitics.molitician.util.PrefUtil;
import com.molitics.molitician.util.Util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by rahul on 1/11/2017.
 */

public class AboutConstituencyFragment extends Fragment implements ConstituencyPresenter.ConstituencyView, MOnClickListener, ViewRefreshListener {


    @BindView(R.id.ll_main)
    LinearLayout ll_main;
    @BindView(R.id.males_txt)
    TextView males_txt;
    @BindView(R.id.female_txt)
    TextView female_txt;
    @BindView(R.id.voters_txt)
    TextView voters_txt;
    @BindView(R.id.election_result_year)
    TextView election_result_year;
    @BindView(R.id.winner_image)
    CircleImageView winner_image;
    @BindView(R.id.other_candidate_view)
    TextView other_candidate_view;
    @BindView(R.id.other_candidate_recycle)
    RecyclerView other_candidate_recycle;
    @BindView(R.id.constituency_frame_layout)
    FrameLayout constituency_frame_layout;
    @BindView(R.id.winner_name)
    TextView winner_name;
    @BindView(R.id.winner_party_view)
    TextView winner_party_view;
    @BindView(R.id.winner_vote_view)
    TextView winner_vote_view;
    @BindView(R.id.select_constituency_view)
    TextView select_constituency_view;
    @BindView(R.id.rl_no_cons_view)
    RelativeLayout rl_no_cons_view;


    private ConstituencyHandler constituencyHandler;
    private AboutOtherCandidateAdapter otherCandidatesAdapter;
    private AboutConstituencyModel commonModel;
    private List<ConstantModel> mp_list = new ArrayList<>();
    private List<ConstantModel> mla_list = new ArrayList<>();
    private int election_id = 0;
    private String from = "";

    public static Fragment getInstance(int election_id, int state_id, int mp_position, int mla_position, String from) {
        Fragment mFragment = new AboutConstituencyFragment();
        Bundle mBundle = new Bundle();
        mBundle.putInt(Constant.IntentKey.ELECTION_ID, election_id);
        mBundle.putInt(Constant.IntentKey.PAST_MLA, mla_position);
        mBundle.putInt(Constant.IntentKey.PAST_MP, mp_position);
        mBundle.putInt(Constant.IntentKey.PAST_STATE_ID, state_id);
        mBundle.putString(Constant.IntentKey.FROM, from);
        mFragment.setArguments(mBundle);
        return mFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        constituencyHandler = new ConstituencyHandler(this);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View primary_layout = inflater.inflate(R.layout.fragment_about_constituency, container, false);
        ButterKnife.bind(this, primary_layout);

        Bundle mBundle = getArguments();
        int mp_value = mBundle.getInt(Constant.IntentKey.PAST_MP);
        int mla_value = mBundle.getInt(Constant.IntentKey.PAST_MLA);
        int state_id = mBundle.getInt(Constant.IntentKey.PAST_STATE_ID);
        election_id = mBundle.getInt(Constant.IntentKey.ELECTION_ID);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        other_candidate_recycle.setLayoutManager(layoutManager);
        otherCandidatesAdapter = new AboutOtherCandidateAdapter(getContext(), this);
        other_candidate_recycle.setAdapter(otherCandidatesAdapter);
        constituencyHandler.getConstituencyList(election_id);

        from = mBundle.getString(Constant.IntentKey.FROM);
        if (state_id == PrefUtil.getInt(Constant.PreferenceKey.DEFAULT_STATE_VALUE) && (mp_value != 0 || mla_value != 0)) {
            constituencyHandler.getConstituencyData(election_id, mp_value, mla_value);
            rl_no_cons_view.setVisibility(View.GONE);
        } else {
            rl_no_cons_view.setVisibility(View.VISIBLE);
            // Toast.makeText(getContext(), "Please select Constituency", Toast.LENGTH_SHORT).show();
        }

        return primary_layout;
    }

    @OnClick(R.id.select_constituency_view)
    public void onSelectConstituency() {
        SelectLocationDialog locationDialog = SelectLocationDialog.getInstance();
        if (from.equals(Constant.IntentKey.PAST_MP)) {
            if (mp_list != null) {
                locationDialog.showDialog(getContext(), getString(R.string.mp_constituency), mp_list, new RecyclerTouchWithType() {
                    @Override
                    public void onVerticalRecycler(int position, int type) {
                        SelectLocationDialog.dismissDialog();
                        Loader.showMyDialog(getActivity());
                        constituencyHandler.getConstituencyData(election_id, mp_list.get(position).getValue(), 0);
                    }

                    @Override
                    public void onCloseClick() {

                    }
                });
            }
        } else {
            if (mla_list != null) {
                locationDialog.showDialog(getContext(), getString(R.string.mla_constituency), mla_list, new RecyclerTouchWithType() {
                    @Override
                    public void onVerticalRecycler(int position, int type) {
                        SelectLocationDialog.dismissDialog();
                        Loader.showMyDialog(getActivity());
                        constituencyHandler.getConstituencyData(PrefUtil.getInt(Constant.PreferenceKey.DEFAULT_STATE_VALUE), 0, mla_list.get(position).getValue());
                    }

                    @Override
                    public void onCloseClick() {

                    }
                });
            }
        }


    }

    @Override
    public void onConstituencyResponse(APIResponse apiResponse) {
        Loader.dismissMyDialog(getActivity());
        if (apiResponse.getStatus() == 2000) {
            AboutConstituencyModel mlaConstituenyModel = apiResponse.getData().getMlaConstituencyModel();
            AboutConstituencyModel mpConstituenyModel = apiResponse.getData().getMpConstituencyModel();

            if (mlaConstituenyModel != null) {
                commonModel = mlaConstituenyModel;
                handleUI(mlaConstituenyModel);
                otherCandidatesAdapter.addOtherCandidateList(mlaConstituenyModel.getOtherCandidates());
            }
            if (mpConstituenyModel != null) {
                commonModel = mpConstituenyModel;
                handleUI(mpConstituenyModel);
                otherCandidatesAdapter.addOtherCandidateList(mpConstituenyModel.getOtherCandidates());
            }

        }
    }

    @OnClick(R.id.winner_image)
    public void onCandidateClick() {
        Intent mIntent = new Intent(getContext(), NewLeaderProfileActivity.class);
        mIntent.putExtra(Constant.IntentKey.LEADER_PROFILE_ID, commonModel.getWinnerId());
        mIntent.putExtra(Constant.IntentKey.FROM, Constant.IntentKey.NOTIFICATION);
        startActivity(mIntent);

    }

    @Override
    public void onConstituencyApiException(ApiException apiException) {
        Loader.dismissMyDialog(getActivity());
        Util.validateError(getContext(), apiException, constituency_frame_layout, this, null);
    }

    @Override
    public void onConstituencyServerError(ServerException serverException) {
        //  Loader.dismissMyDialog(getActivity());
        DialogClass.showAlert(getContext(), requireContext().getString(R.string.something_went_wrong));
    }


    @Override
    public void onConstituencyList(APIResponse apiResponse) {
        if (apiResponse.getStatus() == 2000) {
            //   Loader.dismissMyDialog(getActivity());
            mla_list.clear();
            mp_list.clear();
            mla_list.addAll(apiResponse.getData().getMla_list());
            mp_list.addAll(apiResponse.getData().getMp_list());
        }
    }

    @Override
    public void onConstituencyListApiException(ApiException apiException) {

    }

    @Override
    public void onMClick(int position) {
        Intent mIntent = new Intent(getContext(), NewLeaderProfileActivity.class);
        mIntent.putExtra(Constant.IntentKey.LEADER_PROFILE_ID, position);
        mIntent.putExtra(Constant.IntentKey.FROM, Constant.IntentKey.NOTIFICATION);
        startActivity(mIntent);
    }

    private void handleUI(AboutConstituencyModel aboutConstituencyModel) {

        ll_main.setVisibility(View.VISIBLE);
        if (aboutConstituencyModel.getOtherCandidates().size() == 0)
            other_candidate_view.setVisibility(View.GONE);
        else
            other_candidate_view.setVisibility(View.VISIBLE);

        checkNull(select_constituency_view, aboutConstituencyModel.getConstitutency());
        checkNull(males_txt, aboutConstituencyModel.getMales());
        checkNull(winner_name, aboutConstituencyModel.getWinnerName());
        checkNull(winner_party_view, aboutConstituencyModel.getWinnerParty());
        checkNull(female_txt, aboutConstituencyModel.getFemales());
        checkNull(voters_txt, aboutConstituencyModel.getVoters());
        checkNull(winner_vote_view, getString(R.string.votes) + aboutConstituencyModel.getWinnerVotes());
        checkNull(election_result_year, getString(R.string.election_year) + " " + aboutConstituencyModel.getElectionYear());

        if (aboutConstituencyModel.getWinnerImage() != null && !TextUtils.isEmpty(aboutConstituencyModel.getWinnerImage()) && aboutConstituencyModel.getWinnerImage().length() > 4) {
            Picasso.with(getContext()).load(aboutConstituencyModel.getWinnerImage()).placeholder(R.drawable.internet_no_cloud).error(R.drawable.error_placeholder).into(winner_image);
        }


    }

    @Override
    public void onRefereshClick() {

    }

    @Override
    public void onNetworkAvailable() {
        onRefereshClick();
    }


    private void checkNull(TextView txt_view, String txt) {
        if (!TextUtils.isEmpty(txt)) {
            txt_view.setVisibility(View.VISIBLE);
            txt_view.setText(txt);
        } else {
            txt_view.setVisibility(View.GONE);
        }
    }
}

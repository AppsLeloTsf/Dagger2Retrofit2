package com.molitics.molitician.ui.dashboard.leader.newleaderprofile;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.molitics.molitician.BasicAcivity;
import com.molitics.molitician.R;
import com.molitics.molitician.UiMediator.Loader;
import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.ui.dashboard.leader.leaderProfile.CandidateProfileModel;
import com.molitics.molitician.ui.dashboard.leader.newleaderprofile.handler.NewCandidateProfileHandler;
import com.molitics.molitician.ui.dashboard.leader.newleaderprofile.handler.NewCandidateProfilePresenter;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.ExtraUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by rahul on 6/30/2017.
 */

public class KnowMoreLeaderActivity extends BasicAcivity implements NewCandidateProfilePresenter.LeaderProfileView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.leader_image)
    CircleImageView leader_image;
    @BindView(R.id.leader_name_view)
    TextView leader_name_view;
    @BindView(R.id.leader_position_view)
    TextView leader_position_view;
    @BindView(R.id.leader_party_view)
    TextView leader_party_view;
    @BindView(R.id.dob_view)
    TextView dob_view;
    @BindView(R.id.state_view)
    TextView state_view;
    @BindView(R.id.assembly_constituency_view)
    TextView assembly_constituency_view;
    @BindView(R.id.lok_sabha_view)
    TextView lok_sabha_view;
    @BindView(R.id.description_txt_view)
    TextView description_txt_view;
    @BindView(R.id.description_view)
    TextView description_view;
    @BindView(R.id.dob_line)
    TextView dob_line;
    @BindView(R.id.RL_main_layout)
    RelativeLayout RL_main_layout;
    @BindView(R.id.party_logo)
    ImageView party_logo;


    private NewCandidateProfileHandler profileHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_know_more_leader);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        showHeader(true, getResources().getString(R.string.know_more_header));

        leader_name_view.setTypeface(ExtraUtils.getRobotoMedium(this));
        toolbar.setNavigationOnClickListener(v -> goBack());

        profileHandler = new NewCandidateProfileHandler(this);

        Intent mIntent = getIntent();
        int leader_id = mIntent.getIntExtra(Constant.IntentKey.LEADER_PROFILE_ID, 0);

        Loader.showProgressDialog(this);
        RL_main_layout.setVisibility(View.GONE);
        profileHandler.getProfile(leader_id);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goBack();
    }

    @Override
    public void onProfileResponse(APIResponse apiResponse) {
        Loader.dismissProgressDialog();
        CandidateProfileModel candidateProfileModel = apiResponse.getData().getCandidateProfileModel();
        handleUi(candidateProfileModel);
    }

    private void handleUi(CandidateProfileModel candidateProfileModel) {
        RL_main_layout.setVisibility(View.VISIBLE);
        String image_url = candidateProfileModel.getProfileImage();
        String party_logo_url = candidateProfileModel.getPartyLogo();
        if (!image_url.isEmpty())
            Picasso.with(this).load(image_url).placeholder(getResources().getDrawable(R.drawable.sample_image)).into(leader_image);
        else
            leader_image.setImageResource(R.drawable.sample_image);

        if (!party_logo_url.isEmpty())
            Picasso.with(this).load(party_logo_url).placeholder(getResources().getDrawable(R.drawable.sample_image)).into(party_logo);
        else
            leader_image.setImageResource(R.drawable.sample_image);

        checkNull(leader_name_view, candidateProfileModel.getName(), null);
        checkNull(leader_position_view, candidateProfileModel.getPosition(), null);
        checkNull(leader_party_view, "Political Party: " + candidateProfileModel.getParty(), null);


        checkNull(dob_view, "DOB: " + candidateProfileModel.getDob(), dob_line);
        checkNull(state_view, "State: " + candidateProfileModel.getState(), null);
        if (!candidateProfileModel.getMlaConstituency().isEmpty())
            checkNull(assembly_constituency_view, Html.fromHtml("<b>" + "Assembly Constituency: " + "</b> ") + candidateProfileModel.getMlaConstituency(), null);
        if (!candidateProfileModel.getMpConstituency().isEmpty())
            checkNull(lok_sabha_view, Html.fromHtml("<b>" + "Lok Sabha Constituency: " + "</b> ") + candidateProfileModel.getMpConstituency(), null);
        checkNull(description_txt_view, candidateProfileModel.getDescription(), description_view);
    }

    @Override
    public void onProfileApiException(ApiException apiException) {
        Loader.dismissProgressDialog();
    }

    @Override
    public void onMoreLeaderFeedsResponse(APIResponse apiResponse) {

    }

    @Override
    public void onMoreLeaderFeedsError(ApiException apiException) {

    }

    private void checkNull(TextView txt_view, String txt, View related_view) {
        if (!TextUtils.isEmpty(txt)) {
            txt_view.setVisibility(View.VISIBLE);
            txt_view.setText(txt);
            if (related_view != null)
                related_view.setVisibility(View.VISIBLE);
        } else {
            if (related_view != null)
                related_view.setVisibility(View.GONE);
            txt_view.setVisibility(View.GONE);
        }
    }

}

package com.molitics.molitician.ui.dashboard.survey;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.molitics.molitician.R;
import com.molitics.molitician.UiMediator.Loader;
import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.interfaces.InitializeServerRequest;
import com.molitics.molitician.interfaces.MOnClickListener;
import com.molitics.molitician.interfaces.ViewRefreshListener;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.ui.dashboard.adapter.SurveyQuAdapter;
import com.molitics.molitician.ui.dashboard.branch_deep_link.BranchShareClass;
import com.molitics.molitician.ui.dashboard.survey.model.AllSurveyModel;
import com.molitics.molitician.ui.dashboard.survey.model.Response;
import com.molitics.molitician.ui.dashboard.survey.model.SubmitSurveyResponseModel;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.DialogClass;
import com.molitics.molitician.util.MoliticsAppPermission;
import com.molitics.molitician.util.ShareScreenShot;
import com.molitics.molitician.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Rahul on 10/19/2016.
 */

public class TakeSurveyFragment extends DialogFragment implements InitializeServerRequest,
        TakeSurveyPresenter.TakeSurveyView, MOnClickListener, ViewRefreshListener, BranchShareClass.DeepLinkCallBack {
    @BindView(R.id.submit_result)
    TextView submit_result;
    @BindView(R.id.survey_recycler)
    RecyclerView survey_recycler;
    @BindView(R.id.RL_handler)
    RelativeLayout RL_handler;
    @BindView(R.id.ll_main_layout)
    LinearLayout ll_main_layout;
    @BindView(R.id.main_progress_bar)
    ProgressBar main_progress_bar;

    private TakeSurveyHandler takeSurveyHandler;
    private SurveyQuAdapter surveyAdapter;
    private int survey_id = 0;
    private int STORAGE_PERMISSION_CODE = 23;
    private int vote = -1;
    private List<Response> response_list = new ArrayList<>();

    public static Fragment getInstance(int survey_id) {

        Fragment detailFragment = new TakeSurveyFragment();
        Bundle mBundle = new Bundle();
        mBundle.putInt(Constant.IntentKey.SURVEY_ID_INTENT, survey_id);
        detailFragment.setArguments(mBundle);
        return detailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        takeSurveyHandler = new TakeSurveyHandler(this);
        setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog d = getDialog();
        if (d != null) {
            int width = WindowManager.LayoutParams.MATCH_PARENT;
            int height = WindowManager.LayoutParams.MATCH_PARENT;
            d.getWindow().setLayout(width, height);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color
                .TRANSPARENT));
        View primaryLayout = inflater.inflate(R.layout.adapter_survey_detail, container, false);
        ButterKnife.bind(this, primaryLayout);

        Bundle mBundle = getArguments();
        assert mBundle != null;
        survey_id = mBundle.getInt(Constant.IntentKey.SURVEY_ID_INTENT);

        surveyAdapter = new SurveyQuAdapter(getContext(), this, Constant.AppTheme.DEFAULT_TEXT);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        survey_recycler.setLayoutManager(linearLayoutManager);
        survey_recycler.setAdapter(surveyAdapter);

        ll_main_layout.setVisibility(View.GONE);
        main_progress_bar.setVisibility(View.VISIBLE);
        createServerRequest(Constant.RequestTag.TAKE_SURVEY);
        return primaryLayout;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_share, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int item_id = item.getItemId();
        if (item_id == R.id.share_menu) {
            if (isReadStorageAllowed()) {
                BranchShareClass.generateShareLink(getActivity(), this, "Molitics Survey", "",
                        Constant.ShareCampaign.SURVEY, Constant.ShareLinkTag.SURVEY, String.valueOf(survey_id), Constant.ShareCampaign.SURVEY);
            } else {
                MoliticsAppPermission.requestReadStoragePermission(getContext());
            }
        }
        return true;
    }

    @Override
    public void createServerRequest(int tag) {
        switch (tag) {
            case Constant.RequestTag.TAKE_SURVEY:
                takeSurveyHandler.getSurveyDetail(survey_id);
                break;
            case Constant.RequestTag.MAIN_SUBMIT_SURVEY:
                takeSurveyHandler.submitSurvey(survey_id, vote);
                break;
        }
    }

    @OnClick(R.id.submit_result)
    public void onSubmitClick() {
        if (this.vote == -1) {
            Toast.makeText(getContext(), getString(R.string.select_one_option), Toast.LENGTH_LONG).show();
        } else {
            Loader.showMyDialog(getActivity());
            submit_result.setVisibility(View.GONE);
            createServerRequest(Constant.RequestTag.MAIN_SUBMIT_SURVEY);
        }
    }

    @Override
    public void onSurveyDetail(APIResponse apiResponse) {
        if (apiResponse.getStatus() == 2000) {
            AllSurveyModel surveyDetailModel = apiResponse.getData().getNewsSurveyModel();
            handleUi(surveyDetailModel);
        }
    }

    @Override
    public void onSurveyApiException(ApiException apiException) {
        Util.validateError(getContext(), apiException, RL_handler, this, surveyAdapter.getItemCount());
    }

    @Override
    public void onSubmitSurveyResponse(APIResponse apiResponse) {
        Loader.dismissMyDialog(getActivity());
        if (apiResponse.getStatus() == 2000) {
            handleResponseUi(apiResponse.getData().getNewsSurveyResponseModel());
        }
    }

    @Override
    public void onSubmitSurveyApiException(ApiException apiException) {
        Loader.dismissMyDialog(getActivity());
        Util.validateError(getContext(), apiException, null, this, null);
    }

    @Override
    public void onSubmitSurveyFormValidation(Map<String, String> errors) {
        Loader.dismissMyDialog(getActivity());
    }

    @Override
    public void onSubmitSurveyServerException(ServerException serverException) {
        Loader.dismissMyDialog(getActivity());
        DialogClass.showAlert(getActivity(), getString(R.string.something_went_wrong));
    }

    private void handleResponseUi(SubmitSurveyResponseModel surveyDetailModel) {
        boolean canReply = false;
        submit_result.setVisibility(View.GONE);
        surveyAdapter.clearSurveyList();
        surveyAdapter.addSurveyList(surveyDetailModel.getResponse(), canReply, surveyDetailModel.getTotalResponse());
    }

    private void handleUi(AllSurveyModel surveyDetailModel) {
        main_progress_bar.setVisibility(View.GONE);
        ll_main_layout.setVisibility(View.VISIBLE);
        boolean canReply = true;
        if (surveyDetailModel.getCanReply() == 0) {
            canReply = false;
            submit_result.setVisibility(View.GONE);
        } else {
            canReply = true;
            submit_result.setVisibility(View.VISIBLE);
        }
        surveyAdapter.clearSurveyList();
        response_list = surveyDetailModel.getResponse();
        surveyAdapter.addSurveyList(surveyDetailModel.getResponse(), canReply, surveyDetailModel.getTotalResponse());
    }

    @Override
    public void onMClick(int position) {
        vote = response_list.get(position).getId();
        surveyAdapter.setSelected(position);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                BranchShareClass.generateShareLink(getActivity(), this, "Molitics Survey",
                        "", Constant.ShareCampaign.SURVEY,
                        Constant.ShareLinkTag.SURVEY, String.valueOf(survey_id), Constant.ShareCampaign.SURVEY);
            } else {
                Toast.makeText(getContext(), getString(R.string.denied_permission), Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean isReadStorageAllowed() {
        if (getContext() != null) {
            int result = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

            return result == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    @Override
    public void onRefereshClick() {
        createServerRequest(Constant.RequestTag.TAKE_SURVEY);
    }

    @Override
    public void onNetworkAvailable() {
        onRefereshClick();
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onGenerateShareLink(String full_txt, String url) {
        DialogFragment myFragment = (SurveyViewPagerFragment) getFragmentManager().findFragmentByTag("Dialog Fragment");
        if (myFragment != null) {
            ShareScreenShot.takeDialogScreenshot(myFragment, getActivity(), getString(R.string.media_of_politics) + full_txt, url);
        }
    }
}

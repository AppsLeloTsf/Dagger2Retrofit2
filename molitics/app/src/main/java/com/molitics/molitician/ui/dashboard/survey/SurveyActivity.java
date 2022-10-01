package com.molitics.molitician.ui.dashboard.survey;


import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.molitics.molitician.BasicAcivity;
import com.molitics.molitician.R;
import com.molitics.molitician.database.Database;
import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.interfaces.InitializeServerRequest;
import com.molitics.molitician.interfaces.MOnClickListener;
import com.molitics.molitician.interfaces.ViewRefreshListener;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.model.Data;
import com.molitics.molitician.ui.dashboard.DashBoardActivity;
import com.molitics.molitician.ui.dashboard.OnFragmentInteractionListener;
import com.molitics.molitician.ui.dashboard.constantData.ConstantModel;
import com.molitics.molitician.ui.dashboard.constantData.RecyclerTouchWithType;
import com.molitics.molitician.ui.dashboard.login.SelectLocationDialog;
import com.molitics.molitician.ui.dashboard.survey.adapter.SurveyAdapter;
import com.molitics.molitician.ui.dashboard.survey.model.AllSurveyModel;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.DialogClass;
import com.molitics.molitician.util.NetworkUtil;
import com.molitics.molitician.util.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.molitics.molitician.util.Constant.SUCCESS_RESULT;


public class SurveyActivity extends BasicAcivity implements InitializeServerRequest, SurveyPresenter.SurveyView,
        MOnClickListener, ViewRefreshListener, SurveyAdapter.SurveyAdapterInterface {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.main_progress_bar)
    ProgressBar main_progress_bar;
    @BindView(R.id.loading_grid)
    LinearLayout loading_grid;

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.local_news_fragment)
    FrameLayout local_news_fragment;
    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private int pageNo = 1;
    private int SURVEY_PAGE_DEFAULT_VALUE = 1;

    private OnFragmentInteractionListener mListener;
    SurveyHandler surveyHandler;
    ArrayList<AllSurveyModel> allSurveyModels = new ArrayList<>();
    SurveyAdapter surveyAdapter;
    boolean loadMore = false;
    boolean newsApiCall = true;
    TextView state_text;
    List<ConstantModel> state_list;
    Integer stateId;

    ImageView remove_feed_state;


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_view);
        ButterKnife.bind(this);
        if (getIntent() != null) {
            mParam1 = getIntent().getStringExtra(ARG_PARAM1);
            mParam2 = getIntent().getStringExtra(ARG_PARAM2);
            stateId = getIntent().getIntExtra(Constant.IntentKey.PAST_STATE_ID, 0);
        }
        surveyHandler = new SurveyHandler(this);
        pageNo = SURVEY_PAGE_DEFAULT_VALUE;

        swipeContainer.setRefreshing(false);
        swipeContainer.setEnabled(false);

        getToolBar();
        bindUI();
        setResult(SUCCESS_RESULT);
    }

    private void getToolBar() {
        setSupportActionBar(toolbar);
        showHeader(true, "");
        toolbar.setNavigationOnClickListener(v -> finish());

        Toolbar toolbar = findViewById(R.id.toolbar);
        RelativeLayout center_toolbar_rl = toolbar.findViewById(R.id.center_toolbar_rl);
        // check null exception
        if (center_toolbar_rl != null) {
            center_toolbar_rl.removeAllViews();
            View child = getLayoutInflater().inflate(R.layout.survey_tool_bar_layout, null);
            center_toolbar_rl.addView(child);

            state_text = child.findViewById(R.id.state_name);
            state_text.setText(getString(R.string.select_state));

            state_text.setOnClickListener(v -> {
                state_list = DashBoardActivity.state_list;
                if (state_list != null) {
                    SelectLocationDialog selectLocationDialog = SelectLocationDialog.getInstance();
                    selectLocationDialog.showDialog(SurveyActivity.this, getString(R.string.select_state), state_list, new RecyclerTouchWithType() {
                        @Override
                        public void onVerticalRecycler(int position, int type) {
                            remove_feed_state.setVisibility(View.VISIBLE);
                            onVerticalRecyclerMy(position, type);
                        }

                        @Override
                        public void onCloseClick() {
                        }
                    });
                } else {
                    onStateNull();
                }
            });

            remove_feed_state = child.findViewById(R.id.remove_feed_state);

            remove_feed_state.setOnClickListener(v -> {
                stateId = null;
                pageNo = SURVEY_PAGE_DEFAULT_VALUE;

                createServerRequest(Constant.RequestTag.ALL_SURVEY);
                state_text.setText(R.string.select_state);
                remove_feed_state.setVisibility(View.GONE);
            });
        }
    }


    private void bindUI() {
        surveyAdapter = new SurveyAdapter(this, this, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recycler_view.setLayoutManager(linearLayoutManager);
        recycler_view.setAdapter(surveyAdapter);
        //provide position of item on click

        if (NetworkUtil.isNetworkConnected(this)) {
            createServerRequest(Constant.RequestTag.ALL_SURVEY);
            main_progress_bar.setVisibility(View.VISIBLE);
        } else {
            Util.addNetworkNotFoundView(this, local_news_fragment, this);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void onStateNull() {
        if (mListener != null) {
            Util.toastLong(this, getString(R.string.state_list_fetching));
        }
    }

    @Override
    public void createServerRequest(int tag) {
        switch (tag) {
            case Constant.RequestTag.ALL_SURVEY:
                surveyHandler.hitSurveyRequest(pageNo, stateId);
                break;
        }
    }


    public void pageNoHandler(Data data) {
        newsApiCall = true;
        if (data.getSurvey_list().size() > 9) {
            loadMore = true;
            pageNo++;
        } else {
            loadMore = false;
        }
    }

    public void onVerticalRecyclerMy(int position, int type) {
        pageNo = SURVEY_PAGE_DEFAULT_VALUE;
        SelectLocationDialog.dismissDialog();
        stateId = state_list.get(position).getValue();
        String select_state_name = state_list.get(position).getKey();
        if (state_text != null) {
            state_text.setText(select_state_name);
        }
        surveyAdapter.clearSurveyList();
        Database.deleteLocalAllNews();
        createServerRequest(Constant.RequestTag.ALL_SURVEY);
        main_progress_bar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSurveyResponse(APIResponse apiResponse) {
        // local_news_fragment -> error layout
        local_news_fragment.removeAllViews();
        local_news_fragment.setVisibility(View.GONE);

        allSurveyModels = apiResponse.getData().getSurvey_list();

        if (main_progress_bar.getVisibility() == View.VISIBLE)
            main_progress_bar.setVisibility(View.GONE);
        if (pageNo == SURVEY_PAGE_DEFAULT_VALUE) {
            surveyAdapter.clearSurveyList();
        }
        if (loading_grid.getVisibility() == View.VISIBLE)
            loading_grid.setVisibility(View.GONE);

        surveyAdapter.addAllSurvey(allSurveyModels);

        if (pageNo == SURVEY_PAGE_DEFAULT_VALUE) {
            openSurveyDialog(0);
        }
        pageNoHandler(apiResponse.getData());
        swipeContainer.setRefreshing(false);

    }


    @Override
    public void onSurveyApiException(ApiException apiException) {
        newsApiCall = true;
        if (main_progress_bar.getVisibility() == View.VISIBLE)
            main_progress_bar.setVisibility(View.GONE);
        loading_grid.setVisibility(View.GONE);

        Util.validateError(this, apiException, local_news_fragment, this, surveyAdapter.getSurveyListSize());

    }

    @Override
    public void onSurveyServerException(ServerException serverException) {
        newsApiCall = true;
        if (main_progress_bar.getVisibility() == View.VISIBLE)
            main_progress_bar.setVisibility(View.GONE);
        loading_grid.setVisibility(View.GONE);
        DialogClass.showAlert(this, getString(R.string.something_went_wrong));
    }

    @Override
    public void onMClick(int position) {
        openSurveyDialog(position);

    }

    @Override
    public void onRefereshClick() {
        if (NetworkUtil.isNetworkConnected(this)) {
            createServerRequest(Constant.RequestTag.ALL_SURVEY);
            main_progress_bar.setVisibility(View.VISIBLE);
        } else {
            Util.addNetworkNotFoundView(this, local_news_fragment, this);
            main_progress_bar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onNetworkAvailable() {
        onRefereshClick();
    }

    DialogFragment survey_dialog_fragment;

    private void openSurveyDialog(int position) {

        FragmentManager fm = getSupportFragmentManager();
        if (fm != null) {
            ArrayList<AllSurveyModel> surveyList = new ArrayList<>(surveyAdapter.getSurveyList());
            survey_dialog_fragment = SurveyViewPagerFragment.getInstance(position, surveyList);
            survey_dialog_fragment.show(fm, "Dialog Fragment");
        }
    }

    @Override
    public void loadMore() {
        if (loadMore && newsApiCall) {
            if (loading_grid.getVisibility() == View.GONE) {
                loading_grid.setVisibility(View.VISIBLE);
                newsApiCall = false;
                createServerRequest(Constant.RequestTag.ALL_SURVEY);
            }
        }
    }
}

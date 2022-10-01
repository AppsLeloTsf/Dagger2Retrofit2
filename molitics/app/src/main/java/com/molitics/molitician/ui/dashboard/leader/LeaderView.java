package com.molitics.molitician.ui.dashboard.leader;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.molitics.molitician.interfaces.LeaderAdapterInterface;
import com.molitics.molitician.interfaces.ViewRefreshListener;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.model.DeviceRegistration;
import com.molitics.molitician.ui.dashboard.OnFragmentInteractionListener;
import com.molitics.molitician.ui.dashboard.constantData.ConstantModel;
import com.molitics.molitician.ui.dashboard.constantData.RecyclerTouchWithType;
import com.molitics.molitician.ui.dashboard.filter.LeaderFilterActivity;
import com.molitics.molitician.ui.dashboard.leader.leaderFilter.data.LeaderFilter;
import com.molitics.molitician.ui.dashboard.leader.model.LeadersIdModel;
import com.molitics.molitician.ui.dashboard.leader.newleaderprofile.NewLeaderProfileActivity;
import com.molitics.molitician.ui.dashboard.leader.searchLeader.SearchLeaderActivity;
import com.molitics.molitician.ui.dashboard.login.LoginDialogFragment;
import com.molitics.molitician.ui.dashboard.login.SelectLocationDialog;
import com.molitics.molitician.ui.dashboard.voice.VoiceFragment;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.DialogClass;
import com.molitics.molitician.util.PrefUtil;
import com.molitics.molitician.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;
import static com.molitics.molitician.util.Constant.From.SEARCH_LEADER;
import static com.molitics.molitician.util.Constant.From.VISIT_LEADER;
import static com.molitics.molitician.util.Constant.RequestTag.LOGINDIALOG;

public class LeaderView extends Fragment implements InitializeServerRequest, LeaderPresenter.LeaderView,
        LeaderAdapterInterface, ViewRefreshListener, LeaderAdapter.OnLoadMoreResult, FollowPresenter.FollowView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @BindView(R.id.leaders_recycler_main)
    RecyclerView leaders_recycler_main;
    @BindView(R.id.main_progress_bar)
    ProgressBar main_progress_bar;
    @BindView(R.id.loading_grid)
    LinearLayout loading_grid;
    @BindView(R.id.RL_handler)
    RelativeLayout RL_handler;
    @BindView(R.id.main_content)
    CoordinatorLayout main_content;

    private final int FILTER_REQUEST = 10001;
    public final static int DETAIL_PROFILE = 10002;
    private LeaderHandler leaderHandler;
    private String pageNo = "1";
    private boolean loadMore = false;
    private boolean newsApiCall = true;
    private LeaderFilter leaderFilter = new LeaderFilter();
    private LeaderAdapter leaderAdapter;
    private String search_text = "";
    private Handler handler = new Handler(Looper.getMainLooper() /*UI thread*/);
    private Runnable workRunnable;
    private boolean isSearch = false;
    private FollowHandler followHandler;
    private int display_type = 0;
    private int openFrom;
    private DeviceRegistration submitLocationModel;

    private String title = "";
    private List<ConstantModel> mla_list;
    private List<ConstantModel> mp_list;
    /// LoadLeaderData loadLeaderData;

    private int likeAction = 0;
    private int adapter_position = 0, candidateId = 0;

    private int temp_follow_position = 0;
    private int temp_unfollow_position = 0;
    SearchView searchView = null;
    MenuItem searchItem;

    public static Fragment getInstance(int display_type, int from) {

        LeaderView leaderView = new LeaderView();
        Bundle mBundle = new Bundle();
        mBundle.putInt(Constant.IntentKey.DISPLAY_TYPE, display_type);
        mBundle.putInt(Constant.IntentKey.FROM, from);
        leaderView.setArguments(mBundle);
        return leaderView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof LoadLeaderData) {
            loadLeaderData = (LoadLeaderData) context;
        }*/
    }

 /*   public interface LoadLeaderData {
        void onLeaderDataResponse(ArrayList<AllLeaderModel> allLeaderModels);
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        leaderHandler = new LeaderHandler(this);
        followHandler = new FollowHandler(this);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View primary_view = inflater.inflate(R.layout.fragment_leader_view, container, false);
        ButterKnife.bind(this, primary_view);
        setRetainInstance(true);
        pageNo = "1";

        getMyArguments();
        bindUi();
        getToolBar();

        if (display_type == Constant.Action.LOCAL) {
            selectConstituency();
        } else {
            createServerRequest(Constant.RequestTag.LEADER_NEWS);
        }

        if (openFrom==VISIT_LEADER) {
            LeaderParentView.filter_float_btn.setOnClickListener(view -> {

                Intent mIntent = new Intent(view.getContext(), LeaderFilterActivity.class);
                mIntent.putExtra(Constant.IntentKey.FILTERS, leaderFilter);
                startActivityForResult(mIntent, FILTER_REQUEST);
            });
        }
        return primary_view;
    }

    private void getMyArguments() {
        Bundle mBundle = getArguments();
        assert mBundle != null;
        display_type = mBundle.getInt(Constant.IntentKey.DISPLAY_TYPE);
        openFrom = mBundle.getInt(Constant.IntentKey.FROM);
    }

    private void bindUi() {
        leaderAdapter = new LeaderAdapter(getContext(), this, this);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        leaders_recycler_main.setLayoutManager(mLayoutManager);
        leaders_recycler_main.setNestedScrollingEnabled(true);
        leaders_recycler_main.setAdapter(leaderAdapter);
        leaders_recycler_main.setHasFixedSize(true);

        leaderFilter.setPage(pageNo);
        main_progress_bar.setVisibility(View.VISIBLE);
        leaderFilter.setTab_serial(display_type);
    }

    private void getToolBar() {
        if (getActivity() != null) {
            Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
            RelativeLayout center_toolbar_rl = toolbar.findViewById(R.id.center_toolbar_rl);
            // check null exception
            if (center_toolbar_rl != null) {
                center_toolbar_rl.removeAllViews();
                View child = getLayoutInflater().inflate(R.layout.common_txt_view, null);
                center_toolbar_rl.addView(child);
                TextView center_text = child.findViewById(R.id.state_name);
                center_text.setText(getString(R.string.leaders));
            }
        }
    }


    private void setViewPage() {
        Fragment getFragmentInstance = getActivity().getSupportFragmentManager().findFragmentById(R.id.home_container);
        if (getFragmentInstance instanceof LeaderParentView) {
            ((LeaderParentView) getFragmentInstance).getViewPager().setCurrentItem(2, true);
            leaderFilter.setTab_serial(display_type);
        }
    }

    private void selectConstituency() {

        if (PrefUtil.getInt(Constant.PreferenceKey.DEFAULT_MLA_VALUE) == 0 || PrefUtil.getInt(Constant.PreferenceKey.DEFAULT_MP_VALUE) == 0) {
            leaderHandler.getAssemblyList(PrefUtil.getInt(Constant.PreferenceKey.DEFAULT_STATE_VALUE));
        } else {
            createServerRequest(Constant.RequestTag.LEADER_NEWS);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_leader_view, menu);

        MenuItem sItem;
        searchItem = menu.findItem(R.id.search_menu);
        final SearchManager searchManager = (SearchManager) (getActivity()).getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo((getActivity()).getComponentName()));
        searchView.setIconifiedByDefault(true);
        sItem = menu.findItem(R.id.search_menu);
        sItem.setActionView(searchView);

        SearchView.SearchAutoComplete searchAutoComplete =
                (SearchView.SearchAutoComplete) searchView.findViewById(R.id.search_src_text);
        // Does help!
        ImageView searchClose = (ImageView) searchView.findViewById(R.id.search_close_btn);
        ImageView searchGo = (ImageView) searchView.findViewById(R.id.search_go_btn);
        searchClose.setImageResource(R.drawable.ic_clear_white_24dp);
        searchGo.setImageResource(R.drawable.ic_clear_white_24dp);

        searchAutoComplete.setHintTextColor(getResources().getColor(R.color.hard_white));
        searchAutoComplete.setTextColor(getResources().getColor(R.color.hard_white));

        if (!search_text.isEmpty()) {
            // MenuItemCompat.expandActionView(menu.findItem(R.id.search_menu));
            searchItem.expandActionView();
            searchView.setQuery(search_text, false);
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(final String text) {

                if (openFrom == SEARCH_LEADER && text.length() > 1) {
                    handler.removeCallbacks(workRunnable);
                    workRunnable = () -> {
                        isSearch = true;
                        pageNo = "1";
                        leaderFilter.setPage("1");
                        search_text = text;
                        leaderAdapter.clearLeaderList();
                        main_progress_bar.setVisibility(View.VISIBLE);
                        if (text.isEmpty()) {

                            createServerRequest(Constant.RequestTag.LEADER_NEWS);
                            leaderFilter.setTab_serial(display_type);
                        } else {
                            LeadersIdModel leadersIdModel = leaderAdapter.getLeadersId();
                            leadersIdModel.setPage(pageNo);
                            leadersIdModel.setName(text);
                            leadersIdModel.setState("");
                            leadersIdModel.setDistrict("");
                            leadersIdModel.setParty("");
                            leadersIdModel.setMla("");
                            leadersIdModel.setMp("");
                            leadersIdModel.setPost("");
                            leadersIdModel.setTab_serial(0);
                            leaderHandler.getFilterCandidates(leadersIdModel);
                            // leaderHandler.searchCandidate(pageNo, text);
                        }
                    };
                    handler.postDelayed(workRunnable, 1000 /*delay*/);
                }
                return true;
            }
        });

        searchView.setOnCloseListener(() -> {
            createServerRequest(Constant.RequestTag.LEADER_NEWS);

            return true;
        });
        searchClose.setOnClickListener(v -> {
            if (openFrom == SEARCH_LEADER) {
                getActivity().finish();
            }
        });
        searchGo.setOnClickListener(v -> {
            if (openFrom == SEARCH_LEADER) {
                searchItem.collapseActionView();
                searchView.setIconified(true);
                startSearchActivity();
            }
        });

        if (openFrom == SEARCH_LEADER) {
            searchView.setIconified(false);
            searchItem.expandActionView();
        } else {
            searchView.clearFocus();
            searchItem.collapseActionView();
            searchView.setIconified(true);
        }
        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Do whatever you need
                startSearchActivity();
                return true; // KEEP IT TO TRUE OR IT DOESN'T OPEN !!
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Do whatever you need
                if (openFrom == SEARCH_LEADER) {
                    getActivity().finish();
                }
                return true; // OR FALSE IF YOU DIDN'T WANT IT TO CLOSE!
            }
        });
    }

    private void startSearchActivity() {
        new Handler().postDelayed(() -> {
            searchItem.collapseActionView();
            searchView.setIconified(true);
            startActivity(new Intent(getContext(), SearchLeaderActivity.class));
        }, 500);

    }

    public void pageNoHandler(ArrayList<AllLeaderModel> data) {
        newsApiCall = true;
        if (data.size() > 9) {
            loadMore = true;
            pageNo = String.valueOf(Integer.valueOf(pageNo) + 1);
        } else {
            loadMore = false;
        }
    }

    @Override
    public void createServerRequest(int tag) {
        switch (tag) {
            case Constant.RequestTag.LEADER_NEWS:
                isSearch = false;
                // leaderHandler.hitLeaderRequest(pageNo);
                getFilterCandidate();
                break;
        }
    }

    @Override
    public void onLeaderResponse(APIResponse apiResponse) {
        Loader.dismissProgressDialog();
        if (main_progress_bar != null && main_progress_bar.getVisibility() == View.VISIBLE)
            main_progress_bar.setVisibility(View.GONE);
        loading_grid.setVisibility(View.GONE);

        RL_handler.setVisibility(View.GONE);
        ArrayList<AllLeaderModel> allLeaderModels = apiResponse.getData().getLeaderModels();
        setDataResponse(allLeaderModels);
    }

    public void setDataResponse(ArrayList<AllLeaderModel> allLeaderModels) {
        if (pageNo.equals("1")) {
            leaderAdapter.clearLeaderList();
        }
        leaderAdapter.setLoadMore(true);
        leaderAdapter.addLeaderList(allLeaderModels);
        pageNoHandler(allLeaderModels);
    }

    @Override
    public void onLeaderApiException(ApiException apiException) {
        Loader.dismissProgressDialog();
        newsApiCall = true;
        loading_grid.setVisibility(View.GONE);
        if (main_progress_bar != null && main_progress_bar.getVisibility() == View.VISIBLE)
            main_progress_bar.setVisibility(View.GONE);
        loading_grid.setVisibility(View.GONE);
        //  setNoDataLayout(getString(R.string.no_data));
        if (apiException.getCode() == 2004 || apiException.getCode() == 2005) {
            leaderAdapter.setLoadMore(false);
        }
        Util.validateError(getContext(), apiException, RL_handler, this, leaderAdapter.getLeaderListSize());
        leaderAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAssemblyList(APIResponse apiResponse) {
        submitLocationModel = new DeviceRegistration();
        List<ConstantModel> constantModels = new ArrayList<>();
        mla_list = apiResponse.getData().getMla_list();
        mp_list = apiResponse.getData().getMp_list();
        if (PrefUtil.getInt(Constant.PreferenceKey.DEFAULT_MLA_VALUE) == 0) {
            title = "Select Assembly Constituency";
            constantModels.addAll(mla_list);
        } else if (PrefUtil.getInt(Constant.PreferenceKey.DEFAULT_MP_VALUE) == 0) {
            title = "Select Lok Sabha Constituency";
            constantModels.addAll(mp_list);

        }
        SelectLocationDialog locationDialog = SelectLocationDialog.getInstance();
        if (constantModels.size() != 0 && isVisible()) {
            locationDialog.showDialog(getContext(), title, constantModels, new RecyclerTouchWithType() {
                @Override
                public void onVerticalRecycler(int position, int type) {
                    SelectLocationDialog.dismissDialog();
                    if (isVisible()) {
                        if (PrefUtil.getInt(Constant.PreferenceKey.DEFAULT_MLA_VALUE) == 0) {
                            submitLocationModel.setMla(mla_list.get(position).getValue());
                            submitLocationModel.setMp(PrefUtil.getInt(Constant.PreferenceKey.DEFAULT_MP_VALUE) == 0 ? null : PrefUtil.getInt(Constant.PreferenceKey.DEFAULT_MP_VALUE));
                        } else {
                            submitLocationModel.setMla(PrefUtil.getInt(Constant.PreferenceKey.DEFAULT_MLA_VALUE) == 0 ? null : PrefUtil.getInt(Constant.PreferenceKey.DEFAULT_MLA_VALUE));
                            submitLocationModel.setMp(mp_list.get(position).getValue());
                        }

                        submitLocationModel.setState(PrefUtil.getInt(Constant.PreferenceKey.DEFAULT_STATE_VALUE));
                        leaderHandler.submitLocation(submitLocationModel);
                    }
                }

                @Override
                public void onCloseClick() {
                    main_progress_bar.setVisibility(View.GONE);
                    RL_handler.setVisibility(View.VISIBLE);
                    if (isVisible())
                        createRetryView();
                }
            });
        }
        title = null;
    }

    private void createRetryView() {
        TextView retry_dialog = new TextView(getContext());
        retry_dialog.setText(R.string.retry_select_constituency);
        retry_dialog.setTextColor(getResources().getColor(R.color.white));
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        ///    (RelativeLayout.LayoutParams) retry_dialog.getLayoutParams();
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

        retry_dialog.setLayoutParams(layoutParams);
        RL_handler.addView(retry_dialog);
        retry_dialog.setOnClickListener(v -> {

        });
    }

    @Override
    public void onAssemblyListException(ApiException apiException) {

    }

    @Override
    public void onLocationSelectionResponse(APIResponse apiResponse) {
    }

    @Override
    public void onLocationSelectionApiException(ApiException apiException) {
        if (apiException.getCode() == 2005 && submitLocationModel != null) {
            PrefUtil.putInt(Constant.PreferenceKey.DEFAULT_MLA_VALUE, submitLocationModel.getMla() == null ? 0 : submitLocationModel.getMla());
            PrefUtil.putInt(Constant.PreferenceKey.DEFAULT_MP_VALUE, submitLocationModel.getMp() == null ? 0 : submitLocationModel.getMp());

            createServerRequest(Constant.RequestTag.LEADER_NEWS);
        } else {
            Util.validateError(getContext(), apiException, null, this, null);
        }
    }

    @Override
    public void onLeaderServerError(ServerException serverException) {
        Loader.dismissProgressDialog();
        newsApiCall = true;
        if (isVisible())
            DialogClass.showAlert(getContext(), getString(R.string.something_went_wrong));
    }

    @Override
    public void onFollowResponse(APIResponse apiResponse) {
        if (apiResponse.getStatus() == 2000) {
            if (leaderAdapter != null)
                leaderAdapter.followDone(temp_follow_position, apiResponse.getData().getKCount());
        } else {
            if (leaderAdapter != null)
                leaderAdapter.onUnSuccessFollow(temp_follow_position);
        }
    }

    @Override
    public void onUnFollowRespnse(APIResponse apiResponse) {

        if (apiResponse.getStatus() == 2000) {
            if (leaderAdapter != null)
                leaderAdapter.unFollowDone(temp_unfollow_position, apiResponse.getData().getKCount());
        } else {
            if (leaderAdapter != null)
                leaderAdapter.onUnSuccessUnFollow(temp_unfollow_position);
        }
    }

    @Override
    public void onFollowApiException(ApiException apiException) {
    }

    @Override
    public void onFollowServerError(ServerException serverException) {
    }

    @Override
    public void onLeaderActionResponse(APIResponse apiResponse) {
        if (likeAction == 1) {
            leaderAdapter.likeDone(adapter_position, apiResponse.getData().getUpvoteCount(), apiResponse.getData().getDownvoteCount());
        } else if (likeAction == 2) {
            leaderAdapter.disLikeDone(adapter_position, apiResponse.getData().getUpvoteCount(), apiResponse.getData().getDownvoteCount());
        }

    }

    @Override
    public void onLeaderActionApiError(ApiException apiException) {

    }

    @Override
    public void onDeleteLeaderActionApiError(ApiException apiException) {
    }

    @Override
    public void onDeleteLeaderAction(APIResponse apiResponse) {
        leaderAdapter.deleteDone(adapter_position, apiResponse.getData().getUpvoteCount(), apiResponse.getData().getDownvoteCount());
    }

    @Override
    public void onLeaderFormValidation(Map<String, String> mapValidation) {
        newsApiCall = true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            if (requestCode == FILTER_REQUEST) {
                if (data != null) {
                    isSearch = false;
                    leaderFilter = data.getParcelableExtra(Constant.IntentKey.FILTERS);
                    pageNo = "1";
                    leaderFilter.setPage(pageNo);
                    if (!TextUtils.isEmpty(leaderFilter.getPost()) || !TextUtils.isEmpty(leaderFilter.getParty())) {
                        display_type = 0;
                    } else {
                        display_type = 2;
                    }
                    leaderFilter.setTab_serial(display_type);

                    leaderAdapter.clearLeaderList();
                    main_progress_bar.setVisibility(View.VISIBLE);

                    Loader.showProgressDialog(getContext());
                    getFilterCandidate();
                    setViewPage();

                }
            }

            if (requestCode == LOGINDIALOG)
                perfromActionOnLogin();

            if (requestCode == DETAIL_PROFILE && data != null) {
                int position = data.getIntExtra(Constant.IntentKey.POSITION, 0);
                int action = data.getIntExtra(Constant.IntentKey.ACTION, 0);
                int upVote = data.getIntExtra(Constant.IntentKey.UPVOTE, 0);
                int downVote = data.getIntExtra(Constant.IntentKey.DOWNVOTE, 0);
                int is_following = data.getIntExtra(Constant.IntentKey.LEADER_IS_FOLLOW, 0);
                int followers = data.getIntExtra(Constant.IntentKey.LEADER_FOLLOW, 0);
                if (leaderAdapter != null && leaderAdapter.getLeaderListSize() > 0) {
                    if (leaderAdapter.getLeaderModel(position) != null) {
                        AllLeaderModel leaderModel = leaderAdapter.getLeaderModel(position);
                        leaderModel.setUser_vote_action(action);
                        leaderModel.setFollowers(String.valueOf(followers));
                        leaderModel.setIsFollowing(is_following);
                        leaderModel.setLike_count(upVote);
                        leaderModel.setDislike_count(downVote);
                        leaderAdapter.notifyDataSetChanged();
                    }
                }
            }
        }
    }

    private void getFilterCandidate() {

        LeadersIdModel leadersIdModel = leaderAdapter.getLeadersId();
        leadersIdModel.setDistrict(leaderFilter.getDistrict());
        leadersIdModel.setPage(leaderFilter.getPage());
        leadersIdModel.setState(leaderFilter.getState());
        leadersIdModel.setMp(leaderFilter.getMp_constituency());
        leadersIdModel.setMla(leaderFilter.getMla_constituency());
        leadersIdModel.setParty(leaderFilter.getParty());
        leadersIdModel.setPost(leaderFilter.getPost());
        leadersIdModel.setName("");
        leadersIdModel.setTab_serial(leaderFilter.getTab_serial());

        leaderHandler.getFilterCandidates(leadersIdModel);
    }


    @Override
    public void onFollowClick(int id, int position) {
        temp_follow_position = position;
        followHandler.followCandidate(id);

    }

    @Override
    public void onUnFollowClick(int id, int position) {
        temp_unfollow_position = position;
        followHandler.unFollowCandidate(id);
    }

    @Override
    public void onLeaderNewsClick(int position, AllLeaderModel allLeaderModel) {
        Intent mIntent = new Intent(getActivity(), NewLeaderProfileActivity.class);
        mIntent.putExtra(Constant.IntentKey.LEADER_PROFILE_ID, Integer.valueOf(allLeaderModel.getId()));
        mIntent.putExtra(Constant.IntentKey.LEADER_NAME, allLeaderModel.getName());
        mIntent.putExtra(Constant.IntentKey.LEADER_IMAGE, allLeaderModel.getProfileImage());
        mIntent.putExtra(Constant.IntentKey.LEADER_POSITION, allLeaderModel.getPosition());
        mIntent.putExtra(Constant.IntentKey.LEADER_LOCATION, allLeaderModel.getLocation());
        mIntent.putExtra(Constant.IntentKey.LEADER_PARTY, allLeaderModel.getPartyCode());
        mIntent.putExtra(Constant.IntentKey.LEADER_PART_LOGO, allLeaderModel.getPartyLogo());
        mIntent.putExtra(Constant.IntentKey.LEADER_FOLLOW, allLeaderModel.getFollowers());
        mIntent.putExtra(Constant.IntentKey.LEADER_IS_FOLLOW, allLeaderModel.getIsFollowing());
        mIntent.putExtra(Constant.IntentKey.IS_VERIFY, allLeaderModel.getIsVerify());
        mIntent.putExtra(Constant.IntentKey.FROM, Constant.IntentKey.LEADER_NEWS);
        mIntent.putExtra(Constant.IntentKey.LEADER_PANEL, allLeaderModel.getPanel_activated());
        mIntent.putExtra(Constant.IntentKey.ACTION, allLeaderModel.getUser_vote_action());
        mIntent.putExtra(Constant.IntentKey.UPVOTE, allLeaderModel.getLike_count());
        mIntent.putExtra(Constant.IntentKey.DOWNVOTE, allLeaderModel.getDislike_count());
        mIntent.putExtra(Constant.IntentKey.POSITION, position);

        startActivityForResult(mIntent, DETAIL_PROFILE);
    }

    @Override
    public void onLeaderClick(int position, AllLeaderModel allLeaderModel) {
        Intent mIntent = new Intent(getActivity(), NewLeaderProfileActivity.class);

        mIntent.putExtra(Constant.IntentKey.LEADER_PROFILE_ID, Integer.valueOf(allLeaderModel.getId()));
        mIntent.putExtra(Constant.IntentKey.LEADER_NAME, allLeaderModel.getName());
        mIntent.putExtra(Constant.IntentKey.LEADER_IMAGE, allLeaderModel.getProfileImage());
        mIntent.putExtra(Constant.IntentKey.LEADER_POSITION, allLeaderModel.getPosition());
        mIntent.putExtra(Constant.IntentKey.LEADER_LOCATION, allLeaderModel.getLocation());
        mIntent.putExtra(Constant.IntentKey.LEADER_PARTY, allLeaderModel.getPartyCode());
        mIntent.putExtra(Constant.IntentKey.LEADER_PART_LOGO, allLeaderModel.getPartyLogo());
        mIntent.putExtra(Constant.IntentKey.IS_VERIFY, allLeaderModel.getIsVerify());
        mIntent.putExtra(Constant.IntentKey.LEADER_PANEL, allLeaderModel.getPanel_activated());
        mIntent.putExtra(Constant.IntentKey.LEADER_FOLLOW, allLeaderModel.getFollowers());
        mIntent.putExtra(Constant.IntentKey.LEADER_IS_FOLLOW, allLeaderModel.getIsFollowing());
        mIntent.putExtra(Constant.IntentKey.ACTION, allLeaderModel.getUser_vote_action());
        mIntent.putExtra(Constant.IntentKey.UPVOTE, allLeaderModel.getLike_count());
        mIntent.putExtra(Constant.IntentKey.DOWNVOTE, allLeaderModel.getDislike_count());
        mIntent.putExtra(Constant.IntentKey.POSITION, position);

        startActivityForResult(mIntent, DETAIL_PROFILE);
    }

    @Override
    public void onLikeDisClick(int position, int candidate_id, int action) {
        adapter_position = position;
        likeAction = action;
        candidateId = candidate_id;

        if (PrefUtil.getString(Constant.PreferenceKey.AUTH_KEY).isEmpty()) {
            showLoginDialog();
        } else followHandler.onLeaderAction(candidate_id, action);
    }

    private void showLoginDialog() {
        DialogFragment dialogFragment = LoginDialogFragment.getInstance();
        dialogFragment.setTargetFragment(this, LOGINDIALOG);
        dialogFragment.show(getFragmentManager(), getString(R.string.dialog_fragment));
    }


    private void perfromActionOnLogin() {

        followHandler.onLeaderAction(candidateId, likeAction);
    }

    @Override
    public void onDeleteClick(int position, int candidate_id) {
        adapter_position = position;

        followHandler.onDeleteLeaderAction(candidate_id);
    }


    @Override
    public void onRefereshClick() {
        if (isSearch) {
            LeadersIdModel leadersIdModel = leaderAdapter.getLeadersId();
            leadersIdModel.setPage(pageNo);
            leadersIdModel.setName(search_text);
            leaderHandler.getFilterCandidates(leadersIdModel);
            //  leaderHandler.searchCandidate(pageNo, search_text, leaderAdapter.getLeadersId());
        } else {
            createServerRequest(Constant.RequestTag.LEADER_NEWS);
        }
        main_content.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNetworkAvailable() {
        onRefereshClick();
    }

    @Override
    public void onLoadMore(int totalItem) {
        if (loadMore && newsApiCall) {
            if (loading_grid.getVisibility() == View.GONE) {
                loading_grid.setVisibility(View.VISIBLE);
            }
            newsApiCall = false;
            leaderFilter.setPage(pageNo);
            if (isSearch) {
                LeadersIdModel leadersIdModel = leaderAdapter.getLeadersId();
                leadersIdModel.setPage(pageNo);
                leadersIdModel.setName(search_text);
                leaderHandler.getFilterCandidates(leadersIdModel);
                //leaderHandler.searchCandidate(pageNo, search_text, leaderAdapter.getLeadersId());
            } else {
                createServerRequest(Constant.RequestTag.LEADER_NEWS);
            }
        }
    }
}

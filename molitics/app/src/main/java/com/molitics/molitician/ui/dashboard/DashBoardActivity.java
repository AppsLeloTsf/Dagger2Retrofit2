package com.molitics.molitician.ui.dashboard;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.UnderlineSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.google.android.material.navigation.NavigationView;
import com.molitics.molitician.BR;
import com.molitics.molitician.BaseActivity;
import com.molitics.molitician.GoogleAnalytics.GAEventTracking;
import com.molitics.molitician.MolticsApplication;
import com.molitics.molitician.R;
import com.molitics.molitician.UiMediator.Loader;
import com.molitics.molitician.database.Database;
import com.molitics.molitician.databinding.ActivityDashBoardBinding;
import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.interfaces.InitializeServerRequest;
import com.molitics.molitician.interfaces.ViewRefreshListener;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.model.Data;
import com.molitics.molitician.model.News;
import com.molitics.molitician.ui.dashboard.articles.ArticleActivity;
import com.molitics.molitician.ui.dashboard.branch_deep_link.BranchShareClass;
import com.molitics.molitician.ui.dashboard.channels.ChannelListActivity;
import com.molitics.molitician.ui.dashboard.constantData.ConstantModel;
import com.molitics.molitician.ui.dashboard.constantData.ConstantServerDataHandler;
import com.molitics.molitician.ui.dashboard.constantData.ConstantServerDataPresenter;
import com.molitics.molitician.ui.dashboard.dashboard.dashboardView.DashBoardViewModel;
import com.molitics.molitician.ui.dashboard.language.LanguageActivity;
import com.molitics.molitician.ui.dashboard.leader.LeaderParentView;
import com.molitics.molitician.ui.dashboard.leader.newleaderprofile.NewLeaderProfileActivity;
import com.molitics.molitician.ui.dashboard.login.TermConditionDialog;
import com.molitics.molitician.ui.dashboard.more.InviteToContactActivity;
import com.molitics.molitician.ui.dashboard.more.MoreTabFragment;
import com.molitics.molitician.ui.dashboard.newsBookmark.NewsBookMarkView;
import com.molitics.molitician.ui.dashboard.newsDetail.DetailNewsActivity;
import com.molitics.molitician.ui.dashboard.notification.NotificationActivity;
import com.molitics.molitician.ui.dashboard.survey.SurveyActivity;
import com.molitics.molitician.ui.dashboard.survey.SurveyViewPagerFragment;
import com.molitics.molitician.ui.dashboard.survey.model.AllSurveyModel;
import com.molitics.molitician.ui.dashboard.termCondition.TermConditionActivity;
import com.molitics.molitician.ui.dashboard.voice.HomeFeedFragment;
import com.molitics.molitician.ui.dashboard.voice.ImageBigFragment;
import com.molitics.molitician.ui.dashboard.voice.VoiceCreatorProfile;
import com.molitics.molitician.ui.dashboard.voice.voiceDetail.VoiceDetailActivity;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.DialogClass;
import com.molitics.molitician.util.MoliticsAppPermission;
import com.molitics.molitician.util.PrefUtil;
import com.molitics.molitician.util.Util;
import com.yandex.metrica.YandexMetrica;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.branch.referral.Branch;

import static com.molitics.molitician.util.Constant.Language.LANGUAGE_SELECTION;
import static com.molitics.molitician.util.Constant.SUCCESS_RESULT;
import static com.molitics.molitician.util.MoliticsAppPermission.CONTACT_PERMISSION_CODE;


public class DashBoardActivity extends BaseActivity<ActivityDashBoardBinding, DashBoardViewModel> implements View.OnClickListener,
        InitializeServerRequest, ConstantServerDataPresenter.ConstantData, BranchShareClass.DeepLinkCallBack,
        NavigationView.OnNavigationItemSelectedListener, ViewRefreshListener {

    String TAG = "DashBoardActivity";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.home_txt)
    TextView home_txt;
    @BindView(R.id.leader_tab)
    TextView leader_tab;
    @BindView(R.id.more_tab)
    TextView more_tab;
//    @BindView(R.id.cartoon_view)
//    ImageView cartoon_view;
    @BindView(R.id.user_profile_image_view)
    ImageView user_profile_image_view;
    @BindView(R.id.navigation_view)
    NavigationView navigation_view;
/*    @BindView(R.id.drawer_layout)
    DrawerLayout drawer_layout;*/
    @BindView(R.id.verified_layout)
    RelativeLayout verified_layout;



    private boolean doubleBackToExitPressedOnce = false;
    private ConstantServerDataHandler constantServerDataHandler;
    public static List<ConstantModel> state_list = null;
    public static List<ConstantModel> post_list = new ArrayList<>();
    public static Long server_time = 12345678910l;

    @Override
    public void onStart() {
        super.onStart();
        Branch.getInstance().userCompletedAction(getString(R.string.install));
        Branch.getInstance().setIdentity(String.valueOf(PrefUtil.getInt(Constant.PreferenceKey.USER_ID)));
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_dash_board;
    }

    @Override
    public DashBoardViewModel getViewModel() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        setActionBar();
        checkTermCondition();

        setCustomNavigationDrawer();
        // set youtube key to base application so that can use anywhere . this is for if user launched application and then login
        if (TextUtils.isEmpty(MolticsApplication.YOUTUBE_KEY))
            MolticsApplication.YOUTUBE_KEY = PrefUtil.getString(Constant.PreferenceKey.API_KEY);

        constantServerDataHandler = new ConstantServerDataHandler(this);
        createServerRequest(Constant.RequestTag.STATE_LIST);
        home_txt.setOnClickListener(this);
        leader_tab.setOnClickListener(this);
        more_tab.setOnClickListener(this);
        replaceFragment(new HomeFeedFragment(), null, false, false);
        Intent intent = getIntent();
        onNewIntent(intent);
        AWSMobileClient.getInstance().initialize(this).execute();
        checkLanguageSelection();
    }

    private void checkLanguageSelection() {
        String selectedLang = PrefUtil.getConstantString(Constant.PreferenceKey.LANGUAGE);
        if (TextUtils.isEmpty(selectedLang)) {
            Intent languageIntent = new Intent(this, LanguageActivity.class);
            languageIntent.putExtra(Constant.IntentKey.FROM, LANGUAGE_SELECTION);
            startActivity(languageIntent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        YandexMetrica.onResumeActivity(this);
        branchIntegration();
    }

    private void setCustomNavigationDrawer() {

        navigation_view.setNavigationItemSelectedListener(this);

        Menu menu = navigation_view.getMenu();
        SwitchCompat actionView = (SwitchCompat) menu.findItem(R.id.nav_notification).getActionView().findViewById(R.id.switch_button);
        SwitchCompat darkNavView = (SwitchCompat) menu.findItem(R.id.nav_day_night_mode).getActionView().findViewById(R.id.switch_button);
        actionView.setOnClickListener(v -> {
            if (PrefUtil.getInt(Constant.PreferenceKey.NOTIFICATION_ACTIVE) == 1) {
                PrefUtil.putInt(Constant.PreferenceKey.NOTIFICATION_ACTIVE, 0);
            } else {
                PrefUtil.putInt(Constant.PreferenceKey.NOTIFICATION_ACTIVE, 1);
            }
        });
        actionView.setChecked(PrefUtil.getInt(Constant.PreferenceKey.NOTIFICATION_ACTIVE) == 1);

        darkNavView.setOnClickListener(v -> {
            if (PrefUtil.getInt(Constant.PreferenceKey.DARK_MODE_ACTIVE) == 1) {
                PrefUtil.putInt(Constant.PreferenceKey.DARK_MODE_ACTIVE, 0);
            } else {
                PrefUtil.putInt(Constant.PreferenceKey.DARK_MODE_ACTIVE, 1);
            }

            Intent dashBoardIntent = getIntent();
            dashBoardIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            dashBoardIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            finishAffinity();
            startActivity(dashBoardIntent);
        });
        if (PrefUtil.getInt(Constant.PreferenceKey.DARK_MODE_ACTIVE) == 1) {
            darkNavView.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            darkNavView.setChecked(false);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        moliticsPlusLink(menu);
    }

    private void relaunchActivity() {
        Intent intent = new Intent(this, DashBoardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void moliticsPlusLink(Menu menu) {

        TextView sign_up_actionView = (TextView) menu.findItem(R.id.nav_mPlus_signup).getActionView().findViewById(R.id.text_view);

        Spannable span = new SpannableString(getString(R.string.are_you_leader));
        span.setSpan(new RelativeSizeSpan(0.8f), getString(R.string.are_you_leader).length(), span.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        span.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.black)), getString(R.string.are_you_leader).length(), span.length(), 0);// set color
        // underline text
        span.setSpan(new UnderlineSpan(), getString(R.string.are_you_leader).length(), span.length(), 0);
        sign_up_actionView.setText(span);

        sign_up_actionView.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.molitics.net/"));
            startActivity(i);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem itemMessages = menu.findItem(R.id.notification_menu);
        setNotificationBadge(itemMessages);
        return super.onCreateOptionsMenu(menu);
    }

    private void setNotificationBadge(MenuItem itemMessages) {
        View view = itemMessages.getActionView();

        LinearLayout noti_ll_laout = view.findViewById(R.id.noti_ll_laout);
        TextView itemMessagesBadgeTextView = view.findViewById(R.id.hotlist_hot);

        noti_ll_laout.setOnClickListener(view1 -> {
            Intent mIntent = new Intent(DashBoardActivity.this, NotificationActivity.class);
            mIntent.putExtra(Constant.IntentKey.NOTIFICATION_TITLE, getString(R.string.notification_txt));
            startActivityForResult(mIntent, Constant.ActivityRequestKey.NOTIFICATION_KEY);
        });
        itemMessagesBadgeTextView.setVisibility(View.GONE);
        int temp_count = PrefUtil.getInt(Constant.PreferenceKey.NOTIFICATION_COUNT);
        if (temp_count > 0) {
            itemMessagesBadgeTextView.setVisibility(View.VISIBLE);
            itemMessagesBadgeTextView.setText(String.valueOf(temp_count));
        } else {
            itemMessagesBadgeTextView.setVisibility(View.GONE);
        }
    }

    public void setActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
    }

    @Override
    public void createServerRequest(int tag) {
        switch (tag) {
            case Constant.RequestTag.STATE_LIST:
                constantServerDataHandler.getStateList();
                break;
            case Constant.RequestTag.FRAGMENT_LIST:
                Loader.showMyDialog(this);
                constantServerDataHandler.getActiveFragment();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onActiveFragmentResponse(Data mData) {
        Loader.dismissMyDialog(this);
        //fragmentListModel = mData.getFragmentListModel();
        server_time = mData.getServerDetails().getServer_time();
    }

    @Override
    public void onStateResponse(APIResponse apiResponse) {
        Loader.dismissMyDialog(this);
        if (apiResponse.getStatus() == 2000) {
            state_list = apiResponse.getData().getState_list();
            post_list = apiResponse.getData().getFilter_post_list();
        }
    }

    @Override
    public void onStateApiException(ApiException apiException) {
    }

    @Override
    public void onStateServerException(ServerException serverException) {
        DialogClass.showAlert(this, getString(R.string.something_went_wrong));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

/*    @OnClick(R.id.cartoon_view)
    public void onCarttonCornerClick() {
        Intent mIntent = new Intent(this, ActivityFragment.class);
        mIntent.putExtra(Constant.INTENT_FROM, Constant.From.FROM_CARICATURE);
        startActivityForResult(mIntent, Constant.ActivityRequestKey.CARTOON_KEY);
    }*/

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, getString(R.string.click_back_to_exit), Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent mIntent;
        switch (id) {
            case R.id.action_survey:
                PrefUtil.putInt(Constant.PreferenceKey.NOTIFICATION_SURVEY_COUNT, 0);
                mIntent = new Intent(this, SurveyActivity.class);
                startActivityForResult(mIntent, Constant.ActivityRequestKey.SURVEY_KEY);
                //closeDrawer();

                setGAEvent(Constant.GoogleAnalyticsKey.SIDE_DRAWER, Constant.GoogleAnalyticsKey.SURVEY_CLICK);
                break;
            case R.id.term_condition:
                mIntent = new Intent(DashBoardActivity.this, TermConditionActivity.class);
                mIntent.putExtra(Constant.IntentKey.TOOL_BAR, R.string.terms_condition);
                mIntent.putExtra(Constant.IntentKey.URL, getString(R.string.term_condition_url));
                startActivity(mIntent);
                //closeDrawer();
                break;
            case R.id.news_bookmark:
                startActivity(new Intent(DashBoardActivity.this, NewsBookMarkView.class));
                //closeDrawer();
                break;

            case R.id.nav_invite:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, Constant.MOLITICS_BRANCHLINK);
                sendIntent.setType("text/plain");
               // closeDrawer();

                startActivity(Intent.createChooser(sendIntent, getString(R.string.share_to)));
                break;
            case R.id.log:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.logout_confirm_txt)
                        .setCancelable(false)
                        .setPositiveButton(getString(R.string.yes), (dialog, id1) -> Util.logout(DashBoardActivity.this))
                        .setNegativeButton(getString(R.string.no), (dialog, id12) -> {
                            //  Action for 'NO' Button
                            dialog.cancel();
                        });
                AlertDialog alert = builder.create();
                alert.setTitle(getString(R.string.log_out));
                alert.show();
                break;

            case R.id.action_channel:
                startActivity(new Intent(DashBoardActivity.this, ChannelListActivity.class));
                //closeDrawer();

                break;
            case R.id.userProfileView:
                startActivity(new Intent(DashBoardActivity.this, VoiceCreatorProfile.class));
                break;
            case R.id.article:
                startActivity(new Intent(this, ArticleActivity.class));
                break;
            case R.id.inviteContact:
                if (MoliticsAppPermission.contactPermission(this)) {
                    startActivity(new Intent(this, InviteToContactActivity.class));
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, CONTACT_PERMISSION_CODE);
                    }
                }
                break;
            case R.id.changeLanguage:
                Intent languageIntent = new Intent(this, LanguageActivity.class);
                languageIntent.putExtra(Constant.IntentKey.FROM, LANGUAGE_SELECTION);
                startActivity(languageIntent);
                break;
           /* case R.id.videosDraw:
                startActivity(new Intent(this, VideoActivity.class));
                break;*/
            case R.id.about_us:
                Util.openBrowser(this, getString(R.string.about_us_url));
                break;

        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == SUCCESS_RESULT) {
            if (requestCode == Constant.ActivityRequestKey.PROFILE_KEY) {
                Database.deleteLocalAllNews();
            } else if (requestCode == Constant.ActivityRequestKey.NOTIFICATION_KEY) {
                invalidateOptionsMenu();
            }
            if (requestCode == Constant.ActivityRequestKey.CARTOON_KEY) {
                invalidateOptionsMenu();
            }
            invalidateOptionsMenu();
        }
    }

  /*  private void closeDrawer() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }*/

    @Override
    public void onRefereshClick() {
        createServerRequest(Constant.RequestTag.STATE_LIST);
        createServerRequest(Constant.RequestTag.FRAGMENT_LIST);
    }

    @Override
    public void onNetworkAvailable() {
        onRefereshClick();
    }

    private void startDetailNews(int news_id, int display_type) {
        ArrayList<News> send_news_list = new ArrayList<>();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.IntentKey.NEWS_LIST, send_news_list);
        Intent newsIntent = new Intent(DashBoardActivity.this, DetailNewsActivity.class);
        newsIntent.putExtra(Constant.IntentKey.NEWS_POSITION, 0);
        newsIntent.putExtra(Constant.IntentKey.DISPLAY_TYPE, display_type);
        newsIntent.putExtra(Constant.IntentKey.NEWS_ID, news_id);
        newsIntent.putExtras(bundle);
        startActivity(newsIntent);
    }


    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
        HashMap<String, String> gcmbundle = (HashMap<String, String>) intent.getSerializableExtra(Constant.IntentKey.NOTIFICATION_DATA);
        if (gcmbundle != null) {
            String type = gcmbundle.get(Constant.IntentKey.NOTIFICATION_TYPE);
            if (type != null) {
                FragmentManager fm = getSupportFragmentManager();

                switch (Integer.parseInt(type)) {

                    case Constant.NotificationType.NEWS_NOTI:
                        int news_id = Integer.valueOf(gcmbundle.get(Constant.ShareLinkTag.NEWS));
                        startDetailNews(news_id, 1);
                        break;

                    case Constant.NotificationType.LEADER_DETAIL:
                        Intent mIntent = new Intent(this, NewLeaderProfileActivity.class);
                        int candidate_id = Integer.valueOf(gcmbundle.get(Constant.NotificationPayLoad.NOTIFICATION_CANDIDATE_ID));
                        mIntent.putExtra(Constant.IntentKey.LEADER_PROFILE_ID, candidate_id);
                        mIntent.putExtra(Constant.IntentKey.FROM, Constant.IntentKey.NOTIFICATION);
                        startActivity(mIntent);
                        break;

                    case Constant.NotificationType.SURVEY_NOTI:
                        /// SurveyModel
                        ArrayList<AllSurveyModel> survey_array_list = new ArrayList<>();
                        AllSurveyModel newsSurvey = new AllSurveyModel();
                        newsSurvey.setId(Integer.valueOf(gcmbundle.get(Constant.NotificationPayLoad.NOTIFICATION_SURVEY_ID)));
                        survey_array_list.add(newsSurvey);
                        SurveyViewPagerFragment dialogFragment = (SurveyViewPagerFragment) SurveyViewPagerFragment.getInstance(0, survey_array_list);
                        dialogFragment.show(fm, getString(R.string.dialog_fragment));
                        break;
                    case Constant.NotificationType.VOICE_NOTI:
                        int voice_id = Integer.valueOf(gcmbundle.get(Constant.NotificationPayLoad.VOICE_ID));
                        Intent voiceIntent = new Intent(DashBoardActivity.this, VoiceDetailActivity.class);
                        voiceIntent.putExtra(Constant.IntentKey.VOICE_ID, voice_id);
                        startActivity(voiceIntent);
                        break;

                    case Constant.NotificationType.ARTICLE_NOTI:
                        if (gcmbundle.get(Constant.ShareLinkTag.NEWS) != null) {
                            int article_id = Integer.valueOf(gcmbundle.get(Constant.ShareLinkTag.NEWS));
                            startDetailNews(article_id, 4);
                        }
                        break;

                    case Constant.NotificationType.CARTOON_NOTI:
                        String image_url = gcmbundle.get(Constant.NotificationPayLoad.CARTOON_IMAGE);

                        ArrayList<String> strings = new ArrayList<>();
                        strings.add(image_url);
                        ImageBigFragment imageBigFragment = (ImageBigFragment) ImageBigFragment.getInstance(Constant.ZERO, Constant.ZERO, strings, null);
                        imageBigFragment.show(fm, getString(R.string.dialog_fragment));
                        break;
                    default:
                        break;
                }
            }
        }
    }

    //replace fragment for main pages
    public void replaceFragment(Fragment fragment, Bundle bundle) {
        if (bundle != null) fragment.setArguments(bundle);
        String tag = fragment.getClass().getSimpleName();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.replace(R.id.state_main_container, fragment, tag);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragment.setRetainInstance(true);
        try {
            ft.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            ft.commitAllowingStateLoss();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.home_txt:
                verified_layout.setVisibility(View.VISIBLE);
                setGAEvent(Constant.GoogleAnalyticsKey.VOICE_TAB, Constant.GoogleAnalyticsKey.CLICK);
                replaceFragment(new HomeFeedFragment(), null, false, false);
                home_txt.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_home_active, 0, 0);
                leader_tab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_leader_inactive, 0, 0);
                more_tab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_more_inactive, 0, 0);

                home_txt.setTextColor(getResources().getColor(R.color.dashboard_bottom_selected));
                leader_tab.setTextColor(getResources().getColor(R.color.dashboard_bottom_unselected));
                more_tab.setTextColor(getResources().getColor(R.color.dashboard_bottom_unselected));

                break;

            case R.id.leader_tab:
                verified_layout.setVisibility(View.GONE);
                setGAEvent(Constant.GoogleAnalyticsKey.LEADER_TAB, Constant.GoogleAnalyticsKey.CLICK);
                replaceFragment(new LeaderParentView(), null, false, false);
                home_txt.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_home_inactive, 0, 0);
                leader_tab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_leader_active, 0, 0);
                more_tab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_more_inactive, 0, 0);

                home_txt.setTextColor(getResources().getColor(R.color.dashboard_bottom_unselected));
                leader_tab.setTextColor(getResources().getColor(R.color.dashboard_bottom_selected));
                more_tab.setTextColor(getResources().getColor(R.color.dashboard_bottom_unselected));

                break;

            case R.id.more_tab:
                verified_layout.setVisibility(View.GONE);
                setGAEvent(Constant.GoogleAnalyticsKey.VOICE_TAB, Constant.GoogleAnalyticsKey.CLICK);
                replaceFragment(new MoreTabFragment(), null, false, false);
                more_tab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_more_active, 0, 0);
                home_txt.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_home_inactive, 0, 0);
                leader_tab.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_leader_inactive, 0, 0);

                more_tab.setTextColor(getResources().getColor(R.color.dashboard_bottom_selected));
                home_txt.setTextColor(getResources().getColor(R.color.dashboard_bottom_unselected));
                leader_tab.setTextColor(getResources().getColor(R.color.dashboard_bottom_unselected));
                break;

        }
    }

    //replace fragment for main pages
    public void replaceFragment(Fragment fragment, Bundle bundle, boolean addToBackStack, boolean anim) {
        if (bundle != null)
            fragment.setArguments(bundle);
        String tag = fragment.getClass().getSimpleName();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.home_container, fragment, tag);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragment.setRetainInstance(false);
        if (addToBackStack)
            ft.addToBackStack(tag);
        try {
            ft.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            ft.commitAllowingStateLoss();
        }
    }


    ///// deep link
    private void branchIntegration() {
        Branch.getInstance().initSession((referringParams, error) -> {
            if (error == null) {
                String survey_id = referringParams.optString(Constant.ShareLinkTag.SURVEY);
                if (!TextUtils.isEmpty(survey_id)) {
                    ////open survey
                    AllSurveyModel surveyModel = new AllSurveyModel();
                    surveyModel.setId(Integer.valueOf(survey_id));
                    ArrayList<AllSurveyModel> survey_array_list = new ArrayList<>();
                    survey_array_list.add(surveyModel);
                    openSurveyDialog(0, survey_array_list);
                }
                String news_id = referringParams.optString(Constant.ShareLinkTag.NEWS);
                String display_type = referringParams.optString(Constant.IntentKey.DISPLAY_TYPE);


                if (!TextUtils.isEmpty(news_id)) {
                    ////open news
                    ArrayList<News> send_news_list = new ArrayList<>();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constant.IntentKey.NEWS_LIST, send_news_list);
                    Intent intent = new Intent(DashBoardActivity.this, DetailNewsActivity.class);
                    intent.putExtra(Constant.IntentKey.NEWS_POSITION, 0);
                    intent.putExtra(Constant.IntentKey.NEWS_ID, Integer.valueOf(news_id));
                    intent.putExtra(Constant.IntentKey.DISPLAY_TYPE, Integer.valueOf(display_type));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

                String voice_id = referringParams.optString(Constant.ShareLinkTag.VOICE);
                if (!TextUtils.isEmpty(voice_id)) {
                    Intent voiceIntent = new Intent(DashBoardActivity.this, VoiceDetailActivity.class);
                    voiceIntent.putExtra(Constant.IntentKey.VOICE_ID, Integer.valueOf(voice_id));
                    startActivity(voiceIntent);
                }

                String leader_id = referringParams.optString(Constant.ShareLinkTag.LEADER);
                if (!TextUtils.isEmpty(leader_id)) {
                    Intent mIntent = new Intent(DashBoardActivity.this, NewLeaderProfileActivity.class);
                    mIntent.putExtra(Constant.IntentKey.LEADER_PROFILE_ID, Integer.valueOf(leader_id));
                    mIntent.putExtra(Constant.IntentKey.FROM, Constant.IntentKey.NOTIFICATION);
                    startActivity(mIntent);
                }
            }
        }, this.getIntent().getData(), this);

    }

    private void openSurveyDialog(int position, ArrayList<AllSurveyModel> allSurveyModels) {
        FragmentManager fm = getSupportFragmentManager();
        SurveyViewPagerFragment.getInstance(position, allSurveyModels).show(fm, getString(R.string.dialog_fragment));
        // Show DialogFragment
    }

    private void handleIntent(Intent intent) {
        Uri appLinkData = intent.getData();
        openDeepLink(appLinkData);
    }

    //deep link custom url not branch
    private void openDeepLink(Uri data) {
        if (data != null) {
            String deepLink = data.toString();
            int index = deepLink.lastIndexOf('/');
            String urlSubString = deepLink.substring(0, index);

            if (!TextUtils.isEmpty(urlSubString)) {
                if (urlSubString.contains(Constant.ShareLinkTag.NEWS)) {
                    startDetailNews(getIdFromUrl(urlSubString), 1);
                } else if (urlSubString.contains(Constant.ShareLinkTag.ARTICLE)) {
                    startDetailNews(getIdFromUrl(urlSubString), 4);
                } else if (urlSubString.contains(Constant.ShareLinkTag.LEADER)) {
                    Intent mIntent = new Intent(DashBoardActivity.this, NewLeaderProfileActivity.class);
                    mIntent.putExtra(Constant.IntentKey.LEADER_PROFILE_ID, getIdFromUrl(urlSubString));
                    mIntent.putExtra(Constant.IntentKey.FROM, Constant.IntentKey.NOTIFICATION);
                    startActivity(mIntent);
                } else if (urlSubString.contains("user-feed") || urlSubString.contains("raise-your-voice")) {
                    Intent voiceIntent = new Intent(DashBoardActivity.this, VoiceDetailActivity.class);
                    voiceIntent.putExtra(Constant.IntentKey.VOICE_ID, getIdFromUrl(deepLink));
                    startActivity(voiceIntent);
                } else if (urlSubString.contains(Constant.ShareCampaign.SURVEY)) {
                    AllSurveyModel surveyModel = new AllSurveyModel();
                    surveyModel.setId(getIdFromUrl(urlSubString));
                    ArrayList<AllSurveyModel> survey_array_list = new ArrayList<>();
                    survey_array_list.add(surveyModel);
                    openSurveyDialog(0, survey_array_list);
                }
            }
        }
    }

    private int getIdFromUrl(String type) {
        return Integer.parseInt(type.replaceFirst(".*//*(\\w+).*", "$1"));
    }

    private void setGAEvent(String category, String action) {
        GAEventTracking.googleEventTracker(this, category, action);
    }

    @Override
    public void onGenerateShareLink(String full_txt, String url) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, url);
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, getString(R.string.share_to)));
    }

    private void checkTermCondition() {

        if (!PrefUtil.getBoolean(Constant.PreferenceKey.IS_USER_AGREE_TERM_CONDITION)) {
            TermConditionDialog conditionDialog = new TermConditionDialog(this);
            conditionDialog.show();
        }
    }
}

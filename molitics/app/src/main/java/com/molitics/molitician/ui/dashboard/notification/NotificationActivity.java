package com.molitics.molitician.ui.dashboard.notification;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.molitics.molitician.BasicAcivity;
import com.molitics.molitician.R;
import com.molitics.molitician.database.Database;
import com.molitics.molitician.database.NotificatoinModel;
import com.molitics.molitician.error.ErrorViewHandler;
import com.molitics.molitician.listener.RecyclerItemClickListener;
import com.molitics.molitician.model.News;
import com.molitics.molitician.ui.dashboard.leader.newleaderprofile.NewLeaderProfileActivity;
import com.molitics.molitician.ui.dashboard.newsDetail.DetailNewsActivity;
import com.molitics.molitician.ui.dashboard.survey.model.AllSurveyModel;
import com.molitics.molitician.ui.dashboard.survey.SurveyViewPagerFragment;
import com.molitics.molitician.ui.dashboard.voice.ImageBigFragment;
import com.molitics.molitician.ui.dashboard.voice.voiceDetail.VoiceDetailActivity;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.PrefUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.molitics.molitician.error.ErrorViewHandler.errorType.NO_NOTIFICATION;
import static com.molitics.molitician.util.Constant.ARTICLE;
import static com.molitics.molitician.util.Constant.IntentKey.NEWS_POSITION;
import static com.molitics.molitician.util.Constant.NEWS;
import static com.molitics.molitician.util.Constant.SUCCESS_RESULT;
import static com.molitics.molitician.util.Constant.ZERO;

/**
 * Created by rahul on 1/10/2017.
 */

public class NotificationActivity extends BasicAcivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.notification_recycler)
    RecyclerView notification_recycler;
    @BindView(R.id.notification_frame_layout)
    FrameLayout notification_frame_layout;

    private List<NotificatoinModel> notification_data = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_notification);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        showHeader(true, getResources().getString(R.string.notification_header));
        toolbar.setNavigationOnClickListener(v -> finish());
        PrefUtil.putInt(Constant.PreferenceKey.NOTIFICATION_COUNT, 0);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        notification_recycler.setLayoutManager(mLayoutManager);
        initializeRecyclerView();
        setResult(SUCCESS_RESULT);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void initializeRecyclerView() {
        int type = 0;
        notification_data = Database.readNotification();
        if (notification_data != null) {
            if (notification_data.isEmpty()) {
                notification_recycler.setVisibility(View.GONE);
                notification_frame_layout.setVisibility(View.VISIBLE);
                ErrorViewHandler.errorNoData(NO_NOTIFICATION, notification_frame_layout, this,
                        getString(R.string.no_notification_right_now), null);
            } else {
                notification_recycler.setVisibility(View.VISIBLE);
                notification_frame_layout.setVisibility(View.GONE);
                notification_frame_layout.removeAllViews();
                NotificationAdapter notification_adapter = new NotificationAdapter(this, notification_data, type);
                notification_recycler.setAdapter(notification_adapter);
                notification_recycler.addOnItemTouchListener(new RecyclerItemClickListener(this, (paramAnonymousView, position) -> {
                    particularNotification(position, notification_data.get(position).getNotification_type());
                }));
            }
        }
    }

    private void particularNotification(int position, String type) {
        try {
            JSONObject noti_json_object = new JSONObject(notification_data.get(position).getPayload());
            FragmentManager fm = getSupportFragmentManager();

            switch (Integer.parseInt(type)) {

                case Constant.NotificationType.NEWS_NOTI:
                    ArrayList<News> send_news_list = new ArrayList<>();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constant.IntentKey.NEWS_LIST, send_news_list);
                    Intent intent = new Intent(NotificationActivity.this, DetailNewsActivity.class);
                    intent.putExtra(NEWS_POSITION, ZERO);
                    int news_id = Integer.valueOf(noti_json_object.getString(Constant.NotificationPayLoad.NOTIFICATION_NEWS_ID));
                    intent.putExtra(Constant.IntentKey.NEWS_ID, news_id);
                    intent.putExtra(Constant.IntentKey.DISPLAY_TYPE, NEWS);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;

                case Constant.NotificationType.LEADER_DETAIL:
                    Intent mIntent = new Intent(NotificationActivity.this, NewLeaderProfileActivity.class);
                    int candidate_id = Integer.valueOf(noti_json_object.getString(Constant.NotificationPayLoad.NOTIFICATION_CANDIDATE_ID));
                    mIntent.putExtra(Constant.IntentKey.LEADER_PROFILE_ID, candidate_id);
                    mIntent.putExtra(Constant.IntentKey.FROM, Constant.IntentKey.NOTIFICATION);
                    startActivity(mIntent);
                    break;

                case Constant.NotificationType.SURVEY_NOTI:
                    ArrayList<AllSurveyModel> survey_array_list = new ArrayList<>();
                    AllSurveyModel newsSurvey = new AllSurveyModel();
                    newsSurvey.setId(Integer.valueOf(noti_json_object.getString(Constant.NotificationPayLoad.NOTIFICATION_SURVEY_ID)));
                    survey_array_list.add(newsSurvey);
                    SurveyViewPagerFragment.getInstance(ZERO, survey_array_list).show(fm, "Dialog Fragment");
                    // Show DialogFragment
                    break;

                case Constant.NotificationType.VOICE_NOTI:
                    int voice_id = Integer.valueOf(noti_json_object.getString(Constant.NotificationPayLoad.VOICE_ID));
                    Intent voiceIntent = new Intent(this, VoiceDetailActivity.class);
                    voiceIntent.putExtra(Constant.IntentKey.VOICE_ID, voice_id);
                    startActivity(voiceIntent);
                    break;

                case Constant.NotificationType.ARTICLE_NOTI:
                    ArrayList<News> send_article_list = new ArrayList<>();
                    Bundle article_bundle = new Bundle();
                    article_bundle.putSerializable(Constant.IntentKey.NEWS_LIST, send_article_list);
                    Intent intent13 = new Intent(NotificationActivity.this, DetailNewsActivity.class);
                    intent13.putExtra(NEWS_POSITION, 0);
                    int article_id = Integer.valueOf(noti_json_object.getString(Constant.NotificationPayLoad.NOTIFICATION_NEWS_ID));
                    intent13.putExtra(Constant.IntentKey.NEWS_ID, article_id);
                    intent13.putExtra(Constant.IntentKey.DISPLAY_TYPE, ARTICLE);
                    intent13.putExtras(article_bundle);
                    startActivity(intent13);
                    break;

                case Constant.NotificationType.CARTOON_NOTI:
                    String cartoon_image = noti_json_object.getString(Constant.NotificationPayLoad.CARTOON_IMAGE);
                    ArrayList<String> strings = new ArrayList<>();
                    strings.add(cartoon_image);
                    ImageBigFragment.getInstance(Constant.ZERO, Constant.ZERO, strings, null).show(fm, "Dialog Fragment");

                default:
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

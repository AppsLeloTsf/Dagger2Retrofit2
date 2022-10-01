package com.molitics.molitician.ui.dashboard.leader.newleaderprofile.leaderNews;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerView;
import com.molitics.molitician.R;
import com.molitics.molitician.ui.dashboard.branch_deep_link.BranchShareClass;
import com.molitics.molitician.ui.dashboard.leader.leaderProfile.CandidateProfileModel;
import com.molitics.molitician.ui.dashboard.leader.leaderProfile.Event;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.ExtraUtils;
import com.molitics.molitician.util.MoliticsAppPermission;
import com.molitics.molitician.util.ShareScreenShot;
import com.molitics.molitician.util.StringUtils;
import com.molitics.molitician.util.VideoExpoPlayer;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutLeaderFragment extends Fragment {

    @BindView(R.id.dob_view)
    TextView dob_view;
    @BindView(R.id.stateView)
    TextView stateView;
    @BindView(R.id.descriptionView)
    TextView descriptionView;
    @BindView(R.id.descriptionTxtView)
    TextView descriptionTxtView;
    @BindView(R.id.assemblyConstituencyView)
    TextView assemblyConstituencyView;
    @BindView(R.id.lokSabhaView)
    TextView lokSabhaView;
    @BindView(R.id.status_description)
    TextView status_description;

    @BindView(R.id.status_image)
    ImageView status_image;

    @BindView(R.id.fb_video_player)
    PlayerView fb_video_player;

    @BindView(R.id.video_mute_button)
    ImageView video_mute_button;
    @BindView(R.id.llEventView)
    LinearLayout llEventView;
    @BindView(R.id.upcomingTextView)
    TextView upcomingTextView;
    @BindView(R.id.todayEventDateView)
    TextView todayEventDateView;
    @BindView(R.id.status_title_view)
    TextView status_title_view;
    @BindView(R.id.eventRLView)
    RelativeLayout eventRLView;
    @BindView(R.id.shareEventView)
    ImageView shareEventView;

    private CandidateProfileModel candidateProfileModel;
    private SimpleExoPlayer player;
    private int VOICE_DESCRIPTION = 150;

    public static Fragment getInstance(Integer candidate_id, String name) {
        Fragment mFragment = new AboutLeaderFragment();
        Bundle mBundle = new Bundle();
        mBundle.putInt(Constant.IntentKey.LEADER_PROFILE_ID, candidate_id);
        mBundle.putString(Constant.IntentKey.LEADER_PROFILE_NAME, name);
        mFragment.setArguments(mBundle);
        return mFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_leader, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        if (candidateProfileModel != null) {
            dob_view.setText(String.format(getString(R.string.dob_string), TextUtils.isEmpty(candidateProfileModel.getDob()) ? "N/A" : candidateProfileModel.getDob()));
            stateView.setText(String.format(getString(R.string.state_string), TextUtils.isEmpty(candidateProfileModel.getState()) ? "N/A" : candidateProfileModel.getState()));
            assemblyConstituencyView.setText(String.format(getString(R.string.mla_string), candidateProfileModel.getMlaConstituency()));
            lokSabhaView.setText(String.format(getString(R.string.mp_string), candidateProfileModel.getMpConstituency()));
            descriptionView.setVisibility(View.VISIBLE);
            addReadMore(candidateProfileModel.getDescription());
            ///   descriptionTxtView.setText(TextUtils.isEmpty(candidateProfileModel.getDescription()) ? "N/A" : candidateProfileModel.getDescription());
            setStatus();
            if (!ExtraUtils.isListNullOrEmpty(candidateProfileModel.getEvents())) {
                addEventView(candidateProfileModel.getEvents());
            }
        }
    }

    @OnClick(R.id.descriptionTxtView)
    public void onReadMoreClick() {
        descriptionTxtView.setText(candidateProfileModel.getDescription());
    }

    public void setLeaderData(CandidateProfileModel candidateProfileModel) {
        this.candidateProfileModel = candidateProfileModel;
        updateUI();
    }

    private void setStatus() {

        if (!StringUtils.isNullOrEmpty(candidateProfileModel.getCandidate_status())) {
            status_description.setVisibility(View.VISIBLE);
            status_description.setText(candidateProfileModel.getCandidate_status());
        } else {
            status_description.setVisibility(View.GONE);
        }

        if (!StringUtils.isNullOrEmpty(candidateProfileModel.getStatus_url())) {
            if (candidateProfileModel.getStatus_url().contains(".mp4")) {
                fb_video_player.setVisibility(View.VISIBLE);
                if (player == null)
                    player = VideoExpoPlayer.prepareVideo(getContext(), fb_video_player,
                            Uri.parse(candidateProfileModel.getStatus_url()));
                onVideoMuteClick(video_mute_button);
                video_mute_button.setOnClickListener(v -> onVideoMuteClick(video_mute_button));

            } else {
                status_image.setVisibility(View.VISIBLE);
                Picasso.with(getContext()).load(candidateProfileModel.getStatus_url())
                        .placeholder(R.drawable.image_placeholder).error(R.drawable.error_placeholder).into(status_image);
            }
        } else {
            fb_video_player.setVisibility(View.GONE);
            status_image.setVisibility(View.GONE);
        }

        if (!StringUtils.isNullOrEmpty(candidateProfileModel.getCandidate_status()) || !StringUtils.isNullOrEmpty(candidateProfileModel.getStatus_url())) {
            status_title_view.setVisibility(View.VISIBLE);
            status_title_view.setText(getString(R.string.status));
        } else {
            status_title_view.setVisibility(View.GONE);
        }
    }

    private String getLeaderNameFromArgument() {
        if (getArguments() == null)
            return "";
        return getArguments().getString(Constant.IntentKey.LEADER_PROFILE_NAME);
    }

    private int getLeaderIdFromArgument() {
        if (getArguments() == null)
            return 0;
        return getArguments().getInt(Constant.IntentKey.LEADER_PROFILE_ID);
    }

    void onVideoMuteClick(ImageView video_mute_button) {
        if (player != null) {
            if (player.getVolume() == 1.0f) {
                player.setVolume(0.0f);
                video_mute_button.setImageResource(R.drawable.mute_white);
            } else {
                player.setVolume(1.0f);
                video_mute_button.setImageResource(R.drawable.volume_white);
            }
        }
    }

    private void addEventView(List<Event> eventList) {
        eventRLView.setVisibility(View.VISIBLE);
        llEventView.removeAllViews();

        upcomingTextView.setText("Today's schedule of " + candidateProfileModel.getName());
        for (int eventIndex = 0; eventIndex < eventList.size(); eventIndex++) {

            Event mEvent = eventList.get(eventIndex);
            View child = LayoutInflater.from(getContext()).inflate(R.layout.adapter_event, null);
            llEventView.addView(child);
            TextView event_heading = child.findViewById(R.id.event_heading);
            TextView event_location = child.findViewById(R.id.event_location);
            TextView event_date = child.findViewById(R.id.event_date);

            event_heading.setText(mEvent.getName());
            event_location.setText(mEvent.getAddress());
            event_date.setText(mEvent.getTime());
            if (eventIndex == 0)
                todayEventDateView.setText(mEvent.getDate());
        }
        shareEventView.setOnClickListener(view ->
                BranchShareClass.generateShareLink(getContext(), (full_txt, url) ->
                                ShareScreenShot.takeViewScreenshot(getContext(), eventRLView, url), candidateProfileModel.getName(), "",
                        "Leader", Constant.ShareLinkTag.LEADER, String.valueOf(getLeaderIdFromArgument()), "leader"));
    }

    private void addReadMore(String descr) {
        String finalDescription = descr.trim();

        if (descr.length() > VOICE_DESCRIPTION) {
            String local_txt = (descr.substring(0, VOICE_DESCRIPTION) + getString(R.string.read_more));
            SpannableStringBuilder des_spanable_string = new SpannableStringBuilder(local_txt);
            StyleSpan iss = new StyleSpan(android.graphics.Typeface.ITALIC); //Span to make text italic
            des_spanable_string.setSpan(iss, VOICE_DESCRIPTION, local_txt.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            des_spanable_string.setSpan(new ForegroundColorSpan(ResourcesCompat.getColor(getResources(), R.color.follow_leader, null)), VOICE_DESCRIPTION, local_txt.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            descriptionTxtView.setText(des_spanable_string);
        } else if (!TextUtils.isEmpty(finalDescription)) {
            descriptionTxtView.setText(finalDescription);
        }
    }

    public void onRemindMeClick(String time, String name, String address) {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getContext(), getString(R.string.following_event_added), Toast.LENGTH_SHORT).show();
            ReminderClickHelper.reminderEvent(getContext(), time, name, address);

        } else {
            MoliticsAppPermission.calenderPermission(getActivity());
        }
    }
}

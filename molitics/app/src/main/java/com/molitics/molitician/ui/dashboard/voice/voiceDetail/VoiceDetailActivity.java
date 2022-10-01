package com.molitics.molitician.ui.dashboard.voice.voiceDetail;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.molitics.molitician.BR;
import com.molitics.molitician.BaseActivity;
import com.molitics.molitician.R;
import com.molitics.molitician.databinding.ActivityVoiceDetailsBinding;
import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.ui.dashboard.branch_deep_link.BranchShareClass;
import com.molitics.molitician.ui.dashboard.hotTopics.hotTopicDetail.HotTopicDetailActivity;
import com.molitics.molitician.ui.dashboard.leader.newleaderprofile.NewLeaderProfileActivity;
import com.molitics.molitician.ui.dashboard.voice.VoiceViewNavigation;
import com.molitics.molitician.ui.dashboard.voice.createVoice.CreateVoiceActivity;
import com.molitics.molitician.ui.dashboard.voice.ImageBigFragment;
import com.molitics.molitician.ui.dashboard.voice.VoiceCreatorProfile;
import com.molitics.molitician.ui.dashboard.voice.VoiceHandler;
import com.molitics.molitician.ui.dashboard.voice.VoicePresenter;
import com.molitics.molitician.ui.dashboard.voice.adapter.AllCommentAdapter;
import com.molitics.molitician.ui.dashboard.voice.adapter.VoiceAllAdapter;
import com.molitics.molitician.ui.dashboard.voice.feedAction.LikeDislikeParticipantDialog;
import com.molitics.molitician.ui.dashboard.voice.model.CommentModel;
import com.molitics.molitician.ui.dashboard.voice.model.VoiceAllModel;
import com.molitics.molitician.ui.dashboard.voice.model.VoiceViewModel;
import com.molitics.molitician.ui.dashboard.voice.reportFeed.ReportFeedDialog;
import com.molitics.molitician.ui.dashboard.voice.viewHolder.VoiceAllViewHolder;
import com.molitics.molitician.ui.dashboard.voice.voiceComment.EditVoiceDialog;
import com.molitics.molitician.ui.dashboard.voice.voiceComment.VoiceCommentHandler;
import com.molitics.molitician.ui.dashboard.voice.voiceComment.VoiceCommentPresenter;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.DialogClass;
import com.molitics.molitician.util.MoliticsAppPermission;
import com.molitics.molitician.util.OnDialogClickListener;
import com.molitics.molitician.util.ShareScreenShot;
import com.molitics.molitician.util.Util;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.molitics.molitician.ui.dashboard.voice.VoiceFragment.VOICE_CREATE_MODEL;
import static com.molitics.molitician.util.MoliticsAppPermission.REQUEST_STORAGE;
import static com.molitics.molitician.util.Util.showProgressAnimation;


/**
 * Created by rahul(rc91108@gmail.com) on 11/01/18.
 */

public class VoiceDetailActivity extends BaseActivity<ActivityVoiceDetailsBinding, VoiceViewModel> implements VoiceCommentPresenter.VoiceCommentUI,
        AllCommentAdapter.CommentAdapterListener, VoiceDetailPresenter.VoiceDetailUi, VoiceAllAdapter.VoiceInterFace,
        VoicePresenter.VoiceActionUi, BranchShareClass.DeepLinkCallBack, VoiceViewNavigation {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.voice_detail_recycler_view)
    RecyclerView voice_detail_recycler_view;
    @BindView(R.id.comment_txt)
    EditText comment_txt;
    @BindView(R.id.post_comment_icon)
    ImageView post_comment_icon;

    private VoiceDetailAdapter adapter;
    private VoiceCommentHandler commentHandler;
    private VoiceDetailHandler voiceDetailHandler;
    private VoiceHandler actionVoiceHandler;
    private int issue_id = 0;
    private int issue_action = 0;
    private int image_position = 0, viewholderPosition = 0;

    private LinearLayoutManager linearLayoutManager;
    private int comment_edit_position = 0;
    private VoiceAllModel voiceAllModel;
    private int VOICE_MODEL = 400;
    private int model_position = 0;

    @Inject
    VoiceViewModel voiceViewModel;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_voice_details;
    }

    @Override
    public VoiceViewModel getViewModel() {
        return voiceViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        voiceViewModel.setNavigator(this);
        voiceViewModel.setContext(this);

        commentHandler = new VoiceCommentHandler(this);
        voiceDetailHandler = new VoiceDetailHandler(this);
        actionVoiceHandler = new VoiceHandler(this);
        showHeader();
        bindUi(getIntent());
    }

    private void showHeader() {
        setSupportActionBar(toolbar);
        showHeader(toolbar, true, getString(R.string.issue_detail));
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        Intent mIntent = new Intent();
        mIntent.putExtra(Constant.IntentKey.VOICE_MODEL, adapter.getVoiceDetail());
        mIntent.putExtra(Constant.IntentKey.POSITION, model_position);
        setResult(RESULT_OK, mIntent);
        super.onBackPressed();
    }


    private void bindUi(Intent mIntent) {

        adapter = new VoiceDetailAdapter(this, this, this);

        linearLayoutManager = new LinearLayoutManager(this);
        voice_detail_recycler_view.setLayoutManager(linearLayoutManager);
        voice_detail_recycler_view.setAdapter(adapter);

        if (mIntent.hasExtra(Constant.IntentKey.VOICE_MODEL)) {
            voiceAllModel = mIntent.getParcelableExtra(Constant.IntentKey.VOICE_MODEL);
            model_position = mIntent.getIntExtra(Constant.IntentKey.TEMP_POSITION, 0);
            issue_id = voiceAllModel.getId();
            if (voiceAllModel != null)
                adapter.setVoiceDetail(voiceAllModel);
        } else {
            issue_id = mIntent.getIntExtra(Constant.IntentKey.VOICE_ID, 0);
            voiceDetailHandler.getDetailVoice(issue_id);
        }
        commentHandler.getAllComment(issue_id, 0);
        voice_detail_recycler_view.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
            if (bottom < oldBottom) {
                voice_detail_recycler_view.postDelayed(() -> {
                    if (voice_detail_recycler_view != null && voice_detail_recycler_view.getAdapter().getItemCount() != 0) {
                        voice_detail_recycler_view.smoothScrollToPosition(voice_detail_recycler_view.getAdapter().getItemCount() - 1);
                    }
                }, 100);
            }
        });
    }

    @OnClick(R.id.post_comment_icon)
    public void onPostClick() {
        String comment = comment_txt.getText().toString();
        if (!TextUtils.isEmpty(comment)) {
            comment_txt.setText("");
            commentHandler.postComment(issue_id, comment);
        }
    }

    @Override
    public void onAllCommentResponse(APIResponse apiResponse) {
        adapter.addAllComments(apiResponse.getData().getAllCommentModel());
    }

    @Override
    public void onAllCommentApiError(ApiException apiException) {
        Util.validateError(this, apiException, null, null, 0);
    }

    @Override
    public void onPostCommentResponse(APIResponse apiResponse) {
        adapter.getVoiceDetail().setComments(adapter.getVoiceDetail().getComments() + 1);
        adapter.addCommentBottom(apiResponse.getData().getSingleComment());
        if (post_comment_icon != null) {
            InputMethodManager imm = (InputMethodManager) post_comment_icon.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(post_comment_icon.getWindowToken(), 0);
        }
        linearLayoutManager.scrollToPosition(adapter.getItemCount());
    }

    @Override
    public void onPostCommentApiError(ApiException apiException) {

    }

    @Override
    public void onEditCommentResponse(APIResponse apiResponse) {
        CommentModel singleCommentModel = apiResponse.getData().getSingleComment();
        adapter.editCommentModel(comment_edit_position, singleCommentModel);
    }

    @Override
    public void onEditCommentApiError(ApiException apiException) {
    }

    @Override
    public void onDeleteCommentResponse(APIResponse apiResponse) {
    }

    @Override
    public void onDeleteCommentError(ApiException apiException) {
        if (apiException.getCode() == 2005) {
            adapter.getVoiceDetail().setComments(adapter.getVoiceDetail().getComments() - 1);
            adapter.deleteComment(comment_edit_position);
        }
    }

    @Override
    public void onLoadMore(int count) {
        commentHandler.getAllComment(issue_id, count);
    }


    @Override
    public void onDetailVoiceResponse(APIResponse apiResponse) {
        voiceAllModel = apiResponse.getData().getVoiceDetailModel();
        adapter.setVoiceDetail(voiceAllModel);
    }

    @Override
    public void onDetailVoiceException(ApiException apiException) {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onCreateVoiceClick(int itemSelection) {

    }

    @Override
    public void onLikeDislikeClick(String from, int actual_position, int position, int issue_id, int action, int image_position) {
        issue_action = action;
        viewholderPosition = actual_position;

        actionVoiceHandler.likeDislike(issue_id, action);
    }

    @Override
    public void onDotClick(int position, int issue_id, int action_type, VoiceAllModel voiceModel) {
        if (action_type == Constant.Action.EDIT) {
            Intent mIntent = new Intent(this, CreateVoiceActivity.class);

            if (voiceAllModel.getTag() != null) {
                mIntent.putExtra(Constant.IntentKey.Hot_TOPIC_TEXT, voiceAllModel.getTagName());

                mIntent.putExtra(Constant.IntentKey.HOT_TOPIC_ID, voiceAllModel.getTag());
            }

            mIntent.putExtra(Constant.IntentKey.POSITION, position);
            mIntent.putExtra(Constant.IntentKey.ISSUE_MODEL, voiceAllModel);
            startActivityForResult(mIntent, VOICE_CREATE_MODEL);
        } else if (action_type == Constant.Action.DELETE) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(getString(R.string.want_to_delete))
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.yes), (dialog, id) -> voiceDetailHandler.deleteIssue(issue_id))
                    .setNegativeButton(getString(R.string.no), (dialog, id) -> {
                        //  Action for 'NO' Button
                        dialog.cancel();
                    });
            AlertDialog alert = builder.create();
            alert.setTitle(getString(R.string.issue_tag));
            alert.show();
        } else if (action_type == Constant.Action.SHARE) {
            onShareClick(issue_id, voiceAllModel.getUserName(), voiceAllModel.getContent());
        }
    }

    @Override
    public void onCommentClick(String from, int position, int issue_id, int image_position) {
        this.image_position = image_position;

    }

    @Override
    public void onShareClick(int issue_id, String userName, String issue_title) {

        if (MoliticsAppPermission.checkWritePermission()) {
            BranchShareClass.generateShareLink(this, this, userName,
                    issue_title, Constant.ShareCampaign.VOICE, Constant.ShareLinkTag.VOICE, String.valueOf(issue_id), "voice");
        } else {
            MoliticsAppPermission.requestReadStoragePermission(this);
        }
    }

    @Override
    public void onFollowClick(String from, int actual_position, int position, int issue_id, int image_position) {
        this.image_position = image_position;
        viewholderPosition = actual_position;
        actionVoiceHandler.onFollow(issue_id);
    }

    @Override
    public void onUnFollowClick(String from, int actual_position, int position, int issue_id, int image_position) {
        this.image_position = image_position;
        actionVoiceHandler.onUnFollow(issue_id);
    }

    @Override
    public void onTagLeaderClick(int leader_id) {
        Intent mIntent = new Intent(this, NewLeaderProfileActivity.class);
        mIntent.putExtra(Constant.IntentKey.LEADER_PROFILE_ID, leader_id);
        mIntent.putExtra(Constant.IntentKey.FROM, Constant.IntentKey.NOTIFICATION);
        startActivity(mIntent);
    }

    @Override
    public void onImageClick(VoiceAllViewHolder voiceAllViewHolder, int position, int issue_id, List<String> imageList) {
        ArrayList<String> strings = new ArrayList<>(imageList);
        FragmentManager fm = getSupportFragmentManager();
        ImageBigFragment.getInstance(position, issue_id, strings, null).show(fm, "Dialog Fragment");
    }

    @Override
    public void onCreatorImageClick(int position, int user_id) {
        Intent mIntent = new Intent(this, VoiceCreatorProfile.class);
        mIntent.putExtra(Constant.IntentKey.USER_ID, user_id);
        startActivity(mIntent);
    }

    @Override
    public void onCreatorImageClick(int position, int user_id, int replyLeader) {
        Intent mIntent = new Intent(this, VoiceCreatorProfile.class);
        mIntent.putExtra(Constant.IntentKey.USER_ID, user_id);
        startActivity(mIntent);
    }

    @Override
    public void onLongClick(Context mContext, View mView, int position, int comment_id, String text) {
        PopupMenu popup = new PopupMenu(mContext, mView);
        //Inflating the Popup using xml file
        popup.getMenuInflater().inflate(R.menu.menu_comment_action, popup.getMenu());

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(item -> {
            comment_edit_position = position - 1;
            if (item.getTitle().equals("Edit")) {
                Dialog edit_comment_dialog = EditVoiceDialog.getInstance(VoiceDetailActivity.this, text, text1 -> commentHandler.editComment(issue_id, comment_id, text1));

                edit_comment_dialog.show();
            } else if (item.getTitle().equals("Delete")) {
                DialogClass.showConfirmationDialog(VoiceDetailActivity.this, getString(R.string.delete_comment), new OnDialogClickListener() {
                    @Override
                    public void onOkClick() {
                        commentHandler.deleteCommet(issue_id, comment_id);
                    }

                    @Override
                    public void onCancelClick() {

                    }
                });
            }
            return true;
        });

        popup.show();//showing popup menu
    }


    @Override
    public void onHashTagClick(int tag_id, String tag_name) {
        Intent mIntent = new Intent(this, HotTopicDetailActivity.class);
        mIntent.putExtra(Constant.IntentKey.HOT_TOPIC_ID, tag_id);
        mIntent.putExtra(Constant.IntentKey.HOT_TOPIC_STRING, tag_name);
        startActivity(mIntent);
    }

    @Override
    public void onDetailClick(int position, int tempPosition, VoiceAllModel voiceModel) {

    }

    @Override
    public void onLikeDislikeClick(int issue_id, int action) {
        FragmentManager fm = getSupportFragmentManager();
        LikeDislikeParticipantDialog.getInstance(this, issue_id, action).show(fm, "LikeDialog");
    }


    @Override
    public void onVideoClick(VoiceAllViewHolder voiceAllViewHolder, String from, int clickPosition, int issueId) {
    }

    @Override
    public void onUserFollowActionClick(int userId, int action) {

    }

    @Override
    public void onReportDialogClick(int userId, int issueId) {
        ReportFeedDialog reportFeedDialog = new ReportFeedDialog();
        Bundle mBundle = new Bundle();
        mBundle.putInt(Constant.IntentKey.USER_ID, userId);
        mBundle.putInt(Constant.IntentKey.ISSUE_ID, issueId);
        mBundle.putString(Constant.IntentKey.FROM, getString(R.string.feed));
        reportFeedDialog.setArguments(mBundle);
        if (getFragmentManager() != null) {
            reportFeedDialog.show(getSupportFragmentManager(), getString(R.string.report_dialog));
        }
    }


    @Override
    public void onLikeDislikeResponse(APIResponse apiResponse) {
        adapter.updateLikeDislike(apiResponse.getData().getLike_count(), apiResponse.getData().getDislike_count(), issue_action, image_position);
        getCurrentItemPosition(viewholderPosition, apiResponse.getData().getLike_count(), apiResponse.getData().getDislike_count());
    }

    private void getCurrentItemPosition(int position, int like_count, int dislike_count) {
        VoiceAllViewHolder voiceAllViewHolder;
        if (voice_detail_recycler_view.findViewHolderForAdapterPosition(position) instanceof VoiceAllViewHolder) {
            voiceAllViewHolder = (VoiceAllViewHolder) voice_detail_recycler_view.findViewHolderForAdapterPosition(position);
            VoiceAllViewHolder.setUpvoteText(this, voiceAllViewHolder.like_count_view, like_count);
            VoiceAllViewHolder.setDownvoteText(this, voiceAllViewHolder.dislike_count_view, dislike_count);


            int totoalVoteCount = like_count + dislike_count;
            if (totoalVoteCount != 0) {
                ////// left to right or right to left progress bar with diff color
                if (issue_action == Constant.Action.LIKE) {
                    int finalPercent = (like_count * 100) / (totoalVoteCount);
                    voiceAllViewHolder.vote_progress_bar.setProgress(finalPercent);
                    showProgressAnimation(voiceAllViewHolder.vote_progress_bar, finalPercent);
                    voiceAllViewHolder.vote_progress_bar.setProgressDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.vote_progress_bar_horizontal, null));
                    voiceAllViewHolder.vote_progress_bar.setRotation(0);
                } else {
                    int finalPercent = (dislike_count * 100) / (totoalVoteCount);
                    voiceAllViewHolder.vote_progress_bar.setProgress(finalPercent);
                    showProgressAnimation(voiceAllViewHolder.vote_progress_bar, finalPercent);
                    voiceAllViewHolder.vote_progress_bar.setProgressDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.downvote_progress_horizontal, null));
                    voiceAllViewHolder.vote_progress_bar.setRotation(180);
                }
            } else {
                voiceAllViewHolder.vote_progress_bar.setProgressDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.gray_progress_horizontal, null));
            }
        }
    }

    @Override
    public void onLikeDislikeError(ApiException apiException) {
    }

    @Override
    public void onFollowResponse(APIResponse apiResponse) {
        adapter.onFollowResponse(apiResponse.getData().getCount(), image_position);
        onFollowResponseUpdateView(viewholderPosition, apiResponse.getData().getCount());


    }

    private void onFollowResponseUpdateView(int position, int follow_count) {
        VoiceAllViewHolder voiceAllViewHolder;
        if (voice_detail_recycler_view.findViewHolderForAdapterPosition(position) instanceof VoiceAllViewHolder) {
            voiceAllViewHolder = (VoiceAllViewHolder)
                    voice_detail_recycler_view.findViewHolderForAdapterPosition(position);
            voiceAllViewHolder.follow_txt.setTextColor(getResources().getColor(R.color.theme));
            voiceAllViewHolder.follow_txt.setText(String.format(getString(R.string.following_tag), follow_count));
            voiceAllViewHolder.follow_txt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.flag_blue, 0, 0, 0);
        }
    }

    @Override
    public void onFollowError(ApiException apiException) {
    }

    @Override
    public void onUnFollowResponse(APIResponse apiResponse) {
        adapter.onUnFollowResponse(apiResponse.getData().getCount(), image_position);
        onUnFollowResponseUpdateView(viewholderPosition, apiResponse.getData().getCount());

    }

    private void onUnFollowResponseUpdateView(int position, int follow_count) {
        VoiceAllViewHolder voiceAllViewHolder;

        if (voice_detail_recycler_view.findViewHolderForAdapterPosition(position) instanceof VoiceAllViewHolder) {
            voiceAllViewHolder = (VoiceAllViewHolder)
                    voice_detail_recycler_view.findViewHolderForAdapterPosition(position);
            voiceAllViewHolder.follow_txt.setTextColor(getResources().getColor(R.color.follow_txte));
            voiceAllViewHolder.follow_txt.setText(String.format(getString(R.string.follow), follow_count));
            voiceAllViewHolder.follow_txt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.flag, 0, 0, 0);
        }
    }


    @Override
    public void onUnFollowError(ApiException apiException) {
    }

    @Override
    public void onDeleteIssueResponse(APIResponse apiResponse) {
    }

    @Override
    public void onDeleteIssueError(ApiException apiException) {

        if (apiException.getCode() == 2005) {
            Toast.makeText(this, getString(R.string.successfully_deleted), Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        } else {
            Util.validateError(this, apiException, null, null, null);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == VOICE_MODEL) {
                if (linearLayoutManager != null)
                    linearLayoutManager.scrollToPosition(0);

                VoiceAllModel voiceAllModel = data.getParcelableExtra(Constant.IntentKey.VOICE_MODEL);
                int mPostion = data.getIntExtra(Constant.IntentKey.POSITION, 0);
                adapter.setVoiceDetail(voiceAllModel);

            } else if (requestCode == VOICE_CREATE_MODEL) {
                VoiceAllModel voiceCreate = data.getParcelableExtra(Constant.IntentKey.VOICE_MODEL);
                adapter.setVoiceDetail(voiceAllModel);
                if (!data.getBooleanExtra(Constant.IntentKey.IS_VOICE_POSTED, false))
                    voiceViewModel.setFeedData(voiceCreate);

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == REQUEST_STORAGE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                BranchShareClass.generateShareLink(this, this, "Molitics Voice", "",
                        Constant.ShareCampaign.VOICE, Constant.ShareLinkTag.VOICE, String.valueOf(issue_id), "user-feed/detail");

            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, getString(R.string.denied_permission), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onGenerateShareLink(String full_txt, String url) {
        ShareScreenShot.takeScreenshot(this, getString(R.string.media_of_politics) + full_txt, url);
    }

    @Override
    public void onVoiceCreateResponse(APIResponse apiResponse) {
        voiceAllModel = apiResponse.getData().getSingleVoiceModel();
        adapter.setVoiceDetail(voiceAllModel);
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void onVideoProgressChanged(int id, int percentDone, int count) {

    }
}

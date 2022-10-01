package com.molitics.molitician.ui.dashboard.voice.voiceComment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.molitics.molitician.BasicAcivity;
import com.molitics.molitician.R;
import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.interfaces.ViewRefreshListener;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.ui.dashboard.leader.newleaderprofile.NewLeaderProfileActivity;
import com.molitics.molitician.ui.dashboard.voice.VoiceCreatorProfile;
import com.molitics.molitician.ui.dashboard.voice.adapter.AllCommentAdapter;
import com.molitics.molitician.ui.dashboard.voice.model.CommentModel;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.DialogClass;
import com.molitics.molitician.util.MoliticsAppPermission;
import com.molitics.molitician.util.OnDialogClickListener;
import com.molitics.molitician.util.Util;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.molitics.molitician.util.MoliticsAppPermission.REQUEST_STORAGE;


/**
 * Created by om on 27/11/17.
 */
public class CommentFragment extends BasicAcivity implements VoiceCommentPresenter.VoiceCommentUI,
        ViewRefreshListener, AllCommentAdapter.CommentAdapterListener {


    @BindView(R.id.comment_recycler_view)
    RecyclerView comment_recycler_view;
    @BindView(R.id.comment_txt)
    EditText comment_txt;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.frame_layout)
    FrameLayout frame_layout;
    @BindView(R.id.post_comment_icon)
    ImageView post_comment_icon;
    @BindView(R.id.loading_grid)
    LinearLayout loading_grid;

    private VoiceCommentHandler commentHandler;
    private AllCommentAdapter allCommentAdapter;
    private int issue_id = 0;
    private int issue_position = 0;

    private LinearLayoutManager linearLayoutManager;
    private int comment_count = 0;
    private int comment_edit_position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_comment);

        ButterKnife.bind(this);
        commentHandler = new VoiceCommentHandler(this);

        bindUI();
    }

    private void bindUI() {
        progressBar.setVisibility(View.VISIBLE);
        comment_txt.requestFocus();
        Intent mBundle = getIntent();
        issue_id = mBundle.getIntExtra(Constant.IntentKey.ISSUE_ID, 0);

        issue_position = mBundle.getIntExtra(Constant.IntentKey.ISSUE_POSITION, 0);
        commentHandler.getAllComment(issue_id, 0);

        allCommentAdapter = new AllCommentAdapter(this, this);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        comment_recycler_view.setLayoutManager(linearLayoutManager);
        comment_recycler_view.setAdapter(allCommentAdapter);


        comment_recycler_view.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
            if (bottom < oldBottom) {
                comment_recycler_view.postDelayed(() -> {
                    if (comment_recycler_view != null && comment_recycler_view.getAdapter().getItemCount() != 0) {
                        comment_recycler_view.smoothScrollToPosition(comment_recycler_view.getAdapter().getItemCount() - 1);
                    }
                }, 100);
            }
        });
    }

    @OnClick(R.id.post_comment_icon)
    public void onPostClick() {

        String comment = comment_txt.getText().toString();
        if (!TextUtils.isEmpty(comment)) {
            commentHandler.postComment(issue_id, comment);

            comment_count++;
            Intent mIntent = new Intent();
            mIntent.putExtra(Constant.IntentKey.MESSAGE_COUNT, comment_count);
            mIntent.putExtra(Constant.IntentKey.COMMENT_POSITION, issue_position);
            setResult(Activity.RESULT_OK, mIntent);

            comment_txt.setText("");
        }
    }

    @Override
    public void onAllCommentResponse(APIResponse apiResponse) {

        loading_grid.setVisibility(View.GONE);
        allCommentAdapter.addAllComments(apiResponse.getData().getAllCommentModel());
        progressBar.setVisibility(View.GONE);

    }


    @Override
    public void onAllCommentApiError(ApiException apiException) {
        progressBar.setVisibility(View.GONE);
        loading_grid.setVisibility(View.GONE);
        Util.validateError(this, apiException, frame_layout, this, allCommentAdapter.getItemCount());
    }

    @Override
    public void onPostCommentResponse(APIResponse apiResponse) {

        allCommentAdapter.addCommentBottom(apiResponse.getData().getSingleComment());
        if (frame_layout != null) {
            frame_layout.removeAllViews();
        }
        if (post_comment_icon != null) {
            InputMethodManager imm = (InputMethodManager) post_comment_icon.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(post_comment_icon.getWindowToken(), 0);
            }
        }
        linearLayoutManager.scrollToPosition(allCommentAdapter.getItemCount());

    }

    @Override
    public void onPostCommentApiError(ApiException apiException) {
        Toast.makeText(this, getString(R.string.internet_not_connected), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEditCommentResponse(APIResponse apiResponse) {
        CommentModel singleCommentModel = apiResponse.getData().getSingleComment();
        allCommentAdapter.editCommentModel(comment_edit_position, singleCommentModel);
    }

    @Override
    public void onEditCommentApiError(ApiException apiException) {
        Toast.makeText(this, getString(R.string.internet_not_connected), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteCommentResponse(APIResponse apiResponse) {

    }

    @Override
    public void onDeleteCommentError(ApiException apiException) {
        if (apiException.getCode() == 2005) {
            allCommentAdapter.deleteComment(comment_edit_position);
            Intent mIntent = new Intent();
            mIntent.putExtra(Constant.IntentKey.MESSAGE_COUNT, comment_count - 1);
            mIntent.putExtra(Constant.IntentKey.COMMENT_POSITION, issue_position);
            setResult(Activity.RESULT_OK, mIntent);
        }
    }


    @Override
    public void onRefereshClick() {
        progressBar.setVisibility(View.VISIBLE);
        commentHandler.getAllComment(issue_id, 0);
    }

    @Override
    public void onNetworkAvailable() {
        onRefereshClick();
    }

    @Override
    public void onLoadMore(int count) {
        loading_grid.setVisibility(View.VISIBLE);
        commentHandler.getAllComment(issue_id, count);
    }

    @Override
    public void onCreatorImageClick(int position, int user_id, int replyLeader) {
        if (replyLeader == 1) {
            Intent mIntent = new Intent(this, NewLeaderProfileActivity.class);
            mIntent.putExtra(Constant.IntentKey.LEADER_PROFILE_ID, user_id);
            mIntent.putExtra(Constant.IntentKey.FROM, Constant.IntentKey.NOTIFICATION);
            startActivity(mIntent);
        } else {
            Intent mIntent = new Intent(this, VoiceCreatorProfile.class);
            mIntent.putExtra(Constant.IntentKey.USER_ID, user_id);
            startActivity(mIntent);
        }
    }


    @Override
    public void onLongClick(Context mContext, View mView, int position, int comment_id, String text) {
        PopupMenu popup = new PopupMenu(mContext, mView);
        //Inflating the Popup using xml file
        popup.getMenuInflater().inflate(R.menu.menu_comment_action, popup.getMenu());

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(item -> {
            comment_edit_position = position;
            if (item.getTitle().equals("Edit")) {
                Dialog edit_comment_dialog = EditVoiceDialog.getInstance(CommentFragment.this, text, text1 -> {
                    commentHandler.editComment(issue_id, comment_id, text1);
                });

                edit_comment_dialog.show();
            } else if (item.getTitle().equals("Delete")) {
                DialogClass.showConfirmationDialog(CommentFragment.this, getString(R.string.delete_comment), new OnDialogClickListener() {
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //  ShareScreenShot.takeScreenshot(getActivity(), getString(R.string.media_of_politics));
                Toast.makeText(this, getString(R.string.permission_granted_now), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, getString(R.string.denied_permission), Toast.LENGTH_LONG).show();
            }
        }
    }
}

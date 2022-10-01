package com.molitics.molitician.ui.dashboard.voice.voiceComment;

import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.model.APIResponse;

/**
 * Created by rahul on 20/11/17.
 */

public class VoiceCommentPresenter {

    public interface VoiceCommentUI {

        void onAllCommentResponse(APIResponse apiResponse);

        void onAllCommentApiError(ApiException apiException);

        void onPostCommentResponse(APIResponse apiResponse);

        void onPostCommentApiError(ApiException apiException);

        void onEditCommentResponse(APIResponse apiResponse);

        void onEditCommentApiError(ApiException apiException);

        void onDeleteCommentResponse(APIResponse apiResponse);

        void onDeleteCommentError(ApiException apiException);


    }


    public interface VoiceCommentRequest {

        void getAllComment(int issue_id, int count);

        void postComment(int issue_id, String comment);

        void editComment(int issue_id, int comment_id, String edit_comment);

        void deleteCommet(int issue_id, int comment_id);

    }

}

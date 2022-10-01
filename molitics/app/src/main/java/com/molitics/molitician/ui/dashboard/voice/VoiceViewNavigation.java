package com.molitics.molitician.ui.dashboard.voice;

import com.molitics.molitician.model.APIResponse;

public interface VoiceViewNavigation {

    void onVoiceCreateResponse(APIResponse apiResponse);

    void handleError(Throwable throwable);

    void onVideoProgressChanged(int id, int percentDone, int count);

    default void videoHandleError(Exception ex) {

    }


}

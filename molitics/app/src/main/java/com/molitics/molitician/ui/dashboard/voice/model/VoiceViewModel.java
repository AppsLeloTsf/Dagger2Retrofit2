package com.molitics.molitician.ui.dashboard.voice.model;

import android.content.Context;
import android.text.TextUtils;

import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.molitics.molitician.BaseViewModel;
import com.molitics.molitician.httpapi.RetrofitRestClient;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.ui.dashboard.leader.AllLeaderModel;
import com.molitics.molitician.ui.dashboard.voice.VoiceViewNavigation;
import com.molitics.molitician.ui.dashboard.voice.createVoice.AwsFileUploadUtils;
import com.molitics.molitician.util.CompressImage;
import com.molitics.molitician.util.Constant;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.molitics.molitician.util.Constant.IMAGE;
import static com.molitics.molitician.util.Constant.VIDEO;
import static com.molitics.molitician.util.VideoExpoPlayer.createVideoPlaceholder;
import static com.molitics.molitician.util.VideoExpoPlayer.generateFileName;
import static com.molitics.molitician.util.VideoExpoPlayer.getMimeType;
import static com.molitics.molitician.util.VideoExpoPlayer.replaceFileToPlaceholder;


public class VoiceViewModel extends BaseViewModel<VoiceViewNavigation> {
    String TAG = VoiceViewModel.class.getSimpleName();
    private ArrayList<String> video_image_list;
    private ArrayList<String> video_image_list_name;
    private VoiceCreateModel voiceCreateModel;
    private Context context;
    int issueId = 0;
    private int uploadedCount;

    private static final int DATA = 1;
    private static final int PLACEHOLDER = 2;


    public void setFeedData(VoiceAllModel voiceAllModel) {
        uploadedCount = 1;
        video_image_list = new ArrayList<>();
        video_image_list_name = new ArrayList<>();
        List<Integer> leader_list = new ArrayList<>();
        for (AllLeaderModel leaderModel : voiceAllModel.getCandidateLeader()) {
            leader_list.add(leaderModel.getId());
        }
        voiceCreateModel = new VoiceCreateModel();
        voiceCreateModel.setContent(voiceAllModel.getContent());
        voiceCreateModel.setTitle(voiceAllModel.getTitle());
        voiceCreateModel.setLeaders(leader_list);
        voiceCreateModel.setUrlSource(voiceAllModel.getUrlSource());
        voiceCreateModel.setSharedUrl(voiceAllModel.getSharedUrl());
        voiceCreateModel.setTag(voiceAllModel.getTag());
        voiceCreateModel.setSharedImageUrl(handleUrlImage(voiceAllModel.getImages()));
        voiceCreateModel.setUploadedImagesUrl(new ArrayList<>());
        if (voiceAllModel.getImages() != null) {
            for (String s : voiceAllModel.getImages()) {
                if (!s.contains("http") && !TextUtils.isEmpty(s)) {
                    video_image_list.add(s);

                }
            }
        }
        issueId = voiceAllModel.getId();

        if (voiceAllModel.getDeleted_image().size() > 0) {
            deleteIMageVoiceObservable(issueId, new ImageDeleteModel(voiceAllModel.getDeleted_image())).subscribe(getObserver());
        }

        if (video_image_list.size() > 0) {
            uploadWithTransferUtility(video_image_list.get(0), "", DATA);
        } else {
            if (issueId != 0) {
                editVoiceObservable(issueId, voiceCreateModel).subscribe(getObserver());
            } else {
                createFeed(voiceCreateModel);
            }
        }
    }


    public void setContext(Context context) {
        this.context = context;
    }

    private void createFeed(VoiceCreateModel voiceCreateModel) {
        createVoiceObservable(voiceCreateModel).subscribe(getObserver());
    }


    private Observable<APIResponse> createVoiceObservable(VoiceCreateModel voiceCreateModel) {
        return RetrofitRestClient.getInstance()
                .createRxVoice(voiceCreateModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<APIResponse> editVoiceObservable(int feed_id, VoiceCreateModel voiceCreateModel) {
        return RetrofitRestClient.getInstance()
                .editRxVoice(feed_id, voiceCreateModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<APIResponse> deleteIMageVoiceObservable(int feed_id, ImageDeleteModel imageDeleteModel) {
        return RetrofitRestClient.getInstance()
                .deleteIssueImageRx(feed_id, imageDeleteModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    private DisposableObserver<APIResponse> getObserver() {
        return new DisposableObserver<APIResponse>() {

            @Override
            public void onNext(@NonNull APIResponse apiResponse) {
                if (getNavigator() != null) {
                    if (apiResponse != null && apiResponse.getData() != null) {
                        getNavigator().onVoiceCreateResponse(apiResponse);
                    }
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {

            }
        };
    }

    String fileName;

    private void uploadWithTransferUtility(String filePath, String placeholderFileName, int type) {
        if (!TextUtils.isEmpty(filePath)) {
            String bucketName;
            File file = new File(filePath);
            if (type != 2) {
                fileName = generateFileName(file.getName());
                voiceCreateModel.getUploadedImagesUrl().add(fileName);
            } else {
                fileName = replaceFileToPlaceholder(placeholderFileName);
            }
            if (getMimeType(file.getPath()).contains(IMAGE)) {
                bucketName = Constant.IMAGE_AWS_BUCKET;
                file = new File(CompressImage.compressImage(context, filePath));
            } else {
                bucketName = Constant.AWS_BUCKET;
            }

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.addUserMetadata("Content-Type", getMimeType(file.getPath()));
            TransferObserver uploadObserver =
                    AwsFileUploadUtils.getAwsConfig(context).upload(
                            bucketName,
                            Constant.AWS_FILE_BASE_PATH + fileName,
                            file,
                            objectMetadata, CannedAccessControlList.PublicRead);

            // Attach a listener to the observer to get state update and progress notifications
            uploadObserver.setTransferListener(new TransferListener() {
                @Override
                public void onStateChanged(int id, TransferState state) {
                    if (TransferState.COMPLETED == state) {
                        // Handle a completed upload.
                        if (getMimeType(filePath).contains(VIDEO)) {
                            uploadWithTransferUtility(createVideoPlaceholder(filePath), fileName, PLACEHOLDER);
                        } else {
                            if (video_image_list.size() > uploadedCount) {
                                uploadWithTransferUtility(video_image_list.get(uploadedCount), "", DATA);
                                uploadedCount++;
                            } else {
                                if (issueId != 0) {
                                    editVoiceObservable(issueId, voiceCreateModel).subscribe(getObserver());
                                } else {
                                    createFeed(voiceCreateModel);
                                }
                            }
                        }
                    } else if (state == TransferState.FAILED) {
                        getNavigator().videoHandleError(null);
                    }
                }

                @Override
                public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                    float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;
                    int percentDone = (int) percentDonef;

                    if (getNavigator() != null) {
                        getNavigator().onVideoProgressChanged(id, percentDone, uploadedCount);
                    }
                }

                @Override
                public void onError(int id, Exception ex) {
                    // Handle errors
                    getNavigator().videoHandleError(ex);
                }
            });
        } else {
            createFeed(voiceCreateModel);
        }
    }

    private String handleUrlImage(List<String> url_image) {
        String saved_image = "";

        Iterator itr = url_image.iterator();
        while (itr.hasNext()) {
            String image = (String) itr.next();
            if (image.contains("http")) {
                saved_image = image;
                itr.remove();
            }
        }
        return saved_image;
    }
}

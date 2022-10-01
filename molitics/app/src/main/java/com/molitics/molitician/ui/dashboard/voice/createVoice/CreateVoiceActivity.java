package com.molitics.molitician.ui.dashboard.voice.createVoice;

import android.Manifest;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.darsh.multipleimageselect.activities.AlbumSelectActivity;
import com.darsh.multipleimageselect.helpers.Constants;
import com.darsh.multipleimageselect.models.Image;
import com.molitics.molitician.BR;
import com.molitics.molitician.BaseActivity;
import com.molitics.molitician.R;
import com.molitics.molitician.UiMediator.Loader;
import com.molitics.molitician.customView.CameraFragmentMainActivity;
import com.molitics.molitician.customView.CustomStaggerdGridLayoutManager;
import com.molitics.molitician.customView.NestedRecyclerView;
import com.molitics.molitician.customView.VideoRecordingCustomActivity;
import com.molitics.molitician.databinding.ActivityCreateIssueBinding;
import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.ui.dashboard.leader.AllLeaderModel;
import com.molitics.molitician.ui.dashboard.login.LoginDialogFragment;
import com.molitics.molitician.ui.dashboard.voice.VoiceAddPresenter;
import com.molitics.molitician.ui.dashboard.voice.VoiceViewNavigation;
import com.molitics.molitician.ui.dashboard.voice.adapter.VoiceAddImageAdapter;
import com.molitics.molitician.ui.dashboard.voice.interfaces.TagLeaderInterfaces;
import com.molitics.molitician.ui.dashboard.voice.model.VoiceAllModel;
import com.molitics.molitician.ui.dashboard.voice.model.VoiceViewModel;
import com.molitics.molitician.ui.dashboard.voice.voiceDetail.RecordVideoInterface;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.ExtraUtils;
import com.molitics.molitician.util.MoliticsAppPermission;
import com.molitics.molitician.util.PrefUtil;
import com.molitics.molitician.util.Util;
import com.tokenautocomplete.TokenCompleteTextView;

import net.alhazmy13.mediapicker.Video.VideoPicker;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


import static com.molitics.molitician.util.Constant.From.FROM_IMAGE_SHARE;
import static com.molitics.molitician.util.MoliticsAppPermission.checkVideoPermission;
import static com.molitics.molitician.util.MoliticsAppPermission.requestCameraPermission;
import static com.molitics.molitician.util.MoliticsAppPermission.requestStoragePermission;
import static com.molitics.molitician.util.Util.showToastLong;
import static com.molitics.molitician.util.VideoExpoPlayer.createTempImage;

/**
 * Created by rahul on 16/11/17.
 */

public class CreateVoiceActivity extends BaseActivity<ActivityCreateIssueBinding, VoiceViewModel> implements VoiceAddPresenter.VoiceUI,
        TokenCompleteTextView.TokenListener<AllLeaderModel>, RecordVideoInterface, VoiceViewNavigation,
        TagLeaderInterfaces {

    String TAG = CreateVoiceActivity.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    NestedRecyclerView recyclerView;
    @BindView(R.id.voice_post)
    Button voice_post;
    @BindView(R.id.inner_container)
    FrameLayout container;
    @BindView(R.id.add_image_view)
    TextView add_image_view;
    @BindView(R.id.add_video_view)
    TextView add_video_view;

    private VoiceAddImageAdapter voiceAddImageAdapter;
    private int image_click_position = 0;

    private int REQUEST_VIDEO_CAPTURE = 13;
    private int GALLERY_IMAGE_REQUEST_CODE = 11;
    private static final int ACTION_TAKE_PHOTO_B = 1;
    private String mCurrentPhotoPath;
    private static final String JPEG_FILE_PREFIX = "IMG_";
    private static final String JPEG_FILE_SUFFIX = ".jpg";
    private Integer hot_topic_id;
    private String hot_topic_txt;
    private int POSITION = 0;
    private boolean IS_EDIT;
    private VoiceAllModel voiceAllModel;

    private ArrayAdapter<AllLeaderModel> adapter;
    //String imageEncoded;
    private List<AllLeaderModel> nameList = new ArrayList<>();
    private static int CUSTOM_VIDEO_RECORDING = 878;
    private static int LEADER_ACTIVITY = 879;

    @Inject
    VoiceViewModel voiceViewModel;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_create_issue;
    }

    @Override
    public VoiceViewModel getViewModel() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setToolbar();

        voiceAllModel = new VoiceAllModel();
        voiceViewModel.setNavigator(this);
        voiceViewModel.setContext(this);
        bindUi();
        handleIntentData(getIntent());
        setAdapterData();

    }

    private void setAdapterData() {
        voiceAddImageAdapter.addVoiceModel(voiceAllModel);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void handleIntentData(Intent mIntent) {

        // Get intent, action and MIME type
        String action = mIntent.getAction();
        String type = mIntent.getType();

        if (mIntent.hasExtra(Constant.IntentKey.TYPE)) {
            int openType = mIntent.getIntExtra(Constant.IntentKey.TYPE, 0);
            if (openType == Constant.createImage) {
                onImageAddClick();
            } else if (openType == Constant.createVideo) {
                onVideoAddClick();
            } else if (openType == Constant.createLeader) {
                onLeaderClick();
            }
        }
        if (mIntent.getExtras() != null) {
            voiceAllModel = mIntent.getExtras().getParcelable(Constant.IntentKey.ISSUE_MODEL);

            //// check if screen open for edit and if  it contains other source link. Disable gallery feature
            if (voiceAllModel == null) {
                voiceAllModel = new VoiceAllModel();
            } else if (!TextUtils.isEmpty(voiceAllModel.getUrlSource())) {
                disableGalleryOptions();
            }
        }

        if (mIntent.hasExtra(Constant.IntentKey.POSITION)) {
            POSITION = mIntent.getIntExtra(Constant.IntentKey.POSITION, 0);
            IS_EDIT = true;
        }
        if (mIntent.hasExtra(Constant.IntentKey.Hot_TOPIC_TEXT)) {
            if (voiceAllModel == null)
                voiceAllModel = new VoiceAllModel();
            hot_topic_id = mIntent.getIntExtra(Constant.IntentKey.HOT_TOPIC_ID, 0);
            hot_topic_txt = mIntent.getStringExtra(Constant.IntentKey.Hot_TOPIC_TEXT);

            voiceAllModel.setTag(hot_topic_id);
            voiceAllModel.setTagName(hot_topic_txt);
        }

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if (voiceAllModel == null)
                voiceAllModel = new VoiceAllModel();
            if ("text/plain".equals(type)) {
                handleSendText(mIntent); // Handle text being sent
                disableGalleryOptions();
            } else if (type.startsWith("image/") /*|| type.startsWith("video/")*/) {
                voiceAllModel.setFrom(FROM_IMAGE_SHARE);
                handleSendImage(mIntent); // Handle single image being sent
            } else if (type.startsWith("video/")) {

            }
        }
    /*    if (TextUtils.isEmpty(voiceAllModel.getUserName())) {
            voiceAllModel.setUserName(PrefUtil.getString(Constant.PreferenceKey.USER_NAME));
        }*/
    }

    void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            voiceAllModel.setContent(sharedText);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    void handleSendImage(Intent intent) {
        Uri imageUri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            String imagePath = createTempImage(bitmap);
            List<String> image_list = voiceAllModel.getImages();
            image_list.add(imagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void disableGalleryOptions() {
        add_image_view.setClickable(false);
        add_image_view.setEnabled(false);
        add_video_view.setClickable(false);
        add_video_view.setEnabled(false);
    }


    @OnClick(R.id.add_image_view)
    public void onImageAddClick() {
        if (!MoliticsAppPermission.checkCameraAndWritePermission()) {
            requestCameraPermission(this);
        } else {
            selectImage();
        }
    }

    @OnClick(R.id.add_video_view)
    public void onVideoAddClick() {
        if (checkVideoPermission()) {
            selectVideo();
        } else {
            requestStoragePermission(this);
        }
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(R.string.raise_voice_title);
        toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.follow_leader_white));
        toolbar.setNavigationContentDescription("BACK");

        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void bindUi() {
        voiceAddImageAdapter = new VoiceAddImageAdapter(this);

        CustomStaggerdGridLayoutManager linearLayoutManager = new CustomStaggerdGridLayoutManager(Constant.STAGGERED_GRID_COUNT, StaggeredGridLayoutManager.VERTICAL) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(voiceAddImageAdapter);
    }

    @OnClick(R.id.recyclerView)
    public void onRecyclerClick() {
        InputMethodManager inputMethodManager =
                (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInputFromWindow(
                recyclerView.getApplicationWindowToken(),
                InputMethodManager.SHOW_FORCED, 0);
    }

    @OnClick(R.id.tag_leader_view)
    public void onLeaderClick() {

        Intent mIntent = new Intent(this, TagLeaderFragment.class);
        Bundle mBundle = new Bundle();
        mBundle.putSerializable(Constant.IntentKey.LEADER_LIST, voiceAllModel.getCandidateLeader());
        mIntent.putExtras(mBundle);
        startActivityForResult(mIntent, LEADER_ACTIVITY);
    }

    @OnClick(R.id.voice_post)
    public void onVoiceClick() {

        voiceAllModel = voiceAddImageAdapter.getVoiceAllModel();

        if (TextUtils.isEmpty(voiceAllModel.getContent().trim())) {
            Toast.makeText(this, R.string.type_issue, Toast.LENGTH_SHORT).show();
            return;
        }
        if (voiceAllModel.getCandidateLeader().size() == 0) {
            onLeaderClick();
        } else {
       /*     if (PrefUtil.getString(Constant.PreferenceKey.AUTH_KEY).isEmpty()) {
                showLoginDialog();
            } else*/
            createFeed();
        }
    }

    private void showLoginDialog() {
        DialogFragment dialogFragment = LoginDialogFragment.getInstance();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        dialogFragment.show(fragmentTransaction, getString(R.string.dialog_fragment));
    }

    private void createFeed() {
        voiceAllModel.getImages().remove("static");

        if (!TextUtils.isEmpty(voiceAllModel.getUrlSource()) || voiceAllModel.getFrom().equals(FROM_IMAGE_SHARE)) {
            Loader.showMyDialog(this);
            voiceViewModel.setFeedData(voiceAllModel);
        } else
            onVoiceCreateResponse();
    }

    @Override
    public void onSearchResponse(APIResponse apiResponse) {
        nameList = apiResponse.getData().getLeaderModels();
        adapter.clear();
        adapter.addAll(nameList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSearchApiError(ApiException apiException) {
    }

    public void onVoiceCreateResponse() {
        Util.showLog("CREATE voice", voiceAllModel.toString());
        PrefUtil.putBoolean(Constant.PreferenceKey.VIDEO_RETRY, false);
        Intent mIntent = new Intent();
        mIntent.putExtra(Constant.IntentKey.VOICE_MODEL, voiceAllModel);
        mIntent.putExtra(Constant.IntentKey.IS_VOICE_POSTED, false);
        mIntent.putExtra(Constant.IntentKey.POSITION, POSITION);
        mIntent.putExtra(Constant.IntentKey.EDIT, IS_EDIT);
        setResult(RESULT_OK, mIntent);
        finishActivity();
    }

    @Override
    public void onDeleteResponse(APIResponse apiResponse) {
        finishActivity();
    }

    @Override
    public void onDeleteError(ApiException apiException) {
    }

    private void finishActivity() {
        Loader.dismissMyDialog(this);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == Constants.REQUEST_CODE && data != null) {
                ArrayList<Image> images = data.getParcelableArrayListExtra(Constants.INTENT_EXTRA_IMAGES);
                for (int i = 0; i < images.size(); i++) {
                    image_click_position = voiceAddImageAdapter.getVoiceImageList().size();
                    setStatusImage(images.get(i).path);
                }
            }
            if (requestCode == CUSTOM_VIDEO_RECORDING) {
                String path = null;
                if (data != null) {
                    path = data.getStringExtra(Constant.IntentKey.VIDEO_URL);
                    getRecordVideoPath(path);
                }
            }
            if (requestCode == LEADER_ACTIVITY) {
                if (data != null) {
                    Bundle mBundle = data.getExtras();
                    List<AllLeaderModel> allLeaderModels = (ArrayList<AllLeaderModel>) mBundle.getSerializable(Constant.IntentKey.LEADER_LIST);
                    if (allLeaderModels.size() > 0)
                        setTagLeaderList(allLeaderModels);
                }
            }

            if (requestCode == VideoPicker.VIDEO_PICKER_REQUEST_CODE) {

                List<String> mPathList = (List<String>) data.getSerializableExtra(VideoPicker.EXTRA_VIDEO_PATH);

                for (int i = 0; i < mPathList.size(); i++) {
                    image_click_position = voiceAddImageAdapter.getVoiceImageList().size();
                    String mPath = mPathList.get(i);
                    if (checkFileSize(mPath)) {
                        setStatusImage(mPath);
                    }
                }
            }

            if (requestCode == REQUEST_VIDEO_CAPTURE && data != null) {
                Uri selectedImageUri = data.getData();
                String selectedImagePath = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    selectedImagePath = ExtraUtils.getRealPathFromURI_API19(this, selectedImageUri);
                }
                if (!TextUtils.isEmpty(selectedImagePath)) {
                    if (checkFileSize(selectedImagePath)) {
                        setStatusImage(selectedImagePath);
                    }
                }
            }

            if (requestCode == GALLERY_IMAGE_REQUEST_CODE && null != data) {

                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                if (data.getData() != null) {
                    Uri mImageUri = data.getData();
                    Cursor cursor = getContentResolver().query(mImageUri,
                            filePathColumn, null, null, null);
                    if (cursor != null) {
                        cursor.moveToFirst();
                    }
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    //imageEncoded = cursor.getString(columnIndex);
                    cursor.close();
                } else {
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();
                        for (int i = 0; i < mClipData.getItemCount(); i++) {

                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();

                            // Get the cursor
                            Cursor cursor2 = getContentResolver().query(uri,
                                    filePathColumn, null, null, null);
                            // Move to first row
                            if (cursor2 != null) {
                                cursor2.moveToFirst();
                                int idx = cursor2.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                                String test = cursor2.getString(idx);

                                int columnIndex = cursor2.getColumnIndex(filePathColumn[0]);
                                // imageEncoded = cursor2.getString(columnIndex);
                                image_click_position = i;

                                setStatusImage(cursor2.getString(columnIndex));
                                cursor2.close();
                            }
                        }
                    }
                }
            }

            if (requestCode == ACTION_TAKE_PHOTO_B) {
                if (resultCode == RESULT_OK) {
                    if (mCurrentPhotoPath != null) {
                        setStatusImage(mCurrentPhotoPath);

                        mCurrentPhotoPath = null;
                    }
                }
            }
        }
    }

    private void setStatusImage(String image_txt) {
        if (checkAssets())
            voiceAddImageAdapter.setVoiceImage(image_txt);
    }

    @Override
    public void onTokenAdded(AllLeaderModel token) {
    }

    @Override
    public void onTokenRemoved(AllLeaderModel token) {
    }

    private void selectImage() {

        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, (dialog, item) -> {
            if (items[item].equals("Take Photo")) {
                dispatchTakePictureIntent(ACTION_TAKE_PHOTO_B);

            } else if (items[item].equals("Choose from Library")) {

                Intent intent = new Intent(CreateVoiceActivity.this, AlbumSelectActivity.class);
                //set limit on number of images that can be selected, default is 5
                intent.putExtra(Constants.INTENT_EXTRA_LIMIT, 5);
                startActivityForResult(intent, Constants.REQUEST_CODE);

            }
        });
        builder.show();
    }

    private void selectVideo() {

        final CharSequence[] items = {"Take Video", "Choose from Library", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Video!");
        builder.setItems(items, (dialog, item) -> {
            if (items[item].equals("Take Video")) {

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    Intent mIntent = new Intent(CreateVoiceActivity.this, CameraFragmentMainActivity.class);
                    startActivityForResult(mIntent, CUSTOM_VIDEO_RECORDING);
                } else {
                    Intent mIntent = new Intent(CreateVoiceActivity.this, VideoRecordingCustomActivity.class);
                    startActivityForResult(mIntent, CUSTOM_VIDEO_RECORDING);
                }
                dialog.dismiss();
            } else if (items[item].equals("Choose from Library")) {

                fetchVideoFromGallery();
                dialog.dismiss();
            } else if (items[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 23 || requestCode == 900) {
            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                int grantResult = grantResults[i];

                if (permission.equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                        break;
                    }
                }
                if (permission.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                        break;
                    }
                }
                if (permission.equals(Manifest.permission.CAMERA)) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {
                        selectImage();
                    }
                }
                if (permission.equals(Manifest.permission.RECORD_AUDIO)) {
                    if (grantResult == PackageManager.PERMISSION_GRANTED) {
                        selectVideo();
                    }
                }
            }
        }
    }

    private void dispatchTakePictureIntent(int actionCode) {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        switch (actionCode) {
            case ACTION_TAKE_PHOTO_B:
                File f = null;

                try {
                    f = setUpPhotoFile();
                    mCurrentPhotoPath = f.getAbsolutePath();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                        Uri photoURI = FileProvider.getUriForFile(this,
                                "com.molitics.molitician.provider", f);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                    } else {
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    f = null;
                    mCurrentPhotoPath = null;
                }
                break;

            default:
                break;
        }

        startActivityForResult(takePictureIntent, actionCode);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void getRecordVideoPath(String pathString) {
        if (!TextUtils.isEmpty(pathString)) {
            if (checkFileSize(pathString)) {
                setStatusImage(pathString);
            }
        }
    }

    private boolean checkFileSize(String mPath) {
        File file_size = new File(mPath);
        long file_size_value = file_size.length() / (1024 * 1024);//call function and convert bytes into MB
        if (file_size_value <= Constant.FILE_SIZE_BELOW) {
            return true;
        } else {
            Toast.makeText(this, R.string.video_size, Toast.LENGTH_LONG).show();
            return false;
        }
    }

    @Override
    public void setTagLeaderList(List<AllLeaderModel> selected_leader_list) {
        voiceAddImageAdapter.tagLeaderList((ArrayList<AllLeaderModel>) selected_leader_list);
    }

    private void fetchVideoFromGallery() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(Intent.createChooser(intent, "Select Video"), REQUEST_VIDEO_CAPTURE);
    }

    private boolean checkAssets() {
        int list_size = voiceAddImageAdapter.getVoiceImageList().size();
        if (list_size < 6) {
            return true;
        } else {
            showToastLong(this, getString(R.string.image_video_limit));
            return false;
        }
    }

    private File createImageFile() throws IOException {
        // Create an image_small_icon file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
        File albumF = Util.getAlbumDir();
        File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, albumF);
        return imageF;
    }

    private File setUpPhotoFile() throws IOException {

        File image_file = createImageFile();
        mCurrentPhotoPath = image_file.getAbsolutePath();
        return image_file;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntentData(intent);
    }

    @Override
    public void onVoiceCreateResponse(APIResponse apiResponse) {
        Loader.dismissMyDialog(this);
        finishActivity();
    }

    @Override
    public void handleError(Throwable throwable) {
    }

    @Override
    public void onVideoProgressChanged(int id, int percentDone, int count) {

    }
}


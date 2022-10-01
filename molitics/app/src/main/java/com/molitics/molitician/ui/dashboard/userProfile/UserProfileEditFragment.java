package com.molitics.molitician.ui.dashboard.userProfile;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.darsh.multipleimageselect.activities.AlbumSelectActivity;
import com.darsh.multipleimageselect.helpers.Constants;
import com.darsh.multipleimageselect.models.Image;
import com.molitics.molitician.GoogleAnalytics.GAEventTracking;
import com.molitics.molitician.R;
import com.molitics.molitician.UiMediator.Loader;
import com.molitics.molitician.error.ApiException;
import com.molitics.molitician.error.ServerException;
import com.molitics.molitician.interfaces.ViewRefreshListener;
import com.molitics.molitician.model.APIResponse;
import com.molitics.molitician.ui.dashboard.constantData.ConstantModel;
import com.molitics.molitician.ui.dashboard.constantData.RecyclerTouchWithType;
import com.molitics.molitician.ui.dashboard.login.SelectLocationDialog;
import com.molitics.molitician.ui.dashboard.userProfile.editContact.EditContactHandler;
import com.molitics.molitician.ui.dashboard.userProfile.editContact.EditNumberPresenter;
import com.molitics.molitician.ui.dashboard.userProfile.editInfo.EditInfoPresenter;
import com.molitics.molitician.ui.dashboard.userProfile.editInfo.EditProfileHandler;
import com.molitics.molitician.ui.dashboard.userProfile.model.EditUserProfileModel;
import com.molitics.molitician.ui.dashboard.userProfile.model.UserProfileModel;
import com.molitics.molitician.util.CompressImage;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.DialogClass;
import com.molitics.molitician.util.MoliticsAppPermission;
import com.molitics.molitician.util.NetworkUtil;
import com.molitics.molitician.util.Util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;

/**
 * Created by rahul on 7/12/2016.
 */
public class UserProfileEditFragment extends Fragment implements UserProfilePresenter.UserProfileView, ViewRefreshListener, EditInfoPresenter.EditInfoView,
        EditNumberPresenter.EditContactView {

    @BindView(R.id.user_name_edit)
    EditText user_name_edit;
    @BindView(R.id.user_contact_edit)
    EditText user_contact_edit;
    @BindView(R.id.child_fragment)
    FrameLayout child_fragment;
    @BindView(R.id.main_scroll_view)
    ScrollView main_scroll_view;
    @BindView(R.id.user_state_edit)
    TextView user_state_edit;
    @BindView(R.id.user_district_edit)
    TextView user_district_edit;
    @BindView(R.id.user_lok_sabha_edit)
    TextView user_lok_sabha_edit;
    @BindView(R.id.user_assembly_edit)
    TextView user_assembly_edit;
    @BindView(R.id.user_party_edit)
    TextView user_party_edit;
    @BindView(R.id.user_profession_edit)
    TextView user_profession_edit;
    @BindView(R.id.gender_radio_button)
    RadioGroup gender_radio_button;
    @BindView(R.id.select_image_view)
    ImageView slect_image_view;
    @BindView(R.id.user_image_view)
    CircleImageView user_image_view;
    @BindView(R.id.user_email_edit)
    TextView user_email_edit;
    @BindView(R.id.saveButton)
    AppCompatButton saveButton;
    @BindView(R.id.user_bio_edit)
    EditText userBioTextView;

    private String contact = "";

    private int MY_REQUEST_CODE = 100;
    private String mContact;

    private static final int ACTION_TAKE_PHOTO_B = 1;
    private int GALLERY_IMAGE_REQUEST_CODE = 11;
    private String profile_image_url = "";
    private String mCurrentPhotoPath;
    private static final String JPEG_FILE_PREFIX = "IMG_";
    private static final String JPEG_FILE_SUFFIX = ".jpg";

    private EditProfileHandler editProfileHandler;
    private EditUserProfileModel editUserProfileModel;
    private EditContactHandler editContactHandler;
    private UserProfileHandler profileHandler;

    private UserProfileModel userProfileModel;


    private List<ConstantModel> state_list = new ArrayList<>();
    private List<ConstantModel> district_list = new ArrayList<>();
    private List<ConstantModel> mp_list = new ArrayList<>();
    private List<ConstantModel> mla_list = new ArrayList<>();
    private List<ConstantModel> party_list = new ArrayList<>();
    private List<ConstantModel> profession_list = new ArrayList<>();

    private boolean number_submit = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileHandler = new UserProfileHandler(this);
        editProfileHandler = new EditProfileHandler(this);
        editUserProfileModel = new EditUserProfileModel();
        editContactHandler = new EditContactHandler(this);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View primary_layout = inflater.inflate(R.layout.fragment_user_profile_edit, container, false);
        ButterKnife.bind(this, primary_layout);
        main_scroll_view.setVisibility(View.GONE);
        gender_radio_button.setId(0);
        if (NetworkUtil.isNetworkConnected(getContext())) {
            Loader.showMyDialog(getActivity());
            profileHandler.getProfile();
        } else {
            Util.addNetworkNotFoundView(getContext(), child_fragment, this);
        }
        userProfileModel = new UserProfileModel();
        editProfileHandler.getStateList();
        number_submit = false;

        gender_radio_button.setOnCheckedChangeListener((group, checkedId) -> {
            int radioButtonID = group.getCheckedRadioButtonId();
            View radioButton = group.findViewById(radioButtonID);
            int idx = group.indexOfChild(radioButton);

            editUserProfileModel.setGender_id(idx + 1);
            userProfileModel.setGender(idx + 1);
        });

        saveButton.setOnClickListener(v -> {
            submitEdit();
            setPhoneNumber();

            setGAEvent(Constant.GoogleAnalyticsKey.EDIT_SAVE);
        });

        return primary_layout;
    }

    @Override
    public void onPause() {
        super.onPause();
        Loader.dismissMyDialog(getActivity());
    }

    @OnClick(R.id.user_contact_edit)
    public void onContactEditClick() {
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Delete Photo",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add Photo!");
        builder.setItems(items, (dialog, item) -> {
            if (items[item].equals("Take Photo")) {
                dispatchTakePictureIntent(ACTION_TAKE_PHOTO_B);

            } else if (items[item].equals("Choose from Library")) {
                Intent intent = new Intent(getContext(), AlbumSelectActivity.class);
                intent.putExtra(Constants.INTENT_EXTRA_LIMIT, 1);
                startActivityForResult(intent, Constants.REQUEST_CODE);


            } else if (items[item].equals("Delete Photo")) {

                editProfileHandler.deleteProfileImage();
                user_image_view.setImageResource(R.drawable.sample_image);
                Util.toastShort(getContext(), "Photo Deleting");
                getActivity().setResult(RESULT_OK);
                dialog.dismiss();

            } else if (items[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @OnClick(R.id.select_image_view)
    public void onProfileImageClick() {
        if (!MoliticsAppPermission.checkCameraAndWritePermission()) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA},
                    MY_REQUEST_CODE);
        } else {
            selectImage();
        }
    }

    @Override
    public void onProfileDataResponse(APIResponse apiResponse) {
    }

    @Override
    public void onProfileDataApiException(ApiException apiException) {
    }

    @Override
    public void onProfileDataServerError(ServerException serverException) {
        Loader.dismissMyDialog(getActivity());
    }

    @Override
    public void onProfileResponse(APIResponse apiResponse) {
        Loader.dismissMyDialog(getActivity());
        main_scroll_view.setVisibility(View.VISIBLE);

        userProfileModel = apiResponse.getData().getProfileModel();
        handleUi(userProfileModel);
        if (userProfileModel.getStateId() != 0)
            editProfileHandler.getAllLocationFromState(userProfileModel.getStateId());
    }

    @Override
    public void onProfileApiException(ApiException apiException) {

        Loader.dismissMyDialog(getActivity());
        main_scroll_view.setVisibility(View.GONE);
        if (isVisible())
            Util.validateError(getContext(), apiException, child_fragment, this, 0);
    }

    @Override
    public void onProfileServerError(ServerException serverException) {
        Loader.dismissMyDialog(getActivity());
        if (isVisible())
            DialogClass.showAlert(getActivity(), "Something went wrong");
    }

    @Override
    public void onUserFollowResponse(APIResponse apiResponse) {
    }

    @Override
    public void onUserFollowError(ApiException apiException) {
    }

    @Override
    public void onUserUnFollowResponse(APIResponse apiResponse) {
    }

    @Override
    public void onUserUnFollowError(ApiException apiException) {

    }

    @Override
    public void onUserFollowingListResponse(APIResponse apiResponse) {
        ///blank
    }

    @Override
    public void onUserFollowingListError(ApiException apiException) {
/// blank
    }

    private void handleUi(UserProfileModel userProfileModel) {
        String name = userProfileModel.getName();
        String email = userProfileModel.getEmail();
        int state_id = userProfileModel.getStateId();
        int district_id = userProfileModel.getDistrictId();
        int mla_id = userProfileModel.getMlaId();
        int mp_id = userProfileModel.getMpId();
        int political_party_id = userProfileModel.getPoliticalOrientationId();
        String state_name = userProfileModel.getState();
        String district_name = userProfileModel.getDistrict();
        String mp_name = userProfileModel.getMp();
        String mla_name = userProfileModel.getMla();
        String political_party_name = userProfileModel.getPoliticalOrientation();

        contact = userProfileModel.getMobile();
        user_name_edit.setText(userProfileModel.getName());

        user_name_edit.setSelection(user_name_edit.getText().length());
        if (!TextUtils.isEmpty(email)) {
            user_email_edit.setVisibility(View.VISIBLE);
            user_email_edit.setText(email);
        } else {
            user_email_edit.setVisibility(View.GONE);
        }

        user_contact_edit.setText(userProfileModel.getMobile());
        user_profession_edit.setText(userProfileModel.getProfessionName());
        user_state_edit.setText(userProfileModel.getState());
        user_district_edit.setText(userProfileModel.getDistrict());
        user_assembly_edit.setText(userProfileModel.getMla());
        user_lok_sabha_edit.setText(userProfileModel.getMp());
        user_party_edit.setText(userProfileModel.getPoliticalOrientation());

        editUserProfileModel.setState(userProfileModel.getStateId());
        if (userProfileModel.getDistrictId() != 0)
            editUserProfileModel.setDistrict(userProfileModel.getDistrictId());
        if (userProfileModel.getMpId() != 0)
            editUserProfileModel.setMp(userProfileModel.getMpId());
        if (userProfileModel.getMlaId() != 0)
            editUserProfileModel.setMla(userProfileModel.getMlaId());
        if (userProfileModel.getPoliticalOrientationId() != 0)
            editUserProfileModel.setPolitical_orientation(userProfileModel.getPoliticalOrientationId());
        if (userProfileModel.getProfession() != 0)
            editUserProfileModel.setProfession(userProfileModel.getProfession());

        if (userProfileModel.getGender() != 0) {
            RadioButton genderButton = (RadioButton) gender_radio_button.getChildAt(userProfileModel.getGender() - 1);
            if (genderButton != null)
                genderButton.setChecked(true);
        }

        if (!TextUtils.isEmpty(userProfileModel.getImage())) {
            Glide.with(requireContext()).load(userProfileModel.getImage()).into(user_image_view);
        } else {
            user_image_view.setImageResource(R.drawable.sample_image);
        }

    }

    private void submitEdit() {

        editUserProfileModel.setName(user_name_edit.getText().toString());
        editUserProfileModel.setUserBio(userBioTextView.getText().toString());
        userProfileModel.setName(user_name_edit.getText().toString());

        editProfileHandler.submitInfo(editUserProfileModel);

        MultipartBody.Part filePart = null;
        if (!TextUtils.isEmpty(profile_image_url)) {
            File mFile = new File(CompressImage.compressImage(getContext(), profile_image_url));
            filePart = MultipartBody.Part.createFormData("image",
                    mFile.getName(), RequestBody.create(MediaType.parse("image/*"), mFile));
            editProfileHandler.submitProfileImage(filePart);
        }
        //  }
    }


    private void setPhoneNumber() {
        mContact = user_contact_edit.getText().toString().trim();

        if (mContact.length() > 9) {
            if (!contact.equals(mContact)) {
                number_submit = true;
                editContactHandler.submitEditContact(mContact);
                Loader.showMyDialog(getActivity());
            }
        } else if (!TextUtils.isEmpty(mContact)) {
            Util.toastShort(getActivity(), "Please enter a valid number.");
        }
    }

    private void moveToOtpScreen() {
        if (getActivity() != null) {
            ((EditUserProfileActivity) getActivity()).showHeader(true, "Enter OTP");
        }
        Bundle otp_bundle = new Bundle();
        otp_bundle.putString("number", mContact);
        if (getActivity() != null) {
            //  ((EditUserProfileActivity) getActivity()).replaceFragment(new EnterOtp(), otp_bundle, true, false);
        }
    }

    @OnClick(R.id.user_state_edit)
    public void onStateClick() {
        if (state_list == null || state_list.size() == 0) {
            Util.toastShort(getContext(), "Please try again");
        } else {
            SelectLocationDialog locationDialog = SelectLocationDialog.getInstance();
            if (state_list != null) {
                locationDialog.showDialog(getContext(), "State", state_list, new RecyclerTouchWithType() {
                    @Override
                    public void onVerticalRecycler(int position, int type) {
                        SelectLocationDialog.dismissDialog();
                        Loader.showMyDialog(getActivity());
                        user_state_edit.setText(state_list.get(position).getKey());
                        editUserProfileModel.setState(state_list.get(position).getValue());
                        editUserProfileModel.setDistrict(null);
                        editUserProfileModel.setMp(null);
                        editUserProfileModel.setMla(null);
                        editUserProfileModel.setPolitical_orientation(null);
                        //for call back result
                        userProfileModel.setState(state_list.get(position).getKey());
                        userProfileModel.setStateId(state_list.get(position).getValue());
                        userProfileModel.setMla(null);
                        userProfileModel.setMp(null);
                        userProfileModel.setDistrict(null);

                        user_district_edit.setText(getContext().getString(R.string.district_display));
                        user_lok_sabha_edit.setText(getContext().getString(R.string.mp_constituency));
                        user_assembly_edit.setText(getContext().getString(R.string.mla_constituency));
                        user_party_edit.setText(getContext().getString(R.string.political_party));
                        editProfileHandler.getAllLocationFromState(state_list.get(position).getValue());
                    }

                    @Override
                    public void onCloseClick() {

                    }
                });
            }
        }
    }

    @OnClick(R.id.user_district_edit)
    public void onDistrictClick() {
        if (district_list == null || district_list.size() == 0) {
            Util.toastShort(getContext(), "Please select state first.");
        } else {
            SelectLocationDialog locationDialog = SelectLocationDialog.getInstance();
            if (district_list != null) {
                locationDialog.showDialog(getContext(), "District", district_list, new RecyclerTouchWithType() {
                    @Override
                    public void onVerticalRecycler(int position, int type) {
                        SelectLocationDialog.dismissDialog();
                        user_district_edit.setText(district_list.get(position).getKey());
                        editUserProfileModel.setDistrict(district_list.get(position).getValue());
                        userProfileModel.setDistrict(district_list.get(position).getKey());
                        userProfileModel.setDistrictId(district_list.get(position).getValue());
                    }

                    @Override
                    public void onCloseClick() {

                    }
                });
            }
        }
    }

    @OnClick(R.id.user_assembly_edit)
    public void onAssemblyClick() {
        if (mla_list == null || mla_list.size() == 0) {
            Util.toastShort(getContext(), "Please select state first.");
        } else {
            SelectLocationDialog locationDialog = SelectLocationDialog.getInstance();
            if (mla_list != null) {
                locationDialog.showDialog(getContext(), "MLA", mla_list, new RecyclerTouchWithType() {
                    @Override
                    public void onVerticalRecycler(int position, int type) {
                        SelectLocationDialog.dismissDialog();
                        user_assembly_edit.setText(mla_list.get(position).getKey());
                        editUserProfileModel.setMla(mla_list.get(position).getValue());
                        userProfileModel.setMla(mla_list.get(position).getKey());
                        userProfileModel.setMlaId(mla_list.get(position).getValue());
                    }

                    @Override
                    public void onCloseClick() {

                    }
                });
            }
        }

    }

    @OnClick(R.id.user_lok_sabha_edit)
    public void onLokSabhaClick() {
        if (mp_list == null || mp_list.size() == 0) {
            Util.toastShort(getContext(), "Please select state first.");
        } else {
            SelectLocationDialog locationDialog = SelectLocationDialog.getInstance();
            if (mp_list != null) {
                locationDialog.showDialog(getContext(), "MP", mp_list, new RecyclerTouchWithType() {
                    @Override
                    public void onVerticalRecycler(int position, int type) {
                        SelectLocationDialog.dismissDialog();
                        user_lok_sabha_edit.setText(mp_list.get(position).getKey());
                        editUserProfileModel.setMp(mp_list.get(position).getValue());
                        userProfileModel.setMp(mp_list.get(position).getKey());
                        userProfileModel.setMlaId(mla_list.get(position).getValue());
                    }

                    @Override
                    public void onCloseClick() {

                    }
                });
            }
        }
    }

    @OnClick(R.id.user_party_edit)
    public void onPartyClick() {
        if (party_list == null || party_list.size() == 0) {
            Util.toastShort(getContext(), "Please select state first.");

        } else {
            SelectLocationDialog locationDialog = SelectLocationDialog.getInstance();
            if (party_list != null) {
                locationDialog.showDialog(getContext(), "Party", party_list, new RecyclerTouchWithType() {
                    @Override
                    public void onVerticalRecycler(int position, int type) {
                        SelectLocationDialog.dismissDialog();
                        user_party_edit.setText(party_list.get(position).getKey());
                        editUserProfileModel.setPolitical_orientation(party_list.get(position).getValue());
                        userProfileModel.setPoliticalOrientation(party_list.get(position).getKey());
                        userProfileModel.setPoliticalOrientationId(party_list.get(position).getValue());
                    }

                    @Override
                    public void onCloseClick() {

                    }
                });
            }
        }
    }

    @OnClick(R.id.user_profession_edit)
    public void onProfessionClick() {
        if (profession_list == null || profession_list.size() == 0) {
            Util.toastShort(getContext(), "Fetching data");

        } else {
            SelectLocationDialog locationDialog = SelectLocationDialog.getInstance();
            if (profession_list != null) {
                locationDialog.showDialog(getContext(), "Profession", profession_list, new RecyclerTouchWithType() {
                    @Override
                    public void onVerticalRecycler(int position, int type) {
                        SelectLocationDialog.dismissDialog();
                        user_profession_edit.setText(profession_list.get(position).getKey());
                        editUserProfileModel.setProfession(profession_list.get(position).getValue());
                        userProfileModel.setProfessionName(profession_list.get(position).getKey());
                        userProfileModel.setProfession(profession_list.get(position).getValue());
                    }

                    @Override
                    public void onCloseClick() {

                    }
                });
            }
        }
    }

    @Override
    public void onRefereshClick() {
        Loader.showMyDialog(getActivity());
        profileHandler.getProfile();
    }

    @Override
    public void onNetworkAvailable() {
        onRefereshClick();
    }

    @Override
    public void onSubmitResponse(APIResponse apiResponse) {
        if (isVisible()) {
            Loader.dismissMyDialog(getActivity());
            Util.saveEditUserInfo(apiResponse.getData().getProfileModel());
            Util.saveEditUserPreference(apiResponse.getData().getProfileModel());
            if (userProfileModel != null) {
                Intent mIntent = new Intent();
                mIntent.putExtra(Constant.IntentKey.USER_PROFILE_MODEL, userProfileModel);
                getActivity().setResult(RESULT_OK, mIntent);
                if (contact != null && (!contact.equals(user_contact_edit.getText().toString().trim()))) {

                } else if (!TextUtils.isEmpty(profile_image_url)) {

                } else {
                    Util.toastShort(getActivity(), getString(R.string.profile_save));
                    getActivity().finish();
                }
            }
        }
    }

    @Override
    public void onSubmitApiException(ApiException apiException) {
        if (isVisible())
            Loader.dismissMyDialog(getActivity());
    }

    @Override
    public void onStateListResponse(APIResponse apiResponse) {

        if (apiResponse.getData().getState_list() != null) {
            state_list = apiResponse.getData().getState_list();
            profession_list = apiResponse.getData().getProfession_list();
        }
    }

    @Override
    public void onStateListApiException(ApiException apiException) {
        Loader.dismissMyDialog(getActivity());
    }

    @Override
    public void onStateSelection(APIResponse apiResponse) {
        Loader.dismissMyDialog(getActivity());
        if (apiResponse.getData().getDistrict_list() != null) {
            district_list = apiResponse.getData().getDistrict_list();
        }
        if (apiResponse.getData().getMp_list() != null) {
            mp_list = apiResponse.getData().getMp_list();
        }
        if (apiResponse.getData().getMla_list() != null) {
            mla_list = apiResponse.getData().getMla_list();
        }
        if (apiResponse.getData().getPolitical_party() != null) {
            party_list = apiResponse.getData().getPolitical_party();
        }
    }

    @Override
    public void onSubmitServerError(ServerException serverException) {
        Loader.dismissMyDialog(getActivity());
    }

    @Override
    public void onProfileImageResponse(APIResponse apiResponse) {
        if (getActivity() != null && !number_submit) {
            getActivity().finish();
        }
    }

    @Override
    public void onProfileImageError(ApiException apiException) {

    }

    @Override
    public void onProfileImageDeleteResponse(APIResponse apiResponse) {
    }

    @Override
    public void onProfileImageDeleteError(ApiException apiException) {

    }

    @Override
    public void onContactResponse(APIResponse apiResponse) {
        if (isVisible()) {
            Loader.dismissMyDialog(getActivity());
            moveToOtpScreen();
        }
    }

    @Override
    public void onContactApiException(ApiException apiException) {
        if (isVisible()) {
            Loader.dismissMyDialog(getActivity());

            if (apiException.getCode() == 2005) {
                moveToOtpScreen();
            } else {
                Util.validateError(getContext(), apiException, null, null, 0);
            }
        }
    }

    @Override
    public void onOtpResponse(APIResponse apiResponse) {

    }

    @Override
    public void onOtpApiException(ApiException apiException) {

    }

    @Override
    public void onContactServerError(ServerException serverException) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_IMAGE_REQUEST_CODE) {

            // CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                Uri uri = data.getData();
                String[] projection = {MediaStore.Images.Media.DATA};

                Cursor cursor = getActivity().getContentResolver().query(uri
                        , projection, null, null, null);
                if (cursor != null) {
                    cursor.moveToFirst();

                    // Log.d(TAG, DatabaseUtils.dumpCursorToString(cursor));

                    int columnIndex = cursor.getColumnIndex(projection[0]);
                    String picturePath = cursor.getString(columnIndex); // returns null
                    cursor.close();

                    setPic(picturePath);
                }
            }
        }
        if (requestCode == Constants.REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            //The array list has the image_small_icon paths of the selected images
            ArrayList<Image> images = data.getParcelableArrayListExtra(Constants.INTENT_EXTRA_IMAGES);
            if (images.size() != 0) {
                setPic(images.get(0).path);
            }
        }

        if (requestCode == ACTION_TAKE_PHOTO_B) {
            if (resultCode == RESULT_OK) {
                if (mCurrentPhotoPath != null) {
                    setPic(mCurrentPhotoPath);
                    mCurrentPhotoPath = null;
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectImage();
                // Now user should be able to use camera
            } else {
                Toast.makeText(getContext(), "No Permission", Toast.LENGTH_SHORT).show();
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

                        Uri photoURI = FileProvider.getUriForFile(getContext(),
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
        } // switch

        startActivityForResult(takePictureIntent, actionCode);
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

    private void setPic(String image_url) {
        profile_image_url = image_url;
        /* There isn't enough memory to open up more than a couple camera photos */
        /* So pre-scale the target bitmap into which the file is decoded */

        /* Get the size of the ImageView */
        int targetW = user_image_view.getWidth();
        int targetH = user_image_view.getHeight();

        /* Get the size of the image_small_icon */
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(image_url, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        /* Figure out which way needs to be reduced less */
        int scaleFactor = 1;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        }

        /* Set bitmap options to scale the image_small_icon decode target */
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        /* Decode the JPEG file into a Bitmap */
        Bitmap bitmap = BitmapFactory.decodeFile(image_url, bmOptions);

        /* Associate the Bitmap to the ImageView */
        user_image_view.setImageBitmap(bitmap);
    }

    private void setGAEvent(String action) {
        GAEventTracking.googleEventTracker(getActivity(), Constant.GoogleAnalyticsKey.USER_PROFILE, action);
    }
}

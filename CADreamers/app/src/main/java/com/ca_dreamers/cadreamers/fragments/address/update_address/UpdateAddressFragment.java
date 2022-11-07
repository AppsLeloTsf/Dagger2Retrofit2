package com.ca_dreamers.cadreamers.fragments.address.update_address;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;

import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.api.Api;
import com.ca_dreamers.cadreamers.api.RetrofitClient;
import com.ca_dreamers.cadreamers.models.address.add_update_address.ModelAddUpdateAddress;
import com.ca_dreamers.cadreamers.storage.SharedPrefManager;
import com.ca_dreamers.cadreamers.utils.Constant;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ca_dreamers.cadreamers.utils.Constant.TAG;

public class UpdateAddressFragment extends Fragment {


    private Context context;
    private String strUserId;
    private String strAddId;
    private String strAddAdhar = "";
    private String strAction;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.etAddAddressName)
    protected EditText etAddAddressName;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.etAddAddressPhone)
    protected EditText etAddAddressPhone;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.etAddAddressAddress)
    protected EditText etAddAddressAddress;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.etAddAddressCity)
    protected EditText etAddAddressCity;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.etAddAddressState)
    protected EditText etAddAddressState;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.etAddAddressCountry)
    protected EditText etAddAddressCountry;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.etAddAddressPinCode)
    protected EditText etAddAddressPinCode;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ivUploadId)
    protected ImageView ivAddAddressAdhar;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btnAddAddressSubmit)
    protected AppCompatButton btnAddAddressSubmit;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_address, container, false);
        ButterKnife.bind(this, view);
        context = (Activity)getContext();
        SharedPrefManager sharedPrefManager = new SharedPrefManager(context);
        assert context != null;
        Glide.with(context).load(R.drawable.logo_main_192).into(ivAddAddressAdhar);
        strUserId = sharedPrefManager.getUserId();
        if (getArguments() != null) {
            strAddId = getArguments().getString(Constant.ADDRESS_ID);
            strAction = getArguments().getString(Constant.ADDRESS_ACTION);
            strAddAdhar = getArguments().getString(Constant.ADDRESS_ADHAR);
        }
        else {
            strAddId = "";
            strAction = "";
            strAddAdhar = "";
        }


        if(!strAddAdhar.equals("")) {
            Glide.with(context).load(strAddAdhar).into(ivAddAddressAdhar);
        }else {
            Glide.with(context).load(R.drawable.logo_main_192).into(ivAddAddressAdhar);
        }
        etAddAddressName.setText(getArguments().getString(Constant.ADDRESS_NAME));
        etAddAddressPhone.setText(getArguments().getString(Constant.ADDRESS_MOBILE));
        etAddAddressAddress.setText(getArguments().getString(Constant.ADDRESS_ADDRESS));
        etAddAddressPinCode.setText(getArguments().getString(Constant.ADDRESS_PIN_CODE));

        if (strAction.equals("add")){
            btnAddAddressSubmit.setText("Add");
        }else if (strAction.equals("update")){
            btnAddAddressSubmit.setText("Update");
        }
        return view;
    }

    private void callAddAddressApi(){
        String strAddName = etAddAddressName.getText().toString().trim();
        String strAddMobile = etAddAddressPhone.getText().toString().trim();
        String strAddAddress = etAddAddressAddress.getText().toString().trim();
        String strAddCity = etAddAddressCity.getText().toString().trim();
        String strAddState = etAddAddressState.getText().toString().trim();
        String strAddPinCode = etAddAddressPinCode.getText().toString().trim();

        BitmapDrawable drawable = (BitmapDrawable) ivAddAddressAdhar.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        strAddAdhar = BitMapToString(bitmap);
        Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
        JsonObject gsonObject;
        try {
            JSONObject paramObject = new JSONObject();

            paramObject.put("addrid", strAddId);
            paramObject.put("name", strAddName);
            paramObject.put("mobile", strAddMobile);
            paramObject.put("address", strAddAddress);
            paramObject.put("city", strAddCity);
            paramObject.put("state", strAddState);
            paramObject.put("country", "India");
            paramObject.put("pincode", strAddPinCode);
            paramObject.put("aadhar_card", strAddAdhar);
            paramObject.put("userid", strUserId);
            paramObject.put("action", strAction);

            gsonObject = (JsonObject) JsonParser.parseString(paramObject.toString());
            Call<ModelAddUpdateAddress> call = api.addUpdateAddress(gsonObject);

            call.enqueue(new Callback<ModelAddUpdateAddress>() {
                @Override
                public void onResponse(@NotNull Call<ModelAddUpdateAddress> call, @NotNull Response<ModelAddUpdateAddress> response) {
                    ModelAddUpdateAddress modelAddUpdateAddress = response.body();
                        Navigation.findNavController(requireView()).popBackStack(R.id.nav_address, false);
                    if (modelAddUpdateAddress != null) {
                        Toast.makeText(getContext(), modelAddUpdateAddress.getMessage().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    btnAddAddressSubmit.setEnabled(true);
                }

                @Override
                public void onFailure(@NotNull Call<ModelAddUpdateAddress> call, @NotNull Throwable t) {
                    btnAddAddressSubmit.setEnabled(true);
                    Log.d(TAG, "Addrerr Failure: "+t.getMessage());
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,10, baos);
        byte [] b=baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.ivUploadId)
    public void ivUploadIdClicked(){
        Dexter.withContext(context)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            selectImage();
                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }
    ActivityResultLauncher<Intent> cameraActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    assert result.getData() != null;
                    Bitmap photo = (Bitmap) result.getData().getExtras().get("data");
                    ivAddAddressAdhar.setImageBitmap(photo);
                    Log.d("TSF_APPS", "Photo picked from Camera");


                }
            });
    ActivityResultLauncher<Intent> galleryActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    assert result.getData() != null;
                    Uri selectedImageURI = result.getData().getData();
                    Glide.with(context).load(selectedImageURI).centerCrop()
                            .into(ivAddAddressAdhar);
                    Log.d("TSF_APPS", "Photo picked from Gallery");
                }
            });
    private void selectImage() {


        final CharSequence[] options = { "Take Photo", "Choose from Gallery", "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Add Photo!");
        builder.setItems(options, (dialog, item) -> {
            if (options[item].equals("Take Photo"))
            {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                cameraActivityResultLauncher.launch(cameraIntent);
            }
            else if (options[item].equals("Choose from Gallery"))
            {
                Intent intent = new   Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryActivityResultLauncher.launch(intent);

            }
            else if (options[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btnAddAddressSubmit)
    public void btnAddAddressSubmitClicked(){
        if (etAddAddressName.getText().equals("")){
            etAddAddressName.setError("Mandatory field can't be empty");
        }

        else if (etAddAddressPhone.getText().equals("")){
            etAddAddressPhone.setError("Mandatory field can't be empty");
        }
       else if (etAddAddressAddress.getText().equals("")){
            etAddAddressAddress.setError("Mandatory field can't be empty");
        }

       else if (etAddAddressCity.getText().equals("")){
            etAddAddressCity.setError("Mandatory field can't be empty");
        }
       else if (etAddAddressState.getText().equals("")){
            etAddAddressState.setError("Mandatory field can't be empty");
        }

       else if (etAddAddressCountry.getText().equals("")){
            etAddAddressCountry.setError("Mandatory field can't be empty");
        }
        else if (etAddAddressPinCode.getText().equals("")){
             etAddAddressPinCode.setError("Mandatory field can't be empty");
        }
        else {
             callAddAddressApi();
             btnAddAddressSubmit.setEnabled(false);
        }

    }
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {

                    Toast.makeText(context, "Opened Setting", Toast.LENGTH_SHORT).show();

                }
            });

    private void openSomeActivityForResult() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        someActivityResultLauncher.launch(intent);
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(getString(R.string.dialog_permission_title));
        builder.setMessage(getString(R.string.dialog_permission_message));
        builder.setPositiveButton(getString(R.string.go_to_settings), (dialog, which) -> {
            dialog.cancel();
            openSomeActivityForResult();
        });
        builder.setNegativeButton(getString(android.R.string.cancel), (dialog, which) -> dialog.cancel());
        builder.show();

    }
}
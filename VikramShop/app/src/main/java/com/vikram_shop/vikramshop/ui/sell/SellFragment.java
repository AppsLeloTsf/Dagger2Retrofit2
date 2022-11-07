package com.vikram_shop.vikramshop.ui.sell;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.BuildConfig;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.vikram_shop.vikramshop.R;
import com.vikram_shop.vikramshop.utils.Constant;
import com.vikram_shop.vikramshop.utils.FileCompressor;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

public class SellFragment extends Fragment {

    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_GALLERY_PHOTO = 2;
    File mPhotoFile;
    FileCompressor mCompressor;

    private SellViewModel mViewModel;
    private String strDeliverTo;
    private int deliverToIndex;
    final int[] checkedTime = {-1};
    final int[] checkedWeight= {-1};
    private int selectedPackage = 0;
    private int selectedTime = 0;
    private int selectedWeight = 0;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tvDeliverTo)
    protected TextView tvDeliverTo;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tvPackageType)
    protected TextView tvPackageType;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tvDeliveryTiming)
    protected TextView tvDeliveryTiming;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.tvProductWeight)
    protected TextView tvProductWeight;


    // initialise the list items for the alert dialog
    final String[] productList = new String[]{"Medicine", "Food Items", "Books / Paper", "Electronics Items",
            "House Hold Items", "Cloths / Accessories", "Others Items"};

    final boolean[] checkedPackage = new boolean[productList.length];

    // copy the items from the main list to the selected item list
    // for the preview if the item is checked then only the item
    // should be displayed for the user
    final List<String> selectedItems = Arrays.asList(productList);


    public static SellFragment newInstance() {
        return new SellFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sell_fragment, container, false);
        ButterKnife.bind(this, view);
        deliverToIndex = getArguments().getInt(Constant.DELIVER_TO_INDEX);
        strDeliverTo = getArguments().getString(Constant.DELIVER_TO);
        tvDeliverTo.setText(strDeliverTo);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SellViewModel.class);
        // TODO: Use the ViewModel
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.tvPackageType)
    public void tvPackageTypeClicked(){

        // initially set the null for the text preview
        tvPackageType.setText(null);

        // initialise the alert dialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        // set the title for the alert dialog
        builder.setTitle("Choose Items");

        // set the icon for the alert dialog
        builder.setIcon(R.drawable.add_circle);

        // now this is the function which sets the alert dialog for multiple item selection ready
        builder.setMultiChoiceItems(productList, checkedPackage, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                checkedPackage[which] = isChecked;
                String currentItem = selectedItems.get(which);
            }
        });

        // alert dialog shouldn't be cancellable
        builder.setCancelable(false);

        // handle the positive button of the dialog
        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (int i = 0; i < checkedPackage.length; i++) {
                    if (checkedPackage[i]) {
                        tvPackageType.setText(tvPackageType.getText() + selectedItems.get(i) + ", ");
                    }
                    else {
                        tvPackageType.setText("Package Type");
                    }
                }
            }
        });

        // handle the negative button of the alert dialog
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        // handle the neutral button of the dialog to clear
        // the selected items boolean checkedItem
        builder.setNeutralButton("CLEAR ALL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for (int i = 0; i < checkedPackage.length; i++) {
                    checkedPackage[i] = false;
                }
            }
        });

        // create the builder
        builder.create();

        // create the alert dialog with the
        // alert dialog builder instance
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @OnClick(R.id.tvUploadProductPicture)
    public void tvUploadProductPictureClicked(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.custom_dialog_image, null);
        builder.setView(customLayout);
        ImageView ivProductImage = customLayout.findViewById(R.id.ivProductImage);
        ImageView ivSelf = customLayout.findViewById(R.id.ivSelf);
        ImageView ivAdharFront = customLayout.findViewById(R.id.ivAdharFront);
        ImageView ivAdharBack = customLayout.findViewById(R.id.ivAdharBack);
        Button btnSubmitDialog = customLayout.findViewById(R.id.btnSubmitDialog);
        Button btnCancelDialog = customLayout.findViewById(R.id.btnCancelDialog);

        ivProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        btnSubmitDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Submitted Successfully", Toast.LENGTH_SHORT)
                        .show();
            }
        });

        btnCancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void sendDialogDataToActivity(String data)
    {
        Toast.makeText(getContext(), data, Toast.LENGTH_SHORT)
                .show();
    }
    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.tvDeliveryTiming)
    public void tvDeliveryTimingClicked(){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setIcon(R.drawable.ic_add_circle);

        alertDialog.setTitle("Select Delivery Timing");

        final String[] listItems = new String[]{"In 30 minutes", "In 1 hour", "In 4 hours", "In 24 hours"};

        alertDialog.setSingleChoiceItems(listItems, checkedTime[0], new DialogInterface.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(DialogInterface dialog, int which) {

                checkedTime[0] = which;
                selectedTime = which;
            }
        });
        alertDialog.setPositiveButton("Okay",  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tvDeliveryTiming.setText(listItems[selectedTime]);
            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog customAlertDialog = alertDialog.create();
        customAlertDialog.show();
    }

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.tvProductWeight)
    public void tvProductWeightClicked(){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setIcon(R.drawable.ic_add_circle);

        alertDialog.setTitle("Select Product Weight");

        final String[] listItems = new String[]{"Under 2 kg", "Under 5 kg", "Under 10 kg"};

        alertDialog.setSingleChoiceItems(listItems, checkedWeight[0], new DialogInterface.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(DialogInterface dialog, int which) {

                checkedWeight[0] = which;
                selectedWeight = which;
            }
        });
        alertDialog.setPositiveButton("Okay",  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tvProductWeight.setText(listItems[selectedWeight]);
            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog customAlertDialog = alertDialog.create();
        customAlertDialog.show();

    }
    private void selectImage() {
        final CharSequence[] items = {
                "Take Photo", "Choose from Library",
                "Cancel"
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setItems(items, (dialog, item) -> {
            if (items[item].equals("Take Photo")) {
                requestStoragePermission(true);
            } else if (items[item].equals("Choose from Library")) {
                requestStoragePermission(false);
            } else if (items[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
                // Error occurred while creating the File
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        BuildConfig.APPLICATION_ID + ".provider",
                        photoFile);
                mPhotoFile = photoFile;
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private void dispatchGalleryIntent() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(pickPhoto, REQUEST_GALLERY_PHOTO);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_PHOTO) {
                try {
                    mPhotoFile = mCompressor.compressToFile(mPhotoFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                Glide.with(getContext())
//                        .load(mPhotoFile)
//                        .apply(new RequestOptions().centerCrop()
//                                .circleCrop()
//                                .placeholder(R.drawable.add_circle))
//                        .into(ivProductImage);
            } else if (requestCode == REQUEST_GALLERY_PHOTO) {
                Uri selectedImage = data.getData();
                try {
                    mPhotoFile = mCompressor.compressToFile(new File(getRealPathFromUri(selectedImage)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                Glide.with(getContext())
//                        .load(mPhotoFile)
//                        .apply(new RequestOptions().centerCrop()
//                                .circleCrop()
//                                .placeholder(R.drawable.add_circle))
//                        .into(ivProductImage);
            }
        }
    }
    private void requestStoragePermission(boolean isCamera) {
        Dexter.withActivity(getActivity())
                .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            if (isCamera) {
                                dispatchTakePictureIntent();
                            } else {
                                dispatchGalleryIntent();
                            }
                        }
                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }

                })
                .withErrorListener(
                        error -> Toast.makeText(getContext(), "Error occurred! ", Toast.LENGTH_SHORT)
                                .show())
                .onSameThread()
                .check();
    }
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Need Permissions");
        builder.setMessage(
                "This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }
    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }
    /**
     * Create file with current timestamp name
     *
     * @throws IOException
     */
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String mFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File mFile = File.createTempFile(mFileName, ".jpg", storageDir);
        return mFile;
    }
    /**
     * Get real file path from URI
     */
    public String getRealPathFromUri(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = getContext().getContentResolver().query(contentUri, proj, null, null, null);
            assert cursor != null;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

//    @OnClick(R.id.ivProductImage)
//    public void ivProductImageClicked(){
//        selectImage();
//    }

}
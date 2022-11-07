package com.ca_dreamers.cadreamers.activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.api.Api;
import com.ca_dreamers.cadreamers.api.RetrofitClient;
import com.ca_dreamers.cadreamers.models.version_detail.ModelVersion;
import com.ca_dreamers.cadreamers.storage.SharedPrefManager;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {
    private static final int REQUEST_READ_PHONE_STATE = 321;
    private ActivityResultLauncher<Intent> someActivityResultLauncher;
    private SharedPrefManager sharedPrefManager;
    private Context tContext;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.pbSplashScreen)
    protected ProgressBar pbSplashScreen;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.ivSplashScreen)
    protected ImageView ivSplashScreen;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        pbSplashScreen.setVisibility(View.VISIBLE);
        ivSplashScreen.setVisibility(View.GONE);
        tContext = getApplicationContext();
        sharedPrefManager = new SharedPrefManager(tContext);

        if(isConnectingToInternet(SplashActivity.this)){

            requestPermissions();
            someActivityResultLauncher = registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            setResult(REQUEST_READ_PHONE_STATE);
                        }
                    });

        }else {
           pbSplashScreen.setVisibility(View.GONE);
           ivSplashScreen.setVisibility(View.VISIBLE);
           AlertDialog dialogUpdateApp;
           AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
           final View customLayout = getLayoutInflater().inflate(R.layout.custom_login_status, null);
           builder.setView(customLayout);
           AppCompatTextView tvUpdateNote = customLayout.findViewById(R.id.tvLoginStatus);
           tvUpdateNote.setText("Please check your internet connection !!!");
           AppCompatButton btnLoginStatus = customLayout.findViewById(R.id.btnLoginCancel);
           btnLoginStatus.setOnClickListener(v -> finishAffinity());
           dialogUpdateApp = builder.create();
           dialogUpdateApp.getWindow().setBackgroundDrawableResource(R.color.white);
           dialogUpdateApp.show();
           dialogUpdateApp.setCancelable(false);
       }

    }
    private void init() {
        Handler handler = new Handler();
        int SPLASH_TIME_OUT = 3000;
        handler.postDelayed(() -> {
            handler.postDelayed(() -> {
                if (!sharedPrefManager.getUserId().equals("")) {
                    Intent intent = new Intent(tContext, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(tContext, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }

            }, 1500);
            pbSplashScreen.setVisibility(View.GONE);
            ivSplashScreen.setVisibility(View.VISIBLE);
        }, SPLASH_TIME_OUT);
    }



    public static boolean isConnectingToInternet(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
    public void requestPermissions() {

        Dexter.withContext(this)

                .withPermissions(Manifest.permission.READ_PHONE_STATE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        if (multiplePermissionsReport.areAllPermissionsGranted()) {
                            init();
                        }
                        if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }
                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).withErrorListener(error -> Toast.makeText(getApplicationContext(), "Error Occurred! "+error.toString(), Toast.LENGTH_SHORT).show())
                .onSameThread().check();
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);

        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs phone state permission to protect your account from un authorize login. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", (dialog, which) -> {
            dialog.cancel();
            openSomeActivityForResult();
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            Toast.makeText(SplashActivity.this, "You must have to enable the phone state permission to protect your account from un authorize login.", Toast.LENGTH_SHORT).show();
            showSettingsDialog();
            dialog.cancel();
        });
        builder.show();
    }
    public void openSomeActivityForResult() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        someActivityResultLauncher.launch(intent);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_READ_PHONE_STATE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                init();
            } else {
                showSettingsDialog();
            }
        }
    }
}
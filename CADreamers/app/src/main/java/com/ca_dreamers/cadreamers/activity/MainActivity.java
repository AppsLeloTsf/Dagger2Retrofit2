 package com.ca_dreamers.cadreamers.activity;

import static android.os.Environment.isExternalStorageEmulated;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ca_dreamers.cadreamers.R;
import com.ca_dreamers.cadreamers.api.Api;
import com.ca_dreamers.cadreamers.api.RetrofitClient;
import com.ca_dreamers.cadreamers.models.cart.fetch_cart.ModelFetchCart;
import com.ca_dreamers.cadreamers.models.logged_out.ModelLogout;
import com.ca_dreamers.cadreamers.models.notification.notification_count.ModelNotificationCount;
import com.ca_dreamers.cadreamers.models.profile.user_image.ModelProfileImage;
import com.ca_dreamers.cadreamers.models.user_token.ModelToken;
import com.ca_dreamers.cadreamers.storage.SharedPrefManager;
import com.ca_dreamers.cadreamers.utils.Constant;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;


 public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

     static final int MY_PERMISSIONS_REQUEST_WRITE_STORAGE = 123;
    private ActivityResultLauncher<Intent> someActivityResultLauncher;
    private AppBarConfiguration mAppBarConfiguration;
    private SharedPrefManager sharedPrefManager;
    private String strUserId;
    private String strToken;
    private NavController navController;
    private MenuItem menuItem;

     private ImageView imageViewUser;

     TextView textCartItemCount;
     TextView textNotificationCount;
     int mCartItemCount = 0;
     int mNotificationCount = 0;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.toolbar)
    protected Toolbar toolbar;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.nav_view)
    protected NavigationView navigationView;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.drawer_layout)
    protected DrawerLayout drawer;
    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.bottomNavigationView)
    protected BottomNavigationView bottomNavigationView;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        setContentView(R.layout.activity_main);


        ButterKnife.bind(this);


        requestPermissions();
        someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        setResult(MY_PERMISSIONS_REQUEST_WRITE_STORAGE);
                    }
                });

        if (!isConnectingToInternet(this)) {
            Toast.makeText(this, "Please Connect to Internet", Toast.LENGTH_LONG).show();
        }
        setSupportActionBar(toolbar);

        sharedPrefManager = new SharedPrefManager(this);
        String strUserPic = getIntent().getStringExtra(Constant.USER_PRO_PIC);
        strUserId = sharedPrefManager.getUserId();
        callTokenApi();

        View headerView = navigationView.getHeaderView(0);
        final TextView tvUserName =  (TextView) headerView.findViewById(R.id.tvUserName);
        tvUserName.setText("Hello "+sharedPrefManager.getUserName());
        imageViewUser =  (ImageView)headerView.findViewById(R.id.ivProfileImage);
        Glide.with(MainActivity.this).load(strUserPic).into(imageViewUser);
        callProfileImageApi();
        imageViewUser.setOnClickListener(v -> {
            Navigation.findNavController(findViewById(R.id.nav_host_fragment)).navigate(R.id.nav_profile);
            drawer.closeDrawer(GravityCompat.START);
        });
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.bottom_courses, R.id.bottom_batch, R.id.bottom_free_courses, R.id.bottom_books,
                R.id.nav_my_courses, R.id.nav_my_books, R.id.nav_offline_books, R.id.nav_offline_courses)
                .setOpenableLayout(drawer)
                .build();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);

    }
     @Override
     public void onStart() {
         super.onStart();
         callTokenApi();

         final int REQUEST_CODE_ASK_PERMISSIONS = 123;
         if( ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
             ActivityCompat.requestPermissions(this,
                     new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                     REQUEST_CODE_ASK_PERMISSIONS);
         }
     }

     @Override
     protected void onRestart() {
         super.onRestart();
         callTokenApi();
     }

     @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

         menuItem = menu.findItem(R.id.menu_cart);
        final MenuItem menuNotification = menu.findItem(R.id.menu_notification);
        View actionView = menuItem.getActionView();
        View viewNotification = menuNotification.getActionView();
        textCartItemCount = (TextView) actionView.findViewById(R.id.cart_badge);
        textNotificationCount = (TextView) viewNotification.findViewById(R.id.tvBadgeNotification);
        callFetchCartApi();
        callGetNotificationCountApi();
        actionView.setOnClickListener(v -> onOptionsItemSelected(menuItem));
        viewNotification.setOnClickListener(v -> onOptionsItemSelected(menuNotification));
        return true;
    }

     @Override
     public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
         return NavigationUI.onNavDestinationSelected(item, navController)
                 || super.onOptionsItemSelected(item);
     }

     @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.nav_update_app:
                UpdateApp();
                break;
            case R.id.nav_invite_friends:
               ShareApp();
                break;
         case R.id.nav_sign_out:
                callLogoutApi();
                break;
        }
        NavigationUI.onNavDestinationSelected(item,navController);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

     public void callProfileImageApi(){
         Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
         JsonObject gsonObject;
         try {
             JSONObject paramObject = new JSONObject();
             paramObject.put("userid", strUserId);
             paramObject.put("encryptfile", "a");
             paramObject.put("type", "");
             gsonObject = (JsonObject) JsonParser.parseString(paramObject.toString());
             Call<ModelProfileImage> call = api.getUserImage(gsonObject);
             call.enqueue(new Callback<ModelProfileImage>() {
                 @Override
                 public void onResponse(@NonNull Call<ModelProfileImage> call, @NonNull Response<ModelProfileImage> response) {
                     ModelProfileImage modelProfileImage = response.body();
                     assert modelProfileImage != null;
                     Glide.with(MainActivity.this).load(modelProfileImage.getData().getFileUrl()).into(imageViewUser);
                 }

                 @Override
                 public void onFailure(@NonNull Call<ModelProfileImage> call, @NonNull Throwable t) {

                 }
             });

         } catch (JSONException e) {
             e.printStackTrace();
         }
     }

     public void callLogoutApi(){
         Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
         JsonObject gsonObject;
         try {
             JSONObject paramObject = new JSONObject();
             paramObject.put("user_id", strUserId);
             gsonObject = (JsonObject) JsonParser.parseString(paramObject.toString());
             Call<ModelLogout> call = api.getLogout(gsonObject);
             call.enqueue(new Callback<ModelLogout>() {
                 @Override
                 public void onResponse(@NonNull Call<ModelLogout> call, @NonNull Response<ModelLogout> response) {
                     sharedPrefManager.clearUserData();
                     startActivity(new Intent(MainActivity.this, LoginActivity.class));
                     finishAffinity();
                 }

                 @Override
                 public void onFailure(@NonNull Call<ModelLogout> call, @NonNull Throwable t) {
                     Log.d("TSF_APPS", "Logout Error: "+t);

                 }
             });

         } catch (JSONException e) {
             e.printStackTrace();
         }
     }
     public void callTokenApi(){
         Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
         JsonObject gsonObject;
         try {
             JSONObject paramObject = new JSONObject();
             paramObject.put("user_id", strUserId);
             gsonObject = (JsonObject) JsonParser.parseString(paramObject.toString());
             Call<ModelToken> call = api.getTokenApi(gsonObject);
             call.enqueue(new Callback<ModelToken>() {
                 @Override
                 public void onResponse(@NonNull Call<ModelToken> call, @NonNull Response<ModelToken> response) {
                     ModelToken modelToken = response.body();
                     assert modelToken != null;
                     strToken = modelToken.getData().getToken();
                     if (!strToken.equals(getUserDeviceId())){
                         callLogoutApi();
                         Toast.makeText(MainActivity.this, "Some on logged in with your credentials.", Toast.LENGTH_SHORT).show();
                     }
                     Log.d("TSF_APPS", "TOKEN RESPONSE: "+strToken);

                 }

                 @Override
                 public void onFailure(@NonNull Call<ModelToken> call, @NonNull Throwable t) {
                     Log.d("TSF_APPS", "TOKEN Error: "+t);

                 }
             });

         } catch (JSONException e) {
             e.printStackTrace();
         }
     }
     public void callFetchCartApi(){
         Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
         JsonObject gsonObject;
         try {
             JSONObject paramObject = new JSONObject();
             paramObject.put("userid", strUserId);
             gsonObject = (JsonObject) JsonParser.parseString(paramObject.toString());
             Call<ModelFetchCart> call = api.fetchCart(gsonObject);

             call.enqueue(new Callback<ModelFetchCart>() {
                 @Override
                 public void onResponse(@NotNull Call<ModelFetchCart> call, @NotNull Response<ModelFetchCart> response) {
                     ModelFetchCart modelFetchCart = response.body();

                     if (modelFetchCart != null) {
                         mCartItemCount = modelFetchCart.getData().getTotItem();

                     if (mCartItemCount==0){
                         textCartItemCount.setVisibility(View.GONE);
                         menuItem.setEnabled(false);
                     }else {
                         textCartItemCount.setVisibility(View.VISIBLE);
                         textCartItemCount.setText(String.valueOf(Math.min(mCartItemCount, 99)));
                     }
                     }
                 }

                 @Override
                 public void onFailure(@NotNull Call<ModelFetchCart> call, @NotNull Throwable t) {

                 }
             });
         } catch (JSONException e) {
             e.printStackTrace();
         }
     }
     public void callGetNotificationCountApi(){
         Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
         JsonObject gsonObject;
         try {
             JSONObject paramObject = new JSONObject();
             paramObject.put("user_id", strUserId);
             gsonObject = (JsonObject) JsonParser.parseString(paramObject.toString());
             Call<ModelNotificationCount> call = api.getNotificationCount(gsonObject);

             call.enqueue(new Callback<ModelNotificationCount>() {
                 @Override
                 public void onResponse(@NonNull Call<ModelNotificationCount> call, @NonNull Response<ModelNotificationCount> response) {
                     ModelNotificationCount modelNotificationCount = response.body();
                     assert modelNotificationCount != null;
                     if (modelNotificationCount.getData().getTotalNotification().equals("0")){
                         textNotificationCount.setVisibility(View.GONE);

                     }else {
                         textNotificationCount.setVisibility(View.VISIBLE);
                         mNotificationCount = Integer.parseInt(modelNotificationCount.getData().getTotalNotification());
                         textNotificationCount.setText(String.valueOf(Math.min(mNotificationCount, 99)));
                     }
                 }

                 @Override
                 public void onFailure(@NonNull Call<ModelNotificationCount> call, @NonNull Throwable t) {

                 }
             });
         } catch (JSONException e) {
             e.printStackTrace();
         }
     }

     public void UpdateApp(){
         AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(this);
         Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
         appUpdateInfoTask.addOnSuccessListener(result -> {

             if (result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                 android.view.ContextThemeWrapper ctw = new android.view.ContextThemeWrapper(this,R.style.Theme_MaterialComponents_Dialog_Alert);
                 final android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(ctw);
                 alertDialogBuilder.setTitle("Update CA Dreamers");
                 alertDialogBuilder.setCancelable(false);
                 alertDialogBuilder.setIcon(R.drawable.logo_main_192);
                 alertDialogBuilder.setMessage("CA Dreamers recommends that you update to the latest version for a seamless & enhanced performance of the app.");
                 alertDialogBuilder.setPositiveButton("Update", (dialog, id) -> {
                     try{
                         startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id="+getPackageName())));
                     }
                     catch (ActivityNotFoundException e){
                         startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id="+getPackageName())));
                     }
                 });
                 alertDialogBuilder.setNegativeButton("No Thanks", (dialog, id) -> {

                 });
                 alertDialogBuilder.show();

             } else {

                 Toast.makeText(this, "App is up to date.", Toast.LENGTH_SHORT).show();
             }
         });
     }

     private void ShareApp(){
         Intent shareIntent = new Intent(Intent.ACTION_SEND);
         shareIntent.setType("text/plain");
         shareIntent.putExtra(Intent.EXTRA_SUBJECT, "CA Dreamers");
         String shareMessage= "\nLet me recommend you the CA Dreamers App for the best preparation for CA.\nDownload by the given link\n\n";
         shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + this.getPackageName() +"\n\n";
         shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
         startActivity(Intent.createChooser(shareIntent, "Share with"));
     }





     @Override
     public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
         super.onRequestPermissionsResult(requestCode, permissions, grantResults);
         if (requestCode == MY_PERMISSIONS_REQUEST_WRITE_STORAGE) {
             if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                 checkFolderCourses();
                 checkFolderBooks();
             } else {
                showSettingsDialog();
             }
         }
     }

     public void checkFolderCourses() {

         File file;
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
             file = new File(this.getExternalFilesDir(null) + Environment.DIRECTORY_MOVIES + File.separator + Constant.FOLDER_COURSES);
         } else {
             file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() , Environment.DIRECTORY_MOVIES + File.separator + Constant.FOLDER_COURSES);
         }

         if (!file.exists()) {
             file.mkdirs();
         }
     }
     public void checkFolderBooks() {
         File file;
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
             file = new File(this.getExternalFilesDir(null) + Environment.DIRECTORY_MOVIES + File.separator + Constant.FOLDER_BOOKS);
         } else {
             file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() , Environment.DIRECTORY_MOVIES + File.separator + Constant.FOLDER_BOOKS);
         }

         if (!file.exists()) {
             file.mkdirs();
         }
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

                 .withPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                 .withListener(new MultiplePermissionsListener() {
                     @Override
                     public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                         if (multiplePermissionsReport.areAllPermissionsGranted()) {
                             checkFolderCourses();
                             checkFolderBooks();
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
         AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

         builder.setTitle("Need Permissions");
         builder.setMessage("This app needs storage permission to download videos for offline mode. You can grant them in app settings.");
         builder.setPositiveButton("GOTO SETTINGS", (dialog, which) -> {
             dialog.cancel();
             openSomeActivityForResult();
         });
         builder.setNegativeButton("Cancel", (dialog, which) -> {
             Toast.makeText(MainActivity.this, "You must have to enable the storage permission to save the videos for offline.", Toast.LENGTH_SHORT).show();
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

     @SuppressLint("HardwareIds")
     public String getUserDeviceId() {
         String deviceId;

         if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
             deviceId = Settings.Secure.getString(
                     getContentResolver(),
                     Settings.Secure.ANDROID_ID);
         } else {
             final TelephonyManager mTelephony = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
             if (mTelephony.getDeviceId() != null) {
                 deviceId = mTelephony.getDeviceId();
             } else {
                 deviceId = Settings.Secure.getString(
                         getContentResolver(),
                         Settings.Secure.ANDROID_ID);
             }
         }

         return deviceId;
     }

 }
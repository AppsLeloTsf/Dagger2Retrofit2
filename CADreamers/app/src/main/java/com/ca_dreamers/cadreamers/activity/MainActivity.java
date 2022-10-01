 package com.ca_dreamers.cadreamers;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ca_dreamers.cadreamers.activity.LoginActivity;
import com.ca_dreamers.cadreamers.adapter.cart.AdapterFetchCart;
import com.ca_dreamers.cadreamers.api.Api;
import com.ca_dreamers.cadreamers.api.RetrofitClient;
import com.ca_dreamers.cadreamers.models.cart.fetch_cart.ModelFetchCart;
import com.ca_dreamers.cadreamers.models.notification.ModelNotification;
import com.ca_dreamers.cadreamers.models.notification.notification_count.ModelNotificationCount;
import com.ca_dreamers.cadreamers.models.profile.ModelUserInfo;
import com.ca_dreamers.cadreamers.models.profile.user_image.ModelProfileImage;
import com.ca_dreamers.cadreamers.storage.SharedPrefManager;
import com.ca_dreamers.cadreamers.utils.Constant;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.appupdate.AppUpdateOptions;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import androidx.annotation.NonNull;
import androidx.core.app.ShareCompat;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

 public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static final int MY_REQUEST_CODE = 101;
    private AppBarConfiguration mAppBarConfiguration;
    private SharedPrefManager sharedPrefManager;
    private String strUserId;
    private NavController navController;
    private MenuItem menuItem;

    private String strUserPic;
    private ImageView imageViewUser;

     TextView textCartItemCount;
     TextView textNotificationCount;
     int mCartItemCount = 10;
     int mNotificationCount = 10;

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;
    @BindView(R.id.nav_view)
    protected NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    protected DrawerLayout drawer;
    @BindView(R.id.bottomNavigationView)
    protected BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        sharedPrefManager = new SharedPrefManager(this);
        strUserPic = getIntent().getStringExtra(Constant.USER_PRO_PIC);
        strUserId = sharedPrefManager.getUserId();


        callFetchCartApi();
        callGetNotificationCountApi();

        View headerView = navigationView.getHeaderView(0);
        final TextView tvUserName =  (TextView) headerView.findViewById(R.id.tvUserName);
        tvUserName.setText("Hello "+sharedPrefManager.getUserName());
        imageViewUser =  (ImageView)headerView.findViewById(R.id.ivProfileImage);
        callProfileImageApi();
        imageViewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(findViewById(R.id.nav_host_fragment)).navigate(R.id.nav_profile);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.bottom_courses, R.id.bottom_top_20, R.id.bottom_free_videos, R.id.bottom_books,
                R.id.nav_my_courses, R.id.nav_my_books)
                .setOpenableLayout(drawer)
                .build();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        final MenuItem menuItem = menu.findItem(R.id.menu_cart);
        final MenuItem menuNotification = menu.findItem(R.id.menu_notification);
        View actionView = menuItem.getActionView();
        View viewNotification = menuNotification.getActionView();
        textCartItemCount = (TextView) actionView.findViewById(R.id.cart_badge);
        textNotificationCount = (TextView) viewNotification.findViewById(R.id.tvBadgeNotification);


        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });
        viewNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuNotification);
            }
        });
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
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "CA Dreamers");
                String shareMessage= "\nLet me recommend you the CA Dreamers App for the best preparation for CA.\nDownload by the given link\n\n";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + this.getPackageName() +"\n\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "Share with"));

                break;
         case R.id.nav_sign_out:
                sharedPrefManager.clearUserData();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finishAffinity();
                break;
        }
        NavigationUI.onNavDestinationSelected(item,navController);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_REQUEST_CODE) {
            if (resultCode != RESULT_OK) {

            }
        }
    }
     public void callProfileImageApi(){
         Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
         JsonObject gsonObject = new JsonObject();
         try {
             JSONObject paramObject = new JSONObject();
             paramObject.put("userid", strUserId);
             paramObject.put("encryptfile", "a");
             paramObject.put("type", "");


             JsonParser jsonParser = new JsonParser();
             gsonObject = (JsonObject) jsonParser.parse(paramObject.toString());
             Call<ModelProfileImage> call = api.getUserImage(gsonObject);
             call.enqueue(new Callback<ModelProfileImage>() {
                 @Override
                 public void onResponse(Call<ModelProfileImage> call, Response<ModelProfileImage> response) {
                     ModelProfileImage modelProfileImage = response.body();
                     Glide.with(MainActivity.this).load(modelProfileImage.getData().getFileUrl()).into(imageViewUser);

                 }

                 @Override
                 public void onFailure(Call<ModelProfileImage> call, Throwable t) {

                 }
             });

         } catch (JSONException e) {
             e.printStackTrace();
         }
     }
     public void callFetchCartApi(){
         Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
         JsonObject gsonObject = new JsonObject();
         try {
             JSONObject paramObject = new JSONObject();
             paramObject.put("userid", strUserId);

             JsonParser jsonParser = new JsonParser();
             gsonObject = (JsonObject) jsonParser.parse(paramObject.toString());
             Call<ModelFetchCart> call = api.fetchCart(gsonObject);

             call.enqueue(new Callback<ModelFetchCart>() {
                 @Override
                 public void onResponse(Call<ModelFetchCart> call, Response<ModelFetchCart> response) {
                     ModelFetchCart modelFetchCart = response.body();
                     if (modelFetchCart.getStatus().getStatuscode().equals("200")){
                         textCartItemCount.setVisibility(View.VISIBLE);
                         mCartItemCount = modelFetchCart.getData().getTotItem();
                         textCartItemCount.setText(String.valueOf(Math.min(mCartItemCount, 99)));
                     }else {
                         textCartItemCount.setVisibility(View.GONE);

                     }
                 }

                 @Override
                 public void onFailure(Call<ModelFetchCart> call, Throwable t) {
                     textCartItemCount.setVisibility(View.GONE);
                 }
             });
         } catch (JSONException e) {
             e.printStackTrace();
         }
     }
     public void callGetNotificationCountApi(){
         Api api = RetrofitClient.createService(Api.class, "cadreamers", "cadreamers@123");
         JsonObject gsonObject = new JsonObject();
         try {
             JSONObject paramObject = new JSONObject();
             paramObject.put("user_id", strUserId);
             JsonParser jsonParser = new JsonParser();
             gsonObject = (JsonObject) jsonParser.parse(paramObject.toString());
             Call<ModelNotificationCount> call = api.getNotificationCount(gsonObject);

             call.enqueue(new Callback<ModelNotificationCount>() {
                 @Override
                 public void onResponse(Call<ModelNotificationCount> call, Response<ModelNotificationCount> response) {
                     ModelNotificationCount modelNotificationCount = response.body();
                     if (modelNotificationCount.getData().getTotalNotification().equals("0")){
                         textNotificationCount.setVisibility(View.GONE);

                     }else {
                         textNotificationCount.setVisibility(View.VISIBLE);
                         mNotificationCount = Integer.parseInt(modelNotificationCount.getData().getTotalNotification());
                         textNotificationCount.setText(String.valueOf(Math.min(mNotificationCount, 99)));
                     }
                 }

                 @Override
                 public void onFailure(Call<ModelNotificationCount> call, Throwable t) {

                 }
             });
         } catch (JSONException e) {
             e.printStackTrace();
         }
     }

     public void UpdateApp(){
         AppUpdateManager appUpdateManager = AppUpdateManagerFactory.create(this);
         Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
         // Checks that the platform will allow the specified type of update.
         appUpdateInfoTask.addOnSuccessListener(result -> {

             if (result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
//                requestUpdate(result);
                 android.view.ContextThemeWrapper ctw = new android.view.ContextThemeWrapper(this,R.style.Theme_MaterialComponents_Dialog_Alert);
                 final android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(ctw);
                 alertDialogBuilder.setTitle("Update CA Dreamers");
                 alertDialogBuilder.setCancelable(false);
                 alertDialogBuilder.setIcon(R.drawable.logo_main_192);
                 alertDialogBuilder.setMessage("CA Dreamers recommends that you update to the latest version for a seamless & enhanced performance of the app.");
                 alertDialogBuilder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog, int id) {
                         try{
                             startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id="+getPackageName())));
                         }
                         catch (ActivityNotFoundException e){
                             startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id="+getPackageName())));
                         }
                     }
                 });
                 alertDialogBuilder.setNegativeButton("No Thanks",new DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog, int id) {

                     }
                 });
                 alertDialogBuilder.show();

             } else {

                 Toast.makeText(this, "App is up to date.", Toast.LENGTH_SHORT).show();
             }
         });
     }

 }
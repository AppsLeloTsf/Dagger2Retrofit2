package com.indianjourno.indianjourno.activity;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import com.indianjourno.indianjourno.activity.ij.news.AllNewsActivity;
import com.indianjourno.indianjourno.adapter.AdapterCategory;
import com.indianjourno.indianjourno.adapter.AdapterPagerFragment;
import com.indianjourno.indianjourno.adapter.AutoScrollPagerAdapter;
import com.indianjourno.indianjourno.api.RetrofitClient;
import com.indianjourno.indianjourno.fragment.FragmentHome;
import com.indianjourno.indianjourno.model.ModelCategory;
import com.indianjourno.indianjourno.model.category.CategoryList;
import com.indianjourno.indianjourno.model.feature.FeatureMessage;
import com.indianjourno.indianjourno.model.feature.FeaturesID;
import com.indianjourno.indianjourno.model.sliders.SliderMessage;
import com.indianjourno.indianjourno.storage.SharedPrefManager;
import com.indianjourno.indianjourno.utils.AutoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import indianjourno.indianjourno.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, TabLayout.OnTabSelectedListener {

    private static final int DEFAULT_OFFSCREEN_PAGES = 1;
    private static final int AUTO_SCROLL_THRESHOLD_IN_MILLI = 4000;

    private Context tContext;
    private String strCatId;
    private String strCatName;
    @BindView(R.id.toolbar_detail)
    protected Toolbar toolbar;
    @BindView(R.id.container_main)
    protected FrameLayout container_main;
    @BindView(R.id.pager)
    protected ViewPager tViewPager;
    @BindView(R.id.tabLayout)
    protected TabLayout tTabLayout;
    @BindView(R.id.bottomNavigationView)
    protected BottomNavigationView bottomNavigationView;
    @BindView(R.id.view_pager)
    protected AutoScrollViewPager viewPager;
    @SuppressLint({"ResourceAsColor", "NonConstantResourceId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        initActivity();
        bottomNavigationView.setSelectedItemId(R.id.bottom_home);
        bottomNavigationView.setOnItemSelectedListener(item -> {
           switch (item.getItemId()){
               case R.id.bottom_home:
                   startActivity(new Intent(this, MainActivity.class));
                   return true;

               case R.id.bottom_account:
                   startActivity(new Intent(this, LoginActivity.class));
                   return true;

               case R.id.bottom_category:
                   startActivity(new Intent(this, CategoryListActivity.class));
                   return true;

               case R.id.bottom_video:
                   startActivity(new Intent(this, AllNewsActivity.class));

                   return true;
           }

            return true;
        });


//        initDrawer(toolbar);
        container_main.setVisibility(View.VISIBLE);
        tViewPager.setVisibility(View.GONE);

        getSupportFragmentManager().beginTransaction().replace(R.id.container_main, new FragmentHome()).commit();
    }

    private void initActivity(){
        tContext = MainActivity.this;
        RecyclerView.LayoutManager tRvManager = new LinearLayoutManager(this);
    }



    @Override
    public void onBackPressed() {
            super.onBackPressed();
    }

//    @OnClick(R.id.ivLogout)
//    public void ivLogoutClicked(View view){
//    tSharedPrefManager.clearUserData();
//    startActivity(new Intent(MainActivity.this, LoginActivity.class));
//    finishAffinity();
//    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        container_main.setVisibility(View.GONE);
        tViewPager.setVisibility(View.VISIBLE);
        tViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.menu_home:
//                tSharedPrefManager.clearUserData();
//                startActivity(new Intent(MainActivity.this, MainActivity.class));
//                finishAffinity();
//                return true;
//            case R.id.menu_bookmark:
//                startActivity(new Intent(MainActivity.this, BookmarkActivity.class));
//                return true;
//            case R.id.menu_feedback:
//                return true;
//            case R.id.menu_term:
//                Intent termIntent = new Intent(MainActivity.this, PrivacyActivity.class);
//                termIntent.putExtra(Constant.CAT_ID, "2");
//                startActivity(termIntent);
//
//                return true;
//            case R.id.menu_privacy:
//                Intent privacyIntent = new Intent(MainActivity.this, PrivacyActivity.class);
//                privacyIntent.putExtra(Constant.CAT_ID, "1");
//                startActivity(privacyIntent);
//                return true;
//            case R.id.menu_share:
//                try {
//                Intent shareIntent = new Intent(Intent.ACTION_SEND);
//                shareIntent.setType("text/plain");
//                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "StepOn");
//                String shareMessage= "\nLet me recommend you the Janata Suddi News App for the best and fast News Feature in Kannada.\nDownload by the given link\n\n";
//                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + Constant.APP_ID +"\n\n";
//                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
//                startActivity(Intent.createChooser(shareIntent, "Share with"));
//            } catch(Exception e) {
//                //e.toString();
//            }
//                return true;
//            case R.id.menu_rate:{
//                rateApp();
//               return true;
//            }
//            case R.id.menu_app_version:{
//                Toast.makeText(tContext, "The current app is: "+getString(R.string.app_version), Toast.LENGTH_SHORT).show();
//               return true;
//            }case R.id.menu_contact_us:{
//                startActivity(new Intent(MainActivity.this, ContactActivity.class));
//                return true;
//            }
//            case R.id.menu_logout:
//                tSharedPrefManager.clearUserData();
//                startActivity(new Intent(MainActivity.this, LoginActivity.class));
//                finishAffinity();
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
    public void rateApp()
    {
        try
        {
            Intent rateIntent = rateIntentForUrl("market://details");
            startActivity(rateIntent);
        }
        catch (ActivityNotFoundException e)
        {
            Intent rateIntent = rateIntentForUrl("https://play.google.com/store/apps/details");
            startActivity(rateIntent);
        }
    }

    private Intent rateIntentForUrl(String url)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s?id=%s", url, getPackageName())));
        int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
        if (Build.VERSION.SDK_INT >= 23)
        {
            flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
        }
        else
        {
            //noinspection deprecation
            flags |= Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;
        }
        intent.addFlags(flags);
        return intent;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}

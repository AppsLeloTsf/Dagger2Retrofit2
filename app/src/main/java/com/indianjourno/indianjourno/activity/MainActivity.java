package com.indainjourno.indianjourno.activity;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.janatasuddi.janatasuddinews.R;
import com.janatasuddi.janatasuddinews.adapter.AdapterCategory;
import com.janatasuddi.janatasuddinews.adapter.AdapterPagerFragment;
import com.janatasuddi.janatasuddinews.adapter.AutoScrollPagerAdapter;
import com.janatasuddi.janatasuddinews.api.RetrofitClient;
import com.janatasuddi.janatasuddinews.fragment.FragmentHome;
import com.janatasuddi.janatasuddinews.model.ModelCategory;
import com.janatasuddi.janatasuddinews.model.category.CategoryList;
import com.janatasuddi.janatasuddinews.model.feature.FeatureMessage;
import com.janatasuddi.janatasuddinews.model.feature.FeaturesID;
import com.janatasuddi.janatasuddinews.model.sliders.SliderMessage;
import com.janatasuddi.janatasuddinews.storage.SharedPrefManager;
import com.janatasuddi.janatasuddinews.utils.AutoScrollViewPager;
import com.janatasuddi.janatasuddinews.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, TabLayout.OnTabSelectedListener {
    private static final int DEFAULT_OFFSCREEN_PAGES = 1;
    private static final int AUTO_SCROLL_THRESHOLD_IN_MILLI = 4000;
    private List<ModelCategory> tModels;
    private SharedPrefManager tSharedPrefManager;
    private FragmentManager tFragmentManager;
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
    @BindView(R.id.rv_inside_nav)
    protected RecyclerView rvNav;
    @BindView(R.id.pbMainActivity)
    protected ProgressBar pbMainActivity;
    @BindView(R.id.drawer_layout)
    protected DrawerLayout drawer;
    @BindView(R.id.main_nav_view)
    protected NavigationView navigationView;
    @BindView(R.id.view_pager)
    protected AutoScrollViewPager viewPager;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        initActivity();
        initDrawer(toolbar);
        container_main.setVisibility(View.VISIBLE);
        tViewPager.setVisibility(View.GONE);

        getSupportFragmentManager().beginTransaction().replace(R.id.container_main, new FragmentHome()).commit();
    }

    private void initActivity(){
        tContext = MainActivity.this;
        tFragmentManager = getSupportFragmentManager();
        RecyclerView.LayoutManager tManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        tSharedPrefManager = new SharedPrefManager(tContext);
        pbMainActivity.setVisibility(View.VISIBLE);
        callSliderApi();
        callApi();
        callCatApi();
        RecyclerView.LayoutManager tRvManager = new LinearLayoutManager(this);
        rvNav.setLayoutManager(tRvManager);
    }

    private void callCatApi(){
        Call<CategoryList> call = RetrofitClient.getInstance().getApi().getCategory();
        call.enqueue(new Callback<CategoryList>() {
            @Override
            public void onResponse(Call<CategoryList> call, Response<CategoryList> response) {
                CategoryList tModel = response.body();
                AdapterCategory tAdapter = new AdapterCategory(getApplicationContext(), tModel.getCategory(), drawer);
                rvNav.setAdapter(tAdapter);
            }
            @Override
            public void onFailure(Call<CategoryList> call, Throwable t) {

            }
        });
    }
    private void callSliderApi() {
        Call<SliderMessage> call = RetrofitClient.getInstance().getApi().getSlider();
        call.enqueue(new Callback<SliderMessage>() {
            @Override
            public void onResponse(Call<SliderMessage> call, Response<SliderMessage> response) {
                SliderMessage tModelsCat = response.body();
                AutoScrollPagerAdapter autoScrollPagerAdapter =
                        new AutoScrollPagerAdapter(tModelsCat.getVideoCategory(), MainActivity.this);
                viewPager.setAdapter(autoScrollPagerAdapter);
                TabLayout tabs = findViewById(R.id.tabs);
                tabs.setupWithViewPager(viewPager);
                // start auto scroll
                viewPager.startAutoScroll();
                // set auto scroll time in mili
                viewPager.setInterval(AUTO_SCROLL_THRESHOLD_IN_MILLI);
                // enable recycling using true
                viewPager.setCycle(true);

            }
            @Override
            public void onFailure(Call<SliderMessage> call, Throwable t) {

            }
        });
    }

    private void callApi(){
       Call<FeatureMessage> call = RetrofitClient.getInstance().getApi().getFeatureCategory();
       call.enqueue(new Callback<FeatureMessage>() {
    @Override
    public void onResponse(Call<FeatureMessage> call, Response<FeatureMessage> response) {
        FeatureMessage tModelsCat = response.body();
        pbMainActivity.setVisibility(View.GONE);

//         Log.d("Feature ID", tModelsCat.getFeaturesID().get(1).getNewsTypeId());
//         strCatName = tModelsCat.getFeaturesID().get(1).getNewsTypeName();

        ArrayList<FeaturesID> categories = new ArrayList<>(tModelsCat.getFeaturesID());
        for (int i = 0; i<tModelsCat.getFeaturesID().size(); i++) {
            tTabLayout.addTab(tTabLayout.newTab().setText(tModelsCat.getFeaturesID().get(i).getNewsTypeName()));
        }
        tTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(tViewPager));
        tViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tTabLayout));
        AdapterPagerFragment tPager = new AdapterPagerFragment(getSupportFragmentManager(), tTabLayout.getTabCount(), categories, tModelsCat.getFeaturesID());
        tViewPager.setAdapter(tPager);
    }

    @Override
    public void onFailure(Call<FeatureMessage> call, Throwable t) {

    }
       });
    }
    private void initDrawer(Toolbar toolbar){
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @OnClick(R.id.ivLogout)
    public void ivLogoutClicked(View view){
    tSharedPrefManager.clearUserData();
    startActivity(new Intent(MainActivity.this, LoginActivity.class));
    finishAffinity();
    }

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_home:
                tSharedPrefManager.clearUserData();
                startActivity(new Intent(MainActivity.this, MainActivity.class));
                finishAffinity();
                return true;
            case R.id.menu_bookmark:
                startActivity(new Intent(MainActivity.this, BookmarkActivity.class));
                return true;
            case R.id.menu_feedback:
                return true;
            case R.id.menu_term:
                Intent termIntent = new Intent(MainActivity.this, PrivacyActivity.class);
                termIntent.putExtra(Constant.CAT_ID, "2");
                startActivity(termIntent);

                return true;
            case R.id.menu_privacy:
                Intent privacyIntent = new Intent(MainActivity.this, PrivacyActivity.class);
                privacyIntent.putExtra(Constant.CAT_ID, "1");
                startActivity(privacyIntent);
                return true;
            case R.id.menu_share:
                try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "StepOn");
                String shareMessage= "\nLet me recommend you the Janata Suddi News App for the best and fast News Feature in Kannada.\nDownload by the given link\n\n";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + Constant.APP_ID +"\n\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "Share with"));
            } catch(Exception e) {
                //e.toString();
            }
                return true;
            case R.id.menu_rate:{
                rateApp();
               return true;
            }
            case R.id.menu_app_version:{
                Toast.makeText(tContext, "The current app is: "+getString(R.string.app_version), Toast.LENGTH_SHORT).show();
               return true;
            }case R.id.menu_contact_us:{
                startActivity(new Intent(MainActivity.this, ContactActivity.class));
                return true;
            }
            case R.id.menu_logout:
                tSharedPrefManager.clearUserData();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finishAffinity();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
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
}

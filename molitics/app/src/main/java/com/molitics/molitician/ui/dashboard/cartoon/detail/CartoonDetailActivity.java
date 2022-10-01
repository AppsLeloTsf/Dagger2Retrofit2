package com.molitics.molitician.ui.dashboard.cartoon.detail;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.molitics.molitician.BasicAcivity;
import com.molitics.molitician.R;
import com.molitics.molitician.ui.dashboard.cartoon.cartoonAdapter.CartoonViewPagerAdapter;
import com.molitics.molitician.ui.dashboard.cartoon.cartoonModel.AllCartoonModel;
import com.molitics.molitician.util.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rahul on 30/05/18.
 */

public class CartoonDetailActivity extends BasicAcivity {

    @BindView(R.id.common_viewpager)
    ViewPager common_viewpager;

    private CartoonViewPagerAdapter cartoonViewPagerAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager);
        ButterKnife.bind(this);
        bindUI();
        manageIntentData();
    }

    private void manageIntentData() {

        Intent mBundle = getIntent();
        List<AllCartoonModel> cartoonModelList = null;
        if (mBundle != null) {

            cartoonModelList = mBundle.getParcelableArrayListExtra(Constant.IntentKey.CARTOON_LIST);
            cartoonViewPagerAdapter.addCartoonList(cartoonModelList);
            int mPosition = mBundle.getIntExtra(Constant.IntentKey.POSITION, 0);
            common_viewpager.setCurrentItem(mPosition);
        }
    }

    public void bindUI() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        cartoonViewPagerAdapter = new CartoonViewPagerAdapter(fragmentManager);
        common_viewpager.setAdapter(cartoonViewPagerAdapter);
    }
}

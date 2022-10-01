package com.molitics.molitician.ui.dashboard.cartoon.cartoonAdapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.molitics.molitician.ui.dashboard.cartoon.cartoonModel.AllCartoonModel;
import com.molitics.molitician.ui.dashboard.cartoon.detail.CartoonDetailHolderFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rahul on 30/05/18.
 */

public class CartoonViewPagerAdapter extends FragmentPagerAdapter {

    private List<AllCartoonModel> cartoonList = new ArrayList<>();

    public CartoonViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addCartoonList(List<AllCartoonModel> cartoonModels) {
        this.cartoonList = cartoonModels;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return CartoonDetailHolderFragment.getInstance(position, cartoonList.get(position));
    }

    @Override
    public int getCount() {
        return cartoonList.size();
    }
}

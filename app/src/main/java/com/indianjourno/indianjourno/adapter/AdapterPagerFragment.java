package com.indianjourno.indianjourno.adapter;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.indianjourno.indianjourno.fragment.NewsFragment;
import com.indianjourno.indianjourno.model.feature.FeaturesID;
import com.indianjourno.indianjourno.utils.Constant;

import java.util.ArrayList;
import java.util.List;

public class AdapterPagerFragment extends FragmentStatePagerAdapter {

    public static int pos = 0;


    private List<FeaturesID> tModelsCategory;
    private ArrayList<FeaturesID> arrFeatureId;
    private int tabCount;
    public AdapterPagerFragment(FragmentManager fm, int tabCount, ArrayList<FeaturesID> arrFeatureId,
                                List<FeaturesID> tModelsCategory) {
        super(fm);
        this.tabCount = tabCount;
        this.arrFeatureId = arrFeatureId;
        this.tModelsCategory = tModelsCategory;
    }

    @Override
    public Fragment getItem(int position) {

        Log.d("Feature Id:", arrFeatureId.get(position).getNewsTypeId());
        Log.d("Feature Name:", arrFeatureId.get(position).getNewsTypeName());
        Fragment fragment = NewsFragment.newInstance(arrFeatureId.get(position).getNewsTypeId(),
                arrFeatureId.get(position).getNewsTypeName(),
                position, tModelsCategory);
        return fragment;
    }
    @Override
    public int getCount() {
        return tabCount;
    }

    public static void setPos(int pos) {
        AdapterPagerFragment.pos = pos;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Log.e(Constant.TAG, arrFeatureId.get(position).getNewsTypeId());
        setPos(position);
        return  arrFeatureId.get(position).getNewsTypeName();
    }

    public static int getPos() {
        return pos;
    }

}

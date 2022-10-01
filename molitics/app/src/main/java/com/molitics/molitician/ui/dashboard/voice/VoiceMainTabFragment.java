package com.molitics.molitician.ui.dashboard.voice;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.material.tabs.TabLayout;
import com.molitics.molitician.R;
import com.molitics.molitician.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rahul on 02/01/18.
 */

public class VoiceMainTabFragment extends Fragment {


    @BindView(R.id.tab_layout)
    TabLayout tab_layout;
    @BindView(R.id.leader_view_pager)
    ViewPager leader_view_pager;

    private View mView;
    private static final VoiceMainTabFragment ourInstance = new VoiceMainTabFragment();

    public static VoiceMainTabFragment getInstance() {
        return ourInstance;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.adapter_voice_main_fragment, container, false);
        ButterKnife.bind(this, mView);
        getToolBar();

        VoiceMainTabAdapter leaderParentAdapter = new VoiceMainTabAdapter(getChildFragmentManager(), Util.getVoiceFragmentTitle(), Util.getVoiceDisplayType());
        leader_view_pager.setAdapter(leaderParentAdapter);
        leader_view_pager.setOffscreenPageLimit(2);
        tab_layout.setupWithViewPager(leader_view_pager);

        return mView;
    }

    private void getToolBar() {
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        RelativeLayout center_toolbar_rl = toolbar.findViewById(R.id.center_toolbar_rl);
        if (center_toolbar_rl != null)
            center_toolbar_rl.removeAllViews();
    }
}

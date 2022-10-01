package com.molitics.molitician.ui.dashboard.newsDetail.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.molitics.molitician.model.News;
import com.molitics.molitician.ui.dashboard.newsDetail.NewsDetailFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rahul  on 12/19/2016.
 */

public class DetailNewsAdapter extends FragmentPagerAdapter {

    private List<News> newsDetailList = new ArrayList<>();

    public DetailNewsAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addNewsList(List<News> newsDetailList) {
        this.newsDetailList.addAll(newsDetailList);
        notifyDataSetChanged();
    }

    public void addNews(News newsDetail) {
        this.newsDetailList.add(newsDetail);
        notifyDataSetChanged();
    }

    public void updateList(int position, News news) {
        this.newsDetailList.set(position, news);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return newsDetailList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return NewsDetailFragment.getInstance(position, newsDetailList.get(position));

    }
}

package com.molitics.molitician.ui.dashboard.articles;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.molitics.molitician.model.News;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rahul on 7/14/2017.
 */

public class ArticleDetailPagerAdapter extends FragmentStatePagerAdapter {

    List<News> allArticle = new ArrayList<>();

    public ArticleDetailPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addArticle(List<News> allArticle) {
        this.allArticle = allArticle;
        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public void updateArticle(int position, News article) {
        this.allArticle.set(position, article);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        News mNews = allArticle.get(position);
        return ArticleDetailFragment.getInstance(mNews.getId(), mNews.getImage(), mNews.getHeading(), mNews.getContent(),
                mNews.getTime(), mNews.getAuthor_name());
    }

    @Override
    public int getCount() {
        return allArticle.size();
    }
}

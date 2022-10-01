package com.molitics.molitician.ui.dashboard.nationalNews.interfacess;

/**
 * Created by rohitkumar on 10/10/16.
 */
public interface OnNewsItemClick {
    void onNewsRecyclerClick(int position, int type);

    void onVideoClick(int position, String video_url);

    default void onLanguageSelectionClick(int language, int page) {

    }

}



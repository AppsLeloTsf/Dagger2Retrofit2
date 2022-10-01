package com.molitics.molitician.util;


import com.molitics.molitician.database.LocalNewsTagLeaderModelDemo;
import com.molitics.molitician.database.NewsBookMarkModel;
import com.molitics.molitician.model.News;
import com.molitics.molitician.ui.dashboard.leader.AllLeaderModel;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;

public class ConvertUtils {

    public static ArrayList<AllLeaderModel> convertRealmListToLeaderList(RealmList<LocalNewsTagLeaderModelDemo> leaderModels) {
        ArrayList<AllLeaderModel> leaderModelList = new ArrayList<>();
        for (LocalNewsTagLeaderModelDemo data : leaderModels) {
            AllLeaderModel singleData = new AllLeaderModel();
            singleData.setId(data.getId());
            singleData.setName(data.getName());
            singleData.setProfileImage(data.getProfileImage());
            leaderModelList.add(singleData);
        }
        return leaderModelList;
    }

    public static RealmList<LocalNewsTagLeaderModelDemo> convertLeaderListToRealm(List<AllLeaderModel> data) {

        RealmList<LocalNewsTagLeaderModelDemo> leaderList = new RealmList<>();

        for (int i = 0; i < data.size(); i++) {
            AllLeaderModel allLeaderModel = data.get(i);
            LocalNewsTagLeaderModelDemo singleData = new LocalNewsTagLeaderModelDemo();
            singleData.setId(allLeaderModel.getId());
            singleData.setName(allLeaderModel.getName());
            singleData.setProfileImage(allLeaderModel.getProfileImage());
            leaderList.add(singleData);
        }
        return leaderList;
    }


    public static ArrayList<News> convertToBookmarkNews(List<NewsBookMarkModel> newsBookMarkModels) {
        ArrayList<News> newsList = new ArrayList<>();
        for (NewsBookMarkModel newsBookMark : newsBookMarkModels) {
            News mNewsObject = new News();
            mNewsObject.setImage(newsBookMark.getImage());
            mNewsObject.setId(newsBookMark.getId());
            mNewsObject.setType(newsBookMark.getType());
            mNewsObject.setTime(newsBookMark.getTime());
            mNewsObject.setHeading(newsBookMark.getHeading());
            mNewsObject.setContent(newsBookMark.getContent());
            mNewsObject.setLink(newsBookMark.getLink());
            mNewsObject.setSource(newsBookMark.getSource());
            mNewsObject.setSourceLogo(newsBookMark.getSourceLogo());
            mNewsObject.setDisplayType(newsBookMark.getDisplayType());
            mNewsObject.setShareLink(newsBookMark.getShare_link());
            mNewsObject.setSourceId(newsBookMark.getSourceId());
            newsList.add(mNewsObject);
        }
        return newsList;
    }
}

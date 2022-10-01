package com.molitics.molitician.db.creator.feed;


import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.molitics.molitician.ui.dashboard.voice.model.FeedActionModel;
import com.molitics.molitician.ui.dashboard.voice.model.FeedImageModel;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Rakesh Kumar on 17/09/2021.
 */

public class FeedActionCreator {

    @TypeConverter
    public static FeedActionModel fromString(String value) {
        Type listType = new TypeToken<FeedActionModel>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(FeedActionModel feedActionModel) {
        Gson gson = new Gson();
        return gson.toJson(feedActionModel);
    }


}
package com.molitics.molitician.db.creator.feed;


import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.molitics.molitician.ui.dashboard.voice.model.FeedImageModel;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Rakesh Kumar on 17/09/2021.
 */

public class FeedImageCreator {

    @TypeConverter
    public static ArrayList<FeedImageModel> fromString(String value) {
        Type listType = new TypeToken<ArrayList<FeedImageModel>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<FeedImageModel> feedImageModel) {
        Gson gson = new Gson();
        return gson.toJson(feedImageModel);
    }


}
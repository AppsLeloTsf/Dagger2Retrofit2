package com.molitics.molitician.db.creator.feed;


import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.molitics.molitician.ui.dashboard.voice.model.LeaderModel;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Rakesh Kumar on 17/09/2021.
 */

public class LeaderListCreator {

    @TypeConverter
    public static ArrayList<LeaderModel> fromString(String value) {
        Type listType = new TypeToken<ArrayList<LeaderModel>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<LeaderModel> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }


}
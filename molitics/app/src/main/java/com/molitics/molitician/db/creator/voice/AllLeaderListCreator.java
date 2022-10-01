package com.molitics.molitician.db.creator.voice;


import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.molitics.molitician.ui.dashboard.leader.AllLeaderModel;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by kailash on 23/2/18.
 */

public class AllLeaderListCreator {

    @TypeConverter
    public static ArrayList<AllLeaderModel> fromString(String value) {
        Type listType = new TypeToken<ArrayList<AllLeaderModel>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<AllLeaderModel> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }


}
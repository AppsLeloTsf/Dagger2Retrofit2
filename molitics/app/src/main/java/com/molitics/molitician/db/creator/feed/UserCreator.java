package com.molitics.molitician.db.creator.feed;


import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.molitics.molitician.ui.dashboard.voice.model.UsersFeedModel;

import java.lang.reflect.Type;

/**
 * Created by Rakesh Kumar on 17/09/2021.
 */

public class UserCreator {

    @TypeConverter
    public static UsersFeedModel fromString(String value) {
        Type listType = new TypeToken<UsersFeedModel>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(UsersFeedModel usersFeedModel) {
        Gson gson = new Gson();
        return gson.toJson(usersFeedModel);
    }


}
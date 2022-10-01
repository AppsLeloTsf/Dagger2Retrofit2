package com.molitics.molitician.db.creator.voice;


import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.molitics.molitician.ui.dashboard.voice.model.VoiceImageModel;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class RYVImageConvertor {

    @TypeConverter
    public static ArrayList<VoiceImageModel> fromString(String value) {
        Type listType = new TypeToken<ArrayList<VoiceImageModel>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<VoiceImageModel> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}

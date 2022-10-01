package com.molitics.molitician.db.dao.homeFeed;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.molitics.molitician.ui.dashboard.voice.model.UsersFeedModel;

import java.util.List;

/**
 * Created by kailash on 22/2/18.
 */

@Dao
public interface UserFeedSuggestionDao {

    @Insert
    void insert(UsersFeedModel usersFeedModel);

    @Insert
    void insertAll(List<UsersFeedModel> record);

    @Query("SELECT * FROM UsersFeedModel limit :limit")
    List<UsersFeedModel> findAllRecord(int limit);

    @Query("DELETE FROM UsersFeedModel")
    void deleteAllRecord();
}

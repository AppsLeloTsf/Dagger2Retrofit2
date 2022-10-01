package com.molitics.molitician.db.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.molitics.molitician.ui.dashboard.voice.model.LeaderVoiceAllModel;

import java.util.List;

/**
 * Created by kailash on 22/2/18.
 */

@Dao
public interface LeaderVoiceDao {

    @Insert
    void insert(LeaderVoiceAllModel record);


    @Insert
    void insertAll(List<LeaderVoiceAllModel> record);

    @Query("SELECT * FROM LeaderVoiceAllModel limit :limit")
    List<LeaderVoiceAllModel> findAllRecord(int limit);

    /* @Query("SELECT * FROM VoiceAllModel WHERE groupId=:groupId and deviceTimestamp=:time")
     VoiceAllModel[] findRecordById(String groupId, String time);
 */
    @Query("DELETE FROM LeaderVoiceAllModel")
    void deleteAllRecord();
}

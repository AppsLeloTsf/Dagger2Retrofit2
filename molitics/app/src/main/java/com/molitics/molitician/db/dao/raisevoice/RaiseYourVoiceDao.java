package com.molitics.molitician.db.dao.raisevoice;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.molitics.molitician.ui.dashboard.voice.model.VoiceAllModel;

import java.util.List;

/**
 * Created by kailash on 22/2/18.
 */

@Dao
public interface RaiseYourVoiceDao {

    @Insert
    void insert(VoiceAllModel record);

    @Insert
    void insertAll(List<VoiceAllModel> allRecord);

    @Query("SELECT * FROM VoiceAllModel limit :limit")
    List<VoiceAllModel> findAllRecord(int limit);

    @Query("SELECT * FROM VoiceAllModel where primaryKey>:from limit :limit")
    List<VoiceAllModel> findAllRecord(int from, int limit);

    @Query("SELECT * FROM VoiceAllModel where id =:id")
    VoiceAllModel findVoicePrimaryKey(int id);

    @Query("SELECT * FROM VoiceAllModel ")
    List<VoiceAllModel>
    findAllRecord();

    /* @Query("SELECT * FROM VoiceAllModel WHERE groupId=:groupId and deviceTimestamp=:time")
     VoiceAllModel[] findRecordById(String groupId, String time);
 */

    @Query("DELETE  FROM VoiceAllModel")
    void deleteAllRecord();



/*    @Query("ALTER TABLE VoiceAllModel AUTO_INCREMENT = 1")
    void clearPrimaryKey();*/

    @Query("UPDATE  VoiceAllModel SET likes=:likes, dislikes=:dislikes, myAction=:action where id=:id")
    void updateLikeAndDislike(int likes, int dislikes, int action, int id);
}

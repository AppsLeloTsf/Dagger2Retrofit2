package com.molitics.molitician.db.dao.homeFeed;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.molitics.molitician.ui.dashboard.voice.model.FeedActionModel;
import com.molitics.molitician.ui.dashboard.voice.model.FeedModel;

import java.util.List;

/**
 * Created by kailash on 22/2/18.
 */

@Dao
public interface HomeFeedDao {

    @Insert
    void insert(FeedModel record);

    @Insert
    void insertAll(List<FeedModel> allFeeds);

    @Query("SELECT * FROM FeedModel limit :limit")
    List<FeedModel> findAllRecord(int limit);

    @Query("SELECT * FROM FeedModel where primaryKey>:from limit :limit")
    List<FeedModel> findAllRecord(int from, int limit);

    @Query("SELECT * FROM FeedModel where id =:id")
    FeedModel findFeedPrimaryKey(int id);

    @Query("SELECT * FROM FeedModel ")
    List<FeedModel> findAllRecord();

    /* @Query("SELECT * FROM VoiceAllModel WHERE groupId=:groupId and deviceTimestamp=:time")
     VoiceAllModel[] findRecordById(String groupId, String time);
 */

    @Query("DELETE  FROM FeedModel")
    void deleteAllRecord();



/*    @Query("ALTER TABLE VoiceAllModel AUTO_INCREMENT = 1")
    void clearPrimaryKey();*/

    @Query("UPDATE  FeedModel SET likes=:likes, dislikes=:dislikes, feedActionModel=:feedActionModel where id=:id")
    void updateLikeAndDislike(int likes, int dislikes, FeedActionModel feedActionModel, int id);
}

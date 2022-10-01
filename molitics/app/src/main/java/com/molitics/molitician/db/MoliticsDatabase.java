package com.molitics.molitician.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.molitics.molitician.db.config.DBConfig;
import com.molitics.molitician.db.creator.feed.FeedActionCreator;
import com.molitics.molitician.db.creator.feed.FeedImageCreator;
import com.molitics.molitician.db.creator.feed.LeaderListCreator;
import com.molitics.molitician.db.creator.feed.UserCreator;
import com.molitics.molitician.db.creator.feed.UserListCreator;
import com.molitics.molitician.db.creator.voice.AllLeaderListCreator;
import com.molitics.molitician.db.creator.voice.StringConverter;
import com.molitics.molitician.db.dao.LeaderVoiceDao;
import com.molitics.molitician.db.dao.homeFeed.HomeFeedDao;
import com.molitics.molitician.db.dao.homeFeed.UserFeedSuggestionDao;
import com.molitics.molitician.db.dao.raisevoice.RaiseYourVoiceDao;
import com.molitics.molitician.ui.dashboard.voice.model.FeedModel;
import com.molitics.molitician.ui.dashboard.voice.model.LeaderVoiceAllModel;
import com.molitics.molitician.ui.dashboard.voice.model.UsersFeedModel;
import com.molitics.molitician.ui.dashboard.voice.model.VoiceAllModel;

/**
 * Created by kailash on 22/2/18.
 */
@Database(entities = {
        FeedModel.class, UsersFeedModel.class, VoiceAllModel.class, LeaderVoiceAllModel.class},
        version = 8, exportSchema = false)
@TypeConverters({StringConverter.class, AllLeaderListCreator.class, UserCreator.class,
        UserListCreator.class, LeaderListCreator.class, FeedImageCreator.class,
        FeedActionCreator.class})
public abstract class MoliticsDatabase extends RoomDatabase {

    private static MoliticsDatabase INSTANCE;

    public abstract HomeFeedDao getAllFeedsDao();

    public abstract UserFeedSuggestionDao getUserSuggestionDao();

    public abstract RaiseYourVoiceDao getAllVoiceRecordDao();

    public abstract LeaderVoiceDao getLeaderVoiceDao();

    public static MoliticsDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (MoliticsDatabase.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), MoliticsDatabase.class, DBConfig.DATABASE_NAME)
                        .addCallback(new Callback() {
                            @Override
                            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                super.onCreate(db);
                            }

                            @Override
                            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                                super.onOpen(db);
                            }
                        })
                        .fallbackToDestructiveMigration()
                        //.allowMainThreadQueries()
                        .build();
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
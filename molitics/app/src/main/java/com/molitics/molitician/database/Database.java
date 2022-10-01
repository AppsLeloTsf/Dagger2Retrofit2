package com.molitics.molitician.database;

import android.content.Context;

import com.molitics.molitician.MolticsApplication;
import com.molitics.molitician.R;
import com.molitics.molitician.db.MoliticsDatabase;
import com.molitics.molitician.listener.OnCompleteTask;
import com.molitics.molitician.model.ArticleModel;
import com.molitics.molitician.model.DeviceRegistration;
import com.molitics.molitician.model.News;
import com.molitics.molitician.model.NewsVideoModel;
import com.molitics.molitician.ui.dashboard.home.model.HomeBrowseModel;
import com.molitics.molitician.ui.dashboard.leader.AllLeaderModel;
import com.molitics.molitician.ui.dashboard.voice.model.FeedModel;
import com.molitics.molitician.ui.dashboard.voice.model.LeaderVoiceAllModel;
import com.molitics.molitician.ui.dashboard.voice.model.UsersFeedModel;
import com.molitics.molitician.ui.dashboard.voice.model.VoiceAllModel;
import com.molitics.molitician.util.Constant;
import com.molitics.molitician.util.PrefUtil;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

import static com.molitics.molitician.util.Constant.HomeBrowseItem.NATIONAL_type;
import static com.molitics.molitician.util.ConvertUtils.convertLeaderListToRealm;
import static com.molitics.molitician.util.Util.showLogE;


/**
 * Created by rahul on 12/12/16.
 */

public class Database {

    private static String TIMESTAMP = "approvedAt";

    public static void saveNotification(final String id, final String message, final String time, final String date, final String notification_type, final String pay_load) {
        Realm realm = Realm.getDefaultInstance();
        if (realm != null) {
            realm.executeTransaction(realm1 -> {
                try {
                    NotificatoinModel scanRecord = new NotificatoinModel();
                    scanRecord.setId(id);
                    scanRecord.setMessage(message);
                    scanRecord.setNotification_type(notification_type);
                    scanRecord.setDate(date);
                    scanRecord.setTime(time);
                    scanRecord.setPayload(pay_load);
                    realm1.copyToRealmOrUpdate(scanRecord);
                } catch (IllegalStateException e) {
                }
            });
        }
    }

    public static List<Integer> getAdvertisePosition() {
        List<Integer> myList = new ArrayList<Integer>();
        Realm realm = Realm.getDefaultInstance();
        List<AdvertisePosition> eventTypes = realm.where(AdvertisePosition.class).findAll();
        for (int i = 0; i < eventTypes.size(); i++) {
            myList.add(eventTypes.get(i).getPosition());
        }
        return myList;
    }


    public static List<NotificatoinModel> readNotification() {
        final Realm realm = Realm.getDefaultInstance();
        if (realm != null) {
            return realm.where(NotificatoinModel.class).sort("id", Sort.DESCENDING).findAll();
        }
        return null;
    }

    public static int findOutNewsBookmark(int news_id) {
        final Realm realm = Realm.getDefaultInstance();
        if (realm != null) {
            NewsBookMarkModel newsBookMark = realm.where(NewsBookMarkModel.class).equalTo("id", news_id).findFirst();
            if (newsBookMark != null) {
                return newsBookMark.getId();
            } else {
                return 0;
            }
        }
        return 0;
    }

    public static void saveNewsAsBookMark(final News newsObject) {
        final Realm realm = Realm.getDefaultInstance();
        if (realm != null) {
            realm.executeTransaction(realm1 -> {
                try {
                    NewsBookMarkModel newsBookMarkModel = realm1.createObject(NewsBookMarkModel.class, newsObject.getId());
                    newsBookMarkModel.setImage(newsObject.getImage());
                    newsBookMarkModel.setType(newsObject.getType());
                    newsBookMarkModel.setHeading(newsObject.getHeading());
                    newsBookMarkModel.setContent(newsObject.getContent());
                    newsBookMarkModel.setDisplayType(newsObject.getDisplayType());
                    newsBookMarkModel.setLink(newsObject.getLink());
                    newsBookMarkModel.setSource(newsObject.getSource());
                    newsBookMarkModel.setSourceLogo(newsObject.getSourceLogo());
                    newsBookMarkModel.setTime(newsObject.getTime());
                    newsBookMarkModel.setSurveyId(newsObject.getSurveyId());
                    newsBookMarkModel.setSourceId(newsObject.getSourceId());
                    newsBookMarkModel.setShare_link(newsObject.getShareLink());
                    newsBookMarkModel.setSource_url(newsObject.getSource_url());
                    newsBookMarkModel.setLink(newsObject.getLink());

                } catch (Exception e) {
                    e.getStackTrace();
                }
            });
        }
    }

    public static List<NewsBookMarkModel> getAllBookMarkNews() {
        final Realm realm = Realm.getDefaultInstance();
        if (realm != null) {
            return realm.where(NewsBookMarkModel.class).sort(TIMESTAMP, Sort.DESCENDING).findAll();
        }
        return null;
    }

    public static void deleteBookMark(final int news_id) {
        final Realm realm = Realm.getDefaultInstance();
        if (realm != null) {
            realm.executeTransaction(realm1 -> realm1.where(NewsBookMarkModel.class).equalTo("id", news_id).findAll().deleteAllFromRealm());
        }
    }

    //final int type
    public static void writeLocalNews(final ArrayList<News> newsList) {
        final Realm realm = Realm.getDefaultInstance();
        if (realm != null) {
            realm.executeTransactionAsync(realm1 -> {
                try {

                    for (int i = 0; i < newsList.size(); i++) {
                        //RealmModel realmModel = realm.createObject(mClass);
                        // if (type == 1) {

                        LocalNewsModel newsModel = new LocalNewsModel();
                        newsModel.setTime(newsList.get(i).getTime());
                        newsModel.setContent(newsList.get(i).getContent());
                        newsModel.setDisplayType(newsList.get(i).getDisplayType());
                        newsModel.setHeading(newsList.get(i).getHeading());
                        newsModel.setId(newsList.get(i).getId());
                        newsModel.setType(newsList.get(i).getType());
                        newsModel.setImage(newsList.get(i).getImage());
                        newsModel.setSource(newsList.get(i).getSource());
                        newsModel.setSourceLogo(newsList.get(i).getSourceLogo());
                        newsModel.setSurveyId(newsList.get(i).getSurveyId());
                        newsModel.setSource_url(newsList.get(i).getSource_url());
                        //newsModel.setAuthor_name(newsList.get(i).getAuthor_name());
                        //newsModel.setAuthor_id(newsList.get(i).getAuthorId());
                        newsModel.setSourceId(newsList.get(i).getSourceId());
                        newsModel.setShare_link(newsList.get(i).getShareLink());
                        newsModel.setLink(newsList.get(i).getLink());
                        newsModel.setLeaderModels(convertLeaderListToRealm(newsList.get(i).getTaggedLeader()));

                        realm1.copyToRealmOrUpdate(newsModel);
                    }
                } catch (Exception e) {
                }
            });
        }
    }


    public static void writeGlobalNews(final ArrayList<News> newsList) {
        final Realm realm = Realm.getDefaultInstance();
        if (realm != null) {
            realm.executeTransactionAsync(realm1 -> {
                try {
                    VerticalNewsModel newsModel;
                    for (int i = 0; i < newsList.size(); i++) {
                        newsModel = new VerticalNewsModel();
                        newsModel.setTime(newsList.get(i).getTime());
                        newsModel.setContent(newsList.get(i).getContent());
                        newsModel.setDisplayType(newsList.get(i).getDisplayType());
                        newsModel.setHeading(newsList.get(i).getHeading());
                        newsModel.setId(newsList.get(i).getId());
                        newsModel.setType(newsList.get(i).getType());
                        newsModel.setImage(newsList.get(i).getImage());
                        newsModel.setSource(newsList.get(i).getSource());
                        newsModel.setSourceLogo(newsList.get(i).getSourceLogo());
                        newsModel.setSurveyId(newsList.get(i).getSurveyId());
                        newsModel.setAuthor_name(newsList.get(i).getAuthor_name());
                        newsModel.setAuthor_id(newsList.get(i).getAuthorId());
                        newsModel.setShare_link(newsList.get(i).getShareLink());
                        newsModel.setSource_url(newsList.get(i).getSource_url());
                        newsModel.setLink(newsList.get(i).getLink());
                        newsModel.setSourceId(newsList.get(i).getSourceId());
                        newsModel.setLeaderModels(convertLeaderListToRealm(newsList.get(i).getTaggedLeader()));
                        realm1.copyToRealmOrUpdate(newsModel);
                    }
                } catch (Exception e) {
                }
            });
        }
    }

    public static void writeNewsLeaders(final ArrayList<AllLeaderModel> leaderList) {
        final Realm realm = Realm.getDefaultInstance();
        if (realm != null) {
            realm.executeTransactionAsync(realm1 -> {
                try {
                    NewsLeaderModel leaderModel;
                    for (int i = 0; i < leaderList.size(); i++) {
                        leaderModel = new NewsLeaderModel();
                        leaderModel.setId(leaderList.get(i).getId());
                        leaderModel.setName(leaderList.get(i).getName());
                        leaderModel.setCanVote(leaderList.get(i).getCanVote());
                        leaderModel.setFollowers(leaderList.get(i).getFollowers());
                        leaderModel.setIsFollowing(leaderList.get(i).getIsFollowing());
                        leaderModel.setIsVoted(leaderList.get(i).getIsVoted());
                        leaderModel.setMessage(leaderList.get(i).getMessage());
                        leaderModel.setNewsCount(leaderList.get(i).getNewsCount());
                        leaderModel.setPartyCode(leaderList.get(i).getPartyCode());
                        leaderModel.setPosition(leaderList.get(i).getPosition());
                        leaderModel.setProfileImage(leaderList.get(i).getProfileImage());
                        leaderModel.setWeightage(leaderList.get(i).getWeightage());
                        leaderModel.setUser_vote_action(leaderList.get(i).getUser_vote_action());
                        leaderModel.setUpvoteCount(leaderList.get(i).getLike_count());
                        leaderModel.setDownVoteCount(leaderList.get(i).getDislike_count());
                        leaderModel.setIsVerified(leaderList.get(i).getIsVerify());

                        realm1.copyToRealmOrUpdate(leaderModel);
                    }
                } catch (Exception e) {
                    //   WriteLog.writeLog("DataBase","Error in writeScanRecords");
                }
            });
        }
    }

    public static void writeLocalNewsLeaders(final ArrayList<AllLeaderModel> leaderList) {
        final Realm realm = Realm.getDefaultInstance();
        if (realm != null) {
            realm.executeTransactionAsync(realm1 -> {
                try {
                    LocalNewsLeaderModel leaderModel;
                    for (int i = 0; i < leaderList.size(); i++) {
                        leaderModel = new LocalNewsLeaderModel();
                        leaderModel.setId(leaderList.get(i).getId());
                        leaderModel.setName(leaderList.get(i).getName());
                        leaderModel.setCanVote(leaderList.get(i).getCanVote());
                        leaderModel.setFollowers(leaderList.get(i).getFollowers());
                        leaderModel.setIsFollowing(leaderList.get(i).getIsFollowing());
                        leaderModel.setIsVoted(leaderList.get(i).getIsVoted());
                        leaderModel.setMessage(leaderList.get(i).getMessage());
                        leaderModel.setNewsCount(leaderList.get(i).getNewsCount());
                        leaderModel.setPartyCode(leaderList.get(i).getPartyCode());
                        leaderModel.setPosition(leaderList.get(i).getPosition());
                        leaderModel.setProfileImage(leaderList.get(i).getProfileImage());
                        leaderModel.setWeightage(leaderList.get(i).getWeightage());
                        leaderModel.setUser_vote_action(leaderList.get(i).getUser_vote_action());
                        leaderModel.setUpvoteCount(leaderList.get(i).getLike_count());
                        leaderModel.setDownVoteCount(leaderList.get(i).getDislike_count());
                        leaderModel.setIsVerified(leaderList.get(i).getIsVerify());

                        realm1.copyToRealmOrUpdate(leaderModel);
                    }
                } catch (Exception e) {
                }
            });
        }
    }

    public static void writeHeadingNews(final ArrayList<News> newsList) {
        final Realm realm = Realm.getDefaultInstance();
        if (realm != null) {
            realm.executeTransactionAsync(realm1 -> {
                try {
                    realm1.where(HeadingNewsModel.class).findAll().deleteAllFromRealm();

                    HeadingNewsModel newsModel;
                    for (int i = 0; i < newsList.size(); i++) {
                        newsModel = new HeadingNewsModel();
                        newsModel.setTime(newsList.get(i).getTime());
                        //  newsModel.setArticleType(newsList.get(i).geta);
                        newsModel.setContent(newsList.get(i).getContent());
                        newsModel.setDisplayType(newsList.get(i).getDisplayType());
                        newsModel.setHeading(newsList.get(i).getHeading());
                        newsModel.setId(newsList.get(i).getId());
                        newsModel.setType(newsList.get(i).getType());
                        newsModel.setImage(newsList.get(i).getImage());
                        newsModel.setSource(newsList.get(i).getSource());
                        newsModel.setSourceLogo(newsList.get(i).getSourceLogo());
                        newsModel.setSurveyId(newsList.get(i).getSurveyId());
                        newsModel.setAuthor_name(newsList.get(i).getAuthor_name());
                        newsModel.setAuthor_id(newsList.get(i).getAuthorId());
                        newsModel.setShare_link(newsList.get(i).getShareLink());
                        newsModel.setSource_url(newsList.get(i).getSource_url());
                        newsModel.setSourceId(newsList.get(i).getSourceId());
                        newsModel.setLink(newsList.get(i).getLink());
                        realm1.copyToRealmOrUpdate(newsModel);
                    }
                } catch (Exception e) {
                }
            });
        }
    }

    public static void writeNewsVideo(final ArrayList<NewsVideoModel> newsVideoModels) {
        Realm realm = Realm.getDefaultInstance();
        if (realm != null) {
            realm.executeTransactionAsync(realm1 -> realm1.copyToRealmOrUpdate(newsVideoModels));
        }
    }

    public static List<VerticalNewsModel> readGlobalNews(int page) {
        Realm realm = Realm.getDefaultInstance();
        if (realm != null) {
            RealmResults<VerticalNewsModel> results = realm.where(VerticalNewsModel.class).findAll();
            if (results.size() != 0) {
                int size = results.size();
                return results.subList(Math.max(page, 0), Math.min(page + 10, size));
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public static List<LocalNewsModel> readLocalNews(int page) {
        Realm realm = Realm.getDefaultInstance();
        if (realm != null) {
            List<LocalNewsModel> results = realm.where(LocalNewsModel.class).sort(TIMESTAMP, Sort.DESCENDING).findAll();
            if (results.size() != 0) {
                int size = results.size();
                return results.subList(Math.max(page, 0), Math.min(page + 10, size));
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public static void writeFeedData(final List<FeedModel> newsList, Context context, boolean delete_data, OnCompleteTask onCompleteTask) {
        new Thread(() -> {
            try {
                MoliticsDatabase.getAppDatabase(context).runInTransaction(() -> {
                    if (delete_data) {
                        deleteVoiceFromDataBase(context);
                    }
                    MoliticsDatabase.getAppDatabase(context).getAllFeedsDao().insertAll(newsList);
                });

                onCompleteTask.onComplete(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void writeRaiseYourVoice(final List<VoiceAllModel> newsList, Context context, boolean delete_data, OnCompleteTask onCompleteTask) {
        new Thread(() -> {
            try {
                MoliticsDatabase.getAppDatabase(context).runInTransaction(() -> {
                    if (delete_data) {
                        deleteVoiceFromDataBase(context);
                    }
                    MoliticsDatabase.getAppDatabase(context).getAllVoiceRecordDao().insertAll(newsList);
                });

                onCompleteTask.onComplete(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static List<FeedModel> getAllFeeds(Context context, int from, int limit) {

        return MoliticsDatabase.getAppDatabase(context).getAllFeedsDao().findAllRecord(from, limit);
    }
    public static List<VoiceAllModel> readRaiseYourVoice(Context context, int from, int limit) {

        return MoliticsDatabase.getAppDatabase(context).getAllVoiceRecordDao().findAllRecord(from, limit);
    }

    public static void writeNationalTrendingLeaders(final ArrayList<AllLeaderModel> leaderList) {
        final Realm realm = Realm.getDefaultInstance();
        if (realm != null) {
            realm.executeTransactionAsync(realm1 -> {
                try {
                    NewsLeaderModel leaderModel;
                    for (int i = 0; i < leaderList.size(); i++) {
                        leaderModel = new NewsLeaderModel();
                        leaderModel.setId(leaderList.get(i).getId());
                        leaderModel.setName(leaderList.get(i).getName());
                        leaderModel.setCanVote(leaderList.get(i).getCanVote());
                        leaderModel.setFollowers(leaderList.get(i).getFollowers());
                        leaderModel.setIsFollowing(leaderList.get(i).getIsFollowing());
                        leaderModel.setIsVoted(leaderList.get(i).getIsVoted());
                        leaderModel.setMessage(leaderList.get(i).getMessage());
                        leaderModel.setNewsCount(leaderList.get(i).getNewsCount());
                        leaderModel.setPartyCode(leaderList.get(i).getPartyCode());
                        leaderModel.setPosition(leaderList.get(i).getPosition());
                        leaderModel.setProfileImage(leaderList.get(i).getProfileImage());
                        leaderModel.setWeightage(leaderList.get(i).getWeightage());
                        leaderModel.setUser_vote_action(leaderList.get(i).getUser_vote_action());
                        leaderModel.setUpvoteCount(leaderList.get(i).getLike_count());
                        leaderModel.setDownVoteCount(leaderList.get(i).getDislike_count());

                        realm1.copyToRealmOrUpdate(leaderModel);
                    }
                    //   WriteLog.writeLog(TAG,"writeScanRecords");
                } catch (Exception e) {
                    //   WriteLog.writeLog("DataBase","Error in writeScanRecords");
                }
            });
        }
    }

    public static void writeSuggestionData(final List<UsersFeedModel> newsList, Context context, OnCompleteTask onCompleteTask) {

        new Thread(() -> {
            try {
                MoliticsDatabase.getAppDatabase(context).runInTransaction(() -> {
                    UsersFeedModel item;
                    List<UsersFeedModel> list = new ArrayList<>();
                    for (UsersFeedModel d : newsList) {
                        item = new UsersFeedModel();
                        item.setId(d.getId());
                        item.setName(d.getName());
                        item.setIsVerify(d.getIsVerify());
                        item.setImage(d.getImage());
                        list.add(item);
                    }
                    MoliticsDatabase.getAppDatabase(context).getUserSuggestionDao().insertAll(list);
                });

                onCompleteTask.onComplete(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }

    public static List<UsersFeedModel> readSuggestionFeed(Context context, int limit) {
        List<UsersFeedModel> models = MoliticsDatabase.getAppDatabase(context).getUserSuggestionDao().findAllRecord(limit);
        List<UsersFeedModel> list = new ArrayList<>();
        UsersFeedModel item;
        for (UsersFeedModel d : models) {
            item = new UsersFeedModel();
            item.setId(d.getId());
            item.setName(d.getName());
            item.setIsVerify(d.getIsVerify());
            item.setImage(d.getImage());
            list.add(item);
        }
        return list;
    }

    public static void writeTrendingData(final List<VoiceAllModel> newsList, Context context, OnCompleteTask onCompleteTask) {

        new Thread(() -> {
            try {
                MoliticsDatabase.getAppDatabase(context).runInTransaction(() -> {


                    LeaderVoiceAllModel item;
                    List<LeaderVoiceAllModel> list = new ArrayList<>();
                    for (VoiceAllModel d : newsList) {
                        item = new LeaderVoiceAllModel();
                        item.setId(d.getId());
                        item.setTitle(d.getTitle());
                        item.setContent(d.getContent());
                        item.setUserId(d.getUserId());
                        item.setCandidateLeader(d.getCandidateLeader());
                        item.setImage(d.getImage());
                        item.setVideo(d.getVideo());
                        item.setUserName(d.getUserName());
                        item.setLikes(d.getLikes());
                        item.setDislikes(d.getDislikes());
                        item.setMyAction(d.getMyAction());
                        item.setComments(d.getComments());
                        item.setFollowing(d.getFollowing());
                        item.setTime(d.getTime());
                        item.setIsFollowing(d.getIsFollowing());
                        item.setTag(d.getTag());
                        item.setTagName(d.getTagName());
                        item.setLocation(d.getLocation());
                        item.setDeleted_image(d.getDeleted_image());
                        item.setImages(d.getImages());
                        item.setUrlSource(d.getUrlSource());
                        item.setSharedUrl(d.getSharedUrl());
                        item.setSharedImageUrl(d.getSharedImageUrl());
                        item.setImageSizes(d.getImageSizes());
                        item.setViewsCount(d.getViewsCount());

                        list.add(item);
                    }

                    MoliticsDatabase.getAppDatabase(context).getLeaderVoiceDao().insertAll(list);
                });

                onCompleteTask.onComplete(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }

    public static List<VoiceAllModel> readLeaderTrendingVoice(Context context, int limit) {
        List<LeaderVoiceAllModel> models = MoliticsDatabase.getAppDatabase(context).getLeaderVoiceDao().findAllRecord(limit);
        List<VoiceAllModel> list = new ArrayList<>();
        VoiceAllModel item;
        for (LeaderVoiceAllModel d : models) {

            item = new VoiceAllModel();
            item.setId(d.getId());
            item.setTitle(d.getTitle());
            item.setContent(d.getContent());
            item.setUserId(d.getUserId());
            item.setCandidateLeader(d.getCandidateLeader());
            item.setImage(d.getImage());
            item.setVideo(d.getVideo());
            item.setUserName(d.getUserName());
            item.setLikes(d.getLikes());
            item.setDislikes(d.getDislikes());
            item.setMyAction(d.getMyAction());
            item.setComments(d.getComments());
            item.setFollowing(d.getFollowing());
            item.setTime(d.getTime());
            item.setIsFollowing(d.getIsFollowing());
            item.setTag(d.getTag());
            item.setTagName(d.getTagName());
            item.setLocation(d.getLocation());
            item.setDeleted_image(d.getDeleted_image());
            item.setImages(d.getImages());
            item.setSharedUrl(d.getSharedUrl());
            item.setUrlSource(d.getUrlSource());
            item.setSharedImageUrl(d.getSharedImageUrl());
            item.setImageSizes(d.getImageSizes());
            item.setViewsCount(d.getViewsCount());
            list.add(item);
        }
        return list;
    }

    public static void deleteVoiceFromDataBase(Context mContext) {
        showLogE("Database ", " Deleting trending news time of new size ");
        MoliticsDatabase.getAppDatabase(mContext).clearAllTables();
    }


    public static List<HeadingNewsModel> readHeadingNews() {

        final Realm realm = Realm.getDefaultInstance();
        if (realm != null) {
            List<HeadingNewsModel> newsModels = realm.where(HeadingNewsModel.class).findAll();
            return newsModels;
        }
        return null;
    }

    public static List<NewsLeaderModel> readLeaderNews() {
        final Realm realm = Realm.getDefaultInstance();
        if (realm != null) {
            List<NewsLeaderModel> newsModels = realm.where(NewsLeaderModel.class).findAll();
            //   WriteLog.writeLog(TAG,"readAsyncData====no of records =="+scanRecords.size() );
            return newsModels;
        }
        return null;
    }


    public static List<NewsLeaderModel> readNationalTrendingLeader() {
        final Realm realm = Realm.getDefaultInstance();
        if (realm != null) {
            List<NewsLeaderModel> newsModels = realm.where(NewsLeaderModel.class).findAll();
            return realm.copyFromRealm(newsModels);
        }
        return null;
    }

    public static List<LocalNewsLeaderModel> readLocalNewsLeader() {
        final Realm realm = Realm.getDefaultInstance();
        if (realm != null) {
            List<LocalNewsLeaderModel> newsModels = realm.where(LocalNewsLeaderModel.class).findAll();
            return newsModels;
        }
        return null;
    }

    public static List<NewsVideoModel> readNewsVideo() {
        final Realm realm = Realm.getDefaultInstance();
        if (realm != null) {
            return realm.where(NewsVideoModel.class)
                    .equalTo("table", "national_table")
                    .findAll();
        }
        return null;
    }

    public static List<NewsVideoModel> readLocalNewsVideo() {
        final Realm realm = Realm.getDefaultInstance();
        if (realm != null) {
            return realm.where(NewsVideoModel.class)
                    .equalTo("table", "local_table")
                    .findAll();
        }
        return null;
    }

    public static void deleteAllNews() {
        Realm realm = Realm.getDefaultInstance();
        if (realm != null) {
            realm.executeTransaction(realm1 -> realm1.where(VerticalNewsModel.class).findAll().deleteAllFromRealm());
        }
    }

    public static void deleteNewsLeader() {
        Realm realm = Realm.getDefaultInstance();
        if (realm != null) {
            realm.executeTransaction(realm1 -> realm1.where(NewsLeaderModel.class).findAll().deleteAllFromRealm());
        }
    }

    public static void deleteLocalNewsLeader() {
        Realm realm = Realm.getDefaultInstance();
        if (realm != null) {
            realm.executeTransaction(realm1 -> realm1.where(LocalNewsLeaderModel.class).findAll().deleteAllFromRealm());
        }
    }

    public static void deleteNewsVideo() {
        Realm realm = Realm.getDefaultInstance();
        if (realm != null) {
            realm.executeTransaction(realm1 -> realm1.where(NewsVideoModel.class).findAll().deleteAllFromRealm());
        }
    }

    public static void deleteArticle() {
        Realm realm = Realm.getDefaultInstance();
        if (realm != null) {
            realm.executeTransaction(realm1 -> realm1.where(ArticleModel.class).findAll().deleteAllFromRealm());
        }
    }

    public static void deleteLocalAllNews() {
        Realm realm = Realm.getDefaultInstance();
        if (realm != null) {
            realm.executeTransaction(realm1 -> realm1.where(LocalNewsModel.class).findAll().deleteAllFromRealm());
        }
    }

    public static int countOfNews() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<VerticalNewsModel> results = realm.where(VerticalNewsModel.class).findAll();
        return results.size();
    }

    public static int countOfLocalNews() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<LocalNewsModel> results = realm.where(LocalNewsModel.class).findAll();
        return results.size();
    }

    public static List<State> getStateList() {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(State.class).findAll();

    }

    @Deprecated
    public static void deviceRegistration(DeviceRegistration deviceRegistration) {

        PrefUtil.putString(Constant.PreferenceKey.AUTH_KEY, deviceRegistration.getToken());
        PrefUtil.putInt(Constant.PreferenceKey.USER_ID, deviceRegistration.getUserId());
        PrefUtil.putString(Constant.PreferenceKey.USER_NAME, deviceRegistration.getUserName());
        PrefUtil.putString(Constant.PreferenceKey.API_KEY, deviceRegistration.getApiKey());
    }

    public static List<HomeBrowseModel> getHomeBrowseSinlgeEntry() {
        final Realm realm = Realm.getDefaultInstance();
        List<HomeBrowseModel> browseModelList = new ArrayList<>();
        if (realm != null) {
            browseModelList.add(new HomeBrowseModel(MolticsApplication.getAppContaxt().getString(R.string.national), "https://molitics.s3.amazonaws.com/images/news_original/6455_48_1567498011.jpg", NATIONAL_type, true));

            LocalNewsModel newsModels = null;
            List<LocalNewsModel> localList = realm.where(LocalNewsModel.class).sort(TIMESTAMP, Sort.DESCENDING).findAll();
            if (localList.size() != 0) {
                newsModels = realm.where(LocalNewsModel.class).sort(TIMESTAMP, Sort.DESCENDING).findAll().first();
            }
            browseModelList.add(new HomeBrowseModel(MolticsApplication.getAppContaxt().getString(R.string.local_news), newsModels == null ? "" : newsModels.getImage(), Constant.HomeBrowseItem.LOCAL_NEWS_type, false));

        }
        return browseModelList;
    }
}

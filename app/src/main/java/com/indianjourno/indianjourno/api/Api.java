package com.indianjourno.indianjourno.api;




import com.indianjourno.indianjourno.model.ModelRegister;
import com.indianjourno.indianjourno.model.sliders.SliderMessage;
import com.indianjourno.indianjourno.model.video_cat.ModelVideoCat;
import com.indianjourno.indianjourno.model.videos.ModelVideos;
import com.indianjourno.indianjourno.model.bookmarks.BookmarkInsertion;
import com.indianjourno.indianjourno.model.bookmarks.BookmarksDelete;
import com.indianjourno.indianjourno.model.bookmarks.BookmarksMessage;
import com.indianjourno.indianjourno.model.category.CategoryList;
import com.indianjourno.indianjourno.model.category.CategoryMessage;
import com.indianjourno.indianjourno.model.feature.FeatureMessage;
import com.indianjourno.indianjourno.model.feature.FeatureNews;
import com.indianjourno.indianjourno.model.login.LoginMessage;
import com.indianjourno.indianjourno.model.static_page.PrivacyModels;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {


    @FormUrlEncoded
    @POST("Viewer/viewer_login")
    Call<LoginMessage> userLogin(
            @Field("viwers_email") String email,
            @Field("viwers_password") String password
    );
    @FormUrlEncoded
    @POST("Viewer/viewer_insert")
    Call<ModelRegister> userRegister(
            @Field("viwers_mobile") String phone,
            @Field("viwers_email") String email,
            @Field("viwers_name") String name,
            @Field("viwers_password") String password
    );
    @POST("Category/category")
    Call<CategoryList> getCategory();

    @POST("Slidder/slidder")
    Call<SliderMessage> getSlider();

    @POST("Features/features")
    Call<FeatureMessage> getFeatureCategory();
 @POST("Video_Category/video_category")
    Call<ModelVideoCat> getVideoCatList();

    @FormUrlEncoded
    @POST("Video/video_cat_id")
    Call<ModelVideos> getVideos(
            @Field("video_cat_id") String video_cat_id
    );

    @FormUrlEncoded
    @POST("News/news_feature_id")
    Call<FeatureNews> getNewsByFeatureId(
            @Field("news_feature_id") String news_feature_id
    );

    @FormUrlEncoded
    @POST("News/news_cat_id")
    Call<CategoryMessage> getNewsById(
            @Field("news_cat_id") String news_cat_id
    );

    @FormUrlEncoded
    @POST("Bookmark/bookmark_data")
    Call<BookmarkInsertion> bookmarkInsert(
            @Field("bookmark_viewers_id") String bookmark_viewers_id,
            @Field("bookmark_news_id") String bookmark_news_id
    );
    @FormUrlEncoded
    @POST("Bookmark/bookmark_fetch")
    Call<BookmarksMessage> bookmarkFetch(
            @Field("bookmark_viewers_id") String bookmark_viewers_id
    );
    @FormUrlEncoded
    @POST("Bookmark/bookmark_delete")
    Call<BookmarksDelete> bookmarkDelete(
            @Field("bookmark_viewers_id") String bookmark_viewers_id,
            @Field("bookmark_news_id") String bookmark_news_id
    );
    @FormUrlEncoded
    @POST("StaticPage/staticpage_id")
    Call<PrivacyModels> getPrivacy(
            @Field("page_id") String user_id
    );

   }

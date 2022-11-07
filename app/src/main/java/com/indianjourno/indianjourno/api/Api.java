package com.indianjourno.indianjourno.api;




import com.indianjourno.indianjourno.model.ij_category.ModelCategory;
import com.indianjourno.indianjourno.model.ij_category.ModelCategoryByCatId;
import com.indianjourno.indianjourno.model.ij_news.ModelBreakingNew;
import com.indianjourno.indianjourno.model.ij_news.ModelHotNews;
import com.indianjourno.indianjourno.model.ij_news.ModelNewsByCatId;
import com.indianjourno.indianjourno.model.ij_news.all_news.ModelAllNews;
import com.indianjourno.indianjourno.model.ModelRegister;
import com.indianjourno.indianjourno.model.ij_news.ModelNewsById;
import com.indianjourno.indianjourno.model.ij_news.ModelStoriesNews;
import com.indianjourno.indianjourno.model.ij_news.ModelTrendingNews;
import com.indianjourno.indianjourno.model.ij_sub_category.ModelSubCatById;
import com.indianjourno.indianjourno.model.ij_sub_category.ModelSubCategory;
import com.indianjourno.indianjourno.model.ij_user.ModelUserLogin;
import com.indianjourno.indianjourno.model.ij_user.ModelUserRegister;
import com.indianjourno.indianjourno.model.ij_video.ModelVideo;
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

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {


    @FormUrlEncoded
    @POST("Viewer_Api/viewer_login")
    Call<ModelUserLogin> userLogin(
            @Field("viwers_email") String email,
            @Field("viwers_password") String password
    );
    @FormUrlEncoded
    @POST("Viewer_Api/viewer_create")
    Call<ModelUserRegister> userRegister(
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


    //Ind
    @POST("News_Api")
    Call<ModelAllNews> getAllNewsIj();

    @FormUrlEncoded
    @POST("News_Api/fetch_news_id")
    Call<ModelNewsById> getNewsByNewsIdIj(
            @Field("news_id") String news_id
    );

    @FormUrlEncoded
    @POST("News_Api/fetch_category_id")
    Call<List<ModelNewsByCatId>> getNewsByCatIdIj(
            @Field("category") String category
    );
    @FormUrlEncoded
    @POST("News_Api/fetch_sub_cat_id")
    Call<ModelNewsById> getNewsBySubCatIdIj(
            @Field("sub_cat_id") String sub_cat_id
    );

    @POST("News_Api/fetch_breaking_news")
    Call<List<ModelBreakingNew>> getAllBreakingNewsIj();

    @POST("News_Api/fetch_top_stories_news")
    Call<List<ModelStoriesNews>> getAllStoriesNewsIj();

    @POST("News_Api/fetch_hot_news")
    Call<List<ModelHotNews>> getAllHotNewsIj();

    @POST("News_Api/fetch_trending_news")
    Call<List<ModelTrendingNews>> getAllTrendingNewsIj();

    @POST("Category_Api")
    Call<ModelCategory> getAllCategoryIj();

    @FormUrlEncoded
    @POST("Category_Api/fetch_category_id")
    Call<ModelCategoryByCatId> getCategoryByCatIdIj(
            @Field("category_id") String category_id
    );

    @POST("Sub_Category_Api")
    Call<ModelSubCategory> getAllSubCategoryIj();

   @FormUrlEncoded
   @POST("Sub_Category_Api/fetch_sub_category_id")
   Call<ModelSubCatById> getSubCategoryCatIdIj(
           @Field("category_id") String category_id
   );

    @POST("Video_Api")
    Call<ModelVideo> getAllVideoIj();

    @FormUrlEncoded
    @POST("Video_Api/fetch_video_id")
    Call<ModelSubCatById> getVideoByVideoIdIj(
            @Field("video_id") String video_id
    );

}

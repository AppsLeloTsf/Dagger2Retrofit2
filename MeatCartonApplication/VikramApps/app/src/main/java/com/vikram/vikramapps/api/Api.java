package com.vikram.vikramapps.api;




import com.google.gson.JsonObject;
import com.vikram.vikramapps.models.banners.MyBanners;
import com.vikram.vikramapps.models.courses.Course;
import com.vikram.vikramapps.models.courses.CourseRequest;
import com.vikram.vikramapps.models.courses.MyCourse;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Api {
   @Headers({ "Content-Type: application/json;charset=UTF-8"})
   @POST("courses")
   Call<MyCourse> getCourse(@Body JsonObject body);

   @Headers({ "Content-Type: application/json;charset=UTF-8"})
   @GET("banners")
   Call<MyBanners> getBanners();
}

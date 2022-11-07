package api;


import com.cadreamrs.model.banners.MyBanners;
import com.cadreamrs.model.courses.MyCourse;
import com.google.gson.JsonObject;


import retrofit2.Call;
import retrofit2.http.Body;
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

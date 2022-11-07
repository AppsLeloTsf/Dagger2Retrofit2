package com.ca_dreamers.cadreamers.api;


import com.ca_dreamers.cadreamers.models.about_us.ModelAboutUs;
import com.ca_dreamers.cadreamers.models.address.ModelAddress;
import com.ca_dreamers.cadreamers.models.address.add_update_address.ModelAddUpdateAddress;
import com.ca_dreamers.cadreamers.models.address.delete_address.ModelDeleteAddress;
import com.ca_dreamers.cadreamers.models.books.ModelBooksList;
import com.ca_dreamers.cadreamers.models.books.book_details.ModelBooksDetails;
import com.ca_dreamers.cadreamers.models.books.product_type.ModelBookMode;
import com.ca_dreamers.cadreamers.models.cart.add_cart.ModelAddCart;
import com.ca_dreamers.cadreamers.models.cart.checkout.ModelCheckout;
import com.ca_dreamers.cadreamers.models.cart.delete_cart.ModelDeleteCart;
import com.ca_dreamers.cadreamers.models.cart.fetch_cart.ModelFetchCart;
import com.ca_dreamers.cadreamers.models.combo_package.ModelCombo;
import com.ca_dreamers.cadreamers.models.combo_package.package_details.ModelPackageDetails;
import com.ca_dreamers.cadreamers.models.contact_us.ModelContactUs;
import com.ca_dreamers.cadreamers.models.feedback.ModelFeedback;
import com.ca_dreamers.cadreamers.models.free_videos.ModelFreeVideo;
import com.ca_dreamers.cadreamers.models.free_videos.banners.ModelBanners;
import com.ca_dreamers.cadreamers.models.books.banners.ModelBooksBanners;
import com.ca_dreamers.cadreamers.models.course_details.ModelCourseDetail;
import com.ca_dreamers.cadreamers.models.courses.ModelCourse;

import com.ca_dreamers.cadreamers.models.courses.banners.ModelCoursesBanner;
import com.ca_dreamers.cadreamers.models.logged_out.ModelLogout;
import com.ca_dreamers.cadreamers.models.login_reg.login.ModelLogin;
import com.ca_dreamers.cadreamers.models.login_reg.otp_match.ModelOtp;
import com.ca_dreamers.cadreamers.models.login_reg.password.ModelForgotPassword;
import com.ca_dreamers.cadreamers.models.login_reg.password.update_pass.ModelUpdatePassword;
import com.ca_dreamers.cadreamers.models.my_orders.ModelMyOrders;
import com.ca_dreamers.cadreamers.models.my_orders.books.ModelMyOrdersBooks;
import com.ca_dreamers.cadreamers.models.my_orders.books.books_details_pdf.ModelBooksDetailsPdf;
import com.ca_dreamers.cadreamers.models.my_orders.course_details.ModelMyOrdersCourseDetails;
import com.ca_dreamers.cadreamers.models.my_orders.course_details.chapters.ModelCourseChapter;
import com.ca_dreamers.cadreamers.models.my_orders.course_details.chapters.topics.ModelTopics;
import com.ca_dreamers.cadreamers.models.my_payment.ModelMyPayment;
import com.ca_dreamers.cadreamers.models.my_payment.package_payment.ModelComboPayment;
import com.ca_dreamers.cadreamers.models.notification.ModelNotification;
import com.ca_dreamers.cadreamers.models.notification.notification_count.ModelNotificationCount;
import com.ca_dreamers.cadreamers.models.notification.notification_read.ModelNotificationRead;
import com.ca_dreamers.cadreamers.models.profile.ModelUserInfo;
import com.ca_dreamers.cadreamers.models.profile.profile_edit.ModelProfileEdit;
import com.ca_dreamers.cadreamers.models.profile.user_image.ModelProfileImage;
import com.ca_dreamers.cadreamers.models.profile.user_image.update_image.ModelUpdateImage;
import com.ca_dreamers.cadreamers.models.login_reg.registration.ModelRegistration;
import com.ca_dreamers.cadreamers.models.top_20.ModelTop20;
import com.ca_dreamers.cadreamers.models.top_20.banners.ModelTop20Banner;
import com.ca_dreamers.cadreamers.models.user_token.ModelToken;
import com.ca_dreamers.cadreamers.models.version_detail.ModelVersion;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Api {
   @Headers({ "Content-Type: application/json;charset=UTF-8"})
   @GET("version_add_update")
   Call<ModelVersion> getVersionDetail();
   @Headers({ "Content-Type: application/json;charset=UTF-8"})
   @POST("logout")
   Call<ModelLogout> getLogout(@Body JsonObject body);
   @Headers({ "Content-Type: application/json;charset=UTF-8"})
   @POST("loggedIn")
   Call<Void> getLoggedIn(@Body JsonObject body);

   @Headers({ "Content-Type: application/json;charset=UTF-8"})
   @POST("getToken")
   Call<ModelToken> getTokenApi(@Body JsonObject body);

   @Headers({ "Content-Type: application/json;charset=UTF-8"})
   @POST("user_info")
   Call<ModelUserInfo> getUserInfo(@Body JsonObject body);

   @Headers({ "Content-Type: application/json;charset=UTF-8"})
   @POST("update_user_info")
   Call<ModelProfileEdit> getUserInfoEdit(@Body JsonObject body);

   @Headers({ "Content-Type: application/json;charset=UTF-8"})
   @POST("fetch_update_user_pic")
   Call<ModelProfileImage> getUserImage(@Body JsonObject body);

   @Headers({ "Content-Type: application/json;charset=UTF-8"})
   @POST("purchasedcourseslist")
   Call<ModelMyOrders> getMyOrders(@Body JsonObject body);

   @Headers({ "Content-Type: application/json;charset=UTF-8"})
   @POST("purchasedcoursedetails")
   Call<ModelMyOrdersCourseDetails> getMyOrdersCourseDetails(@Body JsonObject body);

   @Headers({ "Content-Type: application/json;charset=UTF-8"})
   @POST("chapters")
   Call<ModelCourseChapter> getCourseChapters(@Body JsonObject body);

   @Headers({ "Content-Type: application/json;charset=UTF-8"})
   @POST("topic_contents")
   Call<ModelTopics> getChapterTopic(@Body JsonObject body);

   @Headers({ "Content-Type: application/json;charset=UTF-8"})
   @POST("purchasedbookslist")
   Call<ModelMyOrdersBooks> getMyOrdersBooks(@Body JsonObject body);

   @Headers({ "Content-Type: application/json;charset=UTF-8"})
   @POST("purchasedbooksDetails")
   Call<ModelBooksDetailsPdf> getMyOrdersBooksPdf(@Body JsonObject body);

   @Headers({ "Content-Type: application/json;charset=UTF-8"})
   @GET("get_about_us")
   Call<ModelAboutUs> getAboutUs();


   @Headers({ "Content-Type: application/json;charset=UTF-8"})
   @GET("get_contact_us")
   Call<ModelContactUs> getContactUs();

   @Headers({ "Content-Type: application/json;charset=UTF-8"})
   @POST("courses")
   Call<ModelCourse> getCourse(@Body JsonObject body);

   @Headers({ "Content-Type: application/json;charset=UTF-8"})
   @GET("courses_banner")
   Call<ModelCoursesBanner> getCoursesBanners();

   @Headers({ "Content-Type: application/json;charset=UTF-8"})
   @GET("banners")
   Call<ModelBanners> getBanners();

   @Headers({ "Content-Type: application/json;charset=UTF-8"})
   @GET("free_videos")
   Call<ModelFreeVideo> getFreeVideos();

   @Headers({ "Content-Type: application/json;charset=UTF-8"})
   @POST("login")
   Call<ModelLogin> getLogin(@Body JsonObject body);

   @Headers({ "Content-Type: application/json;charset=UTF-8"})
   @POST("registerotpsent")
   Call<ModelRegistration> getRegister(@Body JsonObject body);

   @Headers({ "Content-Type: application/json;charset=UTF-8"})
   @POST("registerotpmatch")
   Call<ModelOtp> getRegisterSuccess(@Body JsonObject body);

   @Headers({ "Content-Type: application/json;charset=UTF-8"})
   @POST("coursedetails")
   Call<ModelCourseDetail> getCourseDetails(@Body JsonObject body);

   @Headers({ "Content-Type: application/json;charset=UTF-8"})
   @GET("top20_banner")
   Call<ModelTop20Banner> getTop20Banner();

   @Headers({ "Content-Type: application/json;charset=UTF-8"})
   @GET("top20")
   Call<ModelTop20> getTop20();

   @Headers({ "Content-Type: application/json;charset=UTF-8"})
   @GET("books_banner")
   Call<ModelBooksBanners> getBooksBanner();

   @Headers({ "Content-Type: application/json;charset=UTF-8"})
   @POST("books")
   Call<ModelBooksList> getBooksList(@Body JsonObject body);

   @Headers({ "Content-Type: application/json;charset=UTF-8"})
   @POST("product_type")
   Call<ModelBookMode> getBooksMode(@Body JsonObject body);

   @Headers({ "Content-Type: application/json;charset=UTF-8"})
   @POST("bookdetails")
   Call<ModelBooksDetails> getBooksDetails(@Body JsonObject body);

   @Headers({ "Content-Type: application/json;charset=UTF-8"})
   @POST("addtocart")
   Call<ModelAddCart> addCart(@Body JsonObject body);

   @Headers({ "Content-Type: application/json;charset=UTF-8"})
   @POST("getcartproduct")
   Call<ModelFetchCart> fetchCart(@Body JsonObject body);

   @Headers({ "Content-Type: application/json;charset=UTF-8"})
   @POST("deletecartproduct")
   Call<ModelDeleteCart> deleteCart(@Body JsonObject body);

   @Headers({ "Content-Type: application/json;charset=UTF-8"})
   @POST("checkout")
   Call<ModelCheckout> checkoutCart(@Body JsonObject body);

   @Headers({ "Content-Type: application/json;charset=UTF-8"})
   @POST("useraddress")
   Call<ModelAddress> getAddress(@Body JsonObject body);

   @Headers({ "Content-Type: application/json;charset=UTF-8"})
   @POST("add_updateuseraddress")
   Call<ModelAddUpdateAddress> addUpdateAddress(@Body JsonObject body);

   @Headers({ "Content-Type: application/json;charset=UTF-8"})
   @POST("delete_address")
   Call<ModelDeleteAddress> deleteAddress(@Body JsonObject body);

   @Headers({ "Content-Type: application/json;charset=UTF-8"})
   @POST("fetch_update_user_pic")
   Call<ModelUpdateImage> updateImage(@Body JsonObject body);

   @Headers({ "Content-Type: application/json;charset=UTF-8"})
   @POST("notification")
   Call<ModelNotification> getNotification(@Body JsonObject body);

   @Headers({ "Content-Type: application/json;charset=UTF-8"})
   @POST("notification_count")
   Call<ModelNotificationCount> getNotificationCount(@Body JsonObject body);

   @Headers({ "Content-Type: application/json;charset=UTF-8"})
   @POST("notification_read")
   Call<ModelNotificationRead> getNotificationRead(@Body JsonObject body);

   @Headers({ "Content-Type: application/json;charset=UTF-8"})
   @POST("makepayment")
   Call<ModelMyPayment> getPayment(@Body JsonObject body);

   @Headers({ "Content-Type: application/json;charset=UTF-8"})
   @POST("packageMakePayment1")
   Call<ModelComboPayment> getComboPayment(@Body JsonObject body);

   @Headers({ "Content-Type: application/json;charset=UTF-8"})
   @POST("forgotpassword")
   Call<ModelForgotPassword> forgotPassword(@Body JsonObject body);

   @Headers({ "Content-Type: application/json;charset=UTF-8"})
   @POST("updatepassword")
   Call<ModelUpdatePassword> updatePassword(@Body JsonObject body);

   @Headers({ "Content-Type: application/json;charset=UTF-8"})
   @POST("getpakage")
   Call<ModelCombo> getComboPackage(@Body JsonObject body);

   @Headers({ "Content-Type: application/json;charset=UTF-8"})
   @POST("getpakageById")
   Call<ModelPackageDetails> getPackageDetails(@Body JsonObject body);

   @Headers({ "Content-Type: application/json;charset=UTF-8"})
   @GET("get_feedback")
   Call<ModelFeedback> getFeedback();

}

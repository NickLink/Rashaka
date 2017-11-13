package com.rashaka.utils.rest;

import android.support.annotation.NonNull;

import com.rashaka.domain.BaseResponse;
import com.rashaka.domain.LabelItem;
import com.rashaka.domain.PartnersData;
import com.rashaka.domain.RestPageResponse;
import com.rashaka.domain.RestResponse;
import com.rashaka.domain.TermsData;
import com.rashaka.domain.bmi.Bmi;
import com.rashaka.domain.database.DailyList;
import com.rashaka.domain.fake_models.FakeNews;
import com.rashaka.domain.food.LogFood;
import com.rashaka.domain.gallery.Gallery;
import com.rashaka.domain.gallery.GalleryItemFull;
import com.rashaka.domain.login.UserLogin;
import com.rashaka.domain.news.News;
import com.rashaka.domain.news.NewsItem;
import com.rashaka.domain.notif.Notifications;
import com.rashaka.domain.profile.UserProfile;
import com.rashaka.domain.recipes.RecipeItem;
import com.rashaka.domain.routes.RouteInfo;
import com.rashaka.domain.routes.Routes;
import com.rashaka.domain.weight.WeightMonth;
import com.rashaka.domain.weight.WeightWeek;
import com.rashaka.domain.weight.WeightYear;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

/**
 * Created by User on 15.08.2017.
 */

public interface JService {
    //TODO Fake api for free news
    @GET("https://newsapi.org/v1/articles" + "?" + "source=national-geographic&sortBy=top&apiKey=c9c34b7c95b2456498a9abb788523d02")
    Observable<FakeNews> getFakeNews();

    @GET("https://newsapi.org/v1/articles" + "?" + "source=al-jazeera-english&sortBy=top&apiKey=c9c34b7c95b2456498a9abb788523d02")
    Observable<FakeNews> getFakeNews2();

    @GET("https://newsapi.org/v1/articles" + "?" + "source=ars-technica&sortBy=top&apiKey=c9c34b7c95b2456498a9abb788523d02")
    Observable<FakeNews> getFakeNews3();


    //TODO Real Application API Calls

    //TODO Login part
    @FormUrlEncoded
    @POST(RestKeys.PATH_MAIN + RestKeys.POINT_AUTH + "/" + RestKeys.CALL_SIGNIN)
    Observable<BaseResponse> signIn(
            @Field(RestKeys.KEY_EMAIL) @NonNull String email,
            @Field(RestKeys.KEY_PASSWORD) @NonNull String password,
            @Field(RestKeys.KEY_PHONE) @NonNull String phone,
            @Field(RestKeys.KEY_F_NAME) @NonNull String fName,
            @Field(RestKeys.KEY_L_NAME) @NonNull String lName);

    @FormUrlEncoded
    @POST(RestKeys.PATH_MAIN + RestKeys.POINT_AUTH + "/" + RestKeys.CALL_LOGIN)
    Observable<RestResponse<UserLogin>> logIn(
            @Field(RestKeys.KEY_EMAIL) @NonNull String email,
            @Field(RestKeys.KEY_PASSWORD) @NonNull String password);

    @FormUrlEncoded
    @POST(RestKeys.PATH_MAIN + RestKeys.POINT_AUTH + "/" + RestKeys.CALL_UPDATE_PASSWORD)
    Observable<BaseResponse> updatePassword(
            @Field(RestKeys.KEY_EMAIL) @NonNull String email,
            @Field(RestKeys.KEY_PASSWORD) @NonNull String password,
            @Field(RestKeys.KEY_NEW_PASSWORD) @NonNull String newPassword);

    @FormUrlEncoded
    @POST(RestKeys.PATH_MAIN + RestKeys.POINT_AUTH + "/" + RestKeys.CALL_FORGOT_PASSWORD)
    Observable<BaseResponse> forgotPassword(
            @Field(RestKeys.KEY_EMAIL) @NonNull String email);

    @GET(RestKeys.PATH_MAIN + RestKeys.POINT_CONTENT + "/" + RestKeys.CALL_LABELS + "/{" + RestKeys.KEY_LANGUAGE + "}")
    Observable<RestResponse<List<LabelItem>>> getLabelsItems(
            @Path(RestKeys.KEY_LANGUAGE) String lang);

    @GET(RestKeys.PATH_MAIN + RestKeys.POINT_CONTENT + "/" + RestKeys.CALL_PARTNERS)
    Observable<RestResponse<PartnersData>> getPartnersItems(); //@Path(RestKeys.KEY_LANGUAGE) String lang

    @GET(RestKeys.PATH_MAIN + RestKeys.POINT_CONTENT + "/" + RestKeys.CALL_TERMS + "/{" + RestKeys.KEY_LANGUAGE + "}")
    Observable<RestResponse<TermsData>> getTerms(
            @Path(RestKeys.KEY_LANGUAGE) String lang);

    //TODO Main part
    @GET(RestKeys.PATH_MAIN + RestKeys.POINT_USERS + "/" + RestKeys.CALL_USER + "/{" + RestKeys.KEY_USER + "}")
    Observable<RestResponse<UserProfile>> getUserById(
            @Path(RestKeys.KEY_USER) String userId,
            @Header(RestKeys.HEADER_API_KEY) String apiKey
    );

    @FormUrlEncoded
    @POST(RestKeys.PATH_MAIN + RestKeys.POINT_USERS + "/" + RestKeys.CALL_POST_DAILY_WEIGHT + "/{" + RestKeys.KEY_USER + "}")
    Observable<BaseResponse> postDailyWeight(
            @Header(RestKeys.HEADER_API_KEY) String apiKey,
            @Path(RestKeys.KEY_USER) String userId,
            @Field(RestKeys.KEY_WEIGHT) @NonNull String weight);

    @FormUrlEncoded
    @POST(RestKeys.PATH_MAIN + RestKeys.POINT_USERS + "/" + RestKeys.CALL_POST_DAILY_BMI + "/{" + RestKeys.KEY_USER + "}")
    Observable<BaseResponse> postDailyBMI(
            @Header(RestKeys.HEADER_API_KEY) String apiKey,
            @Path(RestKeys.KEY_USER) String userId,
            @Field(RestKeys.KEY_BMI) @NonNull String bmi);

    @FormUrlEncoded
    @POST(RestKeys.PATH_MAIN + RestKeys.POINT_USERS + "/" + RestKeys.CALL_POST_DAILY_STEPS + "/{" + RestKeys.KEY_USER + "}")
    Observable<BaseResponse> postDailySteps(
            @Header(RestKeys.HEADER_API_KEY) String apiKey,
            @Path(RestKeys.KEY_USER) String userId,
            @Field(RestKeys.KEY_STEPS) @NonNull String steps);


    @POST(RestKeys.PATH_MAIN + RestKeys.POINT_USERS + "/" + RestKeys.CALL_USER + "/{" + RestKeys.KEY_USER + "}")
    Call<BaseResponse> updateUser(
            @Header(RestKeys.HEADER_API_KEY) String apiKey,
            @Header(RestKeys.HEADER_CONTENT) String contentType,
            @Path(RestKeys.KEY_USER) int userId,

            @Field(RestKeys.KEY_PHONE) @NonNull String phone,
            @Field(RestKeys.KEY_F_NAME) @NonNull String fName,
            @Field(RestKeys.KEY_L_NAME) @NonNull String lName,
            @Field(RestKeys.KEY_SEX) @NonNull String sex,
            @Field(RestKeys.KEY_HEIGHT) @NonNull String height,
            @Field(RestKeys.KEY_BIRTHDAY) @NonNull String birthday,
            @Field(RestKeys.KEY_IMAGE) @NonNull String image,
            @Field(RestKeys.KEY_BACKGROUND) @NonNull String background);


    @Multipart
    @POST(RestKeys.PATH_MAIN + RestKeys.POINT_USERS + "/" + RestKeys.CALL_USER + "/{" + RestKeys.KEY_USER + "}")
    Observable<BaseResponse> updateUserProfile(
            @Header(RestKeys.HEADER_API_KEY) String apiKey,
            @Path(RestKeys.KEY_USER) String userId,
            @PartMap Map<String, RequestBody> params,
            @Part List<MultipartBody.Part> files);

    @FormUrlEncoded
    @POST(RestKeys.PATH_MAIN + RestKeys.POINT_USERS + "/" + RestKeys.CALL_PUSH + "/{" + RestKeys.KEY_USER + "}")
    Observable<BaseResponse> updateUserPushStatus(
            @Header(RestKeys.HEADER_API_KEY) String apiKey,
            @Path(RestKeys.KEY_USER) String userId,
            @Field(RestKeys.KEY_INDEX) @NonNull int index);

    @FormUrlEncoded
    @POST(RestKeys.PATH_MAIN + RestKeys.POINT_USERS + "/" + RestKeys.CALL_ADD_TOKEN)
    Observable<BaseResponse> addGsmToken(
            @Header(RestKeys.HEADER_API_KEY) @NonNull String apiKey,
            @Field(RestKeys.KEY_DEVICE_TOKEN) @NonNull String dToken,
            @Field(RestKeys.KEY_MEMBER_ID) @NonNull String mId,
            @Field(RestKeys.KEY_DEVICE) @NonNull String mDevice);

    //TODO Get notification log
    @GET(RestKeys.PATH_MAIN + RestKeys.POINT_NOTIF + "/" + RestKeys.CALL_ALL_ITEMS + "/{"
            + RestKeys.KEY_USER + "}" + "/{" + RestKeys.KEY_OFFSET + "}")
    Observable<RestPageResponse<Notifications>> getNotificationLog(
            @Header(RestKeys.HEADER_API_KEY) String apiKey,
            @Path(RestKeys.KEY_USER) String userId,
            @Path(RestKeys.KEY_OFFSET) String offset);

    //TODO Save notification item
    @FormUrlEncoded
    @POST(RestKeys.PATH_MAIN + RestKeys.POINT_NOTIF + "/" + RestKeys.CALL_NEW + "/{" + RestKeys.KEY_USER + "}")
    Observable<BaseResponse> postNotificationItem(
            @Header(RestKeys.HEADER_API_KEY) String apiKey,
            @Path(RestKeys.KEY_USER) String userId,
            @Field(RestKeys.KEY_TITLE) @NonNull String title,
            @Field(RestKeys.KEY_DESCRIPTION) @NonNull String desc,
            @Field(RestKeys.KEY_DATE_TIME) @NonNull String date);


    //TODO BMI statistics
    @GET(RestKeys.PATH_MAIN + RestKeys.POINT_STAT + "/" + RestKeys.CALL_BMI + "/{" + RestKeys.KEY_USER + "}")
    Observable<RestResponse<Bmi>> getUserBMI(
            @Header(RestKeys.HEADER_API_KEY) String apiKey,
            @Path(RestKeys.KEY_USER) String userId);

    //TODO Weight statistics
    @GET(RestKeys.PATH_MAIN + RestKeys.POINT_STAT + "/" + RestKeys.CALL_WEIGHT_WEEK + "/{" + RestKeys.KEY_USER + "}")
    Observable<RestResponse<WeightWeek>> getUserWeightWeek(
            @Header(RestKeys.HEADER_API_KEY) String apiKey,
            @Path(RestKeys.KEY_USER) String userId);

    @GET(RestKeys.PATH_MAIN + RestKeys.POINT_STAT + "/" + RestKeys.CALL_WEIGHT_MONTH + "/{" + RestKeys.KEY_USER + "}")
    Observable<RestResponse<WeightMonth>> getUserWeightMonth(
            @Header(RestKeys.HEADER_API_KEY) String apiKey,
            @Path(RestKeys.KEY_USER) String userId);

    @GET(RestKeys.PATH_MAIN + RestKeys.POINT_STAT + "/" + RestKeys.CALL_WEIGHT_YEAR + "/{" + RestKeys.KEY_USER + "}")
    Observable<RestResponse<WeightYear>> getUserWeightYear(
            @Header(RestKeys.HEADER_API_KEY) String apiKey,
            @Path(RestKeys.KEY_USER) String userId);


    //TODO Post New User Log Food
    @FormUrlEncoded
    @POST(RestKeys.PATH_MAIN + RestKeys.POINT_FOOD + "/" + RestKeys.CALL_NEW + "/{" + RestKeys.KEY_USER + "}")
    Observable<BaseResponse> postUserLogFood(
            @Header(RestKeys.HEADER_API_KEY) String apiKey,
            @Path(RestKeys.KEY_USER) String userId,
            @Field(RestKeys.KEY_DESCRIPTION) @NonNull String desc,
            @Field(RestKeys.KEY_DATE_TIME) @NonNull String date,
            @Field(RestKeys.KEY_FOOD_TYPE) @NonNull String type);

    //TODO Get All Log Food
    @GET(RestKeys.PATH_MAIN
            + RestKeys.POINT_FOOD + "/" + RestKeys.CALL_FOOD_ALL
            + "/{" + RestKeys.KEY_USER + "}" + "/{" + RestKeys.KEY_OFFSET + "}")
    Observable<RestPageResponse<LogFood>> getAllFoodLog(
            @Header(RestKeys.HEADER_API_KEY) String apiKey,
            @Path(RestKeys.KEY_USER) String lang,
            @Path(RestKeys.KEY_OFFSET) String offset);


    //TODO News part
    @GET(RestKeys.PATH_MAIN
            + RestKeys.POINT_NEWS + "/" + RestKeys.CALL_NEWS_ALL
            + "/{" + RestKeys.KEY_LANGUAGE + "}" + "/{" + RestKeys.KEY_OFFSET + "}")
    Observable<RestPageResponse<News>> getNews(
            @Header(RestKeys.HEADER_API_KEY) String apiKey,
            @Path(RestKeys.KEY_LANGUAGE) String lang,
            @Path(RestKeys.KEY_OFFSET) String offset);

    @GET(RestKeys.PATH_MAIN + RestKeys.POINT_NEWS + "/" + RestKeys.CALL_ITEM
            + "/{" + RestKeys.KEY_LANGUAGE + "}" + "/{" + RestKeys.KEY_OFFSET + "}")
    Observable<RestResponse<NewsItem>> getNewsItem(
            @Header(RestKeys.HEADER_API_KEY) String apiKey,
            @Path(RestKeys.KEY_LANGUAGE) String lang,
            @Path(RestKeys.KEY_OFFSET) String id);

    //TODO Gallery part
    @GET(RestKeys.PATH_MAIN
            + RestKeys.POINT_GALLERY + "/" + RestKeys.CALL_GALLERY_ALL
            + "/{" + RestKeys.KEY_LANGUAGE + "}" + "/{" + RestKeys.KEY_OFFSET + "}")
    Observable<RestPageResponse<Gallery>> getGallery(
            @Header(RestKeys.HEADER_API_KEY) String apiKey,
            @Path(RestKeys.KEY_LANGUAGE) String lang,
            @Path(RestKeys.KEY_OFFSET) String offset);

    @GET(RestKeys.PATH_MAIN
            + RestKeys.POINT_GALLERY + "/" + RestKeys.CALL_ITEM
            + "/{" + RestKeys.KEY_LANGUAGE + "}" + "/{" + RestKeys.KEY_OFFSET + "}")
    Observable<RestResponse<GalleryItemFull>> getGalleryItem(
            @Header(RestKeys.HEADER_API_KEY) String apiKey,
            @Path(RestKeys.KEY_LANGUAGE) String lang,
            @Path(RestKeys.KEY_OFFSET) String id);

    //TODO Recipes part
    @GET(RestKeys.PATH_MAIN
            + RestKeys.POINT_RECIPE + "/" + RestKeys.CALL_FOOD_ALL + "/{" + RestKeys.KEY_LANGUAGE + "}"
            + "/{" + RestKeys.KEY_USER + "}" + "/{" + RestKeys.KEY_OFFSET + "}")
    Observable<RestPageResponse<List<RecipeItem>>> getRecipes(
            @Header(RestKeys.HEADER_API_KEY) String apiKey,
            @Path(RestKeys.KEY_LANGUAGE) String lang,
            @Path(RestKeys.KEY_USER) String userId,
            @Path(RestKeys.KEY_OFFSET) String offset);

    @GET(RestKeys.PATH_MAIN
            + RestKeys.POINT_RECIPE + "/" + RestKeys.CALL_ITEM + "/{" + RestKeys.KEY_LANGUAGE + "}"
            + "/{" + RestKeys.KEY_USER + "}" + "/{" + RestKeys.KEY_OFFSET + "}")
    Observable<RestResponse<RecipeItem>> getRecipeItem(
            @Header(RestKeys.HEADER_API_KEY) String apiKey,
            @Path(RestKeys.KEY_LANGUAGE) String lang,
            @Path(RestKeys.KEY_USER) String userId,
            @Path(RestKeys.KEY_OFFSET) String id);


    //TODO Tracker part
    //SAVE New track
    @FormUrlEncoded
    @POST(RestKeys.PATH_MAIN + RestKeys.POINT_TRACKER + "/" + RestKeys.CALL_NEW + "/{" + RestKeys.KEY_USER + "}")
    Observable<BaseResponse> postNewUserRoute(
            @Header(RestKeys.HEADER_API_KEY) @NonNull String apiKey,
            @Path(RestKeys.KEY_USER) @NonNull String userId,
            @Field(RestKeys.KEY_START) @NonNull String start,
            @Field(RestKeys.KEY_STOP) @NonNull String stop,
            @Field(RestKeys.KEY_POINTS) @NonNull String points,
            @Field(RestKeys.KEY_TIME) @NonNull int time,
            @Field(RestKeys.KEY_PACE) @NonNull double pace,
            @Field(RestKeys.KEY_DISTANCE) @NonNull double distance,
            @Field(RestKeys.KEY_DESC) @NonNull String description);


    //LOAD All tracks list
    @GET(RestKeys.PATH_MAIN + RestKeys.POINT_TRACKER + "/" + RestKeys.CALL_ROUTES_ALL
            + "/{" + RestKeys.KEY_USER + "}" + "/{" + RestKeys.KEY_OFFSET + "}")
    Observable<RestPageResponse<Routes>> getAllUserRoutes(
            @Header(RestKeys.HEADER_API_KEY) @NonNull String apiKey,
            @Path(RestKeys.KEY_USER) @NonNull String userId,
            @Path(RestKeys.KEY_OFFSET) @NonNull String offset);

    //LOAD Track by id
    @GET(RestKeys.PATH_MAIN + RestKeys.POINT_TRACKER + "/" + RestKeys.CALL_ITEM
            + "/{" + RestKeys.KEY_ID + "}")
    //+ "/{" + RestKeys.KEY_OFFSET + "}"
    Observable<RestResponse<RouteInfo>> getUserRouteById(
            @Header(RestKeys.HEADER_API_KEY) @NonNull String apiKey,
            //@Path(RestKeys.KEY_USER) @NonNull String userId,
            @Path(RestKeys.KEY_ID) @NonNull String id);


//    @FormUrlEncoded
//    @POST("http://lemall.cyberchisel.com/" + "/" + RestKeys.LOYALTY + "/" + RestKeys.CALL_VERIFY_PHONE)
//    Call<BaseResponse> verifyPhone(@Field(RestKeys.KEY_CARD_ID) @NonNull String cardId,
//                                    @Field(RestKeys.KEY_VERIFY_CODE) @NonNull String verifyCode);

    //TODO Syncronization part
    @FormUrlEncoded
    @POST(RestKeys.PATH_MAIN + RestKeys.POINT_USERS + "/" + RestKeys.CALL_POST_DAILY_STEPS + "/{" + RestKeys.KEY_USER + "}")
    Observable<RestResponse<DailyList>> postDailyItem(
            @Header(RestKeys.HEADER_API_KEY) String apiKey,
            @Path(RestKeys.KEY_USER) String userId,
            @Field(RestKeys.KEY_LIST) @NonNull String list);

    @GET(RestKeys.PATH_MAIN + RestKeys.POINT_STAT + "/" + RestKeys.CALL_STEPS_STAT + "/{" + RestKeys.KEY_USER + "}")
    Observable<RestResponse<DailyList>> getDailyItems(
            @Header(RestKeys.HEADER_API_KEY) String apiKey,
            @Path(RestKeys.KEY_USER) String userId);
}

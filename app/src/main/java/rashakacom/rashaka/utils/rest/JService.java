package rashakacom.rashaka.utils.rest;

import android.support.annotation.NonNull;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rashakacom.rashaka.domain.BaseResponse;
import rashakacom.rashaka.domain.LabelItem;
import rashakacom.rashaka.domain.PartnersData;
import rashakacom.rashaka.domain.RestPageResponse;
import rashakacom.rashaka.domain.RestResponse;
import rashakacom.rashaka.domain.TermsData;
import rashakacom.rashaka.domain.fake_models.FakeNews;
import rashakacom.rashaka.domain.food.LogFood;
import rashakacom.rashaka.domain.login.UserLogin;
import rashakacom.rashaka.domain.news.NewsItem;
import rashakacom.rashaka.domain.profile.UserProfile;
import rashakacom.rashaka.domain.routes.RouteInfo;
import rashakacom.rashaka.domain.routes.Routes;
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



//    @Multipart
//    @POST(RestKeys.PATH_MAIN + RestKeys.POINT_USERS + "/" + RestKeys.CALL_USER + "/{" + RestKeys.KEY_USER + "}")
//    Observable<BaseResponse> updateUserHashMap(
//            @Header(RestKeys.HEADER_API_KEY) String apiKey,
//            //@Header(RestKeys.HEADER_CONTENT) String contentType,
//            @Path(RestKeys.KEY_USER) String userId,
//            @PartMap Map<String, Object> authData); //Part

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
    Observable<RestPageResponse<List<NewsItem>>> getAllNews(
            @Header(RestKeys.HEADER_API_KEY) String apiKey,
            @Path(RestKeys.KEY_LANGUAGE) String lang,
            @Path(RestKeys.KEY_OFFSET) String offset);

    @GET(RestKeys.PATH_MAIN + RestKeys.POINT_NEWS + "/" + RestKeys.CALL_ITEM
            + "/{" + RestKeys.KEY_LANGUAGE + "}" + "/{" + RestKeys.KEY_OFFSET + "}")
    Observable<RestResponse<NewsItem>> getNewsItem(
            @Header(RestKeys.HEADER_API_KEY) String apiKey,
            @Path(RestKeys.KEY_LANGUAGE) String lang,
            @Path(RestKeys.KEY_OFFSET) String offset);





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
            + "/{" + RestKeys.KEY_USER + "}"+ "/{" + RestKeys.KEY_OFFSET + "}")
    Observable<RestPageResponse<Routes>> getAllUserRoutes(
            @Header(RestKeys.HEADER_API_KEY) @NonNull String apiKey,
            @Path(RestKeys.KEY_USER) @NonNull String userId,
            @Path(RestKeys.KEY_OFFSET) @NonNull String offset);

    //LOAD Track by id
    @GET(RestKeys.PATH_MAIN + RestKeys.POINT_TRACKER + "/" + RestKeys.CALL_ITEM
            + "/{" + RestKeys.KEY_ID + "}") //+ "/{" + RestKeys.KEY_OFFSET + "}"
    Observable<RestResponse<RouteInfo>> getUserRouteById(
            @Header(RestKeys.HEADER_API_KEY) @NonNull String apiKey,
            //@Path(RestKeys.KEY_USER) @NonNull String userId,
            @Path(RestKeys.KEY_ID) @NonNull String id);


//    @FormUrlEncoded
//    @POST("http://lemall.cyberchisel.com/" + "/" + RestKeys.LOYALTY + "/" + RestKeys.CALL_VERIFY_PHONE)
//    Call<BaseResponse> verifyPhone(@Field(RestKeys.KEY_CARD_ID) @NonNull String cardId,
//                                    @Field(RestKeys.KEY_VERIFY_CODE) @NonNull String verifyCode);

}

package rashakacom.rashaka.utils.rest;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Observable;
import rashakacom.rashaka.utils.rest.fake_models.FakeNews;
import rashakacom.rashaka.utils.rest.models.BaseResponse;
import rashakacom.rashaka.utils.rest.models.LabelItem;
import rashakacom.rashaka.utils.rest.models.LoginData;
import rashakacom.rashaka.utils.rest.models.PartnersDataItem;
import rashakacom.rashaka.utils.rest.models.RestResponse;
import rashakacom.rashaka.utils.rest.models.TermsData;
import rashakacom.rashaka.utils.rest.models.UserData;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by User on 15.08.2017.
 */

public interface JService {
    //TODO Fake api for free news
    @GET("https://newsapi.org/v1/articles" + "?" + "source=national-geographic&sortBy=top&apiKey=c9c34b7c95b2456498a9abb788523d02")
    Observable<FakeNews> getFakeNews();


    @FormUrlEncoded
    @POST(RestKeys.PATH_MAIN + "/" + RestKeys.POINT_AUTH + "/" + RestKeys.CALL_SIGNIN)
    Observable<BaseResponse> signIn(
            @Field(RestKeys.KEY_EMAIL) @NonNull String email,
            @Field(RestKeys.KEY_PASSWORD) @NonNull String password,
            @Field(RestKeys.KEY_PHONE) @NonNull String phone,
            @Field(RestKeys.KEY_F_NAME) @NonNull String fName,
            @Field(RestKeys.KEY_L_NAME) @NonNull String lName);

    @FormUrlEncoded
    @POST(RestKeys.PATH_MAIN + "/" + RestKeys.POINT_AUTH + "/" + RestKeys.CALL_LOGIN)
    Observable<RestResponse<LoginData>> logIn(
            @Field(RestKeys.KEY_EMAIL) @NonNull String email,
            @Field(RestKeys.KEY_PASSWORD) @NonNull String password);


    @FormUrlEncoded
    @POST(RestKeys.PATH_MAIN + "/" + RestKeys.POINT_AUTH + "/" + RestKeys.CALL_UPDATE_PASSWORD)
    Call<BaseResponse> updatePassword(
            @Field(RestKeys.KEY_EMAIL) @NonNull String email,
            @Field(RestKeys.KEY_PASSWORD) @NonNull String password,
            @Field(RestKeys.KEY_NEW_PASSWORD) @NonNull String newPassword);

    @FormUrlEncoded
    @POST(RestKeys.PATH_MAIN + "/" + RestKeys.POINT_AUTH + "/" + RestKeys.CALL_FORGOT_PASSWORD)
    Call<BaseResponse> forgotPassword(
            @Field(RestKeys.KEY_EMAIL) @NonNull String email);

    @GET(RestKeys.PATH_MAIN + "/" + RestKeys.POINT_CONTENT + "/" + RestKeys.CALL_LABELS + "/{" + RestKeys.KEY_LANGUAGE + "}")
    Observable<RestResponse<List<LabelItem>>> getLabelsItems(
            @Path(RestKeys.KEY_LANGUAGE) String lang);

    @GET(RestKeys.PATH_MAIN + "/" + RestKeys.POINT_CONTENT + "/" + RestKeys.CALL_PARTNERS)
    Observable<RestResponse<List<PartnersDataItem>>> getPartnersItems(); //@Path(RestKeys.KEY_LANGUAGE) String lang

    @GET(RestKeys.PATH_MAIN + "/" + RestKeys.POINT_CONTENT + "/" + RestKeys.CALL_TERMS + "/{" + RestKeys.KEY_LANGUAGE + "}")
    Observable<RestResponse<TermsData>> getTerms(
            @Path(RestKeys.KEY_LANGUAGE) String lang);

    @GET(RestKeys.PATH_MAIN + "/" + RestKeys.POINT_USERS + "/" + RestKeys.CALL_USER + "/{" + RestKeys.KEY_USER + "}")
    Call<RestResponse<UserData>> getUser(
            @Header(RestKeys.HEADER_API_KEY) String apiKey,
            @Header(RestKeys.HEADER_CONTENT) String contentType,
            @Path(RestKeys.KEY_USER) int userId);

    @POST(RestKeys.PATH_MAIN + "/" + RestKeys.POINT_USERS + "/" + RestKeys.CALL_POST_DAILY + "/{" + RestKeys.KEY_USER + "}")
    Call<BaseResponse> postDaily(
            @Header(RestKeys.HEADER_API_KEY) String apiKey,
            @Header(RestKeys.HEADER_CONTENT) String contentType,
            @Path(RestKeys.KEY_USER) int userId,
            @Field(RestKeys.KEY_WEIGHT) @NonNull String weight,
            @Field(RestKeys.KEY_WEIGHT_GOAL) @NonNull String weightGoal,
            @Field(RestKeys.KEY_STEPS_GOAL) @NonNull String stepsGoal);

    @POST(RestKeys.PATH_MAIN + "/" + RestKeys.POINT_USERS + "/" + RestKeys.CALL_USER + "/{" + RestKeys.KEY_USER + "}")
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


//    @FormUrlEncoded
//    @POST("http://lemall.cyberchisel.com/" + "/" + RestKeys.LOYALTY + "/" + RestKeys.CALL_VERIFY_PHONE)
//    Call<BaseResponse> verifyPhone(@Field(RestKeys.KEY_CARD_ID) @NonNull String cardId,
//                                    @Field(RestKeys.KEY_VERIFY_CODE) @NonNull String verifyCode);

}

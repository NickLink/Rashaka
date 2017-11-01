package com.rashaka.utils.rest;

/**
 * Created by User on 15.08.2017.
 */

import android.accounts.NetworkErrorException;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.rashaka.domain.routes.PointInfo;
import com.rashaka.domain.routes.RouteInfo;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.TimeoutException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by com on 01.08.16.
 */
public class Rest {

    private static final String TAG = Rest.class.getSimpleName();
    private static final int INAUTHENTIC = 403;
    private static Retrofit sRetrofit;
    private static JService sService;
    private static OkHttpClient okClient;

    static {
        initService();
    }

    private static void initClient() {
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.setLevel(HttpLoggingInterceptor.Level.BODY);
        okClient = new OkHttpClient.Builder()
                .addInterceptor(logger)
//				.addInterceptor(new Interceptor() {
//					@Override
//					public Response intercept(Chain chain) throws IOException {
//						Request request = chain.request().newBuilder()
//								.addHeader(RestKeys.X_AUTHORIZATION, UserDataHelper.getUser().getLoginToken())
//								.build();
//						return chain.proceed(request);
//					}
//				})
                .build();


        sRetrofit = new Retrofit.Builder()
                .baseUrl(RestKeys.PATH_MAIN)
                .addConverterFactory(GsonConverterFactory.create(
                        new GsonBuilder()
                                .registerTypeAdapter(RouteInfo.class, new RouteInfoDeserializer())
                                .create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okClient)
                .build();

    }

    private static void initService() {
        if (sRetrofit == null) {
            initClient();
        }
        sService = sRetrofit.create(JService.class);
    }

//    public static Retrofit getRetrofit() {
//        return sRetrofit;
//    }

    @NonNull
    public static JService call() {
        if (sService == null) {
            initService();
        }
        return sService;
    }

    public static String ErrorMessages(Throwable error) {
        String message = null;
        if (error instanceof NetworkErrorException) {
            message = "Cannot connect to Internet...Please check your connection.";
        } else if (error instanceof JsonParseException) {
            message = "Receiving data error. Possibly server under maintenance.";
        } else if (error instanceof TimeoutException) {
            message = "Connection TimeOut. Please check your internet connection.";
        }
        return message;
    }

    public static final String MULTIPART_FORM_DATA = "multipart/form-data";
    public static final String MEDIA_TYPE_PNG = "image/jpeg";

    public static RequestBody createRequestBody(@NonNull File file) {
        return RequestBody.create(
                MediaType.parse(MEDIA_TYPE_PNG), file);
    }

    public static RequestBody createRequestBody(@NonNull String s) {
        return RequestBody.create(
                MediaType.parse(MULTIPART_FORM_DATA), s);
    }

    private static class RouteInfoDeserializer implements JsonDeserializer<RouteInfo> {
        @Override
        public RouteInfo deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            RouteInfo routeInfo = new RouteInfo();
            JsonObject jsonObject = json.getAsJsonObject();

            routeInfo.setId(Long.parseLong(jsonObject.get("id").getAsString()));
            routeInfo.setStart(jsonObject.get("start").getAsString());
            routeInfo.setStop(jsonObject.get("stop").getAsString());
            routeInfo.setTime(Integer.parseInt(jsonObject.get("time").getAsString()));
            routeInfo.setDistance(Double.parseDouble(jsonObject.get("distance").getAsString()));
            routeInfo.setPace(Double.parseDouble(jsonObject.get("pace").getAsString()));
            routeInfo.setDesc(jsonObject.get("desc").getAsString());

            if(jsonObject.has("points")){
                String points = jsonObject.get("points").getAsString();
                routeInfo.setPoints(toList(points));
            }

            return routeInfo;
        }
    }

    public static List<PointInfo> toList(String json) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        Gson gson = new Gson();
        Type listType = new TypeToken<List<PointInfo>>(){}.getType();
        return (List<PointInfo>) gson.fromJson(json, listType);
    }

    public static <T> List<T> toList(String json, Class<T> clazz) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        Gson gson = new Gson();
        Type listType = new TypeToken<List<T>>(){}.getType();
        return (List<T>) gson.fromJson(json, listType);
    }

}
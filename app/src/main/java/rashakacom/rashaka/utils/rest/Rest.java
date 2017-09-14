package rashakacom.rashaka.utils.rest;

/**
 * Created by User on 15.08.2017.
 */

import android.accounts.NetworkErrorException;
import android.support.annotation.NonNull;

import com.google.gson.JsonParseException;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeoutException;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by com on 01.08.16.
 */
public class Rest {

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
                .addConverterFactory(GsonConverterFactory.create())
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
}
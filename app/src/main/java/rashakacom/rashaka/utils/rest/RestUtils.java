package rashakacom.rashaka.utils.rest;

import android.accounts.NetworkErrorException;

import com.google.gson.JsonParseException;

import java.util.concurrent.TimeoutException;

/**
 * Created by User on 23.08.2017.
 */

public class RestUtils {

    public static String ErrorMessages(Throwable error) {
        String message = null;
        if (error instanceof NetworkErrorException) {
            message = "Cannot connect to Internet...Please check your connection.";
        } else if (error instanceof JsonParseException) {
            message = "Receiving data error. Possibly server under maintenance.";
        } else if (error instanceof TimeoutException) {
            message = "Connection TimeOut. Please check your internet connection.";
        } else message = error.getLocalizedMessage();
        return message;
    }
}

package rashakacom.rashaka.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AlignmentSpan;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;

import rashakacom.rashaka.R;
import rashakacom.rashaka.RaApp;

import static rashakacom.rashaka.utils.Consts.CHECK_NAME_LENGTH;
import static rashakacom.rashaka.utils.Consts.CHECK_PASS_LENGTH;
import static rashakacom.rashaka.utils.Consts.CHECK_PHONE_LENGTH;

/**
 * Created by User on 22.08.2017.
 */

public class Support {

    public static final int Outline = 1;

    public static final int MEDIA_TYPE_IMAGE = 901;


    public static String getDateFromMillis(long dateInMillis){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss", Locale.US);
        return formatter.format(new Date(dateInMillis));
    }

    public static String getCurrentStringDate(){
        Calendar calendar = Calendar.getInstance();
        int thisYear = calendar.get(Calendar.YEAR);
        int thisMonth = calendar.get(Calendar.MONTH);
        int thisDay = calendar.get(Calendar.DAY_OF_MONTH);
        return thisDay + " " + getMonthByInt(thisMonth) + " " + thisYear;
    }

    public static String getMonthByInt(int month){
        switch (month){
            case Calendar.JANUARY:
                return RaApp.getLabel("key_jan");
            case Calendar.FEBRUARY:
                return RaApp.getLabel("key_feb");
            case Calendar.MARCH:
                return RaApp.getLabel("key_march");
            case Calendar.APRIL:
                return RaApp.getLabel("key_april");
            case Calendar.MAY:
                return RaApp.getLabel("key_may");
            case Calendar.JUNE:
                return RaApp.getLabel("key_june");
            case Calendar.JULY:
                return RaApp.getLabel("key_july");
            case Calendar.AUGUST:
                return RaApp.getLabel("key_aug");
            case Calendar.SEPTEMBER:
                return RaApp.getLabel("key_sep");
            case Calendar.OCTOBER:
                return RaApp.getLabel("key_oct");
            case Calendar.NOVEMBER:
                return RaApp.getLabel("key_nov");
            case Calendar.DECEMBER:
                return RaApp.getLabel("key_dec");
            default:
                return "";
        }
    }

    public static void setRedOutline(View view) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                RippleDrawable ripple = (RippleDrawable) view.getBackground();
                GradientDrawable back_phone = (GradientDrawable) ripple.findDrawableByLayerId(R.id.background);
                back_phone.setStroke(Support.Outline, Color.RED);
            } else {
                GradientDrawable back_phone = (GradientDrawable) view.getBackground();
                back_phone.setStroke(Support.Outline, Color.RED);
            }

        } catch (Exception e) {
            Log.e("TAG", "setRedOutline " + e.getLocalizedMessage());
        }
    }

    public static void setWhiteOutline(View view) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                RippleDrawable ripple = (RippleDrawable) view.getBackground();
                GradientDrawable back_phone = (GradientDrawable) ripple.findDrawableByLayerId(R.id.background);
                back_phone.setStroke(Support.Outline, Color.WHITE);
            } else {
                GradientDrawable back_phone = (GradientDrawable) view.getBackground();
                back_phone.setStroke(Support.Outline, Color.WHITE);
            }

        } catch (Exception e) {
            Log.e("TAG", "setWhiteOutline " + e.getLocalizedMessage());
        }
    }

    public static boolean nameOk(String name){
        if (!TextUtils.isEmpty(name) && name.length() >= CHECK_NAME_LENGTH)
            return true;
        else
            return false;
    }

    public static boolean emailOk(String email) {
        if(TextUtils.isEmpty(email)) return false;
        Matcher matcher = Patterns.EMAIL_ADDRESS.matcher(email);
        if (matcher.matches())
            return true;
        else
            return false;
    }

    public static boolean passwordOk(String password){
        if (password != null && password.length() >= CHECK_PASS_LENGTH && !password.equals(password.toLowerCase()))
            return true;
        else
            return false;
    }

    public static boolean phoneOk(String password){
        if (password != null && password.length() == CHECK_PHONE_LENGTH)
            return true;
        else
            return false;
    }

    public static void setStatusBarColor(Activity activity, int color){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            // clear FLAG_TRANSLUCENT_STATUS flag:
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            // finally change the color
            window.setStatusBarColor(ContextCompat.getColor(activity, color)); //R.color.login_statusbar_color
        }
    }

    public static boolean isCameraPermission() {
        return (ContextCompat.checkSelfPermission(
                RaApp.getContext(),
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    public static boolean isWritePermission() {
        return ContextCompat.checkSelfPermission(
                RaApp.getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    public static SpannableString getArMenu(String key){
        SpannableString s = new SpannableString(RaApp.getLabel(key));
        s.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_OPPOSITE), 0, s.length(), 0);
        return s;
    }

}

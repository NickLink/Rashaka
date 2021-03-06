package com.rashaka.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.CompoundButtonCompat;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.Layout;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AlignmentSpan;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.rashaka.R;
import com.rashaka.RaApp;
import com.rashaka.system.lang.LangKeys;
import com.rashaka.utils.helpers.views.span.TopAlignSuperscriptSpan;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;

import static com.rashaka.utils.Consts.CHECK_NAME_LENGTH;
import static com.rashaka.utils.Consts.CHECK_PASS_LENGTH;
import static com.rashaka.utils.Consts.CHECK_PHONE_LENGTH;

/**
 * Created by User on 22.08.2017.
 */

public class Support {

    private static final String TAG = Support.class.getSimpleName();
    public static final int Outline = 1;
    public static final String DATE_FORMAT_FULL = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String NOTY_DATE = "dd/MM/yyyy";
    public static final String NOTY_TIME = "HH:mm aa";

    public static final int MEDIA_TYPE_IMAGE = 901;

    public static long startOfDay() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTimeInMillis();
    }

    public static long startOfDay(long time) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTimeInMillis();
    }

    public static double getBMI(@NonNull String weight, @NonNull String height){
        double mWeight, mHeight, bmi;
        try {
            mWeight = Double.parseDouble(weight);
            mHeight = Double.parseDouble(height);
            bmi = mWeight / (mHeight * mHeight / 10000);
        } catch (Exception e){
            bmi = 0;
        }
        return round(bmi, 2);
    }

    public static String getNotyTimeFromDate(String date){
        Date mDate = null;
        try {
            mDate = new SimpleDateFormat(DATE_FORMAT_FULL, getCurrentLocale(RaApp.getContext())).parse(date); //Locale.US
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String newString = new SimpleDateFormat(NOTY_TIME).format(mDate);
        return newString.toUpperCase();
    }

    public static String getNotyDateFromDate(String date){
        Date mDate = null;
        try {
            mDate = new SimpleDateFormat(DATE_FORMAT_FULL, getCurrentLocale(RaApp.getContext())).parse(date); //Locale.US
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String newString = new SimpleDateFormat(NOTY_DATE).format(mDate);
        return newString;
    }

    public static int getDiffYears(Date first, Date last) {
        Calendar a = getCalendar(first);
        Calendar b = getCalendar(last);
        int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
        if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH) ||
                (a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(Calendar.DATE))) {
            diff--;
        }
        return diff;
    }

    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(getCurrentLocale(RaApp.getContext())); //Locale.US
        cal.setTime(date);
        return cal;
    }

    public static SpannableStringBuilder getUpperCase(String text, String upper){
        SpannableStringBuilder builder = new SpannableStringBuilder();
        SpannableString spannableString = new SpannableString(upper);
        spannableString.setSpan(new TopAlignSuperscriptSpan( (float)0.35 ), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE );
        builder.append(text);
        builder.append(spannableString);
        return builder;
    }

    public static void setCheckBoxColor(AppCompatCheckBox checkBox, int state, int color){
        int states[][] = {{state}, {}};
        int colors[] = {color, color};
        CompoundButtonCompat.setButtonTintList(checkBox, new ColorStateList(states, colors));
    }

    public static String getDateFromMillis(long dateInMillis, String format){
        SimpleDateFormat formatter = new SimpleDateFormat(format, getCurrentLocale(RaApp.getContext())); //Locale.US //DATE_FORMAT_FULL
        return formatter.format(new Date(dateInMillis));
    }

    public static String GetDateTimeForScreen(){
        Date presentTime_Date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy, HH:mm");
        dateFormat.setTimeZone(getDefaultTimeZone());
        return dateFormat.format(presentTime_Date);
    }

    public static String GetDateTimeForAPI(String format){
        Date presentTime_Date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setTimeZone(getDefaultTimeZone());
        return dateFormat.format(presentTime_Date);
    }

    public static String GetPreviousDateForAPI(String format){
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(c.getTimeInMillis() - 1000 * 60 * 60 *24);
        Date presentTime_Date = c.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setTimeZone(getDefaultTimeZone());
        return dateFormat.format(presentTime_Date);
    }

    public static TimeZone getDefaultTimeZone(){
        TimeZone timeZone = TimeZone.getDefault();
        Log.e(TAG, "Default TimeZone -> " + timeZone.getDisplayName());
        return timeZone;
    }

    public static String getDateFromFullDate(@NonNull String date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getDateFromString(date, DATE_FORMAT_FULL));
        return new StringBuilder()
                .append(calendar.get(Calendar.YEAR))
                .append("-")
                .append(String.format("%02d", calendar.get(Calendar.MONTH) + 1)) //As in Android calendar month starts from 0
                .append("-")
                .append(String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH)))
                .toString();
    }

    public static Locale getCurrentLocale(Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            return context.getResources().getConfiguration().getLocales().get(0);
        } else{
            //noinspection deprecation
            return context.getResources().getConfiguration().locale;
        }
    }

    public static String getDatePeriodMonth(String start, String end){
        int mDay, mMonth;
        StringBuilder sb = new StringBuilder();
        Calendar calendar = Calendar.getInstance(); //
        calendar.setTime(getDateFromString(start, DATE_FORMAT));
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        sb.append(String.valueOf(mDay));
        sb.append("-");
        calendar.setTime(getDateFromString(end, DATE_FORMAT));
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        sb.append(String.valueOf(mDay));
        sb.append(" ");
        mMonth = calendar.get(Calendar.MONTH);
        sb.append(getMonthByInt(mMonth));
        return sb.toString();
    }

    public static Date getDateFromString(@NonNull String date, @NonNull String format){
        Date d = null;
        try {
            SimpleDateFormat f = new SimpleDateFormat(format);
            d = f.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            return d;
        }
    }

    public static String getPreviousDayDate(boolean time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(calendar.getTimeInMillis() - 1000 * 60 * 60 * 24);
        int thisYear = calendar.get(Calendar.YEAR);
        int thisMonth = calendar.get(Calendar.MONTH);
        int thisDay = calendar.get(Calendar.DAY_OF_MONTH);
        if(time){
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int min = calendar.get(Calendar.MINUTE);
            int sec = calendar.get(Calendar.SECOND);
            return thisDay + " " + getMonthByInt(thisMonth) + " "
                    + thisYear + " "
                    + String.format("%02d", hour) + ":" + String.format("%02d", min) + ":" + String.format("%02d", sec);
        } else {
            return thisDay + " " + getMonthByInt(thisMonth) + " " + thisYear;
        }
    }

    public static String getCurrentStringDate(boolean time){
        Calendar calendar = Calendar.getInstance();
        int thisYear = calendar.get(Calendar.YEAR);
        int thisMonth = calendar.get(Calendar.MONTH);
        int thisDay = calendar.get(Calendar.DAY_OF_MONTH);
        if(time){
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int min = calendar.get(Calendar.MINUTE);
            int sec = calendar.get(Calendar.SECOND);
            return thisDay + " " + getMonthByInt(thisMonth) + " "
                    + thisYear + " "
                    + String.format("%02d", hour) + ":" + String.format("%02d", min) + ":" + String.format("%02d", sec);
        } else {
            return thisDay + " " + getMonthByInt(thisMonth) + " " + thisYear;
        }
    }

    public static String getStringDateByDate(String date, String format, boolean time){
        Calendar calendar = Calendar.getInstance(); //
        calendar.setTime(getDateFromString(date, format));
        int thisYear = calendar.get(Calendar.YEAR);
        int thisMonth = calendar.get(Calendar.MONTH);
        int thisDay = calendar.get(Calendar.DAY_OF_MONTH);
        if(time){
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int min = calendar.get(Calendar.MINUTE);
            int sec = calendar.get(Calendar.SECOND);
            return thisDay + " " + getMonthByInt(thisMonth) + " "
                    + thisYear + " "
                    + String.format("%02d", hour) + ":" + String.format("%02d", min) + ":" + String.format("%02d", sec);
        } else {
            return thisDay + " " + getMonthByInt(thisMonth) + " " + thisYear;
        }
    }

    public static int getYearByDate(String date, String format){
        Calendar calendar = Calendar.getInstance(); //
        calendar.setTime(getDateFromString(date, format));
        int thisYear = calendar.get(Calendar.YEAR);
        return thisYear;
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

    public static String getDayByInt(int day){
        switch (day){
            case Calendar.MONDAY:
                return RaApp.getLabel(LangKeys.key_mon);
            case Calendar.TUESDAY:
                return RaApp.getLabel(LangKeys.key_tues);
            case Calendar.WEDNESDAY:
                return RaApp.getLabel(LangKeys.key_weds);
            case Calendar.THURSDAY:
                return RaApp.getLabel(LangKeys.key_thurs);
            case Calendar.FRIDAY:
                return RaApp.getLabel(LangKeys.key_fri);
            case Calendar.SATURDAY:
                return RaApp.getLabel(LangKeys.key_sat);
            case Calendar.SUNDAY:
                return RaApp.getLabel(LangKeys.key_sun);
            default:
                return RaApp.getLabel(LangKeys.key_mon);
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
            Log.e("setRedOutline", "setRedOutline " + e.getLocalizedMessage());
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
            Log.e("setWhiteOutline", "setWhiteOutline " + e.getLocalizedMessage());
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

    public static boolean isGPSPermission() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                (ContextCompat.checkSelfPermission(RaApp.getContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
    }

    public static SpannableString getArMenu(String key){
        SpannableString s = new SpannableString(RaApp.getLabel(key));
        s.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_OPPOSITE), 0, s.length(), 0);
        return s;
    }

    public static int dpToPx(int dp){
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}

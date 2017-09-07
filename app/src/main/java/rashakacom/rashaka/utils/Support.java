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

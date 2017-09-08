package rashakacom.rashaka.fragments.settings.profile;

import android.support.design.widget.BottomSheetDialogFragment;

/**
 * Created by User on 24.08.2017.
 */

public interface ProfileView {

    void setLangValue();

    void selectImage();

    void setProfileBackground(String background);
    void setProfileImage(String image);

    void setProfileInfo(String name, String email);

    void setProfileGenderText(String text);
    void setProfileDobText(String text);
    void setProfileHeightText(String text);
    void setProfileWeightText(String text);
    void setProfileWeightGText(String text);
    void setProfileStepsGText(String text);


    void setActiveBar(int max, int progress);
    void setStepsBar(int max, int progress);


    void setBottomDialog(BottomSheetDialogFragment bottomDialog);
}

package rashakacom.rashaka;

import java.util.List;

import rashakacom.rashaka.utils.rest.models.PartnersDataItem;

/**
 * Created by User on 22.08.2017.
 */

public interface LoginRouter {

    void callLang();
    void callRegister();
    void callSignIn(int side);
    void callForgotPass();

    void setPartnersLogos(List<PartnersDataItem> list);

    void showError(String error);

    void goMainActivity();
}

package com.rashaka;

import com.rashaka.domain.PartnersDataItem;

import java.util.List;

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

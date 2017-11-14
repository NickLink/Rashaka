package com.rashaka;

/**
 * Created by User on 17.08.2017.
 */

public interface MainRouter {

    void showError(String error);

    //void pushFragment(BaseFragment fragment);

    void showLoader();
    void hideLoader();

    void showDialog(String title, String text, String button);

    void onBackPressed();
    void RestartService();
}

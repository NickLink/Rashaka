package rashakacom.rashaka.fragments.login.register;

/**
 * Created by User on 22.08.2017.
 */

public interface RegisterView {

    void goSign();

    void setViewsValues();
    void setViewsOk();

    void setTermsDialog(String title, String text);

    void nameError();
    void lNameError();
    void emailError();
    void passwError();
    void passwConfError();
    void termsError();

}

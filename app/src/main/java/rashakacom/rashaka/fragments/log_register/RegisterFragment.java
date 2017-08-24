package rashakacom.rashaka.fragments.log_register;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import rashakacom.rashaka.LoginRouter;
import rashakacom.rashaka.R;
import rashakacom.rashaka.RaApp;
import rashakacom.rashaka.utils.Support;
import rashakacom.rashaka.utils.dialogs.DialogStandartButton;
import rashakacom.rashaka.utils.helpers.structure.SuperFragment;
import rashakacom.rashaka.utils.helpers.structure.SuperPresenter;
import rashakacom.rashaka.utils.helpers.structure.helpers.Layout;
import rashakacom.rashaka.utils.helpers.views.email.EmailEditText;
import rashakacom.rashaka.utils.helpers.views.name.NameEditText;
import rashakacom.rashaka.utils.helpers.views.passw.PasswordEditText;
import rashakacom.rashaka.utils.helpers.views.phone.PhoneEditText;

import static rashakacom.rashaka.utils.Consts.ANIMATION_LEFT;

/**
 * Created by User on 17.08.2017.
 */

@Layout(id = R.layout.log_item_register)
public class RegisterFragment extends SuperFragment implements RegisterView {

    private LoginRouter myRouter;
    private RegisterPresenter mPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("TAG", "RegisterFragment onAttach");
        myRouter = (LoginRouter) getActivity();
        mPresenter = new RegisterPresenter();
    }

    @Override
    public void onStop(){
        super.onStop();
        mPresenter.onStop();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        //TODO On register click
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.onRegisterClick(
                        mRegisterName.getText().toString(),
                        mRegisterLName.getText().toString(),
                        mRegisterEmail.getText().toString(),
                        mRegisterPassword.getText().toString(),
                        mRegisterPasswordConfirm.getText().toString(),
                        mRegisterPhone.getText().toString(),
                        mRegisterCheckBox.isChecked());
            }
        });

        //TODO On Terms & Conditions Dialog
        mRegisterTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.onTermsClicked();
            }
        });

        //TODO GO to SignIn Page
        mRegisterSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.onSignInSelected();
            }
        });

        Log.e("TAG", "RegisterFragment onViewCreated");
    }

    @NonNull
    @Override
    public SuperPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void goSign() {
        myRouter.callSignIn(ANIMATION_LEFT);
    }

    @Override
    public void setViewsValues() {
        mRegisterName.setHint(RaApp.getLabel("key_name"));
        mRegisterLName.setHint(RaApp.getLabel("key_last_name"));
        mRegisterEmail.setHint(RaApp.getLabel("key_email"));
        mRegisterPassword.setHint(RaApp.getLabel("key_password"));
        mRegisterPasswordConfirm.setHint(RaApp.getLabel("key_confirm_password"));
        mRegisterPhone.setHint(RaApp.getLabel("key_phone_number"));
        mRegisterButtonText.setText(RaApp.getLabel("key_register"));
        mRegisterTerms.setText(RaApp.getLabel("key_terms_conditions"));
        mRegisterSignInButton.setText(RaApp.getLabel("key_signin"));
    }

    @Override
    public void setViewsOk(){
        Support.setWhiteOutline(mRegisterName);
        Support.setWhiteOutline(mRegisterLName);
        Support.setWhiteOutline(mRegisterEmail);
        Support.setWhiteOutline(mRegisterPassword);
        Support.setWhiteOutline(mRegisterPasswordConfirm);
        Support.setWhiteOutline(mRegisterPhone);
    }

    @Override
    public void setTermsDialog(String title, String text) {
        DialogStandartButton dialog = new DialogStandartButton(
                getActivity(),
                title,
                text,
                null);
        dialog.show();
    }

    @Override
    public void nameError() {
        Support.setRedOutline(mRegisterName);
    }

    @Override
    public void lNameError() {
        Support.setRedOutline(mRegisterLName);
    }

    @Override
    public void emailError() {
        Support.setRedOutline(mRegisterEmail);
    }

    @Override
    public void passwError() {
        Support.setRedOutline(mRegisterPassword);
    }

    @Override
    public void passwConfError() {
        Support.setRedOutline(mRegisterPasswordConfirm);
    }

    @Override
    public void termsError() {

    }

    @Override
    public void onResume(){
        super.onResume();
        Log.e("TAG", "RegisterFragment onResume");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.e("TAG", "RegisterFragment onPause");
    }


    @BindView(R.id.register_name_edittext)
    NameEditText mRegisterName;
    @BindView(R.id.register_lname_edittext)
    NameEditText mRegisterLName;
    @BindView(R.id.register_email_edittext)
    EmailEditText mRegisterEmail;
    @BindView(R.id.register_password_edittext)
    PasswordEditText mRegisterPassword;
    @BindView(R.id.register_password_confirm_edittext)
    PasswordEditText mRegisterPasswordConfirm;
    @BindView(R.id.register_phone_edittext)
    PhoneEditText mRegisterPhone;
    @BindView(R.id.register_button)
    FrameLayout mRegisterButton;
    @BindView(R.id.register_button_text)
    TextView mRegisterButtonText;
    @BindView(R.id.register_checkbox_terms)
    CheckBox mRegisterCheckBox;
    @BindView(R.id.register_terms_title)
    TextView mRegisterTerms;
    @BindView(R.id.register_signin_button)
    TextView mRegisterSignInButton;

}

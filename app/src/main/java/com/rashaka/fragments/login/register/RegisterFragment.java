package com.rashaka.fragments.login.register;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.rashaka.LoginRouter;
import com.rashaka.R;
import com.rashaka.RaApp;
import com.rashaka.system.lang.LangKeys;
import com.rashaka.utils.Consts;
import com.rashaka.utils.Support;
import com.rashaka.utils.dialogs.DialogStandartButton;
import com.rashaka.utils.helpers.structure.SuperFragment;
import com.rashaka.utils.helpers.structure.SuperPresenter;
import com.rashaka.utils.helpers.structure.helpers.Layout;
import com.rashaka.utils.helpers.views.email.EmailEditText;
import com.rashaka.utils.helpers.views.name.NameEditText;
import com.rashaka.utils.helpers.views.passw.PasswordEditText;
import com.rashaka.utils.helpers.views.phone.PhoneEditText;

import butterknife.BindView;
import butterknife.ButterKnife;

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
        myRouter = (LoginRouter) getActivity();
        mPresenter = new RegisterPresenter(myRouter);
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
        mRegisterButton.setOnClickListener(view1 -> mPresenter.onRegisterClick(
                mRegisterName.getText().toString(),
                mRegisterLName.getText().toString(),
                mRegisterEmail.getText().toString(),
                mRegisterPassword.getText().toString(),
                mRegisterPasswordConfirm.getText().toString(),
                mRegisterPhone.getText().toString(),
                mRegisterCheckBox.isChecked()));

        //TODO On Terms & Conditions Dialog
        mRegisterTerms.setOnClickListener(view12 -> mPresenter.onTermsClicked());

        //TODO GO to SignIn Page
        mRegisterSignInButton.setOnClickListener(view13 -> mPresenter.onSignInSelected());

        //TODO Checkbox color setting
        mRegisterCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                Support.setCheckBoxColor(
                        mRegisterCheckBox,
                        android.R.attr.state_checked,
                        getResources().getColor(R.color.background_white)
                );
            } else {
                Support.setCheckBoxColor(
                        mRegisterCheckBox,
                        android.R.attr.state_empty,
                        Color.RED
                );
            }
        });

    }

    @NonNull
    @Override
    public SuperPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void goSign() {
        myRouter.callSignIn(Consts.ANIMATION_LEFT);
    }

    @Override
    public void setViewsValues() {
        mRegisterName.setHint(RaApp.getLabel(LangKeys.key_name));
        mRegisterLName.setHint(RaApp.getLabel(LangKeys.key_last_name));
        mRegisterEmail.setHint(RaApp.getLabel(LangKeys.key_email));
        mRegisterPassword.setHint(RaApp.getLabel(LangKeys.key_password));
        mRegisterPasswordConfirm.setHint(RaApp.getLabel(LangKeys.key_confirm_password));
        mRegisterPhone.setHint(RaApp.getLabel(LangKeys.key_phone_number));
        mRegisterButtonText.setText(RaApp.getLabel(LangKeys.key_register));
        mRegisterTerms.setText(RaApp.getLabel(LangKeys.key_terms_conditions));
        mRegisterSignInButton.setText(RaApp.getLabel(LangKeys.key_signin));
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
        Support.setCheckBoxColor(
                mRegisterCheckBox,
                android.R.attr.state_empty,
                Color.RED
        );
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onPause(){
        super.onPause();
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
    android.support.v7.widget.AppCompatCheckBox mRegisterCheckBox;
    @BindView(R.id.register_terms_title)
    TextView mRegisterTerms;
    @BindView(R.id.register_signin_button)
    TextView mRegisterSignInButton;

}

package com.rashaka.fragments.login.signin;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.rashaka.LoginRouter;
import com.rashaka.R;
import com.rashaka.RaApp;
import com.rashaka.system.lang.LangKeys;
import com.rashaka.utils.Support;
import com.rashaka.utils.helpers.structure.SuperFragment;
import com.rashaka.utils.helpers.structure.SuperPresenter;
import com.rashaka.utils.helpers.structure.helpers.Layout;
import com.rashaka.utils.helpers.views.email.EmailEditText;
import com.rashaka.utils.helpers.views.passw.PasswordEditText;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by User on 17.08.2017.
 */

@Layout(id = R.layout.log_item_signin)
public class SignInFragment extends SuperFragment implements SignInView {

    private LoginRouter myRouter;
    private SignInPresenter mPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myRouter = (LoginRouter) getActivity();
        mPresenter = new SignInPresenter();
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.onStop();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        //TODO SignIn API Call
        mSigninButton.setOnClickListener(view1 -> mPresenter.onSignInClick(
                mSigninEmail.getText().toString(),
                mSigninPassword.getText().toString())
        );

        mRegister.setOnClickListener(view12 -> mPresenter.goRegister());

        mForgotPass.setOnClickListener(view13 -> mPresenter.goForgotPass());

        if(!TextUtils.isEmpty(RaApp.getBase().getLoggedEmail())){
            mSigninEmail.setText(RaApp.getBase().getLoggedEmail());
        }
        //TODO Put Log in data
//        mSigninEmail.setText("nick.oliinyk@gmail.com");
//        mSigninPassword.setText("M1111111");
    }

    @NonNull
    @Override
    public SuperPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void goReg() {
        myRouter.callRegister();
    }

    @Override
    public void goPass() {
        myRouter.callForgotPass();
    }

    @Override
    public void setViewsValues() {
        mSigninEmail.setHint(RaApp.getLabel(LangKeys.key_email));
        mSigninPassword.setHint(RaApp.getLabel(LangKeys.key_password));
        mSigninButtonText.setText(RaApp.getLabel(LangKeys.key_signin));
        mForgotPass.setText(RaApp.getLabel(LangKeys.key_forgot_password));
        mRegister.setText(RaApp.getLabel(LangKeys.key_registration));
    }

    @Override
    public void setViewsOk() {
        Support.setWhiteOutline(mSigninEmail);
        Support.setWhiteOutline(mSigninPassword);
    }

    @BindView(R.id.signin_email_edittext)
    EmailEditText mSigninEmail;

    @BindView(R.id.signin_password_edittext)
    PasswordEditText mSigninPassword;

    @BindView(R.id.signin_button)
    FrameLayout mSigninButton;

    @BindView(R.id.signin_button_text)
    TextView mSigninButtonText;

    @BindView(R.id.signin_forgot_button)
    TextView mForgotPass;

    @BindView(R.id.signin_register_button)
    TextView mRegister;

}
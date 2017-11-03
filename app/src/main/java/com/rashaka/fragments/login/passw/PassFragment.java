package com.rashaka.fragments.login.passw;

import android.content.Context;
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
import com.rashaka.utils.helpers.structure.SuperFragment;
import com.rashaka.utils.helpers.structure.SuperPresenter;
import com.rashaka.utils.helpers.structure.helpers.Layout;
import com.rashaka.utils.helpers.views.email.EmailEditText;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by User on 17.08.2017.
 */

@Layout(id = R.layout.log_item_password)
public class PassFragment extends SuperFragment implements PassView {

    private LoginRouter myRouter;
    private PassPresenter mPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        myRouter = (LoginRouter) getActivity();
        mPresenter = new PassPresenter(myRouter);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        //TODO Call reset password API
        mResetPass.setOnClickListener(view12 -> {
            if(Support.emailOk(mPasswordEmail.getText().toString()))
                mPresenter.onResetPass(mPasswordEmail.getText().toString());
        });

        //TODO Go to SignIn
        mPasswordSignIn.setOnClickListener(view1 -> mPresenter.onGoSignIn());

    }

    @NonNull
    @Override
    public SuperPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void goResetPassword() {
        myRouter.callRegister();
    }

    @Override
    public void goSign() {
        myRouter.callSignIn(Consts.ANIMATION_RIGHT);
    }

    @Override
    public void setViewsValues() {
        mPasswordEmail.setHint(RaApp.getLabel(LangKeys.key_email));
        mPasswordResetText.setText(RaApp.getLabel(LangKeys.key_reset_password));
        mPasswordAddText.setText(RaApp.getLabel(LangKeys.key_forgot_details));
        mPasswordSignIn.setText(RaApp.getLabel(LangKeys.key_signin));
    }

    @BindView(R.id.password_email_edittext)
    EmailEditText mPasswordEmail;

    @BindView(R.id.password_add_text)
    TextView mPasswordAddText;

    @BindView(R.id.password_email_reset_button)
    FrameLayout mResetPass;

    @BindView(R.id.password_email_reset_button_text)
    TextView mPasswordResetText;

    @BindView(R.id.password_register_button)
    TextView mPasswordSignIn;

}

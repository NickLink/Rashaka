package rashakacom.rashaka.fragments.login.passw;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import rashakacom.rashaka.LoginRouter;
import rashakacom.rashaka.R;
import rashakacom.rashaka.RaApp;
import rashakacom.rashaka.utils.helpers.structure.SuperFragment;
import rashakacom.rashaka.utils.helpers.structure.SuperPresenter;
import rashakacom.rashaka.utils.helpers.structure.helpers.Layout;
import rashakacom.rashaka.utils.helpers.views.email.EmailEditText;

import static rashakacom.rashaka.utils.Consts.ANIMATION_RIGHT;
import static rashakacom.rashaka.utils.Support.emailOk;

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
        mPresenter = new PassPresenter();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        //TODO Call reset password API
        mResetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(emailOk(mPasswordEmail.getText().toString()))
                    mPresenter.onResetPass(mPasswordEmail.getText().toString());
            }
        });

        //TODO Go to SignIn
        mPasswordSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.onGoSignIn();
            }
        });

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
        myRouter.callSignIn(ANIMATION_RIGHT);
    }

    @Override
    public void setViewsValues() {
        mPasswordEmail.setHint(RaApp.getLabel("key_email"));
        //TODO MISSING VALUES for key_reset_password & key_add_email
        //mPasswordResetText.setText(RaApp.getLabel("key_reset_password"));
        //mPasswordAddText.setText(RaApp.getLabel("key_add_email"));
        mPasswordSignIn.setText(RaApp.getLabel("key_signin"));
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
